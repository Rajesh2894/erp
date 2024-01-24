package com.abm.mainet.sfac.ui.controller;

import java.util.List;

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
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.MilestoneCompletionMasterDto;
import com.abm.mainet.sfac.dto.MilestoneMasterDto;
import com.abm.mainet.sfac.service.MilestoneCompletionService;
import com.abm.mainet.sfac.ui.model.MilestoneCompletionApprovalModel;

@Controller
@RequestMapping(MainetConstants.Sfac.MILESTONE_COMP_FORM_APPROVAL_HTML)
public class MilestoneCompletionApprovalFormController extends AbstractFormController<MilestoneCompletionApprovalModel>{
	
	@Autowired MilestoneCompletionService milestoneCompletionService;
	
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
		MilestoneCompletionApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs(MainetConstants.Sfac.MILESTONE_COMP_FORM_APPROVAL_HTML);
		
		MilestoneCompletionMasterDto  dto = milestoneCompletionService.fetchMilestoneCompletionbyAppId(Long.valueOf(applicationId));
		List<MilestoneMasterDto> milestoneMasterDtos =	milestoneCompletionService.getMilestoneDetailsByID(dto.getMsId());
		
		approvalModel.setMilestoneMasterDtos(milestoneMasterDtos);
	
		
		if (dto != null)  {

			approvalModel.setDto(dto);
		}
		
		approvalModel.getWorkflowActionDto().setReferenceId(applicationId);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
	

		return new ModelAndView(MainetConstants.Sfac.MILESTONE_COMP_FORM_APPROVAL, MainetConstants.FORM_NAME, approvalModel);
	}

}
