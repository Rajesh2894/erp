package com.abm.mainet.bnd.ui.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.service.IDeathRegistrationService;
import com.abm.mainet.bnd.service.IHospitalMasterService;
import com.abm.mainet.bnd.ui.model.BirthRegistrationApprovalModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.domain.CFCAttachment;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;


@Controller
@RequestMapping(value = "/BirthRegApproval.html")
public class BirthRegApprovalController extends AbstractFormController<BirthRegistrationApprovalModel> {

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
	private IHospitalMasterService iHospitalMasterService;
	
	@Autowired
	IBirthRegService iBirthRegService;
	
	@Autowired
	private IChecklistVerificationService iChecklistVerificationService;
	
	@Autowired
	private IDeathRegistrationService iDeathRegistrationService;
	
	
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
		BirthRegistrationApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("BirthRegApproval.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		 //approvalModel.setSaveMode(MainetConstants.MODE_VIEW); 
		getModel().bind(httpServletRequest);
		 //Query for Fetching Dat
		List<HospitalMasterDTO> hospitalList = approvalModel
				.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitls(orgId));
		model.addAttribute("hospitalList", hospitalList);
		 //Load the role from TB_GROUP_MAST
	    Boolean checkFinalAproval = iDeathRegistrationService.checkEmployeeRole(UserSession.getCurrent());
         //model.setFetchApplnUpload(iChecklistVerificationService.getDocumentUploadedByRefNo(model.getReqDTO().getRtiNo(), orgId));
		// List<TbDeathregDTO> tbDeathRegDtoList = ideathregCorrectionService.getDeathRegisteredAppliDetail(null, Long.valueOf(complainNo),null, null,null, null,orgId);
	    List<BirthRegistrationDTO> tbBirthRegDto = iBirthRegService.getBirthRegisteredAppliDetail(null,null,null, null, null,complainNo , orgId);
		this.getModel().setBirthRegDto(tbBirthRegDto.get(0));
		 //fetch uploaded document
		List<CFCAttachment> attachList = new ArrayList<>();
		 List<CFCAttachment> checklist = new ArrayList<>();
		 attachList=iChecklistVerificationService.findAttachmentsForAppId(Long.valueOf(complainNo),null, orgId);
		 if(CollectionUtils.isNotEmpty(attachList)) {
		 for(int i=0;i<attachList.size();i++)
		 {
			 if(attachList.get(i).getClmAprStatus()!=null && attachList.get(i).getClmAprStatus().equals(MainetConstants.FlagY))
			 {
			 checklist.add(attachList.get(i));
			 }
		  }
		 }
		approvalModel.setFetchDocumentList(checklist);
		
		model.addAttribute("CheckFinalApp", checkFinalAproval);
		return new ModelAndView("BirthRegApproval", MainetConstants.FORM_NAME, this.getModel());
	 }
	
	@ResponseBody
	@RequestMapping(params = "saveBirthRegApproval", method = RequestMethod.POST)
	public Map<String, Object> saveBirthRegApproval(HttpServletRequest request)
    {
		getModel().bind(request);
		this.getModel().saveBirthApprovalDetails(String.valueOf(this.getModel().getBirthRegDto().getApplicationId()), UserSession.getCurrent().getOrganisation().getOrgid(),this.getModel().getWorkflowActionDto().getTaskName());//request.getSession().getAttribute("auditTask").toString()
		Map<String, Object> object = new LinkedHashMap<String, Object>();
	    object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	    object.put("BirthWfStatus",	this.getModel().getBirthRegDto().getBirthRegstatus());	    
		return object;
   }
	
	@Override
	@RequestMapping(params ="viewRefNoDetails", method = RequestMethod.POST)
 	public ModelAndView viewDetails(@RequestParam("appNo") final String complainNo,
   			@RequestParam("taskId") final long serviceId,@RequestParam("actualTaskId") final long actualTaskId, final HttpServletRequest httpServletRequest )throws Exception{      
		sessionCleanup(httpServletRequest);
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
		BirthRegistrationApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("BirthRegApproval.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		getModel().bind(httpServletRequest);
		
	    List<BirthRegistrationDTO> tbBirthRegDto = iBirthRegService.getBirthRegisteredAppliDetail(null,null,null, null, null,complainNo , orgId);
		this.getModel().setBirthRegDto(tbBirthRegDto.get(0));
		 //fetch uploaded document
		List<CFCAttachment> attachList = new ArrayList<>();
		 List<CFCAttachment> checklist = new ArrayList<>();
		 attachList=iChecklistVerificationService.findAttachmentsForAppId(Long.valueOf(complainNo),null, orgId);
		 if(CollectionUtils.isNotEmpty(attachList)) {
		 for(int i=0;i<attachList.size();i++)
		 {
			 if(attachList.get(i).getClmAprStatus()!=null && attachList.get(i).getClmAprStatus().equals(MainetConstants.FlagY))
			 {
			 checklist.add(attachList.get(i));
			 }
		  }
		 }
		approvalModel.setFetchDocumentList(checklist);
		approvalModel.setViewFlag("Y");
		return new ModelAndView("BirthRegApproval", MainetConstants.FORM_NAME, this.getModel());
	 }
	
}
