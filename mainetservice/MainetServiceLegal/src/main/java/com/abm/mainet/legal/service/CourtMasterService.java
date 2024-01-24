package com.abm.mainet.legal.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.legal.dao.CaseEntryDAO;
import com.abm.mainet.legal.dao.ICaseEntryDAO;
import com.abm.mainet.legal.domain.CourtMaster;
import com.abm.mainet.legal.domain.CourtMasterHistory;
import com.abm.mainet.legal.dto.CourtMasterDTO;
import com.abm.mainet.legal.repository.CourtMasterRepository;

/**
 * @author israt.ali
 *
 */
/**
 * @author israt.ali
 *
 */
@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.legal.service.ICourtMasterService")
@Path(value = "/courtmasterservice")
public class CourtMasterService implements ICourtMasterService {

    @Autowired
    private CourtMasterRepository courtMasterRepository;

    @Autowired
    private AuditService auditService;

    @Autowired
    private  ICaseEntryDAO caseEntryDAO;
    
    @Override
    @Transactional
    @POST
    @Path(value = "/save")
    public void saveCourtMaster(CourtMasterDTO courtMasterDto) {

        CourtMaster courtMaster = new CourtMaster();
        BeanUtils.copyProperties(courtMasterDto, courtMaster);
        courtMaster.setCrtStatus(MainetConstants.Common_Constant.YES);
        
        if(courtMasterDto.getCseTypId() != null && !courtMasterDto.getCseTypId().isEmpty()) {
        	courtMaster.setCseTypId(String.join(",", courtMasterDto.getCseTypId()));
        }
        courtMasterRepository.save(courtMaster);

        CourtMasterHistory history = new CourtMasterHistory();
        history.setStatus(MainetConstants.InsertMode.ADD.getStatus());
        auditService.createHistory(courtMaster, history);
    }

    @Override
    @Transactional
    @POST
    @Path(value = "/update")
    public void updateCourtMaster(CourtMasterDTO courtMasterDto) {

        CourtMaster courtMaster = new CourtMaster();
        BeanUtils.copyProperties(courtMasterDto, courtMaster);
        
        if(courtMasterDto.getCseTypId() != null && !courtMasterDto.getCseTypId().isEmpty()) {
        	courtMaster.setCseTypId(String.join(",", courtMasterDto.getCseTypId()));
        }
         CourtMaster master =courtMasterRepository.save(courtMaster);

        CourtMasterHistory history = new CourtMasterHistory();
        history.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(master, history);
    }

    /**
     * @author israt.ali
     * @return
     */
    // this method is use only to distinct list by using their object attributes
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

	@Override
	@WebMethod(exclude = true)
	public List<CourtMasterDTO> getAllActiveCourtMaster(Long orgid) {
		//Long parentOrgId = ApplicationContextProvider.getApplicationContext().getBean(IOrganisationService.class).getSuperUserOrganisation().getOrgid();
		List<CourtMasterDTO> activeUserListDTOs = StreamSupport
				.stream(courtMasterRepository
						.findByOrgId( orgid)
						.spliterator(), false)
				.filter(distinctByKey(CourtMaster::getCrtName))// distinct by object attributes(CRT_NAME)
				.map(entity -> {
					CourtMasterDTO dto = new CourtMasterDTO();
					BeanUtils.copyProperties(entity, dto);
					return dto;
				}).collect(Collectors.toList());
		return activeUserListDTOs;
	}

    @Override
    @WebMethod(exclude = true)
    public List<CourtMasterDTO> getAllCourtMaster(Long orgid) {
        List<CourtMasterDTO> activeUserListDTOs = StreamSupport
                .stream(courtMasterRepository.findByOrgId(orgid).spliterator(), false).map(entity -> {
                    CourtMasterDTO dto = new CourtMasterDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
        return activeUserListDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    @GET
    @Path(value = "/get/{id}")
    public CourtMasterDTO getCourtMasterById(Long id) {
        CourtMaster courtMaster = courtMasterRepository.findOne(id);
        CourtMasterDTO dto = new CourtMasterDTO();
        BeanUtils.copyProperties(courtMaster, dto);
        
        if(courtMaster.getCseTypId() != null && courtMaster.getCseTypId().length() > 0) {
        	dto.setCseTypId(new ArrayList<String>(Arrays.asList(courtMaster.getCseTypId().split(","))));
        }
        return dto;
    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean deleteCourtMaster(CourtMasterDTO courtMasterDto) {
        CourtMaster courtMaster = courtMasterRepository.findOne(courtMasterDto.getId());
        if (courtMaster == null)
            return false;
        courtMaster.setUpdatedBy(courtMasterDto.getUpdatedBy());
        courtMaster.setOrgId(courtMasterDto.getOrgId());
        courtMaster.setUpdatedDate(courtMasterDto.getUpdatedDate());
        courtMaster.setLgIpMacUpd(courtMasterDto.getLgIpMacUpd());
        courtMaster.setCrtStatus(MainetConstants.Common_Constant.NO);
        courtMasterRepository.save(courtMaster);

        CourtMasterHistory history = new CourtMasterHistory();
        history.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(courtMaster, history);
        return true;
    }

	@Transactional(readOnly = true)
	@Override
	@WebMethod(exclude = true)
	public String getCourtNameById(Long id) {
		String courtName = courtMasterRepository.findCrtNameById(id);
		return courtName;
	}

    //127193
	@Override
	public List<CourtMasterDTO> getCourtMasterDetailByIds(Long crtId, Long crtType, Long orgid) {
		List<CourtMasterDTO> courtMstDtoList = new ArrayList<>();
		List<CourtMaster> courtMasEntity = caseEntryDAO.findCourtDetailsByIds(crtId,crtType,orgid);
		if (CollectionUtils.isNotEmpty(courtMasEntity))
		for (CourtMaster courtMaster : courtMasEntity) {
			CourtMasterDTO dto = new CourtMasterDTO();
			BeanUtils.copyProperties(courtMaster, dto);
			courtMstDtoList.add(dto);
		}
		return courtMstDtoList;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<Long> getCaseTypeByCourtId(Long crtId, Long orgId) {
		String caseTypeId = courtMasterRepository.getCaseTypeId(crtId, orgId);
		List<Long> cseTypId = new ArrayList<>();
		
		if (StringUtils.isNotBlank(caseTypeId)) {
			List<String> csetyId = new ArrayList<String>(Arrays.asList(caseTypeId.split(",")));
			for(String str : csetyId) {
				cseTypId.add(Long.parseLong(str));
			}
		}
		return cseTypId;
	}

}
