package com.abm.mainet.workManagement.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.workManagement.dto.WorkCompletionRegisterDto;
import com.abm.mainet.workManagement.dto.WorkDefinationWardZoneDetailsDto;
import com.abm.mainet.workManagement.dto.WorkDefinationYearDetDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;
import com.abm.mainet.workManagement.dto.WorkDefinitionSancDetDto;
import com.abm.mainet.workManagement.rest.dto.ViewWorkDefinitionDto;

/**
 * Work Definition master related services
 * 
 * @author hiren.poriya
 * @Since 08-Feb-2018
 */
public interface WorkDefinitionService {

	/**
	 * this service is used to create new work definitions
	 * 
	 * @param workDefDto
	 * @param requestDTO
	 * @param list
	 */
	WorkDefinitionDto createWorkDefinition(WorkDefinitionDto workDefDto, List<DocumentDetailsVO> list,
			RequestDTO requestDTO);

	/**
	 * this service is used to find all work definitions by organization id
	 * 
	 * @param orgId
	 * @return List of work definition DTOs
	 */
	List<WorkDefinitionDto> findAllWorkDefinitionsByOrgId(Long orgId);

	/**
	 * this service is used to filter search based on work code or work name or work
	 * start date or work end date or project id or work type id or project phase id
	 * 
	 * @param orgId
	 * @param workDefDto
	 * @return List<WorkDefinitionDto>
	 */
	List<WorkDefinitionDto> filterWorkDefRecords(Long orgId, WorkDefinitionDto workDefDto);

	/**
	 * this service is used to find work definition details by its primary key work
	 * id.
	 * 
	 * @param workId
	 * @return WorkDefinitionDto
	 */
	WorkDefinitionDto findAllWorkDefinitionById(Long workId);

	/**
	 * this service is used to update work definitions
	 * 
	 * @param workDefDto
	 * @param list
	 * @param requestDTO
	 * @param removeAssetIdList
	 * @param removeYearIdList
	 * @param removedDocIds
	 * @param removeWarZoneIdList
	 */
	void updateWorkDefinition(WorkDefinitionDto workDefDto, List<DocumentDetailsVO> list, RequestDTO requestDTO,
			List<Long> removeAssetIdList, List<Long> removeYearIdList, List<Long> removedDocIds,
			List<Long> removeScanIdList, List<Long> removeWarZoneIdList);

	/**
	 * this service is used to find all Work definition except which are used in
	 * work estimation
	 * 
	 * @param orgid
	 * @param workIdList
	 * @param projId
	 * @return
	 */
	List<WorkDefinitionDto> findAllWorkDefinitionsExcludedWork(long orgid, List<Long> workIdList, Long projId);

	void updateWorkDefinationMode(Long workDefination, String flag);

	/**
	 * get list Work Definition by search criteria (only work which is associated
	 * with estimation)
	 * 
	 * @param orgId
	 * @param estimateNo
	 * @param projectId
	 * @param workName
	 * @param status
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	List<WorkDefinitionDto> getWorkDefinationBySearch(Long orgId, String estimateNo, Long projectId, Long workName,
			String status, Date fromDate, Date toDate);

	/**
	 * find All Work Defination By ProjId
	 * 
	 * @param orgId
	 * @param projId
	 * @return
	 */
	List<WorkDefinitionDto> findAllWorkDefinationByProjId(Long orgId, Long projId);

	/**
	 * find All Work Defination Sanction By ProjId
	 * 
	 * @param orgId
	 * @param projId
	 * @param workSancNo
	 * @return
	 */
	WorkDefinitionDto findAllWorkDefinationSanctionByProjId(Long orgId, Long projId, String workSancNo);

	/**
	 * find All Work By Work List
	 * 
	 * @param orgid
	 * @param workId
	 * @return
	 */
	List<WorkDefinitionDto> findAllWorkByWorkList(long orgid, List<Long> workId);

	/**
	 * this service is used to find work definition details by its work code
	 * 
	 * @param workCode
	 * @return WorkDefinitionDto
	 */
	WorkDefinitionDto findWorkDefinitionByWorkCode(String workCode, Long orgId);

	/**
	 * find all approved work definitions by project id and orgId
	 * 
	 * @param projId
	 * @param orgid
	 * @return List<WorkDefinitionDto>
	 */
	List<WorkDefinitionDto> findAllApprovedNotUsedInOtherTenderWorkByTenderIdAndProjId(Long projId, Long orgid,
			Long tenderId);

	/**
	 * get All approved and not initiated works by project id and org id
	 * 
	 * @param projId
	 * @param orgId
	 * @return list of not initiated works
	 */
	List<WorkDefinitionDto> findAllApprovedNotInitiatedWorkByProjIdAndOrgId(Long projId, Long orgId);

	/**
	 * update work status to initiated.
	 * 
	 * @param worksId
	 */
	void updateWorksStatusToInitiated(List<Long> worksId);

	/**
	 * get All Approved and not initiated work which are not being used in other
	 * tender but used in particular tender
	 * 
	 * @param tndId
	 * @param projId
	 * @param orgid
	 * @return List<WorkDefinitionDto>
	 */
	List<WorkDefinitionDto> findAllApprovedNotInitiatedWork(Long tndId, Long projId, Long orgid);

	/**
	 * update work id status as 'T' means work is used for tender updation
	 * 
	 * @param workIds
	 */
	void updateWorkStatusAsTendered(List<Long> workIds);

	/**
	 * update Sanction Number
	 * 
	 * @param sanctionNumber
	 * @param workId
	 * @param orgid
	 * @param deptId
	 * @param workSancBy
	 * @param workDesignBy
	 */
	void updateSanctionNumber(String sanctionNumber, Long workId, Long orgid, Long deptId, String workSancBy,
			String workDesignBy);

	/**
	 * get All Sanction Details By WorkId
	 * 
	 * @param workId
	 * @return
	 */
	List<WorkDefinitionSancDetDto> getAllSanctionDetailsByWorkId(Long workId);

	/**
	 * used to update total estimate amount by work id
	 * 
	 * @param WorkId
	 * @param sorAndNonSorAmount
	 * @return
	 */
	void updateTotalEstimatedAmount(Long workId, BigDecimal sorAndNonSorAmount);

	/**
	 * find All Works By ProjId And Status
	 * 
	 * @param orgId
	 * @param projId
	 * @return
	 */
	public List<Object> findAllWorksByProjIdAndStatus(Long orgId, Long projId);

	/**
	 * find Sanction No By WorkId
	 * 
	 * @param orgId
	 * @param workId
	 * @return
	 */
	public List<WorkDefinitionSancDetDto> findSanctionNoByWorkId(Long orgId, Long workId);

	// public List<WorkDefinitionDto> findAllWorksByProjIdAndStatus(Long orgId ,
	// Long projId, String status);

	/**
	 * used to update work Completion Date by work id
	 * 
	 * @param WorkId
	 * @param workCompletionDate
	 * @return
	 */
	void updateWorkCompletionDate(Long workId, Date workCompletionDate, String completionNo);

	/**
	 * this service is used to find all completed Work definition
	 * 
	 * @param orgId
	 * @return List<WorkDefinitionDto>
	 */
	List<WorkDefinitionDto> findAllCompletedWorks(Long orgId);

	/**
	 * this service is used to find all completed Work definition By project Id
	 * 
	 * @param orgId
	 * @return List<WorkDefinitionDto>
	 */
	List<WorkDefinitionDto> findAllCompletedWorksByProjId(Long projId, String flag, Long orgId);

	/**
	 * this service is used to filter Completion Records
	 * 
	 * @param projId
	 * @param workId
	 * @param completionNo
	 * @param orgId
	 * @return
	 */
	List<WorkDefinitionDto> filterCompletionRecords(Long projId, Long workId, String completionNo, Long orgId);

	/**
	 * This Service is used for get All Budget Head By WorkId
	 * 
	 * @param workId
	 * @return
	 */
	List<WorkDefinationYearDetDto> getAllBudgetHeadByWorkId(Long workId);

	/**
	 * get Work Completion Register By WorkId
	 * 
	 * @param workId
	 * @return
	 */
	WorkDefinitionDto getWorkRegisterByWorkId(Long workId, String raCode, Long orgId);

	/**
	 * get Work Completion Register By WorkId
	 * 
	 * @param workId
	 * @return
	 */
	List<WorkCompletionRegisterDto> getWorkRegisterDetailsByWorkId(Long workId, String raCode, Long orgId);

	/**
	 * get Budget Expenditure Details
	 * 
	 * @param workId
	 * @return
	 */
	VendorBillApprovalDTO getBudgetExpenditureDetails(VendorBillApprovalDTO billApprovalDTO);

	/**
	 * this service is used to find all Work Against which work-order Generated
	 * 
	 * @param orgId
	 * @return List<WorkDefinitionDto>
	 */
	List<WorkDefinitionDto> findAllWorkOrderGeneratedWorks(Long orgId);

	/**
	 * update work overhead amount
	 * 
	 * @param workId
	 * @param overheadAmount
	 */
	void updateOverHeadAmount(Long workId, BigDecimal overheadAmount);

	/**
	 * find All Work Id By OrgId With Work Status not NULL AND NOT D AND NOT P MODE
	 * 
	 * @param workIdList
	 * @param orgId
	 * @return List<Long>
	 */

	List<WorkDefinitionDto> findAllWorkByOrgIdWithWorkStatus(Long orgId);

	/**
	 * get ProjCode And WorkCode
	 * 
	 * @param projCode
	 * @param workCode
	 * @return
	 */
	WorkDefinitionDto getProjCodeAndWorkCode(String projCode, String workCode);

	void saveSanctionDetailsApproval(WorkDefinitionSancDetDto definitionSancDetDto);

	/**
	 * get WorkDefinationDto by workId
	 * 
	 * @param workId
	 * @param orgId
	 * @return
	 */
	WorkDefinitionDto findWorkDefinitionByWorkId(Long workId, Long orgId);

	// To save Year details
	void saveWorkDefinationYesrDet(WorkDefinitionDto workDefinitionDto, WorkDefinationYearDetDto definitionDto);

	// To save Ward zone details
	void saveWardZoneDto(WorkDefinitionDto workDefinitionDto,
			List<WorkDefinationWardZoneDetailsDto> definationWardZoneDetailsDto);

	List<WorkDefinationYearDetDto> getYearDetailByWorkId(WorkDefinitionDto workDefDto, Long orgId);
	
	
	ViewWorkDefinitionDto findAllWorkDefinitionByWorkCodeNo(String workCode,Long orgId);

	List<WorkDefinitionDto> findAllWorkDefByProjIdAndWorkType(Long orgId, Long projId, Long workType);

	List<WorkDefinitionDto> findAllWorkdefinationforScehedularByOrgId(Long orgId);

	void updateWorkDefinitionLatLong(WorkDefinitionDto workDefDto, List<DocumentDetailsVO> attachmentList,
			RequestDTO requestDTO);

	VendorBillApprovalDTO getBudgetExpenditure(VendorBillApprovalDTO billApprovalDTO);

	void updateBudgetHead(Long yearId, Long sacHeadId, Long orgId);
}
