package com.abm.mainet.workManagement.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.abm.mainet.workManagement.domain.WorkDefinationEntity;
import com.abm.mainet.workManagement.domain.WorkDefinitionSancDet;
import com.abm.mainet.workManagement.dto.WorkDefinitionDto;

/**
 * @author hiren.poriya
 * @Since 12-Feb-2018
 */
public interface WorkDefinitionDao {

    /**
     * used to create new work definition
     * 
     * @param workDefDto
     */
    WorkDefinationEntity createWorkDefination(WorkDefinationEntity workDefEntity);

    /**
     * this service is used to find all work definitions by organization id
     * 
     * @param orgId
     * @return List of work definition DTOs
     */
    List<WorkDefinationEntity> findAllWorkDefinitionsByOrgId(Long orgId);

    /**
     * used to filter search based on work code or work name or work start date or work end date or project id or work type id or
     * project phase id
     * 
     * @param orgId
     * @param workDefDto
     * @return List<WorkDefinitionDto>
     */
    List<WorkDefinationEntity> filterWorkDefRecords(Long orgId, WorkDefinitionDto workDefDto);

    /**
     * this service is used to find work definition details by its primary key work id.
     * 
     * @param workId
     * @return WorkDefinitionDto
     */
    WorkDefinationEntity findAllWorkDefinitionById(Long workId);

    /**
     * Inactive asset details for given primary keys
     * 
     * @param removeAssetIdList
     * @param updatedBy
     */
    void iactiveAssetsByIds(List<Long> removeAssetIdList, Long updatedBy);

    /**
     * inactive work definition year details for given primary keys
     * 
     * @param removeYearIdList
     * @param updatedBy
     */
    void iactiveYearsByIds(List<Long> removeYearIdList, Long updatedBy);

    List<WorkDefinationEntity> findAllWorkDefinitionsExcludedWork(long orgid, List<Long> workIdList, Long projId);

    void updateWorkDefinationMode(Long workDefination, String flag);

    List<WorkDefinationEntity> getWorkDefinationBySearch(Long orgId, String estimateNo, Long projectId, Long workName,
            String status, Date fromDate, Date toDate);

    List<WorkDefinationEntity> filterWorkDefRecordsByProjId(Long orgId, Long projId);

    List<WorkDefinitionSancDet> filterWorkDefSanctionRecordsByProjId(Long orgId, Long projId, String workSancNo);

    List<WorkDefinationEntity> findAllWorkByWorkList(long orgId, List<Long> workId);

    /**
     * this service is used to find work definition details by work code
     * 
     * @param workCode
     * @return WorkDefinitionEntity
     */
    WorkDefinationEntity findWorkDefinitionByWorkCode(String workCode, Long orgId);

    /**
     * find all approved work definitions by project id and orgId
     * 
     * @param projId
     * @param orgid
     * @return List<WorkDefinitionDto>
     */
    List<WorkDefinationEntity> findAllApprovedNotUsedInOtherTenderWorkByTenderIdAndProjId(Long projId, Long orgId,
            Long tenderId);

    /**
     * get All approved and not initiated works by project id and org id
     * 
     * @param projId
     * @param orgId
     * @return list of not initiated works
     */
    List<WorkDefinationEntity> findAllApprovedNotInitiatedWorkByProjIdAndOrgId(Long projId, Long orgId);

    /**
     * update work state to initiated
     * 
     * @param worksId
     * @param orgId
     */
    void updateWorksStatusToInitiated(List<Long> worksId);

    /**
     * get All Approved and not initiated work which are not being used in other tender but used in particular tender
     * 
     * @param tndId
     * @param projId
     * @param orgid
     * @return List<WorkDefinationEntity>
     */
    List<WorkDefinationEntity> findAllApprovedNotInitiatedWork(Long tndId, Long projId, Long orgId);

    /**
     * update works status as 'T' means works is used for tender
     * 
     * @param workIds
     */
    void updateWorkStatusAsTendered(List<Long> workIds);

    void deleteSanctionDetails(List<Long> removeScanIdList);

    List<WorkDefinitionSancDet> finadAllSanctionDetailsByWorkId(Long workId);

    void updateWorkSanctionNumber(String workSancNo, Long workId, Long orgid, Long deptId, String workSancBy,
            String workDesignBy);

    List<Object[]> filterWorkDefRecordsByProjIdAndStatus(Long orgId, Long projId);

    List<WorkDefinitionSancDet> filterSanctionNoRecordsByWrojId(Long orgId, Long workId);

    /// List<WorkDefinationEntity> filterWorkDefRecordsByProjIdAndStatus(Long orgId, Long projId, String status);

    void updateTotalEstimatedAmount(Long workId, BigDecimal totalAmount);

    void updateWorkCompletionDate(Long workId, Date workCompletionDate, String completionNo);

    /**
     * this service is used to find all completed Work definition
     * @param orgId
     * @return List<WorkDefinitionDto>
     */
    List<WorkDefinationEntity> findAllCompletedWorks(Long orgId);

    /**
     * this service is used to find all completed Work definition By project Id
     * @param projId
     * @param orgId
     * @return List<WorkDefinitionDto>
     */
    List<Object[]> findAllCompletedWorksByProjId(Long projId, String flag, Long orgId);

    /**
     * this service is used to find all completed Work definition By project Id,workId,completionNo
     * @param projId
     * @param workId
     * @param completionNo
     * @param orgId
     * @return List<WorkDefinationEntity>
     */
    List<WorkDefinationEntity> filterCompletionRecords(Long projId, Long workId, String completionNo, Long orgId);

    /**
     * get All Budget Head By WorkId
     * @param workId
     * @return
     */
    List<Object[]> getAllBudgetHeadByWorkId(Long workId);

    /**
     * get Work Completion Register By WorkId
     * @param workId
     * @return
     */
    List<Object[]> getWorkCompletionRegisterByWorkId(Long workId, String raCode);

    /**
     * get Work Completion Register By WorkId
     * @param workId
     * @return
     */
    List<Object[]> getRegisterDetailsByWorkId(Long workId, String raCode);

    /**
     * this is used to find all Work Against which work-order Generated
     * @param orgId
     * @return List<WorkDefinitionDto>
     */
    List<WorkDefinationEntity> findAllWorkOrderGeneratedWorks(Long orgId);

    /**
     * used to update overhead Amount
     * @param workId
     * @param overheadAmount
     */
    void updateOverHeadAmount(Long workId, BigDecimal overheadAmount);

    /**
     * Save Sanction Details Data
     * @param definitionSancDet
     */
    void saveSanctionDetails(WorkDefinitionSancDet definitionSancDet);

	List<Object[]> findAllCompletedWorkBasedOnEnv(Long projId, String flag, Long orgId);

}
