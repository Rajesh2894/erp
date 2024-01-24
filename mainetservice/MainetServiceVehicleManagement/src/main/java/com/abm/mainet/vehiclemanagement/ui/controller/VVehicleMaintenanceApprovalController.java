package com.abm.mainet.vehiclemanagement.ui.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.service.IEmployeeService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.service.IWorkflowTaskService;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.VehicleMaintenanceDTO;
import com.abm.mainet.vehiclemanagement.service.ISLRMEmployeeMasterService;
import com.abm.mainet.vehiclemanagement.service.IVehicleMaintenanceService;
import com.abm.mainet.vehiclemanagement.ui.model.VehicleMaintenanceApprovalModel;

//@Controller
//@RequestMapping(value = "/vehicleMaintenanceApproval.html")
public class VVehicleMaintenanceApprovalController extends AbstractFormController<VehicleMaintenanceApprovalModel> {
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
	private IVehicleMaintenanceService vehicleMaintenanceService;

	@Autowired
	ISLRMEmployeeMasterService sLRMEmployeeMasterService;

	@Autowired
	ILocationMasService locationMasService;

	@Autowired
	private IWorkflowTaskService iWorkflowTaskService;
	
	@Autowired
	private IEmployeeService iEmployeeService;

	@ResponseBody
	@RequestMapping(params = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.SHOWDETAILS, method = RequestMethod.POST)
	public ModelAndView order(@RequestParam(MainetConstants.WORKS_MANAGEMENT_WORKFLOW.APP_NO) final String complainNo,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.ACTUAL_TASKID, required = false) final Long actualTaskId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_ID, required = false) final Long serviceId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.WORKFLOW_ID, required = false) final Long workflowId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_NAME, required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {
		ApplicationContextProvider.getApplicationContext().getBean(IFileUploadService.class).sessionCleanUpForFileUpload();
		VehicleMaintenanceApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("vehicleMaintenanceApproval.html");
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		this.getModel().bind(httpServletRequest);
		// approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		// approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);

		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		List<SLRMEmployeeMasterDTO> sLRMEmpList = sLRMEmployeeMasterService.searchEmployeeList(null, null, null,UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("employees", sLRMEmpList);

		List<TbLocationMas> locations = locationMasService.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("locations", locations);

		String result = complainNo;
		int len = result.length();
		int len1 = result.indexOf(Constants.str);
		String result1 = result.substring(len1 + 1, len);
		String requestNo = complainNo;
		//to get data on approval page
		VehicleMaintenanceDTO vehicleMaintenanceDTO = vehicleMaintenanceService.getDetailByVehNo(requestNo, orgid);
		this.getModel().setVehicleMaintenanceDTO(vehicleMaintenanceDTO);
		// to get Current Checker Level
		getModel().setLevelcheck(iWorkflowTaskService.findByTaskId(actualTaskId).getCurentCheckerLevel());
				return new ModelAndView("addVehMaintenance/Form", MainetConstants.FORM_NAME, this.getModel());

	}



}