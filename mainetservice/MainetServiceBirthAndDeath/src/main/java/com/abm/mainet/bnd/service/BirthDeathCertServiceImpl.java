package com.abm.mainet.bnd.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.repository.BirthCertificateRepository;
import com.abm.mainet.cfc.challan.domain.ChallanMaster;
import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.audit.service.AuditService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbServicesMst;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.master.service.TbServicesMstService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ICFCApplicationMasterService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author rahul.chaubey
 * 
 */

@Produces({ "application/xml", "application/json" })
@WebService(endpointInterface = "com.abm.mainet.bnd.service.BirthDeathCertServiceImpl")
@Api(value = "/BirthDeathCertService")
@Path("/BirthDeathCertService")
@Service(value = "DrainageConnectionServices")
public class BirthDeathCertServiceImpl implements birthDeathCertService {

	private static final Logger logger = Logger.getLogger(birthDeathCertService.class);
	@Autowired
	TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
	@Autowired
	private GroupMasterService groupMasterService;

	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	DepartmentService departmentService;

	@Autowired
	private TbServicesMstService tbServicesMstService;

	@Autowired
	private ICFCApplicationMasterService cfcService;


	@Autowired
	BirthCertificateRepository birthCerRepro;


	@Override
	public Long saveApplicationData(RequestDTO requestDTO) {
		Long applicationId = 0l;
		return applicationId = applicationService.createApplication(requestDTO);
	}

	/*@Override
	public List<Long> getApplicationNo(Long orgId, Long serviceId) {

		List<TbCfcApplicationMstEntity> applicationNo = drainageConnectionRepository.loadSummaryData(orgId, serviceId);
		List<Long> appNo = new ArrayList<Long>();
		applicationNo.forEach(no -> {
			appNo.add(no.getApmApplicationId());

		});
		return appNo;

	}*/

	@Override
	public List<RequestDTO> loadSummaryData(Long orgId, String deptCode) {
		Long deptId = departmentService.getDepartmentIdByDeptCode(deptCode);
		List<TbServicesMst> services = tbServicesMstService.findALlActiveServiceByDeptId(deptId, orgId);
		List<Long> rtsServiceIds = services.stream().map(TbServicesMst::getSmServiceId).collect(Collectors.toList());
		List<TbCfcApplicationMstEntity> entities = cfcService.fetchCfcApplicationsByServiceIds(rtsServiceIds, orgId);
		List<RequestDTO> summaryData = new ArrayList<RequestDTO>();
		for (TbCfcApplicationMstEntity entity : entities) {
			RequestDTO dto = new RequestDTO();
			dto.setTitleId(entity.getApmTitle());
			dto.setfName(entity.getApmFname());
			dto.setmName(entity.getApmMname());
			dto.setlName(entity.getApmLname());
			dto.setGender(entity.getApmSex());
			dto.setServiceId(entity.getTbServicesMst().getSmServiceId());
			dto.setApplicationId(entity.getApmApplicationId());
			dto.setServiceShortCode(tbServicesMstService.getServiceNameByServiceId(dto.getServiceId()));
			summaryData.add(dto);
		}

		return summaryData;
	}

	/*@Override
	@POST
	@ApiOperation(value = "fetch first appeal data by application no", notes = "fetch first appeal data by application no", response = RequestDTO.class)
	@Path("/getAppealData/{applicationId}/{orgId}")
	public RequestDTO getApplicationFormData(
			@ApiParam(value = "applicationId", required = true) @PathParam("applicationId") Long applicationId,
			@ApiParam(value = "orgId", required = true) @PathParam("orgId") Long orgId) {

		RequestDTO requestDto = new RequestDTO();

		TbCfcApplicationMstEntity mstEntity = cfcService.getCFCApplicationByApplicationId(applicationId, orgId);
		CFCApplicationAddressEntity adrEntity = drainageConnectionRepository.getApplicationAddressData(orgId,
				applicationId);

		requestDto.setApplicationId(mstEntity.getApmApplicationId());

		requestDto.setTitleId(mstEntity.getApmTitle());
		requestDto.setfName(mstEntity.getApmFname());
		requestDto.setmName(mstEntity.getApmMname());
		requestDto.setlName(mstEntity.getApmLname());
		requestDto.setGender(mstEntity.getApmSex());

		requestDto.setCityName(adrEntity.getApaCityName());
		requestDto.setBldgName(adrEntity.getApaBldgnm());
		requestDto.setBlockName(adrEntity.getApaBlockName());
		requestDto.setRoadName(adrEntity.getApaRoadnm());
		requestDto.setWardNo(adrEntity.getApaWardNo());
		requestDto.setPincodeNo(adrEntity.getApaPincode());
		requestDto.setMobileNo(adrEntity.getApaMobilno());
		requestDto.setEmail(adrEntity.getApaEmail());
		mstEntity.getTbServicesMst();

		requestDto.setServiceId(mstEntity.getTbServicesMst().getSmServiceId());
		requestDto.setServiceShortCode(mstEntity.getTbServicesMst().getSmServiceName());

		return requestDto;
	}
*/

	
	@Override
	@POST
	@ApiOperation(value = "get department", notes = "get department")
	@Path("/getDept/{orgId}")
	@Transactional
	public Map<Long, String> getDept(@PathParam("orgId") Long orgId) {
		// TODO Auto-generated method stub
		final Map<Long, String> deptMap = new LinkedHashMap<>(0);
		List<Object[]> department = null;
		department = departmentService.getAllDeptTypeNames();
		for (final Object[] dep : department) {
			if (dep[0] != null) {
				deptMap.put((Long) (dep[0]), (String) dep[1]);
			}
		}
		return deptMap;
	}

	@Override
	@POST
	@ApiOperation(value = "get service", notes = "get service")
	@Path("/getService/{orgId}/{deptId}/{activeStatus}")
	@Transactional
	public Map<Long, String> getService(@PathParam("orgId") Long orgId, @PathParam("deptId") Long deptId,
			@PathParam("activeStatus") String activeStatus) {

		List<Object[]> service = null;

		final Map<Long, String> serviceMap = new LinkedHashMap<Long, String>();
		service = ApplicationContextProvider.getApplicationContext().getBean(ServiceMasterService.class)
				.findAllActiveServicesForDepartment(orgId, deptId);

		for (final Object[] obj : service) {
			if (obj[0] != null) {
				serviceMap.put((Long) (obj[0]), (String) (obj[1]));
			}
		}
		return serviceMap;
	}

	/*@Override
	public List<RequestDTO> searchData(Long applicationId, Long serviceId, Long orgId) {

		List<RequestDTO> requestDTOList = new ArrayList<RequestDTO>();
		RequestDTO dto;

		ServiceMaster serviceMasterData = serviceMaster.getServiceMaster(serviceId, orgId);
		List<TbCfcApplicationMstEntity> mstData = drainageConnectionServiceDAO.searchData(applicationId, serviceId,
				orgId);
		String serviceName = serviceMasterData.getSmServiceName();
		for (TbCfcApplicationMstEntity data : mstData) {
			dto = new RequestDTO();
			dto.setfName(data.getApmFname());
			dto.setlName(data.getApmLname());
			dto.setApplicationId(data.getApmApplicationId());
			dto.setServiceShortCode(serviceName);

			requestDTOList.add(dto);
		}

		return requestDTOList;
	}*/

	
	@Override
	@Transactional
	public Boolean getEmployeeRole(UserSession ses) {
		@SuppressWarnings("deprecation")
		LookUp lookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(BndConstants.ROLE_CODE,
				BndConstants.DESIGN_PRIFIX, ses.getLanguageId());
		GroupMaster groupMaster = groupMasterService.findByGmId(ses.getEmployee().getGmid(),
				ses.getOrganisation().getOrgid());
		boolean checkFinalAproval = false;
		if (lookup.getLookUpCode().equalsIgnoreCase(groupMaster.getGrCode())) {
			checkFinalAproval = true;
		}
		return checkFinalAproval;
	}
	
	@Override
	public List<Long> getApplicationNo(Long orgId, Long serviceId) {

		List<TbCfcApplicationMstEntity> applicationNo = birthCerRepro.loadSummaryData(orgId, serviceId);
		List<Long> appNo = new ArrayList<Long>();
		applicationNo.forEach(no -> {
			appNo.add(no.getApmApplicationId());

		});
		return appNo;

	}
	
	@Override
	@POST
	@ApiOperation(value = "fetch first appeal data by application no", notes = "fetch first appeal data by application no", response = RequestDTO.class)
	@Path("/getAppealData/{applicationId}/{orgId}")
	public RequestDTO getApplicationFormData(
			@ApiParam(value = "applicationId", required = true) @PathParam("applicationId") Long applicationId,
			@ApiParam(value = "orgId", required = true) @PathParam("orgId") Long orgId) {

		RequestDTO requestDto = new RequestDTO();

		TbCfcApplicationMstEntity mstEntity = cfcService.getCFCApplicationByApplicationId(applicationId, orgId);
		CFCApplicationAddressEntity adrEntity = birthCerRepro.getApplicationAddressData(orgId,
				applicationId);

		requestDto.setApplicationId(mstEntity.getApmApplicationId());

		requestDto.setTitleId(mstEntity.getApmTitle());
		requestDto.setfName(mstEntity.getApmFname());
		requestDto.setmName(mstEntity.getApmMname());
		requestDto.setlName(mstEntity.getApmLname());
		requestDto.setGender(mstEntity.getApmSex());

		requestDto.setCityName(adrEntity.getApaCityName());
		requestDto.setBldgName(adrEntity.getApaBldgnm());
		requestDto.setBlockName(adrEntity.getApaBlockName());
		requestDto.setRoadName(adrEntity.getApaRoadnm());
		requestDto.setWardNo(adrEntity.getApaWardNo());
		requestDto.setPincodeNo(adrEntity.getApaPincode());
		requestDto.setMobileNo(adrEntity.getApaMobilno());
		requestDto.setEmail(adrEntity.getApaEmail());
		mstEntity.getTbServicesMst();

		requestDto.setServiceId(mstEntity.getTbServicesMst().getSmServiceId());
		requestDto.setServiceShortCode(mstEntity.getTbServicesMst().getSmServiceName());

		return requestDto;
	}

	
}
