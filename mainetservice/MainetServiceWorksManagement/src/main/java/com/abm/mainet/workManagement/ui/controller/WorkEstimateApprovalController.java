package com.abm.mainet.workManagement.ui.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import com.abm.mainet.common.constant.MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
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
import com.abm.mainet.workManagement.dto.WorkDefinationWardZoneDetailsDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionSancDetDto;
import com.abm.mainet.workManagement.service.WorkDefinitionService;
import com.abm.mainet.workManagement.service.WorkEstimateService;
import com.abm.mainet.workManagement.ui.model.WorkEstimateApprovalModel;

/**
 * @author vishwajeet.kumar
 * @since 8 March 2018
 */
@Controller
@RequestMapping("/WorkEstimateApproval.html")
public class WorkEstimateApprovalController extends AbstractFormController<WorkEstimateApprovalModel> {

	private static final String EXCEPTION_IN_FINANCIAL_YEAR_DETAIL = "Exception while getting financial year Details :";
	@Resource
	@Autowired
	private IWorkflowActionService workFlowActionService;

	@Autowired
	private WorkDefinitionService workDefinationService;
	
	@Autowired
	private IOrganisationService orgService;
	
	@Autowired
    private ILocationMasService locationMasService;

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
		WorkEstimateApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("WorkEstimateApproval.html");
		Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Organisation org = UserSession.getCurrent().getOrganisation();
		Long parentOrgId = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class)
				.findById(workflowId).getCurrentOrgId();
		approvalModel.setParentOrgId(parentOrgId);
		approvalModel.setSaveMode(MainetConstants.WorksManagement.APPROVAL);
		approvalModel.setReportType(MainetConstants.FlagA);
		approvalModel.setServiceId(serviceId);
		WorkDefinitionDto defDetails = workDefinationService.findWorkDefinitionByWorkCode(workCode, parentOrgId);
		approvalModel.setWorkDefinitionDto(workDefinationService.findAllWorkDefinitionById(defDetails.getWorkId()));
		Organisation organisationById = orgService.getOrganisationById(defDetails.getOrgId());
		List<LookUp> listLookup = CommonMasterUtility.getListLookup("ZWB", organisationById);
		for(WorkDefinationWardZoneDetailsDto wardZoneDTO : approvalModel.getWorkDefinitionDto().getWardZoneDto()) {
			//WorkDefinationWardZoneDetailsDto definationWardZoneDetailsDto=new WorkDefinationWardZoneDetailsDto();
			if(wardZoneDTO.getCodId1() != null) {
				wardZoneDTO.setCodId1Desc(CommonMasterUtility.getHierarchicalLookUp(wardZoneDTO.getCodId1(), org).getLookUpDesc());
				wardZoneDTO.setLevel1(listLookup.get(0).getLookUpDesc());}
			if(wardZoneDTO.getCodId2() != null) {
				wardZoneDTO.setCodId2Desc(CommonMasterUtility.getHierarchicalLookUp(wardZoneDTO.getCodId2(), org).getLookUpDesc());
				wardZoneDTO.setLevel2(listLookup.get(1).getLookUpDesc());}
			if(wardZoneDTO.getCodId3() != null)	{
				wardZoneDTO.setCodId3Desc(CommonMasterUtility.getHierarchicalLookUp(wardZoneDTO.getCodId3(), org).getLookUpDesc());
				wardZoneDTO.setLevel3(listLookup.get(2).getLookUpDesc());}
			if(wardZoneDTO.getCodId4() != null) {
				wardZoneDTO.setCodId4Desc(CommonMasterUtility.getHierarchicalLookUp(wardZoneDTO.getCodId4(), org).getLookUpDesc());
				wardZoneDTO.setLevel4(listLookup.get(3).getLookUpDesc());}
			if(wardZoneDTO.getCodId5() != null) {
				wardZoneDTO.setCodId5Desc(CommonMasterUtility.getHierarchicalLookUp(wardZoneDTO.getCodId5(), org).getLookUpDesc());
				wardZoneDTO.setLevel5(listLookup.get(4).getLookUpDesc());}
		}
		
		
		approvalModel.setWorkId(this.getModel().getWorkDefinitionDto().getWorkId());
		setSanctionDetails(approvalModel.getWorkId());
		setBudgetDetails(approvalModel.getWorkId());
		approvalModel.setWorkCode(this.getModel().getWorkDefinitionDto().getWorkcode());
		approvalModel.setProjId(this.getModel().getWorkDefinitionDto().getProjId());
		approvalModel.setWorkflowId(workflowId);
		approvalModel
				.setWorkEstimateNo(ApplicationContextProvider.getApplicationContext().getBean(WorkEstimateService.class)
						.findWorkEstimateNoByWorkId(this.getModel().getWorkId(), parentOrgId));
		approvalModel.setDepartmentsList(ApplicationContextProvider.getApplicationContext()
				.getBean(TbDepartmentService.class).findMappedDepartments(currentOrgId));
		approvalModel.setEmployeeList(ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class)
				.getAllEmployeeNames(currentOrgId));
		approvalModel.getWorkflowActionDto().setReferenceId(workCode);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.setActualTaskId(actualTaskId);
		approvalModel.setTaskName(taskName);
		if(taskName.contains("Admin"))
			this.getModel().setSanctionFlag(MainetConstants.FlagA);
		else
			this.getModel().setSanctionFlag(MainetConstants.FlagT);
		WorkflowRequest workflowRequest = ApplicationContextProvider.getApplicationContext()
				.getBean(IWorkflowRequestService.class)
				.getWorkflowRequestByAppIdOrRefId(null, approvalModel.getWorkCode(), parentOrgId);

		final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
				.getBean(TbFinancialyearService.class).findAllFinancialYearByOrgId(org);
		// approvalModel.getFaYears().clear();
		List<TbFinancialyear> finYearTempList = new ArrayList<TbFinancialyear>();
		if (finYearList != null && !finYearList.isEmpty()) {
			finYearList.forEach(finYearTemp -> {
				try {
					finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
					finYearTempList.add(finYearTemp);
				} catch (Exception ex) {
					// throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
				}
			});
			approvalModel.setFaYears(finYearTempList);
			Comparator<TbFinancialyear> comparing = Comparator.comparing(TbFinancialyear::getFaYearFromTo,
					Comparator.reverseOrder());
			Collections.sort(approvalModel.getFaYears(), comparing);
		}

		LookUp defaultVal = CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
		if (defaultVal != null) {
			approvalModel.setCpdMode(CommonMasterUtility
					.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE).getLookUpCode());

			approvalModel.setBudgetList(ApplicationContextProvider.getApplicationContext()
					.getBean(SecondaryheadMasterService.class).getSecondaryHeadcodesForWorks(defDetails.getOrgId()));//#141913

		}

		if (approvalModel.getTaskName().equals(MainetConstants.WorksManagement.INITIATOR)
				&& workflowRequest.getLastDecision().equals(MainetConstants.WorkFlow.Decision.SEND_BACK)) {
			approvalModel.setEstimateMode(MainetConstants.WorksManagement.EDIT);
			approvalModel.setFlagForSendBack(workflowRequest.getLastDecision());
		} else {
			approvalModel.setEstimateMode(MainetConstants.FlagV);
		}
         //Defect #85072
     /*	approvalModel.setTermsConditionDtosList(
				ApplicationContextProvider.getApplicationContext().getBean(ApprovalTermsConditionService.class)
						.getTermsList(approvalModel.getWorkEstimateNo(), parentOrgId));*/

		short s = (short) UserSession.getCurrent().getLanguageId();
		//124175 Method call change. Getting the List<WorkflowTaskActionWithDocs> by reference Id
		List<WorkflowTaskActionWithDocs> actionWithDocs = workFlowActionService.getWorkflowActionLogByReferenceId(defDetails.getWorkcode(), s);
		approvalModel.setActionWithDocs(actionWithDocs);
		if (workflowRequest != null) {
			approvalModel.setOrgId(workflowRequest.getOrgId());
		}
		return new ModelAndView(MainetConstants.WorksManagement.WORK_ESTIMATE_APPROVAL, MainetConstants.FORM_NAME,
				this.getModel());

	}

	private void setBudgetDetails(Long workId) {
		this.getModel().setYearDetDtosList(workDefinationService.getAllBudgetHeadByWorkId(workId));

	}

	private void setSanctionDetails(Long workId) {
		List<WorkDefinitionSancDetDto> definitionSancDetslist = workDefinationService
				.getAllSanctionDetailsByWorkId(workId);
		if (!definitionSancDetslist.isEmpty() && definitionSancDetslist != null) {
			for (WorkDefinitionSancDetDto sancDetDto : definitionSancDetslist) {
				if (sancDetDto.getWorkSancNo() != null && !sancDetDto.getWorkSancNo().isEmpty()) {
					this.getModel().setSanctionNumber(sancDetDto.getWorkSancNo());
					this.getModel().setEmpName(sancDetDto.getWorkSancBy());
					this.getModel().setSancDate(
							new SimpleDateFormat(MainetConstants.DATE_FORMAT).format(sancDetDto.getWorkSancDate()));
				}
			}
		}
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.GET_WORK_ABSTRACTSHEET, method = RequestMethod.POST)
	public ModelAndView viewWorkEstimateReport(@RequestParam(MainetConstants.WorksManagement.PROJ_ID) Long projId,
			@RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId,
			@RequestParam(MainetConstants.WorksManagement.REPORT_TYPE) String reportType,
			@RequestParam(MainetConstants.Common_Constant.DEPTID) Long deptId,
			@RequestParam(MainetConstants.WorksManagement.WORKTYPE) Long workType, final HttpServletRequest request) {
		getModel().bind(request);
		request.getSession().setAttribute(MainetConstants.WorksManagement.SAVEMODE,
				MainetConstants.WorksManagement.APPROVAL);
		request.getSession().setAttribute(MainetConstants.WorksManagement.WORKS_PARENT_ORGID,
				this.getModel().getParentOrgId());

		return new ModelAndView(MainetConstants.WorksManagement.REDIRECTTO_WORKESTIMATE_REPORT + projId
				+ MainetConstants.WorksManagement.AND_WORKID + workId + MainetConstants.WorksManagement.AND_REPORTTYPE
				+ reportType + MainetConstants.WorksManagement.AND_DEPTID + deptId + "&workType=" + workType);

	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.EDIT_WORK_ESTIMATE, method = RequestMethod.POST)
	public ModelAndView getWorkEstimate(@RequestParam(MainetConstants.WorksManagement.WORK_ID) Long workId,
			@RequestParam(MainetConstants.WorksManagement.MODE) String mode, final HttpServletRequest request) {
		getModel().bind(request);
		request.getSession().setAttribute(MainetConstants.WorksManagement.SAVEMODE,
				MainetConstants.WorksManagement.APPROVAL);
		request.getSession().setAttribute(MainetConstants.WorksManagement.WORKS_PARENT_ORGID,
				this.getModel().getParentOrgId());
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL) || Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_ASCL) ||  Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL) ) {
		return new ModelAndView(MainetConstants.WorksManagement.REDIRECTTO_WORKESTIMATE + workId
				+ MainetConstants.WorksManagement.AND_MODE + MainetConstants.FlagE);
		}
		else {
			return new ModelAndView(MainetConstants.WorksManagement.REDIRECTTO_WORKESTIMATE + workId
					+ MainetConstants.WorksManagement.AND_MODE + mode);
		}
	}

	@RequestMapping(params = MainetConstants.WorksManagement.SHOWAPPROVAL_CURRENTFORM, method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView showCurrentpage(HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		final WorkEstimateApprovalModel approvalModel = this.getModel();
		return new ModelAndView(MainetConstants.WorksManagement.WORK_ESTIMATE_APPROVAL, MainetConstants.FORM_NAME,
				approvalModel);
	}

	@RequestMapping(params = MainetConstants.WorksManagement.SHOW_ESTIMATE_CURRENTFORM, method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView showCurrentApproval(HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		if(getModel().getWorkId()!=null){
			this.getModel().setWorkDefinitionDto(workDefinationService.findAllWorkDefinitionById(getModel().getWorkId()));
		}
		final WorkEstimateApprovalModel approvalModel = this.getModel();
		return new ModelAndView(MainetConstants.WorksManagement.WORK_ESTIMATE_APPROVAL, MainetConstants.FORM_NAME,
				approvalModel);
	}

	@ResponseBody
	@RequestMapping(params = MainetConstants.WorksManagement.SAVE_SANCTION_DETAILS, method = RequestMethod.POST)
	public Map<String, Object> saveSanctionDetails(HttpServletRequest httpServletRequest) {
		getModel().bind(httpServletRequest);
		this.getModel().prepareSaveSanctionDetails();

		Map<String, Object> object = new LinkedHashMap<String, Object>();
		object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
		object.put(MainetConstants.WorksManagement.SANCTION_NUMBER,
				this.getModel().getWorkDefinitionDto().getSanctionNumber());
		object.put(MainetConstants.WorksManagement.WORK_ID, this.getModel().getWorkDefinitionDto().getWorkId());
		object.put(MainetConstants.WorksManagement.PROJ_ID, this.getModel().getWorkDefinitionDto().getProjId());
		object.put(MainetConstants.WorksManagement.WORK_STATUS, this.getModel().getWorkDefinitionDto().getWorkStatus());
		return object;

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
		Long parentOrgId = this.getModel().getParentOrgId();
		return new ModelAndView(MainetConstants.WorksManagement.REDIRECTTO_APPROVALLETTER + projId
				+ MainetConstants.WorksManagement.AND_WORKID + workId + MainetConstants.WorksManagement.AND_WORKSAN_NO
				+ workSancNo + "&parentOrgId=" + parentOrgId);
	}
	
	 @ResponseBody
	    @RequestMapping(params = MainetConstants.Council.Proposal.GET_BUDGET_HEAD_DETAIL, method = RequestMethod.POST)
	    public VendorBillApprovalDTO checkBudgetHeadDetails(
	            @RequestParam(MainetConstants.Council.Proposal.SAC_HEAD_ID) final Long sacHeadId,
	            @RequestParam(MainetConstants.Council.Proposal.YEAR_ID) final Long yearId,
	            @RequestParam(MainetConstants.Council.Proposal.PROPOSAL_AMOUNT) final BigDecimal proposalAmt,
	            @RequestParam("fieldId") final Long fieldId,@RequestParam("dpDeptId") final Long dpDeptId) {
	        Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
	        WorkEstimateApprovalModel approvalModel = this.getModel();
	        VendorBillApprovalDTO budgetHeadDTO = new VendorBillApprovalDTO();
	        budgetHeadDTO.setBillAmount(proposalAmt);
	        budgetHeadDTO.setDepartmentId(dpDeptId);
	        budgetHeadDTO.setFaYearid(yearId);
	        budgetHeadDTO.setBudgetCodeId(sacHeadId);
	        budgetHeadDTO.setOrgId(orgId);
	        Long fieldId1=null;
	        if(fieldId!=null) {
	        fieldId1=locationMasService.getFieldIdWithWard(approvalModel.getWorkDefinitionDto().getDeptId(), approvalModel.getWorkDefinitionDto().getWardZoneDto().get(0).getCodId1(), UserSession.getCurrent().getOrganisation().getOrgid());
	    		}
	        budgetHeadDTO.setFieldId(fieldId1);
	        VendorBillApprovalDTO dto = workDefinationService.getBudgetExpenditure(budgetHeadDTO);
	        if (dto != null) {
	            dto.setBillAmount(proposalAmt.setScale(2, RoundingMode.UP));
	            dto.setAuthorizationStatus(MainetConstants.FlagY);
	            if (dto.getInvoiceAmount().subtract(dto.getSanctionedAmount()).compareTo(proposalAmt) < 0) {
	                dto.setDisallowedRemark(MainetConstants.FlagY);
	            }
	        } else {
	            dto = new VendorBillApprovalDTO();
	            dto.setAuthorizationStatus(MainetConstants.FlagN);
	        }

	        return dto;
	    }
	 
	 @Override
		@RequestMapping(params = "viewRefNoDetails")
		public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
				@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
				final HttpServletRequest request) throws Exception {

			sessionCleanup(request);
			ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
					.sessionCleanUpForFileUpload();
			WorkEstimateApprovalModel approvalModel = this.getModel();
			this.getModel().setCommonHelpDocs("WorkEstimateApproval.html");
			Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
			Organisation org = UserSession.getCurrent().getOrganisation();
			WorkflowRequest dto = ApplicationContextProvider.getApplicationContext()
					.getBean(IWorkflowRequestService.class)
					.getWorkflowRequestByAppIdOrRefId(null, applicationId,
							UserSession.getCurrent().getOrganisation().getOrgid());
			this.getModel().setCompletedFlag(MainetConstants.FlagY);
			
			 Long parentOrgId = ApplicationContextProvider.getApplicationContext().getBean(IWorkFlowTypeService.class).findById(dto.getWorkflowTypeId()).getCurrentOrgId();
			 approvalModel.setParentOrgId(parentOrgId);
			approvalModel.setSaveMode(MainetConstants.WorksManagement.APPROVAL);
			approvalModel.setReportType(MainetConstants.FlagA);
			approvalModel.setServiceId(serviceId);
			 WorkDefinitionDto defDetails = workDefinationService.findWorkDefinitionByWorkCode(applicationId, parentOrgId);
			approvalModel.setWorkDefinitionDto(workDefinationService.findAllWorkDefinitionById(defDetails.getWorkId()));
				Organisation organisationById = orgService.getOrganisationById(defDetails.getOrgId());
			 List<LookUp> listLookup = CommonMasterUtility.getListLookup("ZWB", organisationById);
			
			  for(WorkDefinationWardZoneDetailsDto wardZoneDTO :
			  approvalModel.getWorkDefinitionDto().getWardZoneDto()) {
			  WorkDefinationWardZoneDetailsDto definationWardZoneDetailsDto=new WorkDefinationWardZoneDetailsDto();
			  
			  if(wardZoneDTO.getCodId1() != null) {
			  wardZoneDTO.setCodId1Desc(CommonMasterUtility.getHierarchicalLookUp(wardZoneDTO.getCodId1(), org).getLookUpDesc());
			  wardZoneDTO.setLevel1(listLookup.get(0).getLookUpDesc());
			  }
			  if(wardZoneDTO.getCodId2() != null) {
			  wardZoneDTO.setCodId2Desc(CommonMasterUtility.getHierarchicalLookUp(wardZoneDTO.getCodId2(), org).getLookUpDesc());
			  wardZoneDTO.setLevel2(listLookup.get(1).getLookUpDesc());
			  }
			  if(wardZoneDTO.getCodId3() != null) {
			  wardZoneDTO.setCodId3Desc(CommonMasterUtility.getHierarchicalLookUp(wardZoneDTO.getCodId3(), org).getLookUpDesc());
			  wardZoneDTO.setLevel3(listLookup.get(2).getLookUpDesc());
			  }
			  if(wardZoneDTO.getCodId4() != null) {
			  wardZoneDTO.setCodId4Desc(CommonMasterUtility.getHierarchicalLookUp(wardZoneDTO.getCodId4(), org).getLookUpDesc());
			  wardZoneDTO.setLevel4(listLookup.get(3).getLookUpDesc());
			  }
			  if(wardZoneDTO.getCodId5() != null) {
			  wardZoneDTO.setCodId5Desc(CommonMasterUtility.getHierarchicalLookUp(wardZoneDTO.getCodId5(), org).getLookUpDesc());
			  wardZoneDTO.setLevel5(listLookup.get(4).getLookUpDesc());
			  }
			  
			  }
			 

			approvalModel.setWorkId(this.getModel().getWorkDefinitionDto().getWorkId());
			setSanctionDetails(approvalModel.getWorkId());
			setBudgetDetails(approvalModel.getWorkId());
			approvalModel.setWorkCode(this.getModel().getWorkDefinitionDto().getWorkcode());
			approvalModel.setProjId(this.getModel().getWorkDefinitionDto().getProjId());
			
			 approvalModel.setWorkflowId(dto.getWorkflowTypeId());
			
			 approvalModel.setWorkEstimateNo(ApplicationContextProvider.getApplicationContext().getBean(WorkEstimateService.class).findWorkEstimateNoByWorkId(this.getModel().getWorkId(), parentOrgId));
			 
			approvalModel.setDepartmentsList(ApplicationContextProvider.getApplicationContext()
					.getBean(TbDepartmentService.class).findMappedDepartments(currentOrgId));
			approvalModel.setEmployeeList(ApplicationContextProvider.getApplicationContext().getBean(IEmployeeService.class)
					.getAllEmployeeNames(currentOrgId));
			approvalModel.getWorkflowActionDto().setReferenceId(applicationId);
			approvalModel.getWorkflowActionDto().setTaskId(taskId);
			approvalModel.setActualTaskId(taskId);
			
			final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
					.getBean(TbFinancialyearService.class).findAllFinancialYearByOrgId(org);

			List<TbFinancialyear> finYearTempList = new ArrayList<TbFinancialyear>();
			if (finYearList != null && !finYearList.isEmpty()) {
				finYearList.forEach(finYearTemp -> {
					try {
						finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
						finYearTempList.add(finYearTemp);
					} catch (Exception ex) {
						
					}
				});
				approvalModel.setFaYears(finYearTempList);
				Comparator<TbFinancialyear> comparing = Comparator.comparing(TbFinancialyear::getFaYearFromTo,
						Comparator.reverseOrder());
				Collections.sort(approvalModel.getFaYears(), comparing);
			}

			LookUp defaultVal = CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
			if (defaultVal != null) {
				approvalModel.setCpdMode(CommonMasterUtility
						.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE).getLookUpCode());

				
				  approvalModel.setBudgetList(ApplicationContextProvider.getApplicationContext().getBean(SecondaryheadMasterService.class).getSecondaryHeadcodesForWorks(defDetails.getOrgId()));//#141913
				 
			}

			short s = (short) UserSession.getCurrent().getLanguageId();
			
			  List<WorkflowTaskActionWithDocs> actionWithDocs =workFlowActionService.getWorkflowActionLogByReferenceId(defDetails.getWorkcode(), s);
			  approvalModel.setActionWithDocs(actionWithDocs);
			 
			
			return new ModelAndView(MainetConstants.WorksManagement.WORK_ESTIMATE_APPROVAL, MainetConstants.FORM_NAME,
					this.getModel());

		}


}
