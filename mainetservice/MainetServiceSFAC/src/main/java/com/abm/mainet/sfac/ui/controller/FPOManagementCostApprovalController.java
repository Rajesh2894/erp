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
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.sfac.dto.FPOManagementCostMasterDTO;
import com.abm.mainet.sfac.service.FPOManagementCostMasterService;
import com.abm.mainet.sfac.service.IAMasterService;
import com.abm.mainet.sfac.ui.model.FPOManagementCostApprovalModel;

@Controller
@RequestMapping("FPOManagementCostApproval.html")
public class FPOManagementCostApprovalController extends AbstractFormController<FPOManagementCostApprovalModel>{

	
	@Autowired FPOManagementCostMasterService fpoManagementCostMasterService;
	
	@Autowired
	private IAMasterService iaMasterService;

	@Autowired
	private IOrganisationService orgService;
	
	
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
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
		FPOManagementCostApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("FPOManagementCostApproval.html");
	
		FPOManagementCostMasterDTO  dto = fpoManagementCostMasterService.fetchFPOManagementCostbyAppId(Long.valueOf(applicationId));
		if (dto != null)  {

			approvalModel.setDto(dto);
		}
		this.getModel().setViewMode(MainetConstants.FlagV);
		Organisation org = orgService.getActiveOrgByUlbShortCode(MainetConstants.Sfac.CBBO);
		this.getModel().setFaYears(iaMasterService.getfinancialYearList(org));
		approvalModel.getWorkflowActionDto().setReferenceId(applicationId);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
	

		return new ModelAndView("FPOManagementCostApproval", MainetConstants.FORM_NAME, approvalModel);
	}
}
