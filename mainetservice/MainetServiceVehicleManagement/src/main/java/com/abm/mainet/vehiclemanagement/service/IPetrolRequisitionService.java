package com.abm.mainet.vehiclemanagement.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.vehiclemanagement.dto.PetrolRequisitionDTO;

public interface IPetrolRequisitionService {

	public PetrolRequisitionDTO save(PetrolRequisitionDTO petrolRequisitionDTO);

	public List<PetrolRequisitionDTO> getAllRecord(Long orgId);

	List<PetrolRequisitionDTO> getAllVehicles(Long orgid);

	public List<PetrolRequisitionDTO> searchPetrolRequest(Date fromDate, Date toDate, Long department, Long veNo,
			Long orgid);

	public PetrolRequisitionDTO getDetailById(Long requestId);

	Boolean checkEmployeeRole(UserSession current);

	public void updatePetrolApproveStatus(PetrolRequisitionDTO entity, String status, String lastDecision);

	public void updatePetrolWorkFlowStatus(Long requestId, String pending, long orgId);
	
	public void updatePetrolWorkFlowStatus(Long requestId, String pending, long orgId, Long puId);

	public void updatePetrolStatus(Long referenceId, String flaga);

	public List<PetrolRequisitionDTO> getDetailByVehNo(String complainNo, Long orgid);

	public String initiateWorkFlowWorksService(WorkflowTaskAction prepareWorkFlowTaskAction, WorkflowMas workFlowMas,
			String url, String workFlowFlag);

	public String updateWorkFlowPetrolService(WorkflowTaskAction workflowTaskAction);

	String updateWorkFlowWorksService(WorkflowTaskAction workflowTaskAction);

	public void updateComplainStatus(Long long1, String flagr);

	public void updatePetrolStatus(String referenceId, String flagr);

	PetrolRequisitionDTO findByComplainNo(String referenceId, long orgid);

	public List<PetrolRequisitionDTO> fetchVeNoByDeptAndVeType(Long department, Long vehicleType, Long orgId);

	String fetchVehicleNoByVeId(Long veId, String requestStatus);
	
	String getRemark(String refId,Long orgId);
	

}
