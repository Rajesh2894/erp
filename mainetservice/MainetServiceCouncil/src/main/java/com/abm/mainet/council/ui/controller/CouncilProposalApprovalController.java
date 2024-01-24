package com.abm.mainet.council.ui.controller;

import java.util.ArrayList;
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
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.MainetConstants.BUG_HEAD_OPENING_BALANCE_MASTER;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.acccount.dto.AccountFundMasterBean;
import com.abm.mainet.common.integration.acccount.service.AccountFieldMasterService;
import com.abm.mainet.common.integration.acccount.service.SecondaryheadMasterService;
import com.abm.mainet.common.integration.acccount.service.TbAcCodingstructureMasService;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.master.service.TbOrganisationService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.service.IWorkflowRequestService;
import com.abm.mainet.council.dto.CouncilProposalMasterDto;
import com.abm.mainet.council.service.ICouncilProposalMasterService;
import com.abm.mainet.council.ui.model.CouncilProposalApprovalModel;

/**
 * @author aarti.paan
 * @since 24th May 2019
 */
@Controller
@RequestMapping(MainetConstants.Council.Council_Management_Workflow.PROPOSALAPPROVAL_URL)

public class CouncilProposalApprovalController extends AbstractFormController<CouncilProposalApprovalModel> {

    // As per HTML Getting all Ward list and given as drop down but thereafter removed and getting from create proposal
    /*
     * @RequestMapping(method = { RequestMethod.POST, RequestMethod.GET }) public ModelAndView index(HttpServletRequest request)
     * throws Exception { this.sessionCleanup(request); CouncilProposalApprovalModel model = this.getModel(); Long orgId =
     * UserSession.getCurrent().getOrganisation().getOrgid();
     * model.setLookupListLevel1(CommonMasterUtility.getNextLevelData("EWZ", 1, orgId)); return defaultResult(); }
     */
    @Autowired
    private ICouncilProposalMasterService iCouncilProposalMasterService;

    @Autowired
    private TbDepartmentService iTbDepartmentService;

    @Autowired
    private IWorkflowRequestService WorkflowRequestService;
    
    @Autowired
    private IEmployeeService employeeService;
    
    @Resource
	private AccountFieldMasterService tbAcFieldMasterService;
    
    @Autowired
	private TbOrganisationService tbOrganisationService;
    
    @Resource
	private TbAcCodingstructureMasService tbAcCodingstructureMasService;

    @ResponseBody
    @RequestMapping(params = MainetConstants.Council.Council_Management_Workflow.SHOWDETAILS, method = RequestMethod.POST)
    public ModelAndView proposalApproval(
            @RequestParam(MainetConstants.Council.Council_Management_Workflow.APP_NO) final String proposalNo,
            @RequestParam(value = MainetConstants.Council.Council_Management_Workflow.ACTUAL_TASKID, required = false) final Long actualTaskId,
            @RequestParam(value = MainetConstants.Council.Council_Management_Workflow.TASK_ID, required = false) final Long serviceId,
            @RequestParam(value = MainetConstants.Council.Council_Management_Workflow.WORKFLOW_ID, required = false) final Long workflowId,
            @RequestParam(value = MainetConstants.Council.Council_Management_Workflow.TASK_NAME, required = false) final String taskName,
            final HttpServletRequest httpServletRequest, final Model model) {

        sessionCleanup(httpServletRequest);

        ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
        CouncilProposalApprovalModel approvalModel = this.getModel();
        Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        approvalModel.setServiceId(serviceId);

        CouncilProposalMasterDto proposalTempData = iCouncilProposalMasterService.getCouncilProposalMasterByproposalNo(proposalNo,
                currentOrgId);
        // get proposal id For further data fetch basically doing this reuse code for getting data
        Long proposalId = proposalTempData.getProposalId();
        CouncilProposalMasterDto proposalMasterDto = iCouncilProposalMasterService
                .getCouncilProposalMasterByproposalId(proposalId);
        approvalModel.setCouProposalMasterDto(proposalMasterDto);
        

        approvalModel.getCouProposalMasterDto()
                .setProposalDeptName(UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptdesc());
        Long deptId = approvalModel.getCouProposalMasterDto().getProposalDepId();
        Department dept = iTbDepartmentService.findDepartmentById(deptId);
        approvalModel.getCouProposalMasterDto().setProposalDeptName(dept.getDpDeptdesc());
        approvalModel.setDeptId(deptId);
        approvalModel.setLookupListLevel1(
                CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 1, currentOrgId));
        approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
        approvalModel.getWorkflowActionDto().setReferenceId(proposalNo);

        // As per HTML Developed thereafter as per nilima mam removed
        /*
         * Proposal Workflow History String proposalNumber =
         * iCouncilProposalMasterService.getCouncilProposalMasterByproposalNo(proposalNo, currentOrgId) .getProposalNo();
         * WorkflowRequest workflowRequest = WorkflowRequestService.getWorkflowRequestByAppIdOrRefId(null, proposalNumber,
         * currentOrgId); List<WorkflowTaskActionWithDocs> actionHistory =
         * ApplicationContextProvider.getApplicationContext().getBean(IWorkflowActionService.class)
         * .getActionLogByUuidAndWorkflowId(proposalNumber, workflowRequest.getId(), (short)
         * UserSession.getCurrent().getLanguageId()); approvalModel.setActionHistory(actionHistory);
         */

        /*// get attached document
        final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext().getBean(IAttachDocsService.class)
                .findByCode(
                        UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.Council.Proposal.FILE_UPLOAD_IDENTIFIER + MainetConstants.WINDOWS_SLASH + proposalId);
        this.getModel().setAttachDocsList(attachDocs);*/
        /*Defect 90802*/
        // get attached document
        
        Organisation org = UserSession.getCurrent().getOrganisation();
        final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).findAllFinancialYearByOrgId(org);
        approvalModel.getFaYears().clear();
        if (finYearList != null && !finYearList.isEmpty()) {
            finYearList.forEach(finYearTemp -> {
                try {
                    finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                    approvalModel.getFaYears().add(finYearTemp);
                } catch (Exception ex) {
                    //throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
                }
            });
        }
        
        LookUp defaultVal = CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
        if (defaultVal != null) {
        	approvalModel.setCpdMode(defaultVal.getLookUpCode());
            this.getModel().setCpdMode(defaultVal.getLookUpCode());
            if (approvalModel.getCpdMode().equals(MainetConstants.FlagL)) {

            	approvalModel.setBudgetList(ApplicationContextProvider.getApplicationContext()
                        .getBean(SecondaryheadMasterService.class).getSecondaryHeadcodesForWorks(org.getOrgid()));
            }
        } else {
        	approvalModel.setCpdMode(null);
        }
        
        
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			boolean isSendBack = taskName.equals(MainetConstants.WorkFlow.EventNames.INITIATOR);

			// Set the SendBackFlag in the approval model based on the decision
			approvalModel.setSendBackFlag(!isSendBack);
		}
        
    	addAttachedDoc(proposalId);
    	Map<Long, String> fieldList = tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid());
        model.addAttribute(MainetConstants.BUDGET_PROJECTED_EXPENDITURE_MASTER.FIELD_MASTER_ITEMS, fieldList);
        approvalModel.setFieldList(fieldList);
    	populateFund();
        return new ModelAndView(MainetConstants.Council.Council_Management_Workflow.COUNCIL_PROPOSAL_APPROVAL,
                MainetConstants.FORM_NAME,
                this.getModel());

    }

    // As per HTML Developed thereafter as per nilima mam removed
    /*
     * @RequestMapping(params = "getWorkFlowHistory", method = RequestMethod.POST) public @ResponseBody ModelAndView
     * getWorkFlowHistory(
     * @RequestParam(name = "proposalNo") final String proposalNo,
     * @RequestParam(name = "mode") final String mode, ModelMap modelMap) { ApplicationContextProvider.getApplicationContext();
     * Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid(); String proposalNumber =
     * iCouncilProposalMasterService.getCouncilProposalMasterByproposalNo(proposalNo, currentOrgId) .getProposalNo();
     * WorkflowRequest workflowRequest = applicationContext.getBean(WorkflowRequestService.class)
     * .getWorkflowRequestByAppIdOrRefId(null, proposalNumber, currentOrgId); List<WorkflowTaskActionWithDocs> actionHistory =
     * applicationContext.getBean(IWorkflowActionService.class) .getActionLogByUuidAndWorkflowId(proposalNumber,
     * workflowRequest.getId(), (short) UserSession.getCurrent().getLanguageId()); modelMap.put("actions", actionHistory); return
     * new ModelAndView("councilWorkFlowHistory", MainetConstants.FORM_NAME, modelMap); }
     */

    @ResponseBody
    @RequestMapping(params = MainetConstants.Council.Council_Management_Workflow.SAVE_PROPOSAL_APPROVAL, method = RequestMethod.POST)
    public Map<String, Object> saveProposalApprovalDetails(HttpServletRequest httpServletRequest) {
        getModel().bind(httpServletRequest);
        this.getModel().saveProposalApprovalDetails();
        Map<String, Object> object = new LinkedHashMap<String, Object>();
        object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
        object.put(MainetConstants.Council.Council_Management_Workflow.WORKFLOW_STATUS,
                this.getModel().getCouProposalMasterDto().getWfStatus());

        return object;

    }
    
  //Add attached doc to model
    private void addAttachedDoc(Long proposalId) {
    	final List<AttachDocs> attachDocs = ApplicationContextProvider.getApplicationContext()
                .getBean(IAttachDocsService.class).findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                        MainetConstants.Council.Proposal.FILE_UPLOAD_IDENTIFIER + MainetConstants.WINDOWS_SLASH
                                + proposalId);

    	List<DocumentDetailsVO> documentDtos = new ArrayList<>();
        // iterate and set document details
        attachDocs.forEach(doc -> {
        	DocumentDetailsVO docDto = new DocumentDetailsVO();
            // get employee name who attach this image
            //Employee emp = employeeService.findEmployeeById(doc.getUserId());
            docDto.setDocumentName(doc.getAttFname());
            docDto.setAttachmentId(doc.getAttId());
            docDto.setUploadedDocumentPath(doc.getAttPath());
            documentDtos.add(docDto);
        });
        this.getModel().setAttachments(documentDtos);
    }
    
    public void populateFund()
	{
		boolean fieldDefaultFlag = false;
		final boolean isDafaultOrgExist = tbOrganisationService.defaultexist(MainetConstants.MASTER.Y);
		boolean fundDefaultFlag = false;
		if (isDafaultOrgExist) {
			fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
					ApplicationSession.getInstance().getSuperUserOrganization().getOrgid(), MainetConstants.MASTER.Y);
		} else {
			fundDefaultFlag = tbAcCodingstructureMasService.checkDefaultFlagIsExists(PrefixConstants.FUND_CPD_VALUE,
					UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.MASTER.Y);
		}
		Organisation defultorg = null;
		Long defultorgId = null;
		if (isDafaultOrgExist && fundDefaultFlag) {
			defultorg = ApplicationSession.getInstance().getSuperUserOrganization();
			defultorgId = ApplicationSession.getInstance().getSuperUserOrganization().getOrgid();
		} else if (isDafaultOrgExist && (fundDefaultFlag == false)) {
			defultorg = UserSession.getCurrent().getOrganisation();
			defultorgId = UserSession.getCurrent().getOrganisation().getOrgid();
		} else {
			defultorg = UserSession.getCurrent().getOrganisation();
			defultorgId = UserSession.getCurrent().getOrganisation().getOrgid();
		}
		final LookUp fundLookup = CommonMasterUtility.getLookUpFromPrefixLookUpValue(PrefixConstants.FUND_CPD_VALUE,
				PrefixConstants.CMD, UserSession.getCurrent().getLanguageId(),
				UserSession.getCurrent().getOrganisation());
		List<AccountFundMasterBean> fundList = tbAcCodingstructureMasService.getFundMasterActiveStatusList(defultorgId, defultorg,
				fundLookup.getLookUpId(), UserSession.getCurrent().getLanguageId());
		this.getModel().setFundList(fundList);
	}
    
    @Override
    @RequestMapping(params = "viewRefNoDetails") 
   	public ModelAndView viewDetails(@RequestParam("appNo") final String proposalNo,
   			@RequestParam("taskId") final long serviceId,@RequestParam("actualTaskId") final long actualTaskId, final HttpServletRequest request )throws Exception{      
  
        sessionCleanup(request);
        ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
        CouncilProposalApprovalModel approvalModel = this.getModel();
        Long currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
        approvalModel.setServiceId(serviceId);

        CouncilProposalMasterDto proposalTempData = iCouncilProposalMasterService.getCouncilProposalMasterByproposalNo(proposalNo,
                currentOrgId);
        // get proposal id For further data fetch basically doing this reuse code for getting data
        Long proposalId = proposalTempData.getProposalId();
        CouncilProposalMasterDto proposalMasterDto = iCouncilProposalMasterService
                .getCouncilProposalMasterByproposalId(proposalId);
        approvalModel.setCouProposalMasterDto(proposalMasterDto);
        

        approvalModel.getCouProposalMasterDto()
                .setProposalDeptName(UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptdesc());
        Long deptId = approvalModel.getCouProposalMasterDto().getProposalDepId();
        Department dept = iTbDepartmentService.findDepartmentById(deptId);
        approvalModel.getCouProposalMasterDto().setProposalDeptName(dept.getDpDeptdesc());
        approvalModel.setDeptId(deptId);
        approvalModel.setLookupListLevel1(
                CommonMasterUtility.getNextLevelData(MainetConstants.Council.ELECTION_WARD_PREFIX, 1, currentOrgId));
        approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
        approvalModel.getWorkflowActionDto().setReferenceId(proposalNo);
        
        Organisation org = UserSession.getCurrent().getOrganisation();
        final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).findAllFinancialYearByOrgId(org);
        approvalModel.getFaYears().clear();
        if (finYearList != null && !finYearList.isEmpty()) {
            finYearList.forEach(finYearTemp -> {
                try {
                    finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                    approvalModel.getFaYears().add(finYearTemp);
                } catch (Exception ex) {
                    //throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
                }
            });
        }
        
        LookUp defaultVal = CommonMasterUtility.getDefaultValue(BUG_HEAD_OPENING_BALANCE_MASTER.SLI_PREFIX_VALUE);
        if (defaultVal != null) {
        	approvalModel.setCpdMode(defaultVal.getLookUpCode());
            this.getModel().setCpdMode(defaultVal.getLookUpCode());
            if (approvalModel.getCpdMode().equals(MainetConstants.FlagL)) {

            	approvalModel.setBudgetList(ApplicationContextProvider.getApplicationContext()
                        .getBean(SecondaryheadMasterService.class).getSecondaryHeadcodesForWorks(org.getOrgid()));
            }
        } else {
        	approvalModel.setCpdMode(null);
        }
        approvalModel.setViewFlag("Y");
    	addAttachedDoc(proposalId);
    	
    	 Map<Long, String> fieldList = tbAcFieldMasterService.getFieldMasterLastLevels(UserSession.getCurrent().getOrganisation().getOrgid());
    	 approvalModel.setFieldList(fieldList);
		
    	populateFund();
        return new ModelAndView(MainetConstants.Council.Council_Management_Workflow.COUNCIL_PROPOSAL_APPROVAL,
                MainetConstants.FORM_NAME,
                this.getModel());

    }
}
