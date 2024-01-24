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
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.dto.TbFinancialyear;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.dto.WorkflowTaskActionWithDocs;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;

@Controller
/* Chief Auditor can Approve, Reject, Send Back */
@RequestMapping(IAuditConstants.AUDIT_PARA_WORKFLOW_L1_URL)
public class AuditParaChiefAuditorApprovalController extends AbstractFormController<AuditParaEntryApprovalModel>
{
	
	@Autowired
	IAuditParaEntryService auditService;
	
	@Autowired
	 private TbDepartmentService deptService;
	
	@Autowired
    private IFileUploadService fileUpload;
	
	@Autowired
    private IAttachDocsService iAttachDocsService;

	@Autowired
	private IWorkflowActionService iWorkflowActionService;
	
	@Autowired
	private ServiceMasterService serviceMaster;
	
	@Autowired
    private TbAcVendormasterService tbAcVendormasterService;
	
	// Need to move to Mainet Constants
	final static String controllerUrl = IAuditConstants.AUDIT_PARA_WORKFLOW_L1_URL.replace("/", "");
	final static String radioButtonsRequired = IAuditConstants.AUDIT_PARA_WORKFLOW_L1_RADIO_BTN;
	final static String radioButtonsRequiredVal = IAuditConstants.AUDIT_PARA_WORKFLOW_L1_RADIO_BTN_VAL;
	
	
	@RequestMapping(params = IAuditConstants.AUDIT_PARA_WORKFLOW_SHOW_DETAILS_PARAM, method = RequestMethod.POST)
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
		
		List<LookUp> chiefAudDeptCodeLkp = auditService.fetchChiefAuditorDept();
		
		chiefAudDeptCodeLkp.forEach(lkp -> {
			if (UserSession.getCurrent().getEmployee().getTbDepartment().getDpDeptcode().equalsIgnoreCase(lkp.getLookUpCode()))
			{
				approvalModel.setKeyTest("Y");
				model.addAttribute("radioButtonsRequired", radioButtonsRequired );
				model.addAttribute("radioButtonsRequiredVal", radioButtonsRequiredVal);
				
			}							
			forwardToDept.add(deptService.findDeptByCode(UserSession.getCurrent().getOrganisation().getOrgid(),MainetConstants.FlagA,lkp.getLookUpCode()).getDpDeptid() + "," +deptService.findDeptByCode(UserSession.getCurrent().getOrganisation().getOrgid(),MainetConstants.FlagA,lkp.getLookUpCode()).getDpDeptdesc() + " (Auditor Dept)");
		});	
		String appId = "null";
		List<WorkflowTaskActionWithDocs> actionHistory = iWorkflowActionService.getActionLogByAppIdOrRefIdAndWorkflowId(appId, auditParaEntryData.getAuditParaCode(), auditParaEntryData.getAuditParaChk(),(short) UserSession.getCurrent().getLanguageId());
		model.addAttribute("actions", actionHistory);
        request.setAttribute("aFinancialYr", loadFaYearsList());
		model.addAttribute("deptList", loadDepartmentList());
		model.addAttribute("forwardToDept", forwardToDept);
		model.addAttribute("radioButtonsRequired", ApplicationSession.getInstance().getMessage("Approve.reject.forward"));
		model.addAttribute("radioButtonsRequiredVal", radioButtonsRequiredVal);
		model.addAttribute("controllerUrl", controllerUrl);
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
		return new ModelAndView(IAuditConstants.AUDIT_PARA_WORKFLOW_TILES,MainetConstants.FORM_NAME,this.getModel());
		
    }
	
	
	// Called from auditParaApproval.js for Approval
	@ResponseBody
	@RequestMapping(params = IAuditConstants.AUDIT_PARA_WORKFLOW_SAVE_PARAM, method = RequestMethod.POST)
	public Map<String, Object> saveAuditParaApproval(HttpServletRequest request)
    {
		getModel().bind(request);
		AuditParaEntryDto auditParaEntryDto=this.getModel().getApprovalAuditParaDto();
		List<Long> removeFileById = null;
		String fileId = auditParaEntryDto.getRemoveFileById();
		if (fileId != null && !fileId.isEmpty()) {
			removeFileById = new ArrayList<>();
			String fileArray[] = fileId.split(MainetConstants.operator.COMMA);
			for (String fields : fileArray) {
				removeFileById.add(Long.valueOf(fields));
			}
		}
		if (removeFileById != null && !removeFileById.isEmpty()) {				
			tbAcVendormasterService.updateUploadedFileDeleteRecords(removeFileById, auditParaEntryDto.getUpdatedBy());
		}
		this.getModel().saveAuditApprovalDetails(request.getSession().getAttribute("auditCode").toString(), UserSession.getCurrent().getOrganisation().getOrgid(),request.getSession().getAttribute("auditTask").toString());
		Map<String, Object> object = new LinkedHashMap<String, Object>();
	    object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	    object.put("auditWfStatus",	this.getModel().getApprovalAuditParaDto().getAuditWfStatus());	    
		return object;
    }
	

	@ResponseBody
	@RequestMapping(params = IAuditConstants.AUDIT_PARA_WORKFLOW_ACTION_PARAM, method = RequestMethod.POST)
	    public Map<String, String> getWorkFlowActionDetail(final HttpServletRequest httpServletRequest,
	            @RequestParam(value = "decision") String decision,
	            @RequestParam(value = "serEventId") String serEventId,
	            @RequestParam(value = "deptId") String deptId) {
	        Map<String, String> map = null;

	        this.getModel().getWorkflowActionDto()
	                .setSendBackToGroup(null);
	        this.getModel().getWorkflowActionDto()
	                .setSendBackToLevel(null);

	        if (this.getModel().getWorkflowActionDto().getApplicationId() == null
	                && (this.getModel().getWorkflowActionDto().getReferenceId() == null ||
	                        this.getModel().getWorkflowActionDto().getReferenceId().equals(""))
	                || this.getModel().getWorkflowActionDto().getTaskId() == null) {
	            logger.error("Application No or ReferenceId or TaskId is not set");
	        } else {
	          	           
	           if (decision.equals(MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE)) {
	            	/* Map will show Audit Department Users (Prefix setup) 
	            	 *  & Audit Para Department user */	            	
	            	
	            	
	            	//map = auditService.getEmpByAuditDeptId(this.getModel().getWorkflowActionDto().getReferenceId(),deptId);
	            	map=auditService.getEmpList(deptId);
	                if (map.containsKey(UserSession.getCurrent().getEmployee().getEmpId().toString()))
	                {
	                	map.remove(UserSession.getCurrent().getEmployee().getEmpId().toString());
	                }
	                
	            	this.getModel().setCheckActMap(map);
	                this.getModel().getWorkflowActionDto().setForwardToEmployeeType(MainetConstants.WorkFlow.EMPLOYEE);
	            } 
			/*
			 * else if (decision.equals(MainetConstants.WorkFlow.Decision.SEND_BACK)) {
			 * 
			 * }
			 */
	        }
	        return map;
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
		this.getModel().setDepAppList(loadDepartmentList());
		request.setAttribute("aFinancialYr", loadFaYearsList());
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
                    //throw new FrameworkException(EXCEPTION_IN_FINANCIAL_YEAR_DETAIL + ex);
                }
            });
        }
        
        return finYearList;
        
	}
	
	
	

}
