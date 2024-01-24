package com.abm.mainet.legal.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.legal.dao.ICaseEntryDAO;
import com.abm.mainet.legal.domain.CourtMaster;
import com.abm.mainet.legal.domain.JudgeDetailMaster;
import com.abm.mainet.legal.domain.JudgeDetailMasterHistory;
import com.abm.mainet.legal.domain.JudgeMaster;
import com.abm.mainet.legal.domain.JudgeMasterHistory;
import com.abm.mainet.legal.dto.CaseEntryDTO;
import com.abm.mainet.legal.dto.JudgeDetailMasterDTO;
import com.abm.mainet.legal.dto.JudgeMasterDTO;
import com.abm.mainet.legal.repository.CourtMasterRepository;
import com.abm.mainet.legal.repository.JudgeDetailRepository;
import com.abm.mainet.legal.repository.JudgeMasterRepository;

@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.legal.service.IJudgeMasterService")
@Path(value = "/judgemasterservice")
public class JudgeMasterService implements IJudgeMasterService {
    @Autowired
    private JudgeMasterRepository judgeMasterRepository;

    @Autowired
    private CourtMasterRepository courtMasterRepository;

    @Autowired
    private AuditService auditService;

    @Autowired
    private JudgeDetailRepository judgeDetailRepository;


    @Autowired
	TbOrganisationService  tbOrganisationService;
    
    @Autowired
    ICaseEntryService caseEntryservice;
    
    @Autowired
    private  ICaseEntryDAO caseEntryDAO;

    @Override
    @Transactional
    @POST
    @Path(value = "/save")
    public void saveJudgeMaster(@RequestBody JudgeMasterDTO judgeMasterDto) {
        JudgeMaster judgeMaster = new JudgeMaster();
        BeanUtils.copyProperties(judgeMasterDto, judgeMaster);

        List<JudgeDetailMaster> judgeDetails = new ArrayList<>();
        judgeMasterDto.getJudgeDetails().forEach(j -> {
            JudgeDetailMaster entity = new JudgeDetailMaster();
            BeanUtils.copyProperties(j, entity);
            CourtMaster court = courtMasterRepository.findOne(j.getCrtId());
            entity.setCourt(court);
            entity.setJudge(judgeMaster);
            entity.setCreateDate(judgeMasterDto.getCreateDate());
            entity.setCreatedBy(judgeMasterDto.getCreatedBy());
            entity.setLgIpMac(judgeMasterDto.getLgIpMac());
            entity.setOrgId(judgeMasterDto.getOrgId());
            judgeDetails.add(entity);
        });

        judgeMaster.setJudgeDetails(judgeDetails);
        judgeMasterRepository.save(judgeMaster);

        JudgeMasterHistory history = new JudgeMasterHistory();
        history.setStatus(MainetConstants.InsertMode.ADD.getStatus());
        auditService.createHistory(judgeMaster, history);

        judgeMaster.getJudgeDetails().forEach(judgeDetail -> {
            JudgeDetailMasterHistory detailsHistory = new JudgeDetailMasterHistory();
            detailsHistory.setStatus(MainetConstants.InsertMode.ADD.getStatus());
            auditService.createHistory(judgeDetail, detailsHistory);
        });

    }

    @Override
    @Transactional
    @POST
    @Path(value = "/update")
    public void updateJudgeMaster(@RequestBody JudgeMasterDTO judgeMasterDto) {
        JudgeMaster judgeMaster = new JudgeMaster();
        BeanUtils.copyProperties(judgeMasterDto, judgeMaster);

        List<JudgeDetailMaster> judgeDetails = new ArrayList<>();
        judgeMasterDto.getJudgeDetails().forEach(j -> {
            JudgeDetailMaster entity = new JudgeDetailMaster();
            BeanUtils.copyProperties(j, entity);
            CourtMaster court = courtMasterRepository.findOne(j.getCrtId());
            entity.setCourt(court);
            entity.setJudge(judgeMaster);
            entity.setOrgId(judgeMasterDto.getOrgId());
            if (j.getId() == null) {
                entity.setCreateDate(judgeMasterDto.getUpdatedDate());
                entity.setCreatedBy(judgeMasterDto.getUpdatedBy());
                entity.setLgIpMac(judgeMasterDto.getLgIpMac());
            } else {
                entity.setUpdatedDate(judgeMasterDto.getUpdatedDate());
                entity.setUpdatedBy(judgeMasterDto.getUpdatedBy());
                entity.setLgIpMacUpd(judgeMasterDto.getLgIpMacUpd());
            }
            judgeDetails.add(entity);
        });
        judgeMaster.setJudgeDetails(judgeDetails);
        judgeMasterRepository.save(judgeMaster);

        JudgeMasterHistory history = new JudgeMasterHistory();
        history.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(judgeMaster, history);

        judgeMaster.getJudgeDetails().forEach(judgeDetail -> {
            JudgeDetailMasterHistory detailsHistory = new JudgeDetailMasterHistory();
            detailsHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            auditService.createHistory(judgeDetail, detailsHistory);
        });

    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean deleteJudgeMaster(JudgeMasterDTO judgeMasterDto) {
        JudgeMaster judgeMaster = judgeMasterRepository.findOne(judgeMasterDto.getId());
        if (judgeMaster == null)
            return false;

        judgeMasterRepository.delete(judgeMasterDto.getId());

        JudgeMasterHistory history = new JudgeMasterHistory();
        history.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(judgeMaster, history);

        judgeMaster.getJudgeDetails().forEach(judgeDetail -> {
            JudgeDetailMasterHistory detailsHistory = new JudgeDetailMasterHistory();
            detailsHistory.setStatus(MainetConstants.InsertMode.UPDATE.getStatus());
            auditService.createHistory(judgeDetail, detailsHistory);
        });

        return true;
    }

    @Transactional(readOnly = true)
    @Override
    @GET
    @Path(value = "/getAll/{orgId}")
    public List<JudgeMasterDTO> getAllJudgeMaster(@PathParam("orgId") Long orgId) {
        List<JudgeMasterDTO> activeUserListDTOs = StreamSupport
                .stream(judgeMasterRepository.findByOrgId(orgId).spliterator(), false).map(entity -> {
                    JudgeMasterDTO dto = new JudgeMasterDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                }).collect(Collectors.toList());
        return activeUserListDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    @GET
    @Path(value = "/get/{id}")
    public JudgeMasterDTO getJudgeMasterById(@PathParam("id") Long id) {
        JudgeMaster judgeMaster = judgeMasterRepository.findOne(id);
        JudgeMasterDTO judgeMasterdto = new JudgeMasterDTO();
        List<JudgeDetailMasterDTO> judgeDetails = new ArrayList<>();
        BeanUtils.copyProperties(judgeMaster, judgeMasterdto);
        judgeMaster.getJudgeDetails().forEach(j -> {
            JudgeDetailMasterDTO dto = new JudgeDetailMasterDTO();
            BeanUtils.copyProperties(j, dto);
            dto.setCrtType(j.getCourt().getCrtType());
            dto.setCrtId(j.getCourt().getId());
            dto.setCrtAddress(j.getCourt().getCrtAddress());
            judgeDetails.add(dto);
        });
        judgeMasterdto.setJudgeDetails(judgeDetails);
        return judgeMasterdto;
    }

    @Transactional
    public List<JudgeDetailMaster> fetchJudgeDetailsByCrtId(Long crtId, Long cseId, Long orgId ) {
        Organisation org = tbOrganisationService.findDefaultOrganisation();
        if(orgId.equals(org.getOrgid())) {
        	CaseEntryDTO caseEntryById = caseEntryservice.getCaseEntryById(cseId);       	
        	CourtMaster court = new CourtMaster();
            court.setId(crtId);
            List<JudgeDetailMaster> judges = judgeDetailRepository.findByCourtId(court, caseEntryById.getConcernedUlb());
            return judges;
        }else {
        CourtMaster court = new CourtMaster();
        court.setId(crtId);
        List<JudgeDetailMaster> judges = judgeDetailRepository.findByCourtId(court, orgId);
        return judges;
        }
    }

    //127193
	@Override
	public List<JudgeMasterDTO> getjudgeDetails(Long crtId, String judgeStatus, String judgeBenchName, Long orgid) {
		List<JudgeMasterDTO> judgeMasterDto = new ArrayList<>();
		List<JudgeMaster> judgeMasterEntity = caseEntryDAO.findJudgeDetails(crtId,judgeStatus,judgeBenchName,orgid);
		if(CollectionUtils.isNotEmpty(judgeMasterEntity)) {
			Set<Long> visitedJudgeId = new HashSet<>();	
			for (JudgeMaster judgeMaster : judgeMasterEntity) {
				JudgeMasterDTO dto = new JudgeMasterDTO();
				if(!visitedJudgeId.contains(judgeMaster.getId())) {
					BeanUtils.copyProperties(judgeMaster, dto);
					judgeMasterDto.add(dto);
					visitedJudgeId.add(judgeMaster.getId());
				}
				
			}
		}
		
		return judgeMasterDto;
	}

}
