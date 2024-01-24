package com.abm.mainet.legal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.annotation.Resource;
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
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.legal.dao.IAdvocateMasterDAO;
import com.abm.mainet.legal.domain.AdvocateEducationDetails;
import com.abm.mainet.legal.domain.AdvocateMaster;
import com.abm.mainet.legal.domain.AdvocateMasterHistory;
import com.abm.mainet.legal.dto.AdvocateEducationDetailDTO;
import com.abm.mainet.legal.dto.AdvocateMasterDTO;
import com.abm.mainet.legal.repository.AdvocateMasterRepository;

@Service
@Produces(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Consumes(value = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@WebService(endpointInterface = "com.abm.mainet.legal.service.IAdvocateMasterService")
@Path(value = "/advocatemasterservice")
public class AdvocateMasterService implements IAdvocateMasterService {

    @Autowired
    private AdvocateMasterRepository advocateMasterRepository;

    @Autowired
    private AuditService auditService;
    
    @Autowired
    private  IAdvocateMasterDAO advocateMasterDAO;
    
    @Resource
    private ApplicationService applicationService;
    
	@Autowired
	ServiceMasterService serviceMasterService;
	
	@Autowired
	private DepartmentService departmentService;
 
	@Resource
	private CommonService commonService;
	
	
    @Override
    @Transactional
    @POST
    @Path(value = "/save")
    public void saveAdvocateMaster(@RequestBody AdvocateMasterDTO advocateMasterDto) {
        AdvocateMaster advocateMaster = new AdvocateMaster();
        BeanUtils.copyProperties(advocateMasterDto, advocateMaster);

        advocateMasterRepository.save(advocateMaster);

        AdvocateMasterHistory history = new AdvocateMasterHistory();
        history.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
        auditService.createHistory(advocateMaster, history);

    }
   //#88473
    @Override
    @Transactional
    @POST
    @Path(value = "/saveAdvocateMaster")
    public AdvocateMasterDTO saveAdvocateMasterData(@RequestBody AdvocateMasterDTO advocateMasterDto) {
        AdvocateMaster advocateMaster = new AdvocateMaster();
        List<AdvocateEducationDetails> advocateEducationDetailsList = new ArrayList<>();
        final ServiceMaster service = serviceMasterService.getServiceByShortName("ADS",
        		advocateMasterDto.getOrgid());
		RequestDTO requestDto = setApplicantRequestDto(advocateMasterDto, service);
        
        Long applicationNo = applicationService.createApplication(requestDto);
        advocateMasterDto.setApplicationId(applicationNo);
        
        BeanUtils.copyProperties(advocateMasterDto, advocateMaster);
        //#139885
        if (CollectionUtils.isNotEmpty(advocateMasterDto.getAdvEducationDetailDTOList())) {
        advocateMasterDto.getAdvEducationDetailDTOList().forEach(list -> {
			AdvocateEducationDetails advEduDetEntity = new AdvocateEducationDetails();
			BeanUtils.copyProperties(list, advEduDetEntity);
			advEduDetEntity.setTbLglAdvMaster(advocateMaster);
			advocateEducationDetailsList.add(advEduDetEntity);
		});
        advocateMaster.setAdvocateEducationDetails(advocateEducationDetailsList);
        }
        advocateMasterRepository.save(advocateMaster);

      
        AdvocateMasterHistory history = new AdvocateMasterHistory();
        history.sethStatus(MainetConstants.InsertMode.ADD.getStatus());
        auditService.createHistory(advocateMaster, history);
        
        ApplicationMetadata applicationData = new ApplicationMetadata();

		ApplicantDetailDTO applicantDetailDTO = new ApplicantDetailDTO();

		applicationData.setApplicationId(advocateMaster.getApplicationId());
		applicationData.setOrgId(advocateMaster.getOrgid());
		applicationData.setIsCheckListApplicable(false);
		applicantDetailDTO.setUserId(advocateMaster.getCreatedBy());
		applicantDetailDTO.setServiceId(service.getSmServiceId());
		applicantDetailDTO.setDepartmentId(departmentService.getDepartmentIdByDeptCode("LEGL"));
		commonService.initiateWorkflowfreeService(applicationData, applicantDetailDTO);
		return advocateMasterDto;

    }

    private RequestDTO setApplicantRequestDto(AdvocateMasterDTO advocateMasterDto, ServiceMaster sm) {
    	RequestDTO requestDto = new RequestDTO();
		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(advocateMasterDto.getCreatedBy());
		requestDto.setOrgId(advocateMasterDto.getOrgid());
		requestDto.setLangId((long) advocateMasterDto.getLangId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		// setting applicant info
		requestDto.setfName(advocateMasterDto.getAdvFirstNm());
		requestDto.setmName(advocateMasterDto.getAdvMiddleNm());
		requestDto.setlName(advocateMasterDto.getAdvLastNm());
		requestDto.setEmail(advocateMasterDto.getAdvEmail());
		requestDto.setMobileNo(advocateMasterDto.getAdvMobile());
		requestDto.setAreaName(advocateMasterDto.getAdvAddress());
		requestDto.setFree(true);
		return requestDto;
	}


	@Override
    @Transactional
    @POST
    @Path(value = "/update")
    public void updateAdvocateMaster(@RequestBody AdvocateMasterDTO advocateMasterDto) {
        AdvocateMaster advocateMaster = new AdvocateMaster();
        BeanUtils.copyProperties(advocateMasterDto, advocateMaster);

        advocateMasterRepository.save(advocateMaster);

        AdvocateMasterHistory history = new AdvocateMasterHistory();
        history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(advocateMaster, history);

    }

    @Override
    @Transactional
    @WebMethod(exclude = true)
    public boolean deleteAdvocateMaster(AdvocateMasterDTO advocateMasterDto) {
        AdvocateMaster AdvocateMaster = advocateMasterRepository.findOne(advocateMasterDto.getAdvId());
        if (AdvocateMaster == null)
            return false;

        advocateMasterRepository.delete(advocateMasterDto.getAdvId());

        AdvocateMasterHistory history = new AdvocateMasterHistory();
        history.sethStatus(MainetConstants.InsertMode.UPDATE.getStatus());
        auditService.createHistory(AdvocateMaster, history);

        return true;
    }

    @Transactional(readOnly = true)
    @Override
    @GET
    @Path(value = "/getAll/{orgId}")
    public List<AdvocateMasterDTO> getAllAdvocateMaster(@PathParam("orgId") Long orgId) {
        List<AdvocateMasterDTO> activeUserListDTOs = StreamSupport
                .stream(advocateMasterRepository.findByOrgid(orgId).spliterator(), false).map(entity -> {
                    AdvocateMasterDTO dto = new AdvocateMasterDTO();
                    BeanUtils.copyProperties(entity, dto);
                    return dto;
                })
                .collect(Collectors.toList());
        return activeUserListDTOs;
    }

    @Transactional(readOnly = true)
    @Override
    @GET
    @Path(value = "/getAllAdvocate/{orgid}")
    public List<AdvocateMasterDTO> getAllAdvocateMasterByOrgid(@PathParam("orgid") Long orgid) {
        List<AdvocateMasterDTO> activeUserListDTOs = StreamSupport
                .stream(advocateMasterRepository.findByOrgidAndAdvStatus(orgid, MainetConstants.Y_FLAG).spliterator(), false)
                .map(entity -> {
                    AdvocateMasterDTO dto = new AdvocateMasterDTO();
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
    public AdvocateMasterDTO getAdvocateMasterById(@PathParam("id") Long id) {
        AdvocateMaster advocateMaster = advocateMasterRepository.findOne(id);
        AdvocateMasterDTO advocateMasterDto = new AdvocateMasterDTO();
        BeanUtils.copyProperties(advocateMaster, advocateMasterDto);
        return advocateMasterDto;
    }

    //Defect #105841
    @Override
    @Transactional(readOnly = true)
    @POST
    @Path(value = "/validateAdvocateMaster")
    public List<AdvocateMasterDTO> validateAdvocateMaster(@RequestBody AdvocateMasterDTO advocateMasterDTO) {
    	List<AdvocateMasterDTO> advocateMasterDTOList= new ArrayList<>();
        IAdvocateMasterDAO advocateMasterDAO = (IAdvocateMasterDAO) ApplicationContextProvider.getApplicationContext()
                .getBean("advocateMasterDAO");
        List<AdvocateMaster> advocateMasterList = advocateMasterDAO.validateAdvocate(advocateMasterDTO.getAdvMobile(),
                advocateMasterDTO.getAdvEmail(),
                advocateMasterDTO.getAdvPanno(), advocateMasterDTO.getAdvUid(), advocateMasterDTO.getAdvId(),
                advocateMasterDTO.getOrgid());
        advocateMasterList.forEach(entity->{
  		  AdvocateMasterDTO dto = new AdvocateMasterDTO();
  		  BeanUtils.copyProperties(entity, dto);
  		  advocateMasterDTOList.add(dto);
  	  });
        return advocateMasterDTOList;
    }

    //127193
    @Transactional(readOnly = true)
	@Override
    public List<AdvocateMasterDTO> getAdvocateMasterDetails(Long advId, Long crtId, String barCouncilNo, String advStatus, Long orgId) {
		List<AdvocateMasterDTO> list = new ArrayList<>();
		List<AdvocateMaster> entity = advocateMasterDAO.findDetails(advId, crtId, barCouncilNo, advStatus, orgId);
		if (CollectionUtils.isNotEmpty(entity))
		for (AdvocateMaster advocateMaster : entity) {
			AdvocateMasterDTO dto = new AdvocateMasterDTO();
			BeanUtils.copyProperties(advocateMaster, dto);
			list.add(dto);
		}
		return list;
	}

    //#88473
	@Override
	@Transactional(readOnly = true)
	@POST
	@Path(value = "/getAdvDetByAppId/{orgid}/{applicationId}")
	public AdvocateMasterDTO findAdvDetByAppId(@PathParam("orgid") Long orgid,
			@PathParam("applicationId") Long applicationId) {
		AdvocateMaster advocateMaster = advocateMasterRepository.findByOrgidAndApplicationId(orgid, applicationId);
		AdvocateMasterDTO advocateMasterDto = new AdvocateMasterDTO();
		BeanUtils.copyProperties(advocateMaster, advocateMasterDto);
		return advocateMasterDto;
	}

	@Override
	@Transactional
	public void saveDetails(AdvocateMasterDTO advocateMasterDTO) {
		AdvocateMaster entity = new AdvocateMaster();
		BeanUtils.copyProperties(advocateMasterDTO, entity);
		advocateMasterRepository.save(entity);
	}
	
	@Override
	@Transactional
	public List<AdvocateEducationDetailDTO> findEducationDetById(Long advId, Long orgid) {
		List<AdvocateEducationDetailDTO> advEducationDetailDTOList = new ArrayList<>();
		List<AdvocateEducationDetails> entityList = advocateMasterRepository.findEducationDetById(advId,orgid);
	    if (CollectionUtils.isNotEmpty(entityList)) {
		for (AdvocateEducationDetails list : entityList) {
			AdvocateEducationDetailDTO dto = new AdvocateEducationDetailDTO();
			BeanUtils.copyProperties(list, dto);
			advEducationDetailDTOList.add(dto);
		 }
	    }
		return advEducationDetailDTOList;
	}
}
