package com.abm.mainet.securitymanagement.ui.controller;

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

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.master.dto.TbLocationMas;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.securitymanagement.dto.ContractualStaffMasterDTO;
import com.abm.mainet.securitymanagement.service.IContractualStaffMasterService;
import com.abm.mainet.securitymanagement.service.IDeploymentOfStaffService;
import com.abm.mainet.securitymanagement.ui.model.DeploymentOfStaffApprovalModel;

@Controller
@RequestMapping(value = "DeploymentOfStaffApproval.html")
public class DeploymentOfStaffApprovalController extends AbstractFormController<DeploymentOfStaffApprovalModel> {

	@Autowired
	IDeploymentOfStaffService deploymentStaffService;

	@Autowired
	private TbAcVendormasterService tbVendormasterService;
	
	@Autowired
	private IContractualStaffMasterService contractualStaffMasterService;
	
	@ResponseBody
	@RequestMapping(params = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.SHOWDETAILS, method = RequestMethod.POST)
	public ModelAndView order(@RequestParam(MainetConstants.WORKS_MANAGEMENT_WORKFLOW.APP_NO) final String complainNo,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.ACTUAL_TASKID, required = false) final Long actualTaskId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_ID, required = false) final Long serviceId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.WORKFLOW_ID, required = false) final Long workflowId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_NAME, required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {

		sessionCleanup(httpServletRequest);
		DeploymentOfStaffApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("DeploymentOfStaffApproval.html");
		this.getModel().setSaveMode("V");
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		this.getModel().bind(httpServletRequest);

		// Query for Fetching Data
		this.getModel().setDeploymentOfStaffDTO(deploymentStaffService.findByDeplSeq(complainNo,
				UserSession.getCurrent().getOrganisation().getOrgid()));
		model.addAttribute("VendorList", loadVendor());
		model.addAttribute("location", loadLocation());
		model.addAttribute("empNameList", findEmployeeNameList());
		return new ModelAndView("DeploymentOfStaffApproval", MainetConstants.FORM_NAME, this.getModel());

	}

	@ResponseBody
	@RequestMapping(params = "saveDeploymentStaffReqApproval", method = RequestMethod.POST)
	public Map<String, Object> saveAuditParaApp(HttpServletRequest request) {
		getModel().bind(request);
		this.getModel().saveDeploymentCallClosureApprovalDetails(this.getModel().getDeploymentOfStaffDTO().getDeplId(),
				UserSession.getCurrent().getOrganisation().getOrgid(),
				this.getModel().getWorkflowActionDto().getTaskName());
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
		object.put("wfStatus", this.getModel().getDeploymentOfStaffDTO().getStatusApproval());
		return object;
	}

	private List<TbLocationMas> loadLocation() {
		ILocationMasService locationMasService = ApplicationContextProvider.getApplicationContext()
				.getBean(ILocationMasService.class);
		List<TbLocationMas> locations = locationMasService
				.fillAllActiveLocationByOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		return locations;
	}

	private List<TbAcVendormaster> loadVendor() {
		final List<TbAcVendormaster> list1 = contractualStaffMasterService
				.findAgencyBasedOnStaffMaster(UserSession.getCurrent().getOrganisation().getOrgid());
		return list1;
	}

	private List<ContractualStaffMasterDTO> findEmployeeNameList() {
		List<ContractualStaffMasterDTO> empNameList = deploymentStaffService
				.findEmployeeNameList(UserSession.getCurrent().getOrganisation().getOrgid());
		return empNameList;

	}
	
	@Override
	@RequestMapping(params = "viewRefNoDetails", method = RequestMethod.POST)
	public ModelAndView viewDetails(@RequestParam("appNo") final String applicationId,
			@RequestParam("taskId") final long serviceId, @RequestParam("actualTaskId") final long taskId,
			final HttpServletRequest httpServletRequest) throws Exception {
		sessionCleanup(httpServletRequest);
		this.getModel().bind(httpServletRequest);
		
		  this.getModel().setCommonHelpDocs("DeploymentOfStaffApproval.html");
		  this.getModel().setSaveMode("V");
		  this.getModel().setApprovalView("A");
		  
		  this.getModel().setDeploymentOfStaffDTO(deploymentStaffService.findByDeplSeq(applicationId,
					UserSession.getCurrent().getOrganisation().getOrgid()));
		   
		return new ModelAndView("DeploymentOfStaffApproval", MainetConstants.FORM_NAME, this.getModel());
	}

}
