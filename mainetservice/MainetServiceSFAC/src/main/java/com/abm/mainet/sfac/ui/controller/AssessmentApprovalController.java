/**
 * 
 */
package com.abm.mainet.sfac.ui.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.abm.mainet.sfac.dto.AssessmentKeyParameterDto;
import com.abm.mainet.sfac.dto.AssessmentMasterDto;
import com.abm.mainet.sfac.service.CBBOAssesementEntryService;
import com.abm.mainet.sfac.service.CBBOMasterService;
import com.abm.mainet.sfac.service.IAMasterService;
import com.abm.mainet.sfac.ui.model.AssessmentApprovalModel;

/**
 * @author pooja.maske
 *
 */
@Controller
@RequestMapping(MainetConstants.Sfac.ASSESSMENT_APPROVAL_URL)
public class AssessmentApprovalController extends AbstractFormController<AssessmentApprovalModel> {

	
	@Autowired
	private CBBOAssesementEntryService assService;

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
		AssessmentApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("AssessmentEntryApproval.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		/*this.getModel().setCbboMasterDtoList(cbboMasterService.findCbboById(UserSession.getCurrent().getEmployee().getMasId()));
		this.getModel().setFaYears(iaMasterService.getfinancialYearList(UserSession.getCurrent().getOrganisation()));*/
		AssessmentMasterDto dto = assService.fetchAssessmentDetByAppId(Long.valueOf(applicationId));
		this.getModel().setAssYear(dto.getAlcYearDesc());
		if (StringUtils.isNotEmpty(dto.getRemark()))
		this.getModel().setCbboAprRemark(dto.getRemark());
		dto.setRemark("");
		List<AssessmentKeyParameterDto> sortedList = dto.getAssementKeyParamDtoList().stream()
				.sorted(Comparator.comparing(AssessmentKeyParameterDto::getKeyParameterDesc))
				.collect(Collectors.toList());
		this.getModel().setAssementKeyParamDtoList(sortedList);
		this.getModel().getAssementMasterDto().setAssementKeyParamDtoList(sortedList);
		this.getModel().setAssementMasterDto(dto);
		approvalModel.getWorkflowActionDto().setReferenceId(applicationId);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		return new ModelAndView("AssesementApproval", MainetConstants.FORM_NAME, this.getModel());
	}
}
