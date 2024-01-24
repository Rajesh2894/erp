
package com.abm.mainet.common.workflow.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.domain.Department;
import com.abm.mainet.common.workflow.domain.WorkflowMas;


/**
 * @author hiren.poriya
 *
 */

@Repository
public interface WorkFlowTypeRepository extends JpaRepository<WorkflowMas, Long> {

    @Modifying
    @Query("update WorkflowDet e set e.status = ?1,e.updatedBy = ?2,e.updatedDate = CURRENT_DATE where e.wfdId in (?3)")
    @Transactional
    void deleteRecordDetails(String flag, Long empId, List<Long> id);

    @Modifying
    @Query("update WorkflowMas e set e.status = ?1,e.updatedBy = ?2,e.updatedDate = CURRENT_DATE where e.wfId in ?3")
    @Transactional
    void deleteWorkFLow(String flag, Long empId, Long id);

    @Query("select w.id,o.ONlsOrgname,o.oNlsOrgnameMar,d.dpDeptdesc,d.dpNameMar,d.dpDeptid,o.orgid,w.complaint.compId,w.complaint.complaintDesc from WorkflowMas w join w.organisation o join w.department d  where w.status = ?1 ")
    List<Object[]> findAllComplaintRecords(String activeFlag);

    @Query("select w.id,o.ONlsOrgname,o.oNlsOrgnameMar,d.dpDeptdesc,d.dpNameMar,d.dpDeptid,o.orgid,w.service.smServiceId,w.service.smServiceName from WorkflowMas w join w.organisation o join w.department d  where w.status = ?1 ")
    List<Object[]> findAllServiceRecords(String activeFlag);

    @Query("select w from WorkflowMas w where w.organisation.orgid = ?1 and"
            + "  w.department.dpDeptid = ?2 and  w.service.smServiceId = ?3 and  w.status = ?4 ")
    List<WorkflowMas> findWorkFlowByServiceAndDept(Long orgId, Long deptId, Long serviceId, String status);

    @Query("select w from WorkflowMas w where w.organisation.orgid = ?1 and  w.department.dpDeptid = ?2 and w.status = ?3 ")
    List<WorkflowMas> findWorkFlowByDepartment(Long orgId, Long deptId, String status);

    @Query("select w from WorkflowMas w"
            + " where w.organisation.orgid = ?1 and  w.department.dpDeptid = ?2 and  w.service.smServiceId = ?3 and  w.status = ?4 ")
    List<WorkflowMas> findRecords(Long orgId, Long deptId, Long serviceId, String status);

    @Query("SELECT wt FROM WorkflowMas wt where wt.organisation.orgid =:orgId and wt.department.dpDeptid =:deptId and wt.complaint.compId =:compId")
    List<WorkflowMas> getWorkFlowTypeByOrgDepartmentAndComplaintType(@Param("orgId") long orgId,
            @Param("deptId") long deptId, @Param("compId") long compId);

    @Query("SELECT wt FROM WorkflowMas wt where wt.organisation.orgid =:orgId and wt.complaint.compId =:compId and wt.type = 'N' AND wt.status = 'Y'")
    public WorkflowMas getWorkFlowTypeByOrgDepartmentAndComplaintTypeForWardZone(@Param("orgId") long orgId,
            @Param("compId") long compId);

    @Query("SELECT wt FROM WorkflowMas wt where wt.organisation.orgid =:orgId and wt.department.dpDeptid =:deptId and wt.complaint.compId =:compId and wt.type = 'N'")
    public WorkflowMas getWorkFlowTypeByOrgDepartmentAndComplaintTypeForWardZone(@Param("orgId") long orgId,
            @Param("deptId") long deptId, @Param("compId") long compId);

    @Query("SELECT wft FROM WorkflowMas wft WHERE wft.service.smServiceId =:smServiceId AND wft.department.dpDeptid =:dpDeptid and wft.organisation.orgid =:orgid")
    public List<WorkflowMas> getWorkFlowByServiceIdDeptIdAndOrgId(@Param("smServiceId") long serviceId,
            @Param("dpDeptid") long deptId, @Param("orgid") long orgId);

    @Query("SELECT wt FROM WorkflowMas wt " + "where wt.organisation.orgid =:orgId " + "AND wt.complaint != NULL "
            + "AND wt.complaint.isActive = 1 " + "AND wt.department.status = 'A' " + "AND wt.status =:status")
    public List<WorkflowMas> getActiveComplaintWorkFlowTypeByOrgId(@Param("orgId") long orgId,
            @Param("status") String status);

    @Query("SELECT wt FROM WorkflowMas wt where wt.organisation.orgid =:orgId AND wt.complaint.compId =:compId AND wt.status = 'Y'")
    public List<WorkflowMas> getComplaintWorkFlowTypeByOrgIdAndComplaintId(@Param("orgId") long orgId,
            @Param("compId") long compId);

    @Query("SELECT COUNT (d.servicesEventEntity.serviceEventId) FROM WorkflowMas m , WorkflowDet d, ServicesEventEntity s WHERE m.status IS 'Y' "
            + " AND d.status IS 'Y' AND d.wfId.wfId= m.wfId AND  d.servicesEventEntity.serviceEventId=s.serviceEventId  AND  "
            + " m.organisation.orgid =:orgId AND m.department.dpDeptid =:deptId AND m.service.smServiceId =:serviceId AND "
            + "  s.systemModuleFunction.smfid in(:eventIds) ")
    int isEventMapped(@Param("eventIds") List<Long> deletedList, @Param("orgId") long orgId,
            @Param("deptId") Long deptId, @Param("serviceId") Long serviceId);

    @Query("SELECT w FROM WorkflowMas w where w.wfId in (:wfIds)")
    public List<WorkflowMas> findAllByIds(@Param("wfIds") Set<Long> wfIds);

    /**
     * Service all type workflow resolver query.
     * 
     * @param orgId
     * @param deptId
     * @param serviceId
     * @return
     */
    @Query("SELECT wt FROM WorkflowMas wt where" + " wt.organisation.orgid =:orgId"
            + " and wt.department.dpDeptid =:deptId" + " and wt.service.smServiceId =:serviceId"
            + " and wt.complaint is NULL" + " and wt.type = 'A'" + " and wt.status = 'Y'")
    public WorkflowMas getWorkFlowTypeByOrgDepartmentAndServiceIdForAllWardZone(@Param("orgId") long orgId,
            @Param("deptId") long deptId, @Param("serviceId") long serviceId);

    /**
     * Complaint all type workflow resolver query.
     * 
     * @param orgId
     * @param compId
     * @return
     */
    @Query("SELECT wt FROM WorkflowMas wt where" + " wt.organisation.orgid =:orgId"
            + " and wt.complaint.compId =:compId" + " and wt.type = 'A'" + " and wt.status = 'Y'")
    public WorkflowMas getWorkFlowTypeByOrgDepartmentAndComplaintTypeForAllWardZone(@Param("orgId") long orgId,
            @Param("compId") long compId);

    /**
     * get find Department Id by TaskName
     * 
     * @param taskName
     * @param orgId
     * @param workFlowId
     * @return deptId
     */
    @Query("Select wfd.eventDepartment.dpDeptid from WorkflowDet wfd,ServicesEventEntity se,SystemModuleFunction smf "
            + "where wfd.servicesEventEntity.serviceEventId = se.serviceEventId and "
            + "se.systemModuleFunction.smfid =smf.smfid and smf.smfname =:taskName "
            + "and wfd.currentOrganisation.orgid =:orgId and wfd.wfId.wfId =:workFlowId ")
    public Long findDepartmentByTaskName(@Param("taskName") String taskName, @Param("orgId") Long orgId,
            @Param("workFlowId") Long workFlowId);

    /**
     * To get All Work flow Defined DepartmentIds By OrgId
     * 
     * @param orgId
     * @return List<Long>
     */
    @Query("SELECT DISTINCT wfd.eventDepartment FROM WorkflowDet wfd, WorkflowMas wt,ServiceMaster service where wt.service.smServiceId = service.smServiceId and wt.wfId = wfd.wfId.wfId and service.smShortdesc=:serviceShortCode and wt.organisation.orgid =:orgId")
    List<Department> getAllWorkflowDefinedDepartmentByOrgId(@Param("orgId") Long orgId,
            @Param("serviceShortCode") String serviceShortCode);

    @Query("select wfm from WorkflowMas wfm where wfm.status = 'Y' and wfm.workflowMode =:workflowMode")
    List<WorkflowMas> resolveAutoEscalationWorkFlow(@Param("workflowMode") Long workflowMode);
    
	@Query("select case when wt.fromAmount is not null and wt.toAmount is not null  THEN true ELSE false END  from WorkflowMas wt where wt.organisation.orgid=:orgId and wt.department.dpDeptid=:dpDeptid and wt.service.smServiceId=:smServiceId and wt.wmSchCodeId2=:getwmschcodeid2 and wt.complaint is NULL and wt.type = 'A' and wt.status = 'Y' AND (wt.organisation.orgid,wt.department.dpDeptid,wt.service.smServiceId,wt.wmSchCodeId2) In (SELECT wz.organisation.orgid,wz.department.dpDeptid,wz.service.smServiceId,wz.wmSchCodeId2 FROM WorkflowMas wz)")
	public Boolean checkAmtBasedWorkflowExits(@Param("orgId") Long orgId,@Param("dpDeptid") Long dpDeptid,@Param("smServiceId") Long smServiceId,@Param("getwmschcodeid2") Long getwmschcodeid2);
	
	/**
     * Complaint all type workflow resolver query.
     * 
     * @param orgId
     * @param vehMainBy
     * @return
     */
    @Query("SELECT wt FROM WorkflowMas wt where" + " wt.organisation.orgid =:orgId"
            + " and wt.extIdentifier =:extIdentifier" + " and wt.type = 'A'" + " and wt.status = 'Y' and wt.service.smServiceId = :serviceId")
    public WorkflowMas getWorkFlowTypeByOrgDepartmentAndExtForAllWardZone(@Param("orgId") long orgId,
            @Param("extIdentifier") long extIdentifier, @Param("serviceId")long serviceId);
    
    @Query(value = "SELECT wt.REFERENCE_ID from tb_workflow_task wt where wt.APM_APPLICATION_ID=:apmApplicationId and wt.ORGID=:orgId", nativeQuery = true)
	List<String> getTaskByAppId(@Param("apmApplicationId") Long apmApplicationId,@Param("orgId") Long orgId);
    
    @Query(value = "SELECT KHRS_DIST, KHRS_DEV_PLAN, KHRS_ZONE, KHRS_SEC FROM TB_BPMS_LIC_MSTR WHERE APPLICATION_NO = :applicationNo AND ORGID = :orgId", nativeQuery = true)
    List<Object[]> findKhrsColumnsByApplicationNoAndOrgId(@Param("applicationNo") Long applicationNo, @Param("orgId") Long orgId);
    
    @Modifying
    @Query(value = "UPDATE TB_BPMS_LIC_MSTR SET DRAFT_FLAG = 'S' WHERE APPLICATION_NO = :appNo AND ORGID = :orgId", nativeQuery = true)
    void updateNLDraftFlagAfterInitiateWorkflow(@Param("appNo") Long appNo, @Param("orgId") Long orgId);
    
}

