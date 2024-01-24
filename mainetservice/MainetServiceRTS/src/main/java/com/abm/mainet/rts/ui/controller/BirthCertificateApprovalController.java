package com.abm.mainet.rts.ui.controller;

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

import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.rts.constant.RtsConstants;
import com.abm.mainet.rts.dto.BirthCertificateDTO;
import com.abm.mainet.rts.service.IBirthCertificateService;
import com.abm.mainet.rts.ui.model.BirthCertificateApprovalModel;

@Controller
@RequestMapping(value="/BirthCertificateApproval.html")
public class BirthCertificateApprovalController extends AbstractFormController<BirthCertificateApprovalModel> {
	
	@Autowired
	IBirthCertificateService birthCer;
	
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
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
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
		BirthCertificateApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs(RtsConstants.BIRTHCERTIFICATEAPPROVAL);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		Long application=Long.valueOf(complainNo);
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		approvalModel.setSaveMode(MainetConstants.MODE_VIEW); 
		getModel().bind(httpServletRequest);
		
		
		Boolean checkFinalAproval = birthCer.checkEmployeeRole(UserSession.getCurrent());
		 model.addAttribute("CheckFinalApp", checkFinalAproval);
		BirthCertificateDTO tbBirthCertDto = birthCer.getBirthRegisteredAppliDetail(application , orgId);
		this.getModel().setBirthCertificateDto(tbBirthCertDto);
		 List<CFCAttachment> checkList = new ArrayList<>();
		 List<CFCAttachment> checklist = new ArrayList<>();
		 checkList=iChecklistVerificationService.findAttachmentsForAppId(application,null, orgId);
		 for(int i=0;i<checkList.size();i++)
		 {
			 if(checkList.get(i).getClmAprStatus().equals(MainetConstants.FlagY))
			 {
			 checklist.add(checkList.get(i));
			 }
		 }
		approvalModel.setFetchDocumentList(checklist);
		return new ModelAndView(RtsConstants.BIRTH_CERTIFICATE_APPROVAL, MainetConstants.FORM_NAME, this.getModel());
				
	}

	
	@ResponseBody
	@RequestMapping(params = "saveBirthRegApproval", method = RequestMethod.POST)
	public Map<String, Object> saveBirthRegApproval(HttpServletRequest request)
    {
		getModel().bind(request);
		this.getModel().saveBirthApprovalDetails(String.valueOf(this.getModel().getBirthCertificateDto().getApmApplicationId()), UserSession.getCurrent().getOrganisation().getOrgid(),this.getModel().getWorkflowActionDto().getTaskName());//request.getSession().getAttribute("auditTask").toString()
		Map<String, Object> object = new LinkedHashMap<String, Object>();
	    object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	    object.put(RtsConstants.BIRTHWFSTATUS,	this.getModel().getBirthCertificateDto().getBirthRegstatus());	    
		return object;
   }
	
	
}
