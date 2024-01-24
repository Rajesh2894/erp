package com.abm.mainet.common.integration.dms.ui.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.dto.DmsDocsMetadataDto;
import com.abm.mainet.common.integration.dms.service.IViewMetadataService;
import com.abm.mainet.common.integration.dms.ui.model.MetadataApprovalModel;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;

@Controller
@RequestMapping(value = "/MetadataApproval.html")
public class MetadataApprovalController extends AbstractFormController<MetadataApprovalModel> {

	@Autowired
	private IViewMetadataService metadataService;

	@ResponseBody
	@RequestMapping(params = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.SHOWDETAILS, method = RequestMethod.POST)
	public ModelAndView workorder(
			@RequestParam(MainetConstants.WORKS_MANAGEMENT_WORKFLOW.APP_NO) final String complainNo,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.ACTUAL_TASKID, required = false) final Long actualTaskId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_ID, required = false) final Long serviceId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.WORKFLOW_ID, required = false) final Long workflowId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_NAME, required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {

		sessionCleanup(httpServletRequest);
		MetadataApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("MetadataApproval.html");
		this.getModel().setSaveMode(MainetConstants.MODE_VIEW);
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		this.getModel().bind(httpServletRequest);
		// Query for Fetching Data
		List<DmsDocsMetadataDto> dtoList = metadataService.getMetadataDetails(null, null, null, null,
				UserSession.getCurrent().getOrganisation().getOrgid(), null, null, null, null, null, null, null,
				complainNo, MainetConstants.LQP.STATUS.OPEN);
		if (!CollectionUtils.isEmpty(dtoList)) {
			this.getModel().setDmsDocsMetadataDto(dtoList);
			List<LookUp> prefixList = new ArrayList<>();
			if (dtoList.get(0).getStorageType().equals(MainetConstants.Dms.DMS)) {
				prefixList = CommonMasterUtility.getLevelData(MainetConstants.Dms.MTD, MainetConstants.NUMBERS.ONE,
						UserSession.getCurrent().getOrganisation());
			} else {
				prefixList = CommonMasterUtility.getLevelData(MainetConstants.Dms.KTD, MainetConstants.NUMBERS.ONE,
						UserSession.getCurrent().getOrganisation());
			}
			this.getModel().setDepartmentList(prefixList);
			this.getModel().setDeptId(Long.valueOf(dtoList.get(0).getDeptId()));
		}
		return new ModelAndView("MetadataApproval", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "saveReqApproval", method = RequestMethod.POST)
	public Map<String, Object> saveAuditParaApp(HttpServletRequest request) {
		getModel().bind(request);
		this.getModel().saveCallClosureApprovalDetails(this.getModel().getWorkflowActionDto().getReferenceId(),
				UserSession.getCurrent().getOrganisation().getOrgid(),
				this.getModel().getWorkflowActionDto().getTaskName());
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
		object.put("wfStatus", this.getModel().getDmsDto().getStatusApproval());
		return object;
	}
}
