package com.abm.mainet.audit.ui.controller;

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

import com.abm.mainet.audit.constant.IAuditConstants;
import com.abm.mainet.audit.dto.AuditParaEntryDto;
import com.abm.mainet.audit.service.IAuditParaEntryService;
import com.abm.mainet.audit.ui.model.AuditParaEntryApprovalModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;

@Controller
/* Standing Committee head can Approve, Reject */
@RequestMapping(IAuditConstants.AUDIT_PARA_WORKFLOW_L2_URL)
public class AuditParaStandingCommitteeApprovalController extends AbstractFormController<AuditParaEntryApprovalModel>
{
	
	@Autowired
	IAuditParaEntryService auditService;
	
	@Autowired
    private IFileUploadService fileUpload;
	
	@Autowired
	private IAttachDocsService iAttachDocsService;

	@Autowired
	IWorkflowActionService iWorkflowActionService;
	
	@Autowired
	private TbDepartmentService deptService;
	
	final static  String radioButtonsRequired = IAuditConstants.AUDIT_PARA_WORKFLOW_L1_RADIO_BTN;
	final static  String radioButtonsRequiredVal = IAuditConstants.AUDIT_PARA_WORKFLOW_L1_RADIO_BTN_VAL;
	final static  String controllerUrl = IAuditConstants.AUDIT_PARA_WORKFLOW_L1_URL.replace("/", "");
	// Need to move to Mainet Constants
	@RequestMapping(params =IAuditConstants.AUDIT_PARA_WORKFLOW_SHOW_DETAILS_PARAM, method = RequestMethod.POST)
    public ModelAndView auditParaApprovalView(
    		@RequestParam("appNo") final String auditParaCode,
    		@RequestParam("actualTaskId") final Long actualTaskId, 
    		@RequestParam("taskId") final Long serviceId,
    		@RequestParam("workflowId") final Long workflowId,
    		@RequestParam("taskName") final String taskName,
    		final HttpServletRequest request,
    		final Model model)
    {
		List<String> forwardToDept = new ArrayList<>();
		sessionCleanup(request);
		fileUpload.sessionCleanUpForFileUpload();
		request.getSession().setAttribute("auditCode", auditParaCode);
		request.getSession().setAttribute("auditTask", taskName);
		request.getSession().setAttribute("actualTaskId", actualTaskId);
		AuditParaEntryApprovalModel approvalModel = this.getModel();
		AuditParaEntryDto auditParaEntryData = auditService.getAuditParaEntryByAuditParaId(Long.valueOf(auditParaCode));
		approvalModel.setApprovalAuditParaDto(auditParaEntryData);
		model.addAttribute("deptList", loadDepartmentList());
		model.addAttribute("radioButtonsRequired", ApplicationSession.getInstance().getMessage("Approve.reject.forward"));
		model.addAttribute("radioButtonsRequiredVal", radioButtonsRequiredVal);
		model.addAttribute("controllerUrl", controllerUrl);
		List<LookUp> chiefAudDeptCodeLkp = auditService.fetchChiefAuditorDept();

		chiefAudDeptCodeLkp.forEach(lkp -> {
			if (UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptcode()
					.equalsIgnoreCase(lkp.getLookUpCode())) {
				//approvalModel.setKeyTest("Y");
				model.addAttribute("radioButtonsRequired", ApplicationSession.getInstance().getMessage("Approve.reject.forward"));
				model.addAttribute("radioButtonsRequiredVal", radioButtonsRequiredVal);

			}
			forwardToDept.add(deptService
					.findDeptByCode(UserSession.getCurrent().getOrganisation().getOrgid(), MainetConstants.FlagA,
							lkp.getLookUpCode())
					.getDpDeptid() + ","
					+ deptService.findDeptByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
							MainetConstants.FlagA, lkp.getLookUpCode()).getDpDeptdesc()
					+ " (Auditor Dept)");
		});
		
		Organisation org = UserSession.getCurrent().getOrganisation();
        final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
                .getBean(TbFinancialyearService.class).findAllFinancialYearByOrgId(org);
         List<TbFinancialyear> faYears = new ArrayList<>();
        if (finYearList != null && !finYearList.isEmpty()) {
            finYearList.forEach(finYearTemp -> {
                try {
                    finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
                    faYears.add(finYearTemp);
                } catch (Exception ex) {
                    //throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
                }
            });
        }
        String appId = "null";
		List<WorkflowTaskActionWithDocs> actionHistory = iWorkflowActionService.getActionLogByAppIdOrRefIdAndWorkflowId(appId, auditParaEntryData.getAuditParaCode(), auditParaEntryData.getAuditParaChk(),(short) UserSession.getCurrent().getLanguageId());
		model.addAttribute("actions", actionHistory);
        request.setAttribute("aFinancialYr", faYears);
		model.addAttribute("forwardToDept", forwardToDept);
		approvalModel.getWorkflowActionDto().setTaskId(Long.parseLong(request.getSession().getAttribute("actualTaskId").toString()));
		approvalModel.getWorkflowActionDto().setReferenceId(auditParaEntryData.getAuditParaCode());
		approvalModel.getWorkflowActionDto().setApplicationId(auditParaEntryData.getAuditParaId());
		AuditParaEntryDto dto=auditService.getAuditParaEntryByAuditParaId(Long.valueOf(auditParaCode));
		approvalModel.getWorkflowActionDto().setComments(dto.getCloserRemarks());
		approvalModel.setServiceId(serviceId);
		if(dto.getSubUnitClosed() != null) {
			approvalModel.setSubUnitClosed(dto.getSubUnitClosed());
		}
		if(auditParaEntryData.getCreatedBy().equals(UserSession.getCurrent().getEmployee().getEmpId()))
			approvalModel.setIsEditable(true);
		else
			approvalModel.setIsEditable(false);
		if(auditParaEntryData.getAuditAppendix() != null)
			approvalModel.setResolutionComments(auditParaEntryData.getAuditAppendix());
		approvalModel.setSaveMode(MainetConstants.MODE_VIEW);
		final List<AttachDocs> attachDocs = iAttachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                IAuditConstants.AUDIT_DEPT_CODE + MainetConstants.WINDOWS_SLASH + auditParaEntryData.getAuditParaId());
		this.getModel().setAttachDocsList(attachDocs);
		final List<AttachDocs> docsList = iAttachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),
                "AUDIT_PARA_ENTRY" + MainetConstants.WINDOWS_SLASH + auditParaEntryData.getAuditParaId());		
		this.getModel().setAttachDocumentList(docsList);
		return new ModelAndView(IAuditConstants.AUDIT_PARA_WORKFLOW_TILES,MainetConstants.FORM_NAME,this.getModel());
		
    }
	
	// Called from auditParaApproval.js for Approval
	@ResponseBody
	@RequestMapping(params = IAuditConstants.AUDIT_PARA_WORKFLOW_SAVE_PARAM, method = RequestMethod.POST)
	public Map<String, Object> saveAuditParaApproval(HttpServletRequest request)
    {
		getModel().bind(request);	
		this.getModel().saveAuditApprovalDetails(request.getSession().getAttribute("auditCode").toString(),UserSession.getCurrent().getOrganisation().getOrgid(),request.getSession().getAttribute("auditTask").toString());
		Map<String, Object> object = new LinkedHashMap<String, Object>();
	    object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	    object.put("auditWfStatus",	this.getModel().getApprovalAuditParaDto().getAuditWfStatus());	    
		return object;
    }
	
	@Override
	@RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			final HttpServletRequest request) throws Exception {
		sessionCleanup(request);
		AuditParaEntryApprovalModel approvalModel = this.getModel();
		AuditParaEntryDto auditParaEntryData = auditService.getAuditParaEntryByAuditParaId(Long.valueOf(applicationId));
		approvalModel.setApprovalAuditParaDto(auditParaEntryData);
		if(auditParaEntryData.getAuditAppendix() != null)
			approvalModel.setResolutionComments(auditParaEntryData.getAuditAppendix());
		request.setAttribute("aFinancialYr", loadFaYearsList());
		this.getModel().setDepAppList(loadDepartmentList());
		approvalModel.getWorkflowActionDto().setTaskId(taskId);
		approvalModel.setServiceId(serviceId);
		approvalModel.setSaveMode(MainetConstants.MODE_VIEW);		
		return new ModelAndView("auditParaEntryApproval",MainetConstants.FORM_NAME,this.getModel());		
    }
	
	
	/**
	 * @return Department List
	 */
	public List<TbDepartment> loadDepartmentList() {
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
			return deptService.getAllDeptBasedOnPrefix();
		}else {
			return deptService.findAllDept(UserSession.getCurrent().getOrganisation().getOrgid());
		}
	}
	
	private List<TbFinancialyear> loadFaYearsList() {

		Organisation org = UserSession.getCurrent().getOrganisation();
		final List<TbFinancialyear> finYearList = ApplicationContextProvider.getApplicationContext()
				.getBean(TbFinancialyearService.class).findAllFinancialYearByOrgId(org);
		List<TbFinancialyear> faYears = new ArrayList<>();
		if (finYearList != null && !finYearList.isEmpty()) {
			finYearList.forEach(finYearTemp -> {
				try {
					finYearTemp.setFaYearFromTo(Utility.getFinancialYearFromDate(finYearTemp.getFaFromDate()));
					faYears.add(finYearTemp);
				} catch (Exception ex) {
					// throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
				}
			});
		}

		return finYearList;

	}

	
	
	
	
	

}