/*package com.abm.mainet.firemanagement.ui.model;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.firemanagement.Constants.Constants;
import com.abm.mainet.firemanagement.dto.PetrolRequisitionDTO;
import com.abm.mainet.firemanagement.service.IPetrolRequisitionService;
import com.abm.mainet.vehicle.management.dto.GenVehicleMasterDTO;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class PetrolRequisitionModel extends AbstractFormModel {

	private static final long serialVersionUID = -7978369850762286197L;

	@Autowired
	private IPetrolRequisitionService iPetrolRequisitionService;

	private PetrolRequisitionDTO petrolRequisitionDTO = new PetrolRequisitionDTO();

	private GenVehicleMasterDTO genVehicleMasterDTO = new GenVehicleMasterDTO();

	private String departments;

	private String saveMode;

	String requestStatus = Constants.OPEN_STATUS;

	@Override
	public boolean saveForm() {
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		Date createdDate = new Date();
		String lgIpMac = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String requestStatus = Constants.OPEN_STATUS;

		if (petrolRequisitionDTO.getRequestId() == null) {

			petrolRequisitionDTO.setOrgid(orgId);
			petrolRequisitionDTO.setCreatedBy(createdBy);
			petrolRequisitionDTO.setCreatedDate(createdDate);
			petrolRequisitionDTO.setLgIpMac(lgIpMac);
			petrolRequisitionDTO.setRequestStatus(requestStatus);

		PetrolRequisitionDTO dto=iPetrolRequisitionService.save(petrolRequisitionDTO);
			this.setSuccessMessage(ApplicationSession.getInstance().getMessage(" Record Saved Succesfully"));

			// initiate workflow process start
			
			getWorkflowActionDto().setApplicationId(dto.getRequestId());
			RequestDTO requestDTO = new RequestDTO();
			requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
			requestDTO.setApplicationId(getWorkflowActionDto().getApplicationId());
			
			ServiceMaster service = ApplicationContextProvider.getApplicationContext()
					.getBean(ServiceMasterService.class).getServiceMasterByShortCode("PRF", orgId);

			WorkflowMas workFlowMas = ApplicationContextProvider.getApplicationContext()
					.getBean(IWorkflowTyepResolverService.class).resolveServiceWorkflowType(orgId,
							service.getTbDepartment().getDpDeptid(), service.getSmServiceId(), null, null, null, null,
							null);
			ApplicationContextProvider.getApplicationContext().getBean(IPetrolRequisitionService.class)
					.initiateWorkFlowWorksService(this.prepareWorkFlowTaskAction(), workFlowMas,
							"petrolRequestForm.html", MainetConstants.FlagA);
			// initiate workflow process end
			

			return true;
		}
		return false;
		
		}

	
	public WorkflowTaskAction prepareWorkFlowTaskAction() {
		WorkflowTaskAction taskAction = new WorkflowTaskAction();
		taskAction.setOrgId(UserSession.getCurrent().getOrganisation().getOrgid());
		taskAction.setEmpId(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpType(UserSession.getCurrent().getEmployee().getEmplType());
		taskAction.setDateOfAction(new Date());
		taskAction.setCreatedDate(new Date());
		taskAction.setCreatedBy(UserSession.getCurrent().getEmployee().getEmpId());
		taskAction.setEmpName(UserSession.getCurrent().getEmployee().getEmplname());
		taskAction.setEmpEmail(UserSession.getCurrent().getEmployee().getEmpemail());
		//taskAction.setReferenceId(petrolRequisitionDTO.getVeNo());
		taskAction.setApplicationId(petrolRequisitionDTO.getRequestId());
		taskAction.setPaymentMode(MainetConstants.FlagF);
		taskAction.setDecision("SUBMITED");
		return taskAction;
	}

	public String getDepartments() {
		return departments;
	}

	public void setDepartments(String departments) {
		this.departments = departments;
	}

	public String getSaveMode() {
		return saveMode;
	}

	public void setSaveMode(String saveMode) {
		this.saveMode = saveMode;
	}

	public PetrolRequisitionDTO getPetrolRequisitionDTO() {
		return petrolRequisitionDTO;
	}

	public void setPetrolRequisitionDTO(PetrolRequisitionDTO petrolRequisitionDTO) {
		this.petrolRequisitionDTO = petrolRequisitionDTO;
	}

	public GenVehicleMasterDTO getGenVehicleMasterDTO() {
		return genVehicleMasterDTO;
	}

	public void setGenVehicleMasterDTO(GenVehicleMasterDTO genVehicleMasterDTO) {
		this.genVehicleMasterDTO = genVehicleMasterDTO;
	}

}
*/