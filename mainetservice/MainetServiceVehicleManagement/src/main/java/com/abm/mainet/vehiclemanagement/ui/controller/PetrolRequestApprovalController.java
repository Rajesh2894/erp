package com.abm.mainet.vehiclemanagement.ui.controller;

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
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dms.service.IFileUploadService;
import com.abm.mainet.common.ui.controller.AbstractFormController;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.vehiclemanagement.dto.PumpMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.SLRMEmployeeMasterDTO;
import com.abm.mainet.vehiclemanagement.service.IGenVehicleMasterService;
import com.abm.mainet.vehiclemanagement.service.IPetrolRequisitionService;
import com.abm.mainet.vehiclemanagement.service.IPumpMasterService;
import com.abm.mainet.vehiclemanagement.service.ISLRMEmployeeMasterService;
import com.abm.mainet.vehiclemanagement.ui.model.PetrolRequestApprovalModel;

@Controller
@RequestMapping(value = "/PetrolRegApprov.html")
public class PetrolRequestApprovalController extends AbstractFormController<PetrolRequestApprovalModel> {

	@Autowired
	private IPetrolRequisitionService petrolRequisitionService;

	@Autowired
    ISLRMEmployeeMasterService sLRMEmployeeMasterService;
	
	@Autowired
    private IPumpMasterService pumpMasterService;
	
	@Autowired
	private IGenVehicleMasterService vehicleMasterService;
	
	@Autowired
	private IFileUploadService fileUpload;
	
		
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
	@ResponseBody
	@RequestMapping(params = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.SHOWDETAILS, method = RequestMethod.POST)
	public ModelAndView order(
			@RequestParam(MainetConstants.WORKS_MANAGEMENT_WORKFLOW.APP_NO) final String complainNo,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.ACTUAL_TASKID, required = false) final Long actualTaskId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_ID, required = false) final Long serviceId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.WORKFLOW_ID, required = false) final Long workflowId,
			@RequestParam(value = MainetConstants.WORKS_MANAGEMENT_WORKFLOW.TASK_NAME, required = false) final String taskName,
			final HttpServletRequest httpServletRequest, final Model model) {
		this.sessionCleanup(httpServletRequest);
		fileUpload.sessionCleanUpForFileUpload();
		this.getModel().bind(httpServletRequest);
		PetrolRequestApprovalModel approvalModel = this.getModel();
		this.getModel().setCommonHelpDocs("PetrolRegApproval.html");
		approvalModel.getWorkflowActionDto().setReferenceId(complainNo);
		approvalModel.getWorkflowActionDto().setTaskId(actualTaskId);
		approvalModel.getWorkflowActionDto().setTaskName(taskName);
		Long orgid = UserSession.getCurrent().getOrganisation().getOrgid();
		this.getModel().setPetrolRequisitionDTO(petrolRequisitionService.getDetailByVehNo(complainNo, orgid));
		this.getModel().setEntity(approvalModel.getPetrolRequisitionDTO().get(0));

		List<SLRMEmployeeMasterDTO> sLRMEmpList = sLRMEmployeeMasterService.searchEmployeeList(null, null, null, UserSession.getCurrent().getOrganisation().getOrgid());
		model.addAttribute("employees", sLRMEmpList);
		
		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL)) {
		    List<PumpMasterDTO> pumpList = pumpMasterService.getPumpIdAndNamesListByVeID(this.getModel().getEntity().getVeId(), orgid);
		    model.addAttribute("pumps", pumpList);			
		}

		return new ModelAndView("PetrolRequestApproval", MainetConstants.FORM_NAME, this.getModel());		
	 }
	
	
	@ResponseBody
	@RequestMapping(params = "savePetrolReqApproval", method = RequestMethod.POST)
	public Map<String, Object> saveAuditParaApp(HttpServletRequest request, final Model model) {
		getModel().bind(request);
		this.getModel().savePetrolCallClosureApprovalDetails(this.getModel().getEntity().getRequestId(),
				UserSession.getCurrent().getOrganisation().getOrgid(), this.getModel().getWorkflowActionDto().getTaskName());

		if (Utility.isEnvPrefixAvailable(UserSession.getCurrent().getOrganisation(), MainetConstants.ENV_PSCL))
			this.getModel().uploadPetrolRequestDoc();
		
		Map<String, Object> object = new LinkedHashMap<String, Object>();
		object.put(MainetConstants.ERROR, this.getModel().getBindingResult().getAllErrors());
		object.put("PetrolWfStatus", this.getModel().getEntity().getPetrolRegstatus());
		object.put("APPSTATUS", this.getModel().getEntity().getPetrolRegstatus());
		return object;
	}

	@RequestMapping(params = "petrolRec", method = { RequestMethod.POST })
	public ModelAndView petrolRec(HttpServletRequest request) {
		//bindModel(request);
		final PetrolRequestApprovalModel birthModel = this.getModel();
		this.getModel();

		this.getModel().setPetrolRequisitionDTO(birthModel.getPetrolRequisitionDTO());
		LookUp pumpTyp = CommonMasterUtility.getNonHierarchicalLookUpObject(Long.valueOf(this.getModel().getEntity().getFuelType()),
							new Organisation(this.getModel().getEntity().getOrgid()));
		LookUp fuelQuantUnitDesc = CommonMasterUtility.getNonHierarchicalLookUpObject(
				Long.valueOf(this.getModel().getEntity().getFuelQuantUnit()), new Organisation(this.getModel().getEntity().getOrgid()));

		this.getModel().getEntity().setPumpTyp(pumpTyp.getDescLangFirst());
		this.getModel().getEntity().setFuelQuantUnitDesc(fuelQuantUnitDesc.getDescLangFirst());
		this.getModel().getEntity().setPumpa(vehicleMasterService.fetchVehicleNoByVeId(this.getModel().getEntity().getVeId()));
		this.getModel().getEntity().setVeChasisSrno(vehicleMasterService.fetchChasisNoByVeIdAndOrgid(
				this.getModel().getEntity().getVeId(), this.getModel().getEntity().getOrgid()));

		return new ModelAndView("petrolReceipt", MainetConstants.FORM_NAME, this.getModel());
	}

}

