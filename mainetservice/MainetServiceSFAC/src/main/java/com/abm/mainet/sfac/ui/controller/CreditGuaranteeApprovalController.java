package com.abm.mainet.sfac.ui.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.sfac.dto.CreditGuaranteeCGFMasterDto;
import com.abm.mainet.sfac.dto.EquityGrantMasterDto;
import com.abm.mainet.sfac.service.CreditGuaranteeRequestMasterService;
import com.abm.mainet.sfac.ui.model.CreditGrantApprovalModel;
import com.abm.mainet.sfac.ui.model.CreditGrantEntryModel;
import com.abm.mainet.sfac.ui.model.EquityGrantApprovalModel;

@Controller
@RequestMapping("CreditGuaranteeApproval.html")
public class CreditGuaranteeApprovalController extends AbstractFormController<CreditGrantApprovalModel>{
	
	@Autowired CreditGuaranteeRequestMasterService creditGuaranteeRequestMasterService;
	
	@Autowired
	private IChecklistVerificationService checklistVerificationService;

	@Autowired
	private IAttachDocsService attachDocsService;
	
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
		CreditGrantApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("EquityGrantApproval.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();

		final List<LookUp> stateList = CommonMasterUtility.getLevelData("SDB", 1,
				UserSession.getCurrent().getOrganisation());
		approvalModel.setStateList(stateList);

		

	
		CreditGuaranteeCGFMasterDto  dto = creditGuaranteeRequestMasterService.fetchCreditGauranteebyAppId(Long.valueOf(applicationId));
		if (dto != null)  {

			approvalModel.setDto(dto);
		}
		this.getModel().setViewMode(MainetConstants.FlagV);
		this.getModel().setStateList(stateList);



		

		List<CFCAttachment>	documentList = checklistVerificationService.getDocumentUploaded(Long.valueOf(applicationId),
				UserSession.getCurrent().getOrganisation().getOrgid());
		if(CollectionUtils.isNotEmpty(documentList))
			approvalModel.setDocumentList(documentList);
		String path = ("CGF" + MainetConstants.FILE_PATH_SEPARATOR + applicationId);
		logger.info("orgid : shortCode : applicationId "+ path);
		this.getModel()
		.setAttachDocsList(attachDocsService.findByCode(UserSession.getCurrent().getOrganisation().getOrgid(),"CGF" + MainetConstants.FILE_PATH_SEPARATOR + applicationId));
		logger.info("doclist"+approvalModel.getAttachDocsList());
		approvalModel.getWorkflowActionDto().setReferenceId(applicationId);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
	

		return new ModelAndView("CreditGuaranteeApproval", MainetConstants.FORM_NAME, approvalModel);
	}

}
