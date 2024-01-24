package com.abm.mainet.sfac.ui.controller;

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
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.sfac.dto.AuditBalanceSheetMasterDto;
import com.abm.mainet.sfac.service.AuditBalanceSheetEntryService;
import com.abm.mainet.sfac.ui.model.ABSEntryRequestApprovalModel;

@Controller
@RequestMapping(MainetConstants.Sfac.ABS_ENTRY_REQ_FORM_APPROVAL_HTML)
public class AuditBalanceSheetApprovalController extends AbstractFormController<ABSEntryRequestApprovalModel>{

	
	@Autowired AuditBalanceSheetEntryService auditBalanceSheetEntryService;
	
	@ResponseBody
	@RequestMapping(params = MainetConstants.Sfac.SHOWDETAILS, method = RequestMethod.POST)
	public ModelAndView showDetails(
			@RequestParam(MainetConstants.Sfac.APP_NO) final String applicationId,
			@RequestParam(value = MainetConstants.Sfac.ACTUAL_TASKID, required = false) final Long actualTaskId,
			@RequestParam(value = MainetConstants.Sfac.TASK_ID, required = false) final Long serviceId,
			@RequestParam(value = MainetConstants.Sfac.WORKFLOW_ID, required = false) final Long workflowId,
			@RequestParam(value = MainetConstants.Sfac.TASK_NAME, required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {
		sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		this.getModel().setViewMode(MainetConstants.FlagV);
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
		ABSEntryRequestApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs(MainetConstants.Sfac.FUND_RELEASE_REQ_FORM_APPROVAL_HTML);
		
		AuditBalanceSheetMasterDto  dto = auditBalanceSheetEntryService.fetchABSEntryReqDetailbyAppId(Long.valueOf(applicationId));
		
	
		
		if (dto != null)  {

			approvalModel.setDto(dto);
		}
	
		
		approvalModel.getWorkflowActionDto().setReferenceId(applicationId);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
	

		return new ModelAndView(MainetConstants.Sfac.DPR_ENTRY_REQ_FORM_APPROVAL, MainetConstants.FORM_NAME, approvalModel);
	}



}
