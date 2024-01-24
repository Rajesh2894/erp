package com.abm.mainet.firemanagement.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.springframework.data.repository.query.Param;

import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.firemanagement.domain.OccuranceBookEntity;
import com.abm.mainet.firemanagement.dto.FireCallRegisterDTO;
import com.abm.mainet.firemanagement.dto.OccuranceBookDTO;
import com.abm.mainet.vehicle.management.dto.GenVehicleMasterDTO;

/**
 * Service Interface for managing ComplainRegister.
 */
@WebService
public interface IFireCallRegisterService {

	/**
	 * Save a complainRegister.
	 *
	 * @param fireCallRegisterDTO the entity to save
	 * @return the persisted entity
	 */
	FireCallRegisterDTO save(FireCallRegisterDTO fireCallRegisterDTO);

	/**
	 * Get all the complainRegisters.
	 *
	 * @return the list of entities
	 */
	List<FireCallRegisterDTO> findAll(Long orgid);

	/**
	 * Get the complainRegister.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	FireCallRegisterDTO findOne(Long id);

	/**
	 * Get the complainRegister.
	 *
	 * @param complainNo the complain no of the entity
	 * @param orgid      organization id
	 * @return the entity
	 */
	
	
	
	FireCallRegisterDTO findByComplainNo(String complainNo, Long orgid);
	
	FireCallRegisterDTO findByComplainNoCallCloser(String complainNo, Long orgid);

	String initiateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas, String url,
			String workFlowFlag);

	String updateWorkFlowWorksService(WorkflowTaskAction workflowTaskAction);

	void updateComplainStatus(String complainNo, String status);

	List<FireCallRegisterDTO> searchFireCallRegister(String complainNo, String complaintStatus, String fireStation,
			Long orgid);
	
	List<FireCallRegisterDTO> searchFireCallCloser(String complainNo, String complaintStatus, String fireStation, Long orgid);

	public String updateWorkFlowFireService(WorkflowTaskAction workflowTaskAction);
	
	void updateFireApproveStatus(FireCallRegisterDTO entity, String status, String lastDecision);

	void updateFireWorkFlowStatus(Long cmplntId, String taskNamePrevious, Long orgId);

	FireCallRegisterDTO saveFireCallcloser(FireCallRegisterDTO fireCallRegisterDTO);
	
	FireCallRegisterDTO findByCallCloserId(Long closerId, Long orgid);

	List<FireCallRegisterDTO> findByCloserCompId(Long compId, Long orgId);

	
	FireCallRegisterDTO getcloserDataAfterAprovalAndSaveInCallRegister(Long closerId, Long orgid);
	  
	void updatecomplaintStatusInSB(Long cmplId,Long orgId,String status);

	FireCallRegisterDTO findOne1(Long id);

	List<FireCallRegisterDTO> getAllVehiclesAssign(long orgId,long deptId);

	public List<FireCallRegisterDTO> findAllFire(Long orgid);

	FireCallRegisterDTO getFireById(Long cmplntId);

	List<FireCallRegisterDTO> searchFireCallRegisterReg(Date fromDate,Date toDate,String fireStation,String complainNo,Long orgid,String status);

	public List<Long> getEmpId(Long depId, Long orgid);

	public List<Long> getEmpIds(Long depId, Long orgid);

	OccuranceBookDTO findAlloccurrences(String cmplntNo, Long orgId);
	
	public GenVehicleMasterDTO getVehicleByVehicleMasterId(Long vehicleId,Long orgId);

	void updateFinalCallClosureComment(String cmplntNo, String comment, Long orgId);
	
}
