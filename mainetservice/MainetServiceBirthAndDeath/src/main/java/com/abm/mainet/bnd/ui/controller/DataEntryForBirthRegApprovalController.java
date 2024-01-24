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

import com.abm.mainet.bnd.dto.BirthRegistrationDTO;
import com.abm.mainet.bnd.dto.HospitalMasterDTO;
import com.abm.mainet.bnd.service.IBirthRegService;
import com.abm.mainet.bnd.service.IDeathRegistrationService;
import com.abm.mainet.bnd.service.IHospitalMasterService;
import com.abm.mainet.bnd.ui.model.DataEntryBirthRegApprovalModel;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.exception.FrameworkException;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;


@Controller
@RequestMapping(value = "/DataEntryBirthRegApproval.html")
public class DataEntryForBirthRegApprovalController extends AbstractFormController<DataEntryBirthRegApprovalModel> {

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
		DataEntryBirthRegApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("BirthRegApproval.html");
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		getModel().bind(httpServletRequest);
	
		try {
			if (UserSession.getCurrent().getLanguageId() == MainetConstants.DEFAULT_LANGUAGE_ID) {
				List<HospitalMasterDTO> hospitalList = this.getModel()
						.setHospitalMasterDTOList(iHospitalMasterService.getAllHospitls(orgId));
				model.addAttribute("hospitalList", hospitalList);
			} else {
				this.getModel().setHospitalMasterDTOList(iHospitalMasterService.getAllHospitalList(orgId));
			}
		} catch (Exception e) {
			throw new FrameworkException("Some Problem Occured While Fetching Hospital List");
		}
		 //Load the role from TB_GROUP_MAST
	    Boolean checkFinalAproval = iDeathRegistrationService.checkEmployeeRole(UserSession.getCurrent());
	    List<BirthRegistrationDTO> tbBirthRegDto = iBirthRegService.getBirthRegDetailFordataEntryTemp(null,null,null, null, null,complainNo , orgId);
		this.getModel().setBirthRegDto(tbBirthRegDto.get(0));
		model.addAttribute("CheckFinalApp", checkFinalAproval);
		return new ModelAndView("DataEntryBirthRegApproval", MainetConstants.FORM_NAME, this.getModel());
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
	
}
