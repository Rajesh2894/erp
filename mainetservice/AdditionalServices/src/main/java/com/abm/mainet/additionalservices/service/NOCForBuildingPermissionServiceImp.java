package com.abm.mainet.additionalservices.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.additionalservices.constant.NOCForBuildPermissionConstant;
import com.abm.mainet.additionalservices.dao.NOCForBuildingPermissionDao;
import com.abm.mainet.additionalservices.domain.NOCForBuildingPermissionEntity;
import com.abm.mainet.additionalservices.dto.NOCForBuildingPermissionDTO;
import com.abm.mainet.additionalservices.repository.NOCForBuildingPermissionRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dao.ICFCApplicationMasterDAO;
import com.abm.mainet.common.dao.IServiceMasterDAO;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.GroupMaster;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.domain.TbCfcApplicationMstEntity;
import com.abm.mainet.common.dto.ApplicantDetailDTO;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.repository.TbCfcApplicationMstJpaRepository;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.GroupMasterService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ApplicationService;
import com.abm.mainet.common.service.CommonService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.ApplicationMetadata;
import com.abm.mainet.common.workflow.dto.WorkflowProcessParameter;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowExecutionService;

@Service
public class NOCForBuildingPermissionServiceImp implements NOCForBuildingPermissionService {

	@Resource
	private ApplicationService applicationService;

	@Resource
	private IServiceMasterDAO iServiceMasterDAO;

	@Autowired
	private GroupMasterService groupMasterService;

	@Autowired
	private NOCForBuildingPermissionRepository nocrepos;

	@Autowired
	private CommonService commonService;

	@Autowired
	private NOCForBuildingPermissionDao nOCForBuildDao;

	@Autowired
	private DepartmentService departmentService;

	@Resource
	private TbCfcApplicationMstJpaRepository tbCfcApplicationMstJpaRepository;
	
	@Autowired
	private ICFCApplicationMasterDAO iCFCApplicationMasterDAO;
	
	@Resource
	private IFileUploadService fileUploadService;
	
	 
	@Resource
	private ServiceMasterService serviceMasterService;
	
	
	
	


	@Override
	public NOCForBuildingPermissionDTO saveData(NOCForBuildingPermissionDTO requestDTO) {

		NOCForBuildingPermissionEntity nocpermission = new NOCForBuildingPermissionEntity();
		final RequestDTO commonRequest = new RequestDTO();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(NOCForBuildPermissionConstant.NBP,
				requestDTO.getOrgId());
		
		commonRequest.setOrgId(requestDTO.getOrgId());
		commonRequest.setServiceId(requestDTO.getSmServiceId());
		commonRequest.setDeptId(requestDTO.getDeptId());
		commonRequest.setApmMode(NOCForBuildPermissionConstant.FLAGF);
		commonRequest.setfName(requestDTO.getfName());
		commonRequest.setGender(requestDTO.getSex());
		commonRequest.setLangId(Long.valueOf(requestDTO.getLangId()));
		commonRequest.setlName(requestDTO.getlName());
		commonRequest.setmName(requestDTO.getmName());
		commonRequest.setEmail(requestDTO.getApplicantEmail());
		
		
		if (serviceMas.getSmFeesSchedule() == 0) {
			commonRequest.setPayStatus(NOCForBuildPermissionConstant.PAYSTSTUSFREE);
		} else {
			commonRequest.setPayStatus(NOCForBuildPermissionConstant.PAYSTATUSCHARGE);
		}
		Long applicationId = null;
		String referenceId=null;
		commonRequest.setUserId(requestDTO.getUserId());
		Organisation org =new Organisation();
		org.setOrgid(commonRequest.getOrgId());
		if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {	
			FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
			String deptCode = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
					.getDeptCode(serviceMas.getTbDepartment().getDpDeptid());
			
			String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
			final Long sequence = ApplicationContextProvider.getApplicationContext()
					.getBean(SeqGenFunctionUtility.class).generateSequenceNo(deptCode, "TB_NOC_FOR_BUILDING_PERMISSION",
							"BP_ID", commonRequest.getOrgId(), MainetConstants.FlagF, null);
			
			referenceId = "PDA"+MainetConstants.WINDOWS_SLASH+"BP"+MainetConstants.WINDOWS_SLASH + finacialYear + MainetConstants.WINDOWS_SLASH
					+ String.format(MainetConstants.WorksManagement.FOUR_PERCENTILE, sequence)+MainetConstants.WINDOWS_SLASH+"NOC";
		    commonRequest.setReferenceId(referenceId);
			nocpermission.setRefNo(referenceId);
		}
		applicationId = applicationService.createApplication(commonRequest);
	    nocpermission.setApmApplicationId(applicationId);
		//commonRequest.setReferenceId(String.valueOf(applicationId));
		//commonRequest.setApplicationId(applicationId);
		if (null == applicationId && null == referenceId ) {
			throw new FrameworkException("Application Not Generated");
		}

		
		//commonRequest.setReferenceId(String.valueOf(applicationId));
		//commonRequest.setApplicationId(applicationId);
		//requestDTO.setApplicationId(applicationId.toString());
		if (referenceId != null) {
			requestDTO.setRefNo(referenceId);
			requestDTO.setApmApplicationId(applicationId);
			requestDTO.setApplicationId(applicationId.toString());
			commonRequest.setReferenceId(referenceId);
			commonRequest.setApplicationId(applicationId);
		}else {
			requestDTO.setApmApplicationId(applicationId);
			requestDTO.setApplicationId(applicationId.toString());
			commonRequest.setApplicationId(applicationId);
		}
		/*if ((requestDTO.getDocList() != null) && !requestDTO.getDocList().isEmpty()) {
			fileUploadService.doFileUpload(requestDTO.getDocList(), commonRequest);
		}*/
		requestDTO.setWfStatus(NOCForBuildPermissionConstant.PENDING);
		BeanUtils.copyProperties(requestDTO, nocpermission);
		nocpermission.setDeptId(requestDTO.getDeptId());
		nocpermission.setContactNumber(requestDTO.getContactNumber());
		nocrepos.save(nocpermission);
		//commonRequest.setReferenceId(String.valueOf(applicationId));
		//commonRequest.setApplicationId(applicationId);
		if ((requestDTO.getDocList() != null) && !requestDTO.getDocList().isEmpty()) {
			fileUploadService.doFileUpload(requestDTO.getDocList(), commonRequest);
		}
		initializeWorkFlowForFreeService(requestDTO);
		BeanUtils.copyProperties(nocpermission, requestDTO);
		return requestDTO;
	}

	@Transactional
	public void initializeWorkFlowForFreeService(NOCForBuildingPermissionDTO requestDto) {
		boolean checkList = false;
		if (CollectionUtils.isNotEmpty(requestDto.getDocList())) {
			checkList = true;
		}
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(NOCForBuildPermissionConstant.NBP,
				requestDto.getOrgId());

		ApplicantDetailDTO applicantDto = new ApplicantDetailDTO();

		ApplicationMetadata applicationMetaData = new ApplicationMetadata();
		applicantDto.setApplicantFirstName(requestDto.getfName());
		applicantDto.setServiceId(serviceMas.getSmServiceId());
		applicantDto.setDepartmentId(departmentService.getDepartmentIdByDeptCode(NOCForBuildPermissionConstant.COM));
		applicantDto.setMobileNo("NA");
		applicantDto.setUserId(requestDto.getUserId());
		applicationMetaData.setApplicationId(requestDto.getApmApplicationId());
		applicationMetaData.setIsCheckListApplicable(checkList);
		applicationMetaData.setOrgId(requestDto.getOrgId());
		applicationMetaData.setReferenceId(requestDto.getRefNo());
		try {
			commonService.initiateWorkflowfreeService(applicationMetaData, applicantDto);
		} catch (Exception e) {
			throw new FrameworkException("Exception occured while calling workflow");
		}
	}

	@Override
	public void saveRegDet(NOCForBuildingPermissionDTO birthRegDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public NOCForBuildingPermissionDTO getRegisteredAppliDetail(Long applicationId, Long orgId) {

		NOCForBuildingPermissionEntity detailEntity = nocrepos.findData(applicationId, orgId);
		NOCForBuildingPermissionDTO birthDto = new NOCForBuildingPermissionDTO();
		BeanUtils.copyProperties(detailEntity, birthDto);
		birthDto.setBirthRegremark(detailEntity.getBrRemarks());
		
		return birthDto;
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
			if (lookup.get(i).getOtherField()!=null && lookup.get(i).getOtherField().equals(NOCForBuildPermissionConstant.NBP)) {
				if (lookup.get(i).getDescLangFirst().equalsIgnoreCase(groupMaster.getGrCode())) {
					checkFinalAproval = true;
				}
			}
		}
		
		return checkFinalAproval;
	}

	@Override
	public void updateApproveStatusBR(NOCForBuildingPermissionDTO nocDTO, String status, String lastDecision) {
		TbCfcApplicationMstEntity cfcApplEntiry = new TbCfcApplicationMstEntity();
		ServiceMaster service = new ServiceMaster();
		cfcApplEntiry.setApmApplicationId(Long.valueOf(nocDTO.getApplicationId()));
		TbCfcApplicationMstEntity tbCfcApplicationMst= iCFCApplicationMasterDAO.getCFCApplicationByApplicationId(Long.valueOf(nocDTO.getApplicationId()), nocDTO.getOrgId());
		BeanUtils.copyProperties(tbCfcApplicationMst,cfcApplEntiry);
		cfcApplEntiry.setTbOrganisation(UserSession.getCurrent().getOrganisation());
		cfcApplEntiry.setApmApplicationDate(new Date());
		service.setSmServiceId(nocDTO.getSmServiceId());
		cfcApplEntiry.setTbServicesMst(service);
		cfcApplEntiry.setUpdatedDate(nocDTO.getDate());
		cfcApplEntiry.setLmoddate(new Date());
		cfcApplEntiry.setUpdatedBy(nocDTO.getUserId());
		cfcApplEntiry.setUserId(nocDTO.getUserId());
		cfcApplEntiry.setApmSex(nocDTO.getSex());
		cfcApplEntiry.setApmFname(nocDTO.getfName());

		/*
		 * LookUp lokkup = null; if (birthRegDTO.getSex() != null) { lokkup =
		 * CommonMasterUtility.getLookUpFromPrefixLookUpDesc(birthRegDTO.getSex(),
		 * GENDER, birthRegDTO.getLangId()); }
		 */
		if (lastDecision.equals(NOCForBuildPermissionConstant.REJECTED)) {
			cfcApplEntiry.setApmAppRejFlag(NOCForBuildPermissionConstant.APMAPPLICATIOREJFLAG);
			cfcApplEntiry.setAppAppRejBy(nocDTO.getSmServiceId());
			cfcApplEntiry.setRejectionDt(new Date());
			cfcApplEntiry.setApmApplClosedFlag(NOCForBuildPermissionConstant.APMAPPLICATIONCLOSEDFLAG);

		} else if (status.equals(NOCForBuildPermissionConstant.APPROVED)
				&& lastDecision.equals(NOCForBuildPermissionConstant.PENDING)) {
			cfcApplEntiry.setApmApplSuccessFlag(NOCForBuildPermissionConstant.APMAPPLICATIONPENDINGFLAG);
			cfcApplEntiry.setApmApprovedBy(nocDTO.getSmServiceId());
			cfcApplEntiry.setApmApplClosedFlag(NOCForBuildPermissionConstant.APMAPPLICATIONCLOSEDOPENFLAG);
			NOCForBuildingPermissionEntity nocpermission = new NOCForBuildingPermissionEntity();
			BeanUtils.copyProperties(nocDTO, nocpermission);
			nocpermission.setBrRemarks(nocDTO.getBirthRegremark());
			nocrepos.save(nocpermission);

		} else if (status.equals(NOCForBuildPermissionConstant.APPROVED)
				&& lastDecision.equals(NOCForBuildPermissionConstant.CLOSED)) {
			cfcApplEntiry.setApmApplSuccessFlag(NOCForBuildPermissionConstant.APMAPPLICATIONCLOSEDFLAG);
			cfcApplEntiry.setApmApprovedBy(nocDTO.getSmServiceId());
			cfcApplEntiry.setApmApplClosedFlag(NOCForBuildPermissionConstant.APMAPPLICATIONCLOSEDOPENFLAG);

			NOCForBuildingPermissionEntity nocpermission = new NOCForBuildingPermissionEntity();
			BeanUtils.copyProperties(nocDTO, nocpermission);
			nocpermission.setBrRemarks(nocDTO.getBirthRegremark());
			nocrepos.save(nocpermission);


			NOCForBuildingPermissionEntity  nocpermission1 = new NOCForBuildingPermissionEntity();
			BeanUtils.copyProperties(nocDTO, nocpermission1);
			nocpermission1.setBrRemarks(nocDTO.getBirthRegremark());
			nocrepos.save(nocpermission1);

		}
		tbCfcApplicationMstJpaRepository.save(cfcApplEntiry);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateWorkFlowStatusBR(Long brId, String taskNamePrevious, Long orgId, String brStatus) {
		nocrepos.updateWorkFlowStatus(brId, orgId, taskNamePrevious, brStatus);
	}

	@Override
	@Transactional
	public String updateWorkFlowService(WorkflowTaskAction workflowTaskAction,NOCForBuildingPermissionDTO nocBuildingPermissionDto) {
		WorkflowProcessParameter workflowProcessParameter = new WorkflowProcessParameter();
		ServiceMaster serviceMas = iServiceMasterDAO.getServiceMasterByShortCode(NOCForBuildPermissionConstant.NBP, nocBuildingPermissionDto.getOrgId());
		String processName = serviceMasterService.getProcessName(serviceMas.getSmServiceId(),nocBuildingPermissionDto.getOrgId());
		workflowProcessParameter.setProcessName(processName);
		
		workflowProcessParameter.setWorkflowTaskAction(workflowTaskAction);
		try {
			ApplicationContextProvider.getApplicationContext().getBean(IWorkflowExecutionService.class)
					.updateWorkflow(workflowProcessParameter);
		} catch (Exception exception) {
			throw new FrameworkException("Exception  Occured when Update Workflow methods", exception);
		}
		return null;
	}

	@Override
	public List<NOCForBuildingPermissionDTO> getAllData() {
		List<NOCForBuildingPermissionEntity> DetailEntity = nocrepos.findAll();
		List<NOCForBuildingPermissionDTO> listbirthRegDraftDto = new ArrayList<NOCForBuildingPermissionDTO>();
		if (DetailEntity != null) {
			DetailEntity.forEach(entity -> {
				NOCForBuildingPermissionDTO dto = new NOCForBuildingPermissionDTO();
				BeanUtils.copyProperties(entity, dto);
				listbirthRegDraftDto.add(dto);
			});

		}

		return listbirthRegDraftDto;
	}

	@Override
	public List<NOCForBuildingPermissionDTO> getAppliDetail(Long apmApplicationId, Date fromDate, Date toDate,
			Long orgId, String refNo) {

		List<NOCForBuildingPermissionEntity> birthregList = nOCForBuildDao.getAppliDetail(apmApplicationId, fromDate,
				toDate, orgId, refNo);
		List<NOCForBuildingPermissionDTO> listDTO = new ArrayList<NOCForBuildingPermissionDTO>();
		if (birthregList != null) {
			birthregList.forEach(entity -> {
				NOCForBuildingPermissionDTO dto = new NOCForBuildingPermissionDTO();
				BeanUtils.copyProperties(entity, dto);
				/*
				 * LookUp lokkup = null; if (!entity.getBrSex().equals("0")) { lokkup =
				 * CommonMasterUtility.getNonHierarchicalLookUpObjectByPrefix(Long.valueOf(
				 * entity.getBrSex()), orgId, GENDER); dto.setBrSex(lokkup.getLookUpDesc()); }
				 */

				listDTO.add(dto);
			});
		}

		return listDTO;
	}

	@Override
	@Transactional
	public NOCForBuildingPermissionDTO getDataById(Long bpId) {
		NOCForBuildingPermissionEntity entity = nocrepos.findOne(bpId);
		NOCForBuildingPermissionDTO dto = new NOCForBuildingPermissionDTO();
		BeanUtils.copyProperties(entity, dto);

		return dto;
	}

	@Override
	@Transactional(readOnly=true)
	public NOCForBuildingPermissionDTO findByRefNo(String refNo) {
		NOCForBuildingPermissionEntity entity = nocrepos.findByRefNo(refNo);
		NOCForBuildingPermissionDTO dto = new NOCForBuildingPermissionDTO();
		BeanUtils.copyProperties(entity, dto);
        return dto;
	}

	@Override
	public List<NOCForBuildingPermissionDTO> getAppliDetail(Long apmApplicationId, Date fromDate, Date toDate,
			Long orgId) {
		// TODO Auto-generated method stub
		return null;
	}

}