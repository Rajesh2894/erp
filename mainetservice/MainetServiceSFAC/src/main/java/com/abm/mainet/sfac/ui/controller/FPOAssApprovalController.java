/**
 * 
 */
package com.abm.mainet.sfac.ui.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
import com.abm.mainet.sfac.dto.FpoAssKeyParameterDto;
import com.abm.mainet.sfac.dto.FpoAssessmentMasterDto;
import com.abm.mainet.sfac.service.FPOAssessmentService;
import com.abm.mainet.sfac.ui.model.FPOAssApprovalModel;

/**
 * @author pooja.maske
 *
 */
@Controller
@RequestMapping(MainetConstants.Sfac.FPO_ASSESSMENT_APPROVAL)
public class FPOAssApprovalController  extends AbstractFormController<FPOAssApprovalModel>{

	@Autowired
	private FPOAssessmentService assService;
	
	@ResponseBody
	@RequestMapping(params = MainetConstants.Sfac.SHOWDETAILS, method = RequestMethod.POST)
	public ModelAndView showDetails(@RequestParam(MainetConstants.Sfac.APP_NO) final String applicationId,
			@RequestParam(value = MainetConstants.Sfac.ACTUAL_TASKID, required = false) final Long actualTaskId,
			@RequestParam(value = MainetConstants.Sfac.TASK_ID, required = false) final Long serviceId,
			@RequestParam(value = MainetConstants.Sfac.WORKFLOW_ID, required = false) final Long workflowId,
			@RequestParam(value = MainetConstants.Sfac.TASK_NAME, required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {
		sessionCleanup(httpServletRequest);
		getModel().bind(httpServletRequest);
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.sessionCleanUpForFileUpload();
		FPOAssApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("FPOAssessmentApproval.html");
		FpoAssessmentMasterDto dto = assService.fetchAssessmentDetByAppId(Long.valueOf(applicationId));
		this.getModel().setAssementMasterDto(dto);
		List<FpoAssKeyParameterDto> sortedList = dto.getAssKeyDtoList().stream()
				.sorted(Comparator.comparing(FpoAssKeyParameterDto::getKeyParameterDesc))
				.collect(Collectors.toList());
		this.getModel().setKeyParamDtoList(sortedList);
		this.getModel().getAssementMasterDto().setAssKeyDtoList(sortedList);
		approvalModel.getWorkflowActionDto().setReferenceId(applicationId);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		return new ModelAndView("fpoAssesementApproval", MainetConstants.FORM_NAME, this.getModel());
	}
}
