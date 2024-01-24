package com.abm.mainet.rnl.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.dto.JsonViewObject;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.dto.FileUploadDTO;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.dto.ContractMappingDTO;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.service.TbApprejMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.rnl.dto.EstateContMappingDTO;
import com.abm.mainet.rnl.service.IEstateContractMappingService;
import com.abm.mainet.rnl.ui.model.EstateContractMappingModel;

/**
 * @author divya.marshettiwar
 *
 */
@Controller
@RequestMapping("/EstateContractMappingApproval.html")
public class EstateContractMappingApprovalController extends AbstractFormController<EstateContractMappingModel>{
	
	@Autowired
	private ServiceMasterService serviceMaster;
	
	@Autowired
    private IEstateContractMappingService iEstateContractMappingService;
	
	@Autowired
	private TbApprejMasService tbApprejMasService;
	
	@Autowired
    private TbDepartmentService tbDepartmentService;
	
	@Autowired
	private IWorkFlowTypeService workFlowTypeService;
	
	@Resource
	private IFileUploadService fileUpload;
	
	@Resource
	private IFileUploadService fileUploadService;
	
	@Autowired
	private IAttachDocsService attachDocsService;

	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.ADH_SHOW_DETAILS, method = { RequestMethod.POST })
	public ModelAndView executeAgencyRegistration(final Model uiModel,
		@RequestParam(MainetConstants.AdvertisingAndHoarding.APP_NO) final Long applicationId,
		@RequestParam(MainetConstants.AdvertisingAndHoarding.TASK_ID) String taskId,
		@RequestParam(value = MainetConstants.AdvertisingAndHoarding.ACTUAL_TASKID) Long actualTaskId,
		final HttpServletRequest request) {
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		ContractMappingDTO contractMappingDto = new ContractMappingDTO();
		EstateContractMappingModel model = this.getModel();
		
		Organisation organisation = new Organisation();
        organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		try {
			contractMappingDto = getData(applicationId, taskId, actualTaskId, request);

		} catch (Exception exception) {
			logger.error("Error While Rendoring the form", exception);
			return defaultExceptionFormView();
		}
		ServiceMaster service = serviceMaster.getServiceMaster(Long.valueOf(taskId),
				UserSession.getCurrent().getOrganisation().getOrgid());
		if (service != null) {
			LookUp artTypeLookUp = CommonMasterUtility.getValueFromPrefixLookUp(
					PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.APP,
					PrefixConstants.ADVERTISEMENT_AND_HOARDING_PREFIX.REM, UserSession.getCurrent().getOrganisation());
			getModel().setRemarkList(
					tbApprejMasService.findByRemarkType(Long.valueOf(taskId), artTypeLookUp.getLookUpId()));
		}
		
		populateModel(contractMappingDto.getContId(), model, MainetConstants.RnLCommon.MODE_VIEW);
        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
        
        EstateContMappingDTO estateContractDto = iEstateContractMappingService.findPropIdByAppId(applicationId.toString(),
				UserSession.getCurrent().getOrganisation().getOrgid());
        model.getEstateContMappingDTO().setContractMappingDTO(estateContractDto.getContractMappingDTO());
        model.getEstateContMappingDTO().setEsId(estateContractDto.getEsId());
        model.getEstateContMappingDTO().setNameEng(estateContractDto.getNameEng());
        model.getEstateContMappingDTO().setNameReg(estateContractDto.getNameReg());
        model.getEstateContMappingDTO().setPropId(estateContractDto.getPropId());
        model.getEstateContMappingDTO().setPropName(estateContractDto.getPropName());
        model.getEstateContMappingDTO().setCode(estateContractDto.getCode());
        model.getEstateContMappingDTO().setAssesmentPropId(estateContractDto.getAssesmentPropId());
        model.getEstateContMappingDTO().setUsageDesc(CommonMasterUtility
        		.getHierarchicalLookUp(estateContractDto.getUsage(), organisation).getLookUpDesc());
        model.getEstateContMappingDTO().setUnitNo(estateContractDto.getUnitNo());
        model.getEstateContMappingDTO().setTotalArea(estateContractDto.getTotalArea());
        if(estateContractDto.getFloor() != null) {
        	model.getEstateContMappingDTO().setFloorDesc(CommonMasterUtility
            		.getNonHierarchicalLookUpObject(estateContractDto.getFloor(), organisation).getLookUpDesc());
        }
        	
		final TbDepartment tbDepartment = tbDepartmentService.findDeptByCode(orgId,MainetConstants.RnLCommon.Flag_A, MainetConstants.RnLCommon.RentLease); 
		
		List<ContractMappingDTO> contractList = iEstateContractMappingService.findContractsByContractId(orgId,
				estateContractDto.getContractMappingDTO().getContId(), tbDepartment);
		if(contractList !=null ) {
			model.setContractList(contractList);
		}
		model.setShowForm(MainetConstants.EstateContract.ESTATE_CONTRACT_APPROVAL_FORM);
		
		return new ModelAndView(MainetConstants.EstateContract.ESTATE_CONTRACT_MAPPING_APPROVAL,
				MainetConstants.CommonConstants.COMMAND, model);
	}
	
	public ContractMappingDTO getData(Long applicationId, String taskId, Long actualTaskId, HttpServletRequest httpServletRequest)
			throws Exception {
		sessionCleanup(httpServletRequest);
		this.getModel().bind(httpServletRequest);
		ContractMappingDTO contractMappingDto = new ContractMappingDTO();
		
		this.getModel().getWorkflowActionDto().setTaskId(actualTaskId);
		this.getModel().getWorkflowActionDto().setApplicationId(applicationId);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		
		ServiceMaster serviceDto = serviceMaster
				.getServiceByShortName(MainetConstants.EstateContract.CTA, orgId);
		if (serviceDto != null) {
			this.getModel().setServiceMaster(serviceDto);
			this.getModel().setServiceId(serviceDto.getSmServiceId());
			this.getModel().setServiceName(serviceDto.getSmServiceName());
		}
		EstateContMappingDTO estateContractDto = iEstateContractMappingService.findPropIdByAppId(applicationId.toString(),
				UserSession.getCurrent().getOrganisation().getOrgid());
		
		this.getModel().setEstateContMappingDTO(estateContractDto);
		this.getModel().setContractMappingDTO(estateContractDto.getContractMappingDTO());
		this.getModel().setApmApplicationId(applicationId);
		return contractMappingDto;
	}
	
	  private void populateModel(final Long contId, final EstateContractMappingModel estateContractMappingModel,
	            final String modeType) {

	        final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	        List<ContractMappingDTO> contractList = null;
	        if (contId == null) {
	            estateContractMappingModel.setModeType(MainetConstants.RnLCommon.MODE_CREATE);
	            contractList = iEstateContractMappingService.findContractDeptWise(orgId, estateContractMappingModel.getTbDepartment(),
	                    MainetConstants.CommonConstants.N);
	        } else {
	            estateContractMappingModel.setModeType(MainetConstants.RnLCommon.MODE_VIEW);
	            contractList = iEstateContractMappingService.findContractsByContractId(orgId, contId,
	                    estateContractMappingModel.getTbDepartment());
	            final EstateContMappingDTO estateContMappingDTO = iEstateContractMappingService.findByContractId(contId);
	            getModel().setEstateContMappingDTO(estateContMappingDTO);
	        }
	        if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL) || Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
	        	  List<ContractMappingDTO> filterContracts = new ArrayList<ContractMappingDTO>();
	              filterContracts = contractList.stream()
	                      .filter(contract -> contract.getContractNo() != null && contract.getFromDate() != null)
	                      .collect(Collectors.toList());

	              estateContractMappingModel.setContractList(filterContracts);
	        }else {
	      
	        List<ContractMappingDTO> filterContracts = new ArrayList<ContractMappingDTO>();
	        filterContracts = contractList.stream()
	                .filter(contract -> contract.getContractNo() != null && contract.getFromDate() != null
	                        && checkDate(Utility.stringToDate(contract.getFromDate()),
	                                Utility.stringToDate(contract.getToDate()), new Date()))
	                .collect(Collectors.toList());

	        estateContractMappingModel.setContractList(filterContracts);
	        }
	    }
	  
	public Boolean checkDate(Date startDate, Date endDate, Date checkDate) {
		return startDate.compareTo(checkDate) * checkDate.compareTo(endDate) >= 0;
	}
	
	@RequestMapping(params = "back", method = RequestMethod.POST)
    public ModelAndView back(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		
		EstateContractMappingModel model = this.getModel();
		EstateContMappingDTO estateContractDto = model.getEstateContMappingDTO();
		Organisation organisation = new Organisation();
        organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		final Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		final TbDepartment tbDepartment = tbDepartmentService.findDeptByCode(orgId,MainetConstants.RnLCommon.Flag_A, MainetConstants.RnLCommon.RentLease); 
		
		List<ContractMappingDTO> contractList = iEstateContractMappingService.findContractsByContractId(orgId,
				estateContractDto.getContractMappingDTO().getContId(), tbDepartment);
		if(contractList !=null ) {
			model.setContractList(contractList);
		}
        return new ModelAndView(MainetConstants.EstateContract.ESTATE_CONTRACT_MAPPING_APPROVAL, MainetConstants.FORM_NAME, this.getModel());
    }
	
	@Override
	@RequestMapping(params = MainetConstants.AdvertisingAndHoarding.SAVE, method = RequestMethod.POST)
	public ModelAndView saveform(HttpServletRequest request) {
		JsonViewObject responseObj = null;
		this.getModel().bind(request);
		EstateContractMappingModel model = this.getModel();
		EstateContMappingDTO estateContractDTO = this.getModel().getEstateContMappingDTO();
		RequestDTO requestDto = new RequestDTO();
		requestDto.setReferenceId(String.valueOf(model.getApmApplicationId()));
		requestDto.setDepartmentName(MainetConstants.RnLCommon.RL_SHORT_CODE);
		requestDto.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		requestDto.setServiceId(model.getServiceId());
		requestDto.setDeptId(model.getServiceMaster().getTbDepartment().getDpDeptid());
		requestDto.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		requestDto.setApplicationId(model.getApmApplicationId());
		
		FileUploadDTO fileRequestDTO = new FileUploadDTO();
		fileRequestDTO.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		fileRequestDTO.setStatus(MainetConstants.FlagA);
		fileRequestDTO.setIdfId(String.valueOf(model.getApmApplicationId()));
		fileRequestDTO.setDepartmentName(MainetConstants.RNL_DEPT_CODE);
		fileRequestDTO.setUserId(UserSession.getCurrent().getEmployee().getEmpId());
		
		List<DocumentDetailsVO> dto = model.getDocuments();
        model.setDocuments(fileUpload.setFileUploadMethod(model.getDocuments()));
        model.setAttachments(fileUpload.setFileUploadMethod(model.getAttachments()));
        fileUpload.doMasterFileUpload(model.getAttachments(), fileRequestDTO);
        
        int i = 0;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility
                .getCurrent().getFileMap().entrySet()) {
            model.getDocuments().get(i).setDoc_DESC_ENGL(
                    dto.get(entry.getKey().intValue()).getDoc_DESC_ENGL());
            i++;
        }
		 	
		boolean lastApproval = workFlowTypeService
				.isLastTaskInCheckerTaskList(model.getWorkflowActionDto().getTaskId());
		if (lastApproval
				&& StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
						MainetConstants.WorkFlow.Decision.APPROVED)
				&& StringUtils.equalsIgnoreCase(model.getServiceMaster().getSmScrutinyChargeFlag(),
						MainetConstants.FlagN)) {
			model.getWorkflowActionDto().setComments(estateContractDTO.getRemark());
			if (model.closeWorkFlowTask()) {
				final UserSession session = UserSession.getCurrent();
				estateContractDTO.setUpdatedDate(new Date());
				estateContractDTO.setUpdatedBy(session.getEmployee().getEmpId());
				responseObj = JsonViewObject.successResult(
						ApplicationSession.getInstance().getMessage("rnl.application.acceptStatus"));
			} else {
				responseObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("rnl.application.acceptStatus"));
			}
		} else if (StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
				MainetConstants.WorkFlow.Decision.APPROVED)) {
			model.getWorkflowActionDto().setComments(estateContractDTO.getRemark());
			boolean approvalFlag = this.getModel().updateEstateContractMappingDecision();
			final UserSession session = UserSession.getCurrent();
			estateContractDTO.setOrgId(session.getOrganisation().getOrgid());
			estateContractDTO.setLgIpMacUp(model.getClientIpAddress());
			estateContractDTO.setCreatedDate(new Date());
			estateContractDTO.setCreatedBy(session.getEmployee().getEmpId());
			estateContractDTO.setLgIpMac(model.getClientIpAddress());
			estateContractDTO.setLangId((long) session.getLanguageId());
			if (approvalFlag) {
				responseObj = JsonViewObject.successResult(
						ApplicationSession.getInstance().getMessage("rnl.application.acceptStatus"));
			} else {
				responseObj = JsonViewObject
						.successResult(ApplicationSession.getInstance().getMessage("rnl.application.acceptStatus"));
			}
		} else if (StringUtils.equalsIgnoreCase(model.getWorkflowActionDto().getDecision(),
				MainetConstants.WorkFlow.Decision.REJECTED)) {
			model.updateEstateContractMappingDecision();
			responseObj = JsonViewObject
					.successResult(ApplicationSession.getInstance().getMessage("rnl.application.rejectedStatus"));
		}
		return jsonResult(responseObj);
	}

	@Override
    @RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			final HttpServletRequest httpServletRequest) throws Exception {	
		
		EstateContractMappingModel model = this.getModel();
		model.bind(httpServletRequest);
		Organisation organisation = new Organisation();
        organisation.setOrgid(UserSession.getCurrent().getOrganisation().getOrgid());
		try {
			getData(Long.valueOf(applicationId), taskId, httpServletRequest);
			model.setModeType("V");
			
			EstateContMappingDTO estateContractDto = iEstateContractMappingService
					.findPropIdByAppId(applicationId.toString(),organisation.getOrgid());
			
			final TbDepartment tbDepartment = tbDepartmentService
					.findDeptByCode(organisation.getOrgid(),MainetConstants.RnLCommon.Flag_A, MainetConstants.RnLCommon.RentLease); 
			
			List<ContractMappingDTO> contractList = iEstateContractMappingService.findContractsByContractId(organisation.getOrgid(),
					estateContractDto.getContractMappingDTO().getContId(), tbDepartment);
			if(contractList !=null ) {
				model.setContractList(contractList);
			}
			
			estateContractDto = iEstateContractMappingService.findPropIdByAppId(applicationId.toString(),
					UserSession.getCurrent().getOrganisation().getOrgid());
	        model.getEstateContMappingDTO().setContractMappingDTO(estateContractDto.getContractMappingDTO());
	        model.getEstateContMappingDTO().setEsId(estateContractDto.getEsId());
	        model.getEstateContMappingDTO().setNameEng(estateContractDto.getNameEng());
	        model.getEstateContMappingDTO().setNameReg(estateContractDto.getNameReg());
	        model.getEstateContMappingDTO().setPropId(estateContractDto.getPropId());
	        model.getEstateContMappingDTO().setPropName(estateContractDto.getPropName());
	        model.getEstateContMappingDTO().setCode(estateContractDto.getCode());
	        model.getEstateContMappingDTO().setAssesmentPropId(estateContractDto.getAssesmentPropId());
	        model.getEstateContMappingDTO().setUsageDesc(CommonMasterUtility
	        		.getHierarchicalLookUp(estateContractDto.getUsage(), organisation).getLookUpDesc());
	        model.getEstateContMappingDTO().setUnitNo(estateContractDto.getUnitNo());
	        model.getEstateContMappingDTO().setTotalArea(estateContractDto.getTotalArea());
	        if(estateContractDto.getFloor() != null) {
	        	model.getEstateContMappingDTO().setFloorDesc(CommonMasterUtility
	            		.getNonHierarchicalLookUpObject(estateContractDto.getFloor(), organisation).getLookUpDesc());
	        }
			
			// fetch uploaded document
			final List<AttachDocs> attachDocs = attachDocsService.findAllDocLikeReferenceId(organisation.getOrgid(), applicationId.toString());
			model.setAttachDocsList(attachDocs);
			
		} catch (final Exception ex) {
			logger.error("Problem while rendering form:", ex);
			return defaultExceptionFormView();
		}
        return new ModelAndView(MainetConstants.EstateContract.ESTATE_CONTRACT_MAPPING_APPROVAL,
        		MainetConstants.FORM_NAME, model);
	}
	
	public void getData(long applicationId, long actualTaskId,HttpServletRequest httpServletRequest) {
			
		EstateContractMappingModel model = this.getModel();
		getModel().bind(httpServletRequest);
		model.getWorkflowActionDto().setTaskId(actualTaskId);
		model.getWorkflowActionDto().setApplicationId(applicationId);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		final ServiceMaster service = serviceMaster.getServiceMasterByShortCode(MainetConstants.EstateContract.CTA,
				orgId);
		model.setServiceId(service.getSmServiceId());	
	}

}
