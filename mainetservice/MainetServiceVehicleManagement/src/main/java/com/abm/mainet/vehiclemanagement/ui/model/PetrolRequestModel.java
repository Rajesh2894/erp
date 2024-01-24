package com.abm.mainet.vehiclemanagement.ui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.master.service.TbFinancialyearService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.ui.model.AbstractFormModel;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.SeqGenFunctionUtility;
import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.common.workflow.service.IWorkflowTyepResolverService;
import com.abm.mainet.vehiclemanagement.Constants.Constants;
import com.abm.mainet.vehiclemanagement.dto.GenVehicleMasterDTO;
import com.abm.mainet.vehiclemanagement.dto.PetrolRequisitionDTO;
import com.abm.mainet.vehiclemanagement.service.IPetrolRequisitionService;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class PetrolRequestModel extends AbstractFormModel {

	private static final long serialVersionUID = -7978369850762286197L;

	@Autowired
	private IPetrolRequisitionService iPetrolRequisitionService;

	private PetrolRequisitionDTO petrolRequisitionDTO = new PetrolRequisitionDTO();

	private GenVehicleMasterDTO genVehicleMasterDTO = new GenVehicleMasterDTO();
	
	private List<GenVehicleMasterDTO> vehicleMasterList = new ArrayList<>();

	private String departments;

	private String saveMode;
	String requestStatus = Constants.OPEN_STATUS;
	
    private List<AttachDocs> attachDocsList = new ArrayList<>();

	
	@Override
	public boolean saveForm() {
		Long createdBy = UserSession.getCurrent().getEmployee().getEmpId();
		Date createdDate = new Date();
		String lgIpMac = UserSession.getCurrent().getEmployee().getEmppiservername();
		Long orgId = UserSession.getCurrent().getOrganisation().getOrgid();
		String requestStatus = Constants.OPEN_STATUS;

		if (petrolRequisitionDTO.getRequestId() == null) {			
			if(null==petrolRequisitionDTO.getVeId())
				petrolRequisitionDTO.setVeId(Long.valueOf(petrolRequisitionDTO.getVeChasisSrno()));
			
			petrolRequisitionDTO.setOrgid(orgId);
			petrolRequisitionDTO.setCreatedBy(createdBy);
			petrolRequisitionDTO.setCreatedDate(createdDate);
			petrolRequisitionDTO.setLgIpMac(lgIpMac);
		//	petrolRequisitionDTO.setRequestStatus(requestStatus);
		} else {
			petrolRequisitionDTO.setUpdatedBy(createdBy);
			petrolRequisitionDTO.setUpdatedDate(createdDate);
			petrolRequisitionDTO.setLgIpMacUpd(lgIpMac);
		}
		Long statusId=petrolRequisitionDTO.getVeId();
		ServiceMaster service = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class).getServiceMasterByShortCode(Constants.FUEL_SERVICE_CODE, orgId);

		FinancialYear financiaYear = ApplicationContextProvider.getApplicationContext().getBean(TbFinancialyearService.class).getFinanciaYearByDate(new Date());
		
		String deptCode = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDeptCode(service.getTbDepartment().getDpDeptid());
		
		String finacialYear = Utility.getFinancialYear(financiaYear.getFaFromDate(), financiaYear.getFaToDate());
		
		final Long sequence = ApplicationContextProvider.getApplicationContext()
				.getBean(SeqGenFunctionUtility.class).generateSequenceNo(Constants.VEHICLE_DRPT_CODE, Constants.FEUL_CALL_REG_TABLE,
						Constants.FEUL_CALL_REG_TABLE_COLUMN, orgId, MainetConstants.FlagC, financiaYear.getFaYear());
		
		String fuelReqNo = deptCode + MainetConstants.WINDOWS_SLASH + finacialYear + MainetConstants.WINDOWS_SLASH
				+ String.format(MainetConstants.WorksManagement.FOUR_PERCENTILE, sequence);
		
	 	petrolRequisitionDTO.setFuelReqNo(fuelReqNo);
    	
	 	String reqStatus = iPetrolRequisitionService.fetchVehicleNoByVeId(statusId, "O");
	 	if(reqStatus !=null && !(reqStatus.isEmpty()) && reqStatus.equals("O")) {
			this.setSuccessMessage(ApplicationSession.getInstance().getMessage("PetrolRequisition.VehicleInProgress"));
		}
		else {
		petrolRequisitionDTO.setRequestStatus(requestStatus);
		petrolRequisitionDTO.setPetrolWfStatus(Constants.OPEN);
		petrolRequisitionDTO.setDate(createdDate);
		PetrolRequisitionDTO dto = iPetrolRequisitionService.save(petrolRequisitionDTO);
		this.setSuccessMessage(ApplicationSession.getInstance().getMessage("PetrolRequisitionDTO.form.closed"+ "", 
				new Object[] { petrolRequisitionDTO.getFuelReqNo()}));
		
		// initiate workflow process start
		getWorkflowActionDto().setApplicationId(dto.getRequestId());
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setReferenceId(getWorkflowActionDto().getReferenceId());
		requestDTO.setApplicationId(getWorkflowActionDto().getApplicationId());

		ServiceMaster service1 = ApplicationContextProvider.getApplicationContext()
				.getBean(ServiceMasterService.class).getServiceMasterByShortCode("FRF", orgId);

		WorkflowMas workFlowMas = ApplicationContextProvider.getApplicationContext()
				.getBean(IWorkflowTyepResolverService.class).resolveServiceWorkflowType(orgId,
						service1.getTbDepartment().getDpDeptid(), service1.getSmServiceId(), null, null, null, null,
						null);
		ApplicationContextProvider.getApplicationContext().getBean(IPetrolRequisitionService.class)
				.initiateWorkFlowWorksService(this.prepareWorkFlowTaskAction(), workFlowMas,
						"petrolRequestForm.html", MainetConstants.FlagA);
		// initiate workflow process end
		}
		return true;
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
		taskAction.setReferenceId(petrolRequisitionDTO.getFuelReqNo());
		//taskAction.setApplicationId(petrolRequisitionDTO.getRequestId());
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


	public IPetrolRequisitionService getiPetrolRequisitionService() {
		return iPetrolRequisitionService;
	}


	public void setiPetrolRequisitionService(IPetrolRequisitionService iPetrolRequisitionService) {
		this.iPetrolRequisitionService = iPetrolRequisitionService;
	}


	public List<GenVehicleMasterDTO> getVehicleMasterList() {
		return vehicleMasterList;
	}


	public void setVehicleMasterList(List<GenVehicleMasterDTO> vehicleMasterList) {
		this.vehicleMasterList = vehicleMasterList;
	}

	public String getRequestStatus() {
		return requestStatus;
	}


	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}


	public List<AttachDocs> getAttachDocsList() {
		return attachDocsList;
	}


	public void setAttachDocsList(List<AttachDocs> attachDocsList) {
		this.attachDocsList = attachDocsList;
	}


         
}
