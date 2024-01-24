package com.abm.mainet.workManagement.ui.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowRequest;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkFlowTypeService;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.workManagement.dto.MeasurementBookMasterDto;
import com.abm.mainet.workManagement.dto.TenderWorkDto;
import com.abm.mainet.workManagement.dto.WorkDefinationWardZoneDetailsDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.service.MeasurementBookService;
import com.abm.mainet.workManagement.service.TenderInitiationService;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorkEstimateService;
import com.abm.mainet.workManagement.service.WorkOrderService;
import com.abm.mainet.workManagement.ui.model.MeasurmentBookApprovalModel;

@Controller
@RequestMapping("/MeasurementBookApproval.html")
public class MeasurementBookApprovalController extends AbstractFormController<MeasurmentBookApprovalModel> {

	@Autowired
	MeasurementBookService mbService;

	@Autowired
	WorkOrderService workOrderService;

	@Resource
	private TenderInitiationService initiationService;

	@Autowired
	WorkEstimateService workEstimateService;

	@Autowired
	private WorkDefinitionService workDefinitionService;

	@Autowired
	private IOrganisationService orgService;

	@Autowired
	private IWorkflowActionService workFlowActionService;

	@ResponseBody
	@RequestMapping(params = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.SHOWDETAILS, method = RequestMethod.POST)
	public ModelAndView workorder(@RequestParam(MainetConstants.WORKS_MANAGEMENT_WORKFLOW.APP_NO) final String workCode,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.ACTUAL_TASKID, required = false) final Long actualTaskId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_ID, required = false) final Long serviceId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.WORKFLOW_ID, required = false) final Long workflowId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_NAME, required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {

		sessionCleanup(httpServletRequest);
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.sessionCleanUpForFileUpload();
		MeasurmentBookApprovalModel approvalModel = this.getModel();

		Long parentOrgId = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
				.findById(workflowId).getCurrentOrgId();
		approvalModel.setParentOrgId(parentOrgId);

		approvalModel.setServiceId(serviceId);

		MeasurementBookMasterDto mbMasDto = mbService.getWorkMbByMBCode(workCode,
				UserSession.getCurrent().getOrganisation().getOrgid());
		this.getModel().setWorkMbId(mbMasDto.getWorkMbId());
		approvalModel.setWorkOrderDto(workOrderService.getWorkOredrByOrderId(mbMasDto.getWorkOrId()));
		Long contractId = this.getModel().getWorkOrderDto().getContractMastDTO().getContId();

		TenderWorkDto tenderWorkDto = initiationService.findWorkByWorkId(contractId);
		approvalModel.setEstimateMasDtoList(
				workEstimateService.getWorkEstimateByWorkId(tenderWorkDto.getWorkId(), parentOrgId));

		approvalModel.setMeasurementBookMasterDto(mbMasDto);
		approvalModel.getWorkOrderDto().setMbTotalAmt(mbMasDto.getMbTotalAmt());

		approvalModel.getWorkOrderDto().setWorkName(tenderWorkDto.getWorkName());
		approvalModel.getWorkOrderDto().setProjName(tenderWorkDto.getProjectName());
		WorkDefinitionDto defDetails = workDefinitionService
				.findAllWorkDefinitionById(approvalModel.getEstimateMasDtoList().get(0).getWorkId());

		approvalModel.setWorkDefinitionDto(defDetails);
		Organisation organisationById = orgService.getOrganisationById(defDetails.getOrgId());
		List<LookUp> listLookup = CommonMasterUtility.getListLookup("ZWB", organisationById);
		Organisation org = UserSession.getCurrent().getOrganisation();
		/*
		 * if (!approvalModel.getWorkDefinitionDto().getWardZoneDto().isEmpty()) { for
		 * (WorkDefinationWardZoneDetailsDto wardZoneDTO :
		 * approvalModel.getWorkDefinitionDto().getWardZoneDto()) {
		 * 
		 * if (wardZoneDTO.getCodId1() != null) { wardZoneDTO.setCodId1Desc(
		 * CommonMasterUtility.getHierarchicalLookUp(wardZoneDTO.getCodId1(),
		 * org).getLookUpDesc());
		 * wardZoneDTO.setLevel1(listLookup.get(0).getLookUpDesc()); } if
		 * (wardZoneDTO.getCodId2() != null) { wardZoneDTO.setCodId2Desc(
		 * CommonMasterUtility.getHierarchicalLookUp(wardZoneDTO.getCodId2(),
		 * org).getLookUpDesc());
		 * wardZoneDTO.setLevel2(listLookup.get(1).getLookUpDesc()); } if
		 * (wardZoneDTO.getCodId3() != null) { wardZoneDTO.setCodId3Desc(
		 * CommonMasterUtility.getHierarchicalLookUp(wardZoneDTO.getCodId3(),
		 * org).getLookUpDesc());
		 * wardZoneDTO.setLevel3(listLookup.get(2).getLookUpDesc()); } if
		 * (wardZoneDTO.getCodId4() != null) { wardZoneDTO.setCodId4Desc(
		 * CommonMasterUtility.getHierarchicalLookUp(wardZoneDTO.getCodId4(),
		 * org).getLookUpDesc());
		 * wardZoneDTO.setLevel4(listLookup.get(3).getLookUpDesc()); } if
		 * (wardZoneDTO.getCodId5() != null) { wardZoneDTO.setCodId5Desc(
		 * CommonMasterUtility.getHierarchicalLookUp(wardZoneDTO.getCodId5(),
		 * org).getLookUpDesc());
		 * wardZoneDTO.setLevel5(listLookup.get(4).getLookUpDesc()); } } }
		 */
		approvalModel.setDepartmentsList(ApplicationContextProvider.getApplicationContext()
				.getBean(TbDepartmentService.class).findMappedDepartments(org.getOrgid()));
		approvalModel.setWorkOrderDtoList(workOrderService.getAllLegacyWorkOrder(MainetConstants.FlagN,
				UserSession.getCurrent().getOrganisation().getOrgid()));
		approvalModel.getWorkflowActionDto().setReferenceId(workCode);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		short s = (short) UserSession.getCurrent().getLanguageId();
		List<WorkflowTaskActionWithDocs> actionWithDocs = workFlowActionService
				.getWorkflowActionLogByReferenceId(workCode, s);
		approvalModel.setActionWithDocs(actionWithDocs);

		return new ModelAndView(MainetConstants.WorksManagement.Measurement_Book_Approval, MainetConstants.FORM_NAME,
				this.getModel());

	}
	
	@RequestMapping(params = MainetConstants.WorksManagement.SHOW_CURRENT_FORM, method = { RequestMethod.POST,
            RequestMethod.GET })
    public ModelAndView showCurrentForm(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        return new ModelAndView(MainetConstants.WorksManagement.Measurement_Book_Approval, MainetConstants.FORM_NAME,
                this.getModel());
    }


	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.GET_TERMS_AND_CONDITION, method = RequestMethod.POST)
	public ModelAndView getWorkEstimate(@RequestParam(MainetConstants.WorksManagement.WORK_SANC_NO) String workSancNo,
			@RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId,
			@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId, final HttpServletRequest request) {
		getModel().bind(request);
		request.getSession().setAttribute(MainetConstants.WorksManagement.SAVEMODE,
				MainetConstants.WorksManagement.APPROVAL);
		request.getSession().setAttribute(MainetConstants.WorksManagement.WORKS_PARENT_ORGID,
				this.getModel().getParentOrgId());

		Object parentOrgId = this.getModel().getParentOrgId();
		return new ModelAndView(MainetConstants.WorksManagement.REDIRECTTO_APPROVALLETTER + projId
				+ MainetConstants.WorksManagement.AND_WORKID + workId + MainetConstants.WorksManagement.AND_WORKSAN_NO
				+ workSancNo + "&parentOrgId=" + parentOrgId);
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.SAVE_SANCTION_DETAILS, method = RequestMethod.POST)
	public Map<String, Object> saveSanctionDetails(HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		this.getModel().prepareSaveSanctionDetails();

		Map<String, Object> object = new LinkedHashMap<String, Object>();
		object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
		object.put(MainetConstants.WorksManagement.WORK_STATUS, this.getModel().getWorkDefinitionDto().getWorkStatus());
		object.put(MainetConstants.WorksManagement.PROJ_ID, this.getModel().getWorkDefinitionDto().getProjId());

		return object;
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.EDIT_WORK_ESTIMATE, method = RequestMethod.POST)
	public ModelAndView getWorkEstimate(@RequestParam(value = "workMbId") Long workMbId,
			@RequestParam(MainetConstants.WorksManagement.MODE) String mode, final HttpServletRequest request) {
		getModel().bind(request);
		request.getSession().setAttribute(MainetConstants.WorksManagement.SAVEMODE,
				MainetConstants.WorksManagement.APPROVAL);
		request.getSession().setAttribute(MainetConstants.WorksManagement.WORKS_PARENT_ORGID,
				this.getModel().getParentOrgId());
		
		
		  return new ModelAndView(MainetConstants.WorksManagement.REDIRECTTO_MBBOOK +
		  workMbId + MainetConstants.WorksManagement.AND_MODE +MainetConstants.FlagV);
		  
		 
	}
	
	 @Override
		@RequestMapping(params = "viewRefNoDetails")
		public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
				@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
				final HttpServletRequest request) throws Exception {

			sessionCleanup(request);
			ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
					.sessionCleanUpForFileUpload();
			MeasurmentBookApprovalModel approvalModel = this.getModel();
			
			//Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
			Organisation org = UserSession.getCurrent().getOrganisation();
			WorkflowRequest dto = ApplicationContextProvider.getApplicationContext()
					.getBean(IWorkflowRequestService.class)
					.getWorkflowRequestByAppIdOrRefId(null, applicationId,
							UserSession.getCurrent().getOrganisation().getOrgid());
			this.getModel().setCompletedFlag(MainetConstants.FlagY);
			Long parentOrgId = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
					.findById(dto.getWorkflowTypeId()).getCurrentOrgId();
			approvalModel.setParentOrgId(parentOrgId);

			approvalModel.setServiceId(serviceId);

		
		  MeasurementBookMasterDto mbMasDto = mbService.getWorkMbByMBCode(applicationId,UserSession.getCurrent().getOrganisation().getOrgid());
		  this.getModel().setWorkMbId(mbMasDto.getWorkMbId());
		 
		
		  approvalModel.setWorkOrderDto(workOrderService.getWorkOredrByOrderId(mbMasDto.getWorkOrId())); 
		  Long contractId = this.getModel().getWorkOrderDto().getContractMastDTO().getContId();
		  
		  TenderWorkDto tenderWorkDto = initiationService.findWorkByWorkId(contractId);
		  approvalModel.setEstimateMasDtoList(
		  workEstimateService.getWorkEstimateByWorkId(tenderWorkDto.getWorkId(),
		  parentOrgId));
		  
		  approvalModel.setMeasurementBookMasterDto(mbMasDto);
		  approvalModel.getWorkOrderDto().setMbTotalAmt(mbMasDto.getMbTotalAmt());
		  
		  approvalModel.getWorkOrderDto().setWorkName(tenderWorkDto.getWorkName());
		  approvalModel.getWorkOrderDto().setProjName(tenderWorkDto.getProjectName());
		  WorkDefinitionDto defDetails = workDefinitionService
		  .findAllWorkDefinitionById(approvalModel.getEstimateMasDtoList().get(0).
		  getWorkId());
		 
			approvalModel.setWorkDefinitionDto(defDetails);
			Organisation organisationById = orgService.getOrganisationById(defDetails.getOrgId());
		
		  List<LookUp> listLookup = CommonMasterUtility.getListLookup("ZWB",organisationById); 
		 
		  approvalModel.setDepartmentsList(ApplicationContextProvider. getApplicationContext()
		  .getBean(TbDepartmentService.class).findMappedDepartments(org.getOrgid()));
		  approvalModel.setWorkOrderDtoList(workOrderService.getAllLegacyWorkOrder(MainetConstants.FlagN,UserSession.getCurrent().getOrganisation().getOrgid()));
		  approvalModel.getWorkflowActionDto().setReferenceId(applicationId);
		 			approvalModel.getWorkflowActionDto().setTaskId(taskId);
			short s = (short) UserSession.getCurrent().getLanguageId();

			return new ModelAndView(MainetConstants.WorksManagement.Measurement_Book_Approval, MainetConstants.FORM_NAME,
					this.getModel());

		}

		}
	