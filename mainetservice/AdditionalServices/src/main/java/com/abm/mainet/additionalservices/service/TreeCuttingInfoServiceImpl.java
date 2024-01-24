package com.abm.mainet.additionalservices.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.additionalservices.constant.NOCForBuildPermissionConstant;
import com.abm.mainet.additionalservices.domain.CFCNursingHomeInfoEntity;
import com.abm.mainet.additionalservices.domain.TreeCuttingInfoEntity;
import com.abm.mainet.additionalservices.dto.CFCNursingHomeInfoDTO;
import com.abm.mainet.additionalservices.dto.NursingHomeSummaryDto;
import com.abm.mainet.additionalservices.dto.TreeCutingTrimingSummaryDto;
import com.abm.mainet.additionalservices.dto.TreeCuttingInfoDto;
import com.abm.mainet.additionalservices.repository.TreeCuttingPermissionRepo;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.CFCApplicationAddressEntity;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.dto.CommonSequenceConfigDto;
import com.abm.mainet.common.dto.SequenceConfigMasterDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.TbCfcApplicationMst;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.repository.CFCApplicationAddressRepository;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

@Service
public class TreeCuttingInfoServiceImpl implements TreeCuttingPermissionService {

	@Autowired
	private SeqGenFunctionUtility seqGenFunctionUtility;

	@Autowired
	private TreeCuttingPermissionRepo treeCuttingRepo;

	@Autowired
	private CommonService commonService;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	IFileUploadService fileUploadService;

	@Autowired
	private CFCApplicationAddressRepository cfcApplicationAddressRepository;

	@Autowired
	private ServiceMasterService serviceMasterService;

	@Autowired
	private TbCfcApplicationMstJpaRepository cfcApplicationMstJpaRepository;

	@Autowired
	private GroupMasterService groupMasterService;

	@Override
	@Transactional
	public String saveTreeInfo(TreeCuttingInfoDto treeCuttingInfoDto, CFCApplicationAddressEntity addressEntity,
			TbCfcApplicationMst cfcApplicationMst, TreeCutingTrimingSummaryDto cutingTrimingSummaryDto) {

		String appicationId = null;
		TreeCuttingInfoEntity cuttingInfoEntity;

		TbCfcApplicationMstEntity cfcApplicationMstEntity = new TbCfcApplicationMstEntity();
		BeanUtils.copyProperties(cfcApplicationMst, cfcApplicationMstEntity);

		ServiceMaster serviceMaster = serviceMasterService.getServiceMaster(cfcApplicationMst.getSmServiceId(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		
		RequestDTO requestDto = setApplicantRequestDto(addressEntity, cfcApplicationMst, serviceMaster);

		if (cfcApplicationMstEntity.getApmApplicationId() == null) {
			appicationId =createApplicationNumber(requestDto);

			ServiceMaster master = new ServiceMaster();
			master.setSmServiceId(cfcApplicationMst.getSmServiceId());
			cfcApplicationMstEntity.setTbServicesMst(master);
			cfcApplicationMstEntity.setApmApplicationId(Long.parseLong(appicationId));
			cfcApplicationMstEntity.setApmPayStatFlag(MainetConstants.FlagF);
			cfcApplicationMst.setApmApplicationId(Long.parseLong(appicationId));
			cfcApplicationMst.setRefNo(appicationId);
			cfcApplicationMstEntity.setRefNo(appicationId);
			cfcApplicationMstEntity.setApmApplicationDate(new Date());
			cfcApplicationMstEntity.setLmoddate(UtilityService.getSQLDate(new Date()));
			final Organisation organisation = new Organisation();
			organisation.setOrgid(requestDto.getOrgId());
			cfcApplicationMstEntity.setTbOrganisation(organisation);
			cfcApplicationMst.setRefNo(appicationId);
			cfcApplicationMstJpaRepository.save(cfcApplicationMstEntity);

			addressEntity.setApmApplicationId(Long.parseLong(appicationId));
			addressEntity.setLmodDate(UtilityService.getSQLDate(new Date()));
			cfcApplicationAddressRepository.save(addressEntity);

			cuttingInfoEntity = new TreeCuttingInfoEntity();
			BeanUtils.copyProperties(treeCuttingInfoDto, cuttingInfoEntity);
			cuttingInfoEntity.setApmApplicationId(Long.parseLong(appicationId));
			cuttingInfoEntity = treeCuttingRepo.save(cuttingInfoEntity);
			requestDto.setReferenceId(appicationId);

			boolean checklist = false;
			if ((cutingTrimingSummaryDto.getDocumentList() != null)
					&& !cutingTrimingSummaryDto.getDocumentList().isEmpty()) {
				requestDto.setApplicationId(Long.parseLong(appicationId));
				checklist = fileUploadService.doFileUpload(cutingTrimingSummaryDto.getDocumentList(), requestDto);
				checklist = true;
			}

			// fileUploadService.doFileUpload(cutingTrimingSummaryDto.getDocumentList(),
			// requestDto);

			/*
			 * int i = 0;
			 * 
			 * if ((cutingTrimingSummaryDto.getAttachments() != null) &&
			 * !cutingTrimingSummaryDto.getAttachments().isEmpty()) {
			 * List<DocumentDetailsVO> getImgList = null;
			 * 
			 * getImgList = new ArrayList<>();
			 * 
			 * requestDto.setReferenceId(appicationId); requestDto.setApplicationId(appiId);
			 * List<DocumentDetailsVO> getList = cutingTrimingSummaryDto.getAttachments();
			 * for (int j = i; j < getList.size(); j++) { DocumentDetailsVO img =
			 * getList.get(i); getImgList.add(img); break;
			 * 
			 * }
			 * 
			 * i++; fileUploadService.doFileUpload(getImgList, requestDto);
			 * 
			 * }
			 */

		}
		if (serviceMaster.getSmFeesSchedule() == 0) {
			initializeWorkFlowForFreeService(cfcApplicationMst, serviceMaster, cutingTrimingSummaryDto,
					addressEntity.getApaMobilno());
		} else {
			// setAndSaveChallanDtoOffLine(model.getOfflineDTO(), model,
			// cfcNursingHomeInfoDTO, applicationMst,addressEntity);
		}

		return appicationId;
	}

	@Override
	public String createApplicationNumber(RequestDTO requestDto) {
		return ApplicationContextProvider.getApplicationContext().getBean(ApplicationService.class)
						.createApplication(requestDto).toString();
	}

	public void initializeWorkFlowForFreeService(TbCfcApplicationMst requestDto, ServiceMaster serviceMaster,
			TreeCutingTrimingSummaryDto treeCutingTrimingSummaryDto, String mobNo) {
		boolean checkList = false;
		if ((treeCutingTrimingSummaryDto.getDocumentList() != null)
				&& !treeCutingTrimingSummaryDto.getDocumentList().isEmpty()) {
			checkList = true;
		}

		// ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(,
		// requestDto.getOrgId());

		/*
		 * ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		 * 
		 * ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		 * applicantDto.setApplicantFirstName(requestDto.getApmFname());
		 * applicantDto.setServiceId(serviceMaster.getSmServiceId());
		 * applicantDto.setDepartmentId(departmentService.getDepartmentIdByDeptCode(
		 * "CFC")); applicantDto.setMobileNo(mobNo);
		 * applicantDto.setUserId(requestDto.getUserId());
		 * applicationMetaData.setApplicationId(requestDto.getApmApplicationId());
		 * applicationMetaData.setIsCheckListApplicable(checkList);
		 * applicationMetaData.setOrgId(requestDto.getOrgid());
		 * applicationMetaData.setReferenceId(requestDto.getRefNo());
		 */

		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();
		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(requestDto.getApmFname());
		applicantDto.setServiceId(serviceMaster.getSmServiceId());
		applicantDto.setDepartmentId(departmentService.getDepartmentIdByDeptCode("CFC"));

		// applicantDto.setMobileNo();
		applicantDto.setUserId(requestDto.getUserId());
		applicationMetaData.setApplicationId(requestDto.getApmApplicationId());
		applicationMetaData.setIsCheckListApplicable(checkList);
		applicationMetaData.setOrgId(requestDto.getOrgid());
		applicationMetaData.setReferenceId(requestDto.getRefNo());
		applicationMetaData.setIsLoiApplicable(false);

		try {
			// commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}

	@Override
	@Transactional
	public Boolean checkEmployeeRole(UserSession ses) {
		@SuppressWarnings("deprecation")
		List<LookUp> lookup = CommonMasterUtility.getListLookup(NOCForBuildPermissionConstant.DESIGN_PRIFIX,
				UserSession.getCurrent().getOrganisation());

		GroupMaster groupMaster = groupMasterService.findByGmId(ses.getEmployee().getGmid(),
				ses.getOrganisation().getOrgid());
		boolean checkFinalAproval = false;
		for (int i = 0; i < lookup.size(); i++) {
			if (lookup.get(i).getOtherField() != null
					&& lookup.get(i).getOtherField().equals(NOCForBuildPermissionConstant.NBP)) {
				if (lookup.get(i).getDescLangFirst().equalsIgnoreCase(groupMaster.getGrCode())) {
					checkFinalAproval = true;
				}
			}
		}

		return checkFinalAproval;
	}

	@Override
	@Transactional
	public String updateWorkFlowService(WorkflowTaskAction workflowTaskAction) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		workflowProcessParameter.setProcessName(MainetConstants.WorkFlow.Process.MAKER_CHECKER);
		workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}
		return null;
	}

	private RequestDTO setApplicantRequestDto(CFCApplicationAddressEntity addressEntity,
			TbCfcApplicationMst applicationMst, ServiceMaster sm) {

		RequestDTO requestDto = new RequestDTO();

		requestDto.setServiceId(sm.getSmServiceId());
		requestDto.setUserId(addressEntity.getUserId());

		requestDto.setOrgId(addressEntity.getOrgId().getOrgid());
		requestDto.setLangId((long) addressEntity.getLangId());
		requestDto.setDeptId(sm.getTbDepartment().getDpDeptid());
		// setting applicant info
		requestDto.setFree(true);
		requestDto.setfName(applicationMst.getApmFname());
		requestDto.setEmail(addressEntity.getApaEmail());
		requestDto.setMobileNo(addressEntity.getApaMobilno());
		requestDto.setAreaName(addressEntity.getApaAreanm());

		/*
		 * if (tradeMasterDto.getTotalApplicationFee() != null) {
		 * requestDto.setPayAmount(tradeMasterDto.getTotalApplicationFee().doubleValue()
		 * ); }
		 */
		return requestDto;

	}

	@Override
	public List<TreeCutingTrimingSummaryDto> getAllByServiceIdAndAppId(Long serviceId, String refId, Long orgId) {

		List<TreeCutingTrimingSummaryDto> dtos = new ArrayList<>();
		TbCfcApplicationMstEntity dto = cfcApplicationMstJpaRepository.fetchCfcApplicationsByServiceId(serviceId,
				refId);
		if (dto != null) {
			TreeCuttingInfoEntity cuttingInfoEntity = treeCuttingRepo.findbyApmApplicationId(dto.getApmApplicationId());
			if (dto != null && dto.getApmApplicationId().equals(cuttingInfoEntity.getApmApplicationId())) {
				TreeCutingTrimingSummaryDto homeSummaryDto = new TreeCutingTrimingSummaryDto();
				homeSummaryDto.setfName(dto.getApmFname());
				homeSummaryDto.setAppId(dto.getApmApplicationId());
				homeSummaryDto.setRefId(refId);
				homeSummaryDto.setlName(dto.getApmLname());
				homeSummaryDto.setServiceName(serviceMasterService.getServiceNameByServiceId(serviceId));
				dtos.add(homeSummaryDto);
			}
		}

		return dtos;
	}

	@Override
	public TreeCuttingInfoDto getTreeCuttingInfo(Long appId) {
		TreeCuttingInfoEntity cuttingInfoEntity = treeCuttingRepo.findbyApmApplicationId(appId);
		TreeCuttingInfoDto cuttingInfoDto = new TreeCuttingInfoDto();
		if (cuttingInfoEntity != null) {

			BeanUtils.copyProperties(cuttingInfoEntity, cuttingInfoDto);
		}
		return cuttingInfoDto;
	}

}
