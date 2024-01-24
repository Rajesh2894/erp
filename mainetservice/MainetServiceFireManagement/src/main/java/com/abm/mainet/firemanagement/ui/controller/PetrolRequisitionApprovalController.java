/*package com.abm.mainet.firemanagement.ui.controller;

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
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.firemanagement.dto.FireCallRegisterDTO;
import com.abm.mainet.firemanagement.service.IPetrolRequisitionService;
import com.abm.mainet.firemanagement.ui.model.PetrolRequisitionApprovalModel;

@Controller
@RequestMapping(value = "/PetrolRegApproval.html")
public class PetrolRequisitionApprovalController extends AbstractFormController<PetrolRequisitionApprovalModel> {
	*//**
	 * @param complainNo
	 * @param actualTaskId
	 * @param serviceId
	 * @param workflowId
	 * @param taskName
	 * @param httpServletRequest
	 * @param model
	 * @return
	 *//*
	@Autowired
	private IPetrolRequisitionService petrolRequisitionService;

	@Autowired
	private IEmployeeService iEmployeeService;
	
	
	@Autowired
	IPetrolRequisitionService iBirthRegService;
	
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
		
		//sessionCleanup(httpServletRequest);
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
		PetrolRequisitionApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("PetrolRegApproval.html");
		
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		this.getModel().bind(httpServletRequest);

		//Load the role from TB_GROUP_MAST
	    //Boolean checkFinalAproval = petrolRequisitionService.checkEmployeeRole(UserSession.getCurrent());
	    //model.addAttribute("CheckFinalApp", checkFinalAproval);
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
	    
		//Query for Fetching Petrol Data
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
    	List<Employee> employeeList = iEmployeeService.findEmpList(orgid);
	    model.addAttribute("employees", employeeList);
		
	    //FireCallRegisterDTO closerDto = iFireCallRegisterService.findOne1(Long.valueOf(complainNo));
		approvalModel.setPetrolRequisitionDTO(petrolRequisitionService.getDetailByVehNo(Long.valueOf(complainNo), orgid));
		approvalModel.setEntity(approvalModel.getPetrolRequisitionDTO().get(0));

		//fetch uploaded document
		approvalModel.setFetchDocumentList(iChecklistVerificationService.getDocumentUploadedByRefNo(complainNo, orgid));
	    
		return new ModelAndView("PetrolRegApproval", MainetConstants.FORM_NAME, this.getModel());
		
	 }
	
	
	@ResponseBody
	@RequestMapping(params = "savePetrolReqApproval", method = RequestMethod.POST)
	public Map<String, Object> saveAuditParaApproval(HttpServletRequest request)
    {
		getModel().bind(request);
		this.getModel().savePetrolCallClosureApprovalDetails(this.getModel().getEntity().getRequestId(), UserSession.getCurrent().getOrganisation().getOrgid(), this.getModel().getWorkflowActionDto().getTaskName());
		Map<String, Object> object = new LinkedHashMap<String, Object>();
	    object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
	    object.put("PetrolWfStatus", this.getModel().getEntity().getPetrolRegstatus());	    
		return object;
   }
	
	
}













*/