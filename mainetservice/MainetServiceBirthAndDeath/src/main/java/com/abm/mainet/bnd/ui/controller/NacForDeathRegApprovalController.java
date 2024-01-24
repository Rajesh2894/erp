package com.abm.mainet.bnd.ui.controller;

import java.util.ArrayList;
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

import com.abm.mainet.bnd.dto.DeathCertificateDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.service.IDeathCertificateApprovalService;
import com.abm.mainet.bnd.ui.model.NacForDeathRegApprovalModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;

/**
 * @author Arun Shinde
 *
 */
@Controller
@RequestMapping(value = "/NacForDeathRegApproval.html")
public class NacForDeathRegApprovalController extends AbstractFormController<NacForDeathRegApprovalModel> {

	@Autowired
	private IDeathCertificateApprovalService iDeathCertificateApprovalService;

	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	@Autowired
	private IBirthRegService iBirthRegService;

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
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class)
				.sessionCleanUpForFileUpload();
		NacForDeathRegApprovalModel approvalModel = this.getModel();
		// this.getModel().setCommonHelpDocs(RtsConstants.DEATHAPPROVAL_URL);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		// approvalModel.setSaveMode(MainetConstants.MODE_VIEW);
		getModel().bind(httpServletRequest);

		// Load the role from TB_GROUP_MAST
		Boolean checkFinalAproval = iDeathCertificateApprovalService.checkEmployeeRole(UserSession.getCurrent());
		model.addAttribute("CheckFinalApp", checkFinalAproval);

		DeathCertificateDTO deathCertificateDTO = iDeathCertificateApprovalService
				.getDeathCertificateDetail(Long.valueOf(complainNo), orgId);
		RequestDTO requestDTO = iBirthRegService.getApplicantDetailsByApplNoAndOrgId(Long.valueOf(complainNo), orgId);
		this.getModel().setDeathCertificateDTO(deathCertificateDTO);
		this.getModel().getDeathCertificateDTO().setRequestDTO(requestDTO);
		// fetch uploaded document
		List<CFCAttachment> checkList = new ArrayList<>();
		List<CFCAttachment> checklist = new ArrayList<>();
		checkList = iChecklistVerificationService.findAttachmentsForAppId(Long.valueOf(complainNo), null, orgId);
		for (int i = 0; i < checkList.size(); i++) {
			if (checkList.get(i).getClmAprStatus().equals(MainetConstants.FlagY)) {
				checklist.add(checkList.get(i));
			}
		}
		approvalModel.setFetchDocumentList(checklist);

		return new ModelAndView("NacForDeathRegApproval", MainetConstants.FORM_NAME, this.getModel());
	}

	@ResponseBody
	@RequestMapping(params = "saveDeathCertRegApproval", method = RequestMethod.POST)
	public Map<String, Object> saveDeathCertRegApproval(HttpServletRequest request) {
		getModel().bind(request);
		String certificateNo = this.getModel().saveDeathApprovalDetails(
				String.valueOf(this.getModel().getDeathCertificateDTO().getApplicationNo()),
				UserSession.getCurrent().getOrganisation().getOrgid(),
				this.getModel().getWorkflowActionDto().getTaskName());
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
		object.put("DeathWfStatus", this.getModel().getDeathCertificateDTO().getDeathRegstatus());
		object.put("certificateNo", certificateNo);
		return object;
	}

}
