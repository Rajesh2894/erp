package com.abm.mainet.bnd.ui.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.bnd.constant.BndConstants;
import com.abm.mainet.bnd.dto.BirthCertificateDTO;
import com.abm.mainet.bnd.dto.DeathCertificateDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.service.IDeathCertificateApprovalService;
import com.abm.mainet.bnd.ui.model.BirthCertificateApprovalModel;
import com.abm.mainet.bnd.ui.model.DeathCertificateApprovalModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.integration.dms.service.IAttachDocsService;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.dto.WorkflowAction;
import com.abm.mainet.common.workflow.service.IWorkflowActionService;

@Controller("bndDeathCertificateApproval")
@RequestMapping(value = "/ApplyDeathCertificateLevels.html")
public class DeathCertificateApprovalController extends AbstractFormController<DeathCertificateApprovalModel>{
	
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	@Autowired 
	private IDeathCertificateApprovalService iDeathCertificateApprovalService;
	
	@Autowired
	private IBirthRegService iBirthRegService;
	
	@Autowired
	private IAttachDocsService attachDocsService;
	
	@Autowired
	private IWorkflowActionService workflowActionService;
	
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
		DeathCertificateApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs(BndConstants.DEATHAPPROVAL_URL);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		//approvalModel.setSaveMode(MainetConstants.MODE_VIEW); 
		getModel().bind(httpServletRequest);
	
		 //Load the role from TB_GROUP_MAST
	     Boolean checkFinalAproval = iDeathCertificateApprovalService.checkEmployeeRole(UserSession.getCurrent());
	     model.addAttribute("CheckFinalApp", checkFinalAproval);

	     DeathCertificateDTO deathCertificateDTO = iDeathCertificateApprovalService.getDeathCertificateDetail(Long.valueOf(complainNo),orgId);
		 this.getModel().setDeathCertificateDTO(deathCertificateDTO);
		 RequestDTO requestDTO = iBirthRegService.getApplicantDetailsByApplNoAndOrgId(Long.valueOf(complainNo), orgId);
		 this.getModel().getDeathCertificateDTO().setRequestDTO(requestDTO);
		 //fetch uploaded document
		 List<CFCAttachment> checkList = new ArrayList<>();
		 List<CFCAttachment> checklist = new ArrayList<>();
		 checkList=iChecklistVerificationService.findAttachmentsForAppId(Long.valueOf(complainNo),null, orgId);
		 for(int i=0;i<checkList.size();i++)
		 {
			 if(checkList.get(i).getClmAprStatus().equals(MainetConstants.FlagY))
			 {
			 checklist.add(checkList.get(i));
			 }
		 }
		approvalModel.setFetchDocumentList(checklist);
		// approvalModel.setFetchDocumentList(iChecklistVerificationService.getDocumentUploadedByRefNo(complainNo, orgId));
		 
		
		final List<AttachDocs> deAttDocsList = attachDocsService.findByCode(
				UserSession.getCurrent().getOrganisation().getOrgid(),deathCertificateDTO.getApplicationNo().toString());
		this.getModel().setDeAttDocsList(deAttDocsList);
		return new ModelAndView(BndConstants.DEATHAPPROVAL, MainetConstants.FORM_NAME, this.getModel());
	 }
	
	
	@ResponseBody
	@RequestMapping(params = "saveDeathCertRegApproval", method = RequestMethod.POST)
	public Map<String, Object> saveDeathCertRegApproval(HttpServletRequest request)
    {
		getModel().bind(request);
		this.getModel().saveDeathApprovalDetails(String.valueOf(this.getModel().getDeathCertificateDTO().getApplicationNo()), UserSession.getCurrent().getOrganisation().getOrgid(),this.getModel().getWorkflowActionDto().getTaskName());//request.getSession().getAttribute("auditTask").toString()
		Map<String, Object> object = new LinkedHashMap<String, Object>();
	    object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	    object.put("DeathWfStatus",	this.getModel().getDeathCertificateDTO().getDeathRegstatus());	    
		return object;
   }
	
	@Override
	@RequestMapping(params ="viewRefNoDetails", method = RequestMethod.POST)
	public ModelAndView viewDetails(@RequestParam("appNo") final String complainNo,
   			@RequestParam("taskId") final long serviceId,@RequestParam("actualTaskId") final long actualTaskId, final HttpServletRequest httpServletRequest )throws Exception{      
		sessionCleanup(httpServletRequest);
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
		DeathCertificateApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs(BndConstants.DEATHAPPROVAL_URL);
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId); 
		getModel().bind(httpServletRequest);
	
		 //Load the role from TB_GROUP_MAST
	     Boolean checkFinalAproval = iDeathCertificateApprovalService.checkEmployeeRole(UserSession.getCurrent());

	     DeathCertificateDTO deathCertificateDTO = iDeathCertificateApprovalService.getDeathCertificateDetail(Long.valueOf(complainNo),orgId);
		 this.getModel().setDeathCertificateDTO(deathCertificateDTO);
		 WorkflowAction dto = workflowActionService.getCommentAndDecisionByAppId(Long.valueOf(complainNo), orgId);
			if(dto!=null) {
				this.getModel().getDeathCertificateDTO().setAuthRemark(dto.getComments());
			}
		 RequestDTO requestDTO = iBirthRegService.getApplicantDetailsByApplNoAndOrgId(Long.valueOf(complainNo), orgId);
		 this.getModel().getDeathCertificateDTO().setRequestDTO(requestDTO);
		 
		 //fetch uploaded document
	
   approvalModel.setFetchDocumentList(iChecklistVerificationService.findAttachmentsForAppId(Long.valueOf(complainNo),null, orgId));
		 
		
		final List<AttachDocs> deAttDocsList = attachDocsService.findByCode(
				UserSession.getCurrent().getOrganisation().getOrgid(),deathCertificateDTO.getApplicationNo().toString());
		this.getModel().setDeAttDocsList(deAttDocsList);
		this.getModel().setViewFlag("Y");
		return new ModelAndView(BndConstants.DEATHAPPROVAL, MainetConstants.FORM_NAME, this.getModel());
		
		
	 }
	
	@SuppressWarnings("unused")
    @RequestMapping(params = "checkFileUpload", method = RequestMethod.POST)
    public @ResponseBody Boolean checkFileUpload(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        this.getModel().bind(httpServletRequest);
        boolean flag = false;
        for (final Map.Entry<Long, Set<File>> entry : FileUploadUtility.getCurrent().getFileMap().entrySet()) {
            flag = true;
            break;
        }
        return flag;
    }
	

}
