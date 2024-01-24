package com.abm.mainet.disastermanagement.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.common.workflow.domain.WorkflowMas;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;
import com.abm.mainet.disastermanagement.dto.CallScrutinyDTO;
import com.abm.mainet.disastermanagement.dto.ComplainRegisterDTO;

/**
 * Service Interface for managing ComplainRegister.
 */
@WebService
public interface IComplainRegisterService {

	/**
	 * Save a complainRegister.
	 *
	 * @param complainRegisterDTO the entity to save
	 * @return the persisted entity
	 */
	ComplainRegisterDTO save(ComplainRegisterDTO complainRegisterDTO);

	/**
	 * Get all the complainRegisters.
	 *
	 * @return the list of entities
	 */
	List<ComplainRegisterDTO> findAll(Long orgid);

	/**
	 * Get the complainRegister.
	 *
	 * @param id the id of the entity
	 * @return the entity
	 */
	ComplainRegisterDTO findOne(Long id);
	/**
	 * Get the complainRegister.
	 *
	 * @param complainNo the complain no of the entity
	 * @param orgid      organization id
	 * @return the entity
	 */
	ComplainRegisterDTO findByComplainNo(String complainNo, Long orgid);

	String initiateWorkFlowWorksService(WorkflowTaskAction workflowActionDto, WorkflowMas workFlowMas, String url,
			String workFlowFlag);

	String updateWorkFlowWorksService(WorkflowTaskAction workflowTaskAction);

	void updateComplainStatus(String complainNo, String status);

	ComplainRegisterDTO getComplainRegData(String complainNo, Long orgId,String complainStatus);

	List<ComplainRegisterDTO> findByCompNoFrmDtToDt(String complainNo, Date frmDate, Date toDate, Long orgId, String formStatusStr, Long userId, Long deptId);

	void updateComplainRegData(String complainNo, String status, String remark, Long orgid,Long empId,ComplainRegisterDTO complainRegisterDTO);
	List<ComplainRegisterDTO> getAllComplaint(Long orgId, String complainStatus);
	List<ComplainRegisterDTO> getcomplaintData(Long orgId, Long userId, Long deptId, String formStatusStr);
	void saveComplainRegistration(ComplainRegisterDTO entity);

	ComplainRegisterDTO getComplainById(Long complainId);
	
	List<CallScrutinyDTO> getDepartmentNameAndEmpName(Long complaintId);
	
	List<ComplainRegisterDTO> getComplaintStatus(String status);

	void updateComplainRegistration(ComplainRegisterDTO complainRegList,ComplainRegisterDTO complainRegisterDTO);

	List<ComplainRegisterDTO> findInjuryDetails(Long complaintType1, Long complaintType2, Long location,
			String complainNo, Long orgId, String srutinyStatus);
	

	
	
}