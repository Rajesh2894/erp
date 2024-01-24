/*package com.abm.mainet.firemanagement.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.common.utility.UserSession;
import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.firemanagement.dto.PetrolRequisitionDTO;

public interface IPetrolRequisitionService {
	
	
	
	public PetrolRequisitionDTO save(PetrolRequisitionDTO petrolRequisitionDTO);
	*//**
	 * Save a PetrolRequisition
	 *
	 * @param petrolRequisitionDTO the entity to save
	 * @return the persisted entity
	 *//*	
	
	public List<PetrolRequisitionDTO> getAllRecord(Long orgId);
	
	*//**
	 * This will help us to search VehicleLogBook Details by fromDate, toDate , fireStation
	 * 
	 * @param id
	 * @return
	 *//*
	
	List<PetrolRequisitionDTO> searchFireCallRegisterwithDate(Date fromDate,Date toDate,String veNo,Long orgid);


	*//**
	 * This will help us to get VehicleLogBook Details by veId 
	 * 
	 * @param id
	 * @return
	 *//*

	public PetrolRequisitionDTO getVehicleById(Long veID);
	

	List<PetrolRequisitionDTO>  getAllVehicles(Long orgid);



	    List<PetrolRequisitionDTO> fetchVeNoByDeptAndVeType(Long department, Long vehicleType, Long orgId);
        public PetrolRequisitionDTO saveDetails(PetrolRequisitionDTO petrolRequisitionDTO);
	    public List<PetrolRequisitionDTO> searchPetrolRequest(Date fromDate, Date toDate, Long department, String veNo,Long orgid);
	     public PetrolRequisitionDTO getDetailById(Long requestId);
	 String getRecordByDate(Long orgid,String requestStatus,String requestId); 
		Boolean checkEmployeeRole(UserSession current);
		public void updatePetrolApproveStatus(PetrolRequisitionDTO entity, String status, String lastDecision);
		public void updatePetrolWorkFlowStatus(Long requestId, String pending, long orgId);
		public void updatePetrolStatus(Long referenceId, String flaga);
		public  List<PetrolRequisitionDTO> getDetailByVehNo(Long complainNo,Long orgid);
		
		public String initiateWorkFlowWorksService(WorkflowTaskAction prepareWorkFlowTaskAction, WorkflowMas workFlowMas,
				String url, String workFlowFlag);
		
		public String updateWorkFlowFireService(WorkflowTaskAction workflowTaskAction);
		public void updateComplainStatus(Long requestId, String flagr);
		String updateWorkFlowWorksService(WorkflowTaskAction workflowTaskAction);
		

}
*/