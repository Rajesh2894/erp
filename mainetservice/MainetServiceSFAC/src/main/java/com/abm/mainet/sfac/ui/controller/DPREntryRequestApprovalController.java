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
import com.abm.mainet.sfac.dto.CBBOMasterDto;
import com.abm.mainet.sfac.dto.DPREntryMasterDto;
import com.abm.mainet.sfac.dto.FPOMasterDto;
import com.abm.mainet.sfac.service.CBBOMasterService;
import com.abm.mainet.sfac.service.DPREntryRequestService;
import com.abm.mainet.sfac.service.FPOMasterService;
import com.abm.mainet.sfac.ui.model.DPREntryRequestApprovalModel;

@Controller
@RequestMapping(MainetConstants.Sfac.DPR_ENTRY_REQ_FORM_APPROVAL_HTML)
public class DPREntryRequestApprovalController extends AbstractFormController<DPREntryRequestApprovalModel>{

	@Autowired DPREntryRequestService dprEntryRequestService;

	@Autowired CBBOMasterService cbboMasterService;

	@Autowired FPOMasterService fpoMasterService;

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
		DPREntryRequestApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs(MainetConstants.Sfac.FUND_RELEASE_REQ_FORM_APPROVAL_HTML);

		DPREntryMasterDto  dto = dprEntryRequestService.fetchDPREntryReqDetailbyAppId(Long.valueOf(applicationId));



		if (dto != null)  {

			approvalModel.setDto(dto);
		}


		approvalModel.getWorkflowActionDto().setReferenceId(applicationId);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		//populateModel(approvalModel);

		return new ModelAndView(MainetConstants.Sfac.DPR_ENTRY_REQ_FORM_APPROVAL, MainetConstants.FORM_NAME, approvalModel);
	}

	

}
