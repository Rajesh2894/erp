package com.abm.mainet.bnd.ui.controller;

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

import com.abm.mainet.bnd.dto.CemeteryMasterDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.dto.TbDeathregDTO;
import com.abm.mainet.bnd.service.ICemeteryMasterService;
import com.abm.mainet.bnd.service.IDeathRegistrationService;
import com.abm.mainet.bnd.service.IHospitalMasterService;
import com.abm.mainet.bnd.service.IdeathregCorrectionService;
import com.abm.mainet.bnd.ui.model.DataEntryDeathRegApprovalModel;
import com.abm.mainet.cfc.checklist.service.IChecklistVerificationService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;


@Controller
@RequestMapping(value = "/DataEntryDeathRegApproval.html")
public class DataEntryDeathRegApprovalController extends AbstractFormController<DataEntryDeathRegApprovalModel> {

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
	private IdeathregCorrectionService ideathregCorrectionService;
	
	@Autowired
	private IHospitalMasterService iHospitalMasterService;
	
	@Autowired
	private ICemeteryMasterService iCemeteryMasterService;
	
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
		DataEntryDeathRegApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("DeathRegApproval.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		approvalModel.setSaveMode(MainetConstants.MODE_VIEW); 
		getModel().bind(httpServletRequest);
		//Query for Fetching Data
		try {
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID) {
				List<HospitalMasterDTO> hospitalList = this.getModel()
						.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitls(orgId));
				this.getModel().setCemeteryMasterDTOList(iCemeteryMasterService.getAllCemetery(orgId));
				model.addAttribute("hospitalList", hospitalList);
				model.addAttribute("cemeteryList",
						 approvalModel.setCemeteryMasterDTOList(iCemeteryMasterService.getAllCemetery(orgId)));
			} else {
				this.getModel().setHospitalMasterDTOList(iHospitalMasterService.getAllHospitalList(orgId));
				this.getModel().setCemeteryMasterDTOList(iCemeteryMasterService.getAllCemeteryList(orgId));
			}
		} catch (Exception e) {
			throw new FrameworkException("Some Problem Occured While Fetching Hospital or cemetery List");
		 }
		
				
		 //Load the role from TB_GROUP_MAST
	     Boolean checkFinalAproval = iDeathRegistrationService.checkEmployeeRole(UserSession.getCurrent());
	     model.addAttribute("CheckFinalApp", checkFinalAproval);
         //model.setFetchApplnUpload(iChecklistVerificationService.getDocumentUploadedByRefNo(model.getReqDTO().getRtiNo(), orgId));
	      List<TbDeathregDTO> tbDeathRegDtoList = ideathregCorrectionService.getDeathRegAppliDetailForDataEntryTemp(null, Long.valueOf(complainNo),null, null,null, null,orgId);
		 this.getModel().setTbDeathregDTO(tbDeathRegDtoList.get(0));
		 CemeteryMasterDTO cemeteryMasterDTO =
		 iCemeteryMasterService.getCemeteryById(tbDeathRegDtoList.get(0).getCeId());
		 if(!(cemeteryMasterDTO.getCeAddrMar().isEmpty()) ||
		 cemeteryMasterDTO.getCeAddrMar()!=null) {
		 approvalModel.setCemeteryAddressMar(cemeteryMasterDTO.getCeAddrMar()); }
		 if(!(cemeteryMasterDTO.getCeNameMar().isEmpty()) ||
		 cemeteryMasterDTO.getCeNameMar()!=null) {
		 approvalModel.setCemeteryNameMar(cemeteryMasterDTO.getCeNameMar()); }
		return new ModelAndView("DataEntryDeathRegApproval", MainetConstants.FORM_NAME, this.getModel());
	 }
	
	@ResponseBody
	@RequestMapping(params = "saveDeathRegApproval", method = RequestMethod.POST)
	public Map<String, Object> saveAuditParaApproval(HttpServletRequest request)
    {
		getModel().bind(request);
		this.getModel().saveDeathApprovalDetails(String.valueOf(this.getModel().getTbDeathregDTO().getApplicationId()), UserSession.getCurrent().getOrganisation().getOrgid(),this.getModel().getWorkflowActionDto().getTaskName());//request.getSession().getAttribute("auditTask").toString()
		Map<String, Object> object = new LinkedHashMap<String, Object>();
	    object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	    object.put("DeathWfStatus",	this.getModel().getTbDeathregDTO().getDeathRegstatus());	    
		return object;
   }
	
}
