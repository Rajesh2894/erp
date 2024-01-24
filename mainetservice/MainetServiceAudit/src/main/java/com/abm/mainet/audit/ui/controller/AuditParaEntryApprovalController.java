package com.abm.mainet.audit.ui.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.audit.dto.AuditParaEntryDto;
import com.abm.mainet.audit.service.IAuditParaEntryService;
import com.abm.mainet.audit.ui.model.AuditParaEntryApprovalModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.master.dto.TbDepartment;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbDepartmentService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;


@Controller
@RequestMapping("/AuditParaEntryApproval.html")

public class AuditParaEntryApprovalController extends AbstractFormController<AuditParaEntryApprovalModel>
{
	
	@Autowired
	IAuditParaEntryService auditService;
	
	@Autowired
	private TbDepartmentService deptService;
	
	String auditCode;
	Long currentOrgId;
	String task;
	
	
	
    @RequestMapping(params = "showDetails", method = RequestMethod.POST)
    public ModelAndView auditParaApprovalView(
    		@RequestParam("appNo") final String auditParaCode,
    		@RequestParam("actualTaskId") final Long actualTaskId, 
    		@RequestParam("taskId") final Long serviceId,
    		@RequestParam("workflowId") final Long workflowId,
    		@RequestParam("taskName") final String taskName,
    		final HttpServletRequest request,
    		final Model model)
    {
		sessionCleanup(request);
		auditCode=auditParaCode;
		task=taskName;
		AuditParaEntryApprovalModel approvalModel = this.getModel();
				
		currentOrgId = UserSession.getCurrent().getOrganisation().getOrgid();
		AuditParaEntryDto auditParaEntryData = auditService.getAuditParaEntryByAuditParaId(Long.valueOf(auditParaCode));
		approvalModel.setApprovalAuditParaDto(auditParaEntryData);
		model.addAttribute("deptList", loadDepartmentList());
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setReferenceId(auditParaCode);
		approvalModel.setServiceId(serviceId);
		if(auditParaEntryData.getAuditAppendix() != null)
			approvalModel.setResolutionComments(auditParaEntryData.getAuditAppendix());
		approvalModel.setSaveMode(MainetConstants.MODE_VIEW);
		
		return new ModelAndView("auditParaEntryApproval",MainetConstants.FORM_NAME,this.getModel());
		
    }
	
	// Called from auditParaApproval.js for Approval
	@ResponseBody
	@RequestMapping(params = "saveAuditParaApproval", method = RequestMethod.POST)
	public Map<String, Object> saveAuditParaApproval(HttpServletRequest request)
    {
		getModel().bind(request);
		this.getModel().saveAuditApprovalDetails(auditCode,currentOrgId,task);
		Map<String, Object> object = new LinkedHashMap<String, Object>();
	    object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	    object.put("auditWfStatus",	this.getModel().getApprovalAuditParaDto().getAuditWfStatus());	    
		return object;
    }
	
	// Get Active Department List from Department Master Dto
		public List<TbDepartment> loadDepartmentList() {
			if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_TSCL)) {
				return deptService.getAllDeptBasedOnPrefix();
			}else {
				return deptService.findAllDept(UserSession.getCurrent().getOrganisation().getOrgid());
			}		}

	
	

	
	
	

}
