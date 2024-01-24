package com.abm.mainet.bnd.ui.controller;

import java.util.LinkedHashMap;
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
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.service.IssuenceOfDeathCertificateService;
import com.abm.mainet.bnd.ui.model.DeathRegistrationCertificateApprovalModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;


@Controller
@RequestMapping(value = "/IssuanceDeathCertificateApproval.html")
public class IssuanceDeathCertificateApprovalController extends AbstractFormController<DeathRegistrationCertificateApprovalModel> {

	/**
	 * @param complainNo
	 * @param actualTaskId
	 * @param serviceId
	 * @param workflowId
	 * @param taskName
	 * @param httpServletRequest
	 * @param model
	 * @return
	 */

	@Autowired
	IBirthRegService iBirthRegService;
	
	@Autowired
	private IssuenceOfDeathCertificateService issanceDeathSertiService;
	
	
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
		DeathRegistrationCertificateApprovalModel approvalModel = this.getModel();
		//this.getModel().setCommonHelpDocs("IssuanceBirthCertificateApproval.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		 //approvalModel.setSaveMode(MainetConstants.MODE_VIEW); 
		getModel().bind(httpServletRequest);
		TbDeathregDTO deathregDetail =issanceDeathSertiService.getDeathissuRegisteredAppliDetail(null,null,null, complainNo,orgId);
		if(deathregDetail!=null)
		deathregDetail.setAuthRemark(null);
		
		RequestDTO requestDTO = iBirthRegService.getApplicantDetailsByApplNoAndOrgId(Long.valueOf(complainNo), orgId);
		approvalModel.setTbDeathregDTO(deathregDetail);
		this.getModel().getTbDeathregDTO().setRequestDTO(requestDTO);
		 //Query for Fetching Dat
		return new ModelAndView("IssuanceDeathCertificateApproval", MainetConstants.FORM_NAME, this.getModel());
	 }
	
	@ResponseBody
	@RequestMapping(params = "saveIssuanceDeathCertificateApproval", method = RequestMethod.POST)
	public Map<String, Object> saveDeathApproval(HttpServletRequest request)
    {
		  getModel().bind(request);
		  String certificateNo = this.getModel().saveDeathhApprovalDetails(String.valueOf(this.getModel().getTbDeathregDTO().getApplicationId()), UserSession.getCurrent().getOrganisation().getOrgid(),this.getModel().getWorkflowActionDto().getTaskName());//request.getSession().getAttribute("auditTask").toString()
		  Map<String, Object> object = new LinkedHashMap<String, Object>();
	      object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	      object.put("BirthWfStatus",	this.getModel().getTbDeathregDTO().getDeathRegstatus());	    
	      object.put("certificateNo", certificateNo);
		  return object;
   }
	
}
