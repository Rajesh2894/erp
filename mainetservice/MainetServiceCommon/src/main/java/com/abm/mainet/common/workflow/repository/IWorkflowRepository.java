package com.abm.mainet.common.workflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.workflow.domain.ServicesEventEntity;
import com.abm.mainet.common.workflow.domain.WorkflowDefEntity;

/**
 *
 * @author Vivek.Kumar
 * @since 23-November-2015
 */
@Repository
public interface IWorkflowRepository extends CrudRepository<WorkflowDefEntity, Long> {
    @Query("SELECT org.orgid, org.ONlsOrgname "
            + "FROM Organisation org WHERE org.orgStatus=:orgStatus AND org.ONlsOrgname IS NOT NULL")
    public List<Object[]> findAllOrganization(@Param("orgStatus") String orgStatus);

    @Query("SELECT dept.dpDeptid, dept.dpDeptdesc FROM DeptOrgMap map, Department dept"
            + " WHERE map.orgid=:orgId AND map.mapStatus=:status AND map.department.dpDeptid=dept.dpDeptid AND dept.dpDeptdesc IS NOT NULL")
    public List<Object[]> findAllDepartmentByOrganization(@Param("orgId") Long orgId,
            @Param("status") String status);

    @Query("SELECT sm.smServiceId, sm.smServiceName FROM ServiceMaster sm "
            + "WHERE sm.orgid=:orgId AND sm.tbDepartment.dpDeptid=:depId AND sm.smServActive=:activeService AND sm.smServiceName IS NOT NULL")
    public List<Object[]> findAllServicesByDepartment(@Param("orgId") Long orgId,
            @Param("depId") Long depId, @Param("activeService") Long activeLookUpId);

    @Query("SELECT gm.gmId, gm.grCode  FROM GroupMaster gm WHERE  gm.grStatus=:grStatus")
    public List<Object[]> findByGroupStatus(@Param("grStatus") String grStatus);

    @Query("SELECT wd FROM WorkflowDefEntity wd WHERE wd.orgId.orgid=:orgId AND wd.wdDeptId.dpDeptid =:deptId "
            + "AND  wd.wdServiceId =:serviceId  AND wd.dwzId1 =:wardZone1 "
            + "AND wd.dwzId2 =:wardZone2 AND wd.dwzId3 =:wardZone3 "
            + "AND wd.dwzId4 =:wardZone4 AND wd.dwzId5 =:wardZone5 "
            + "AND wd.wdMode =:payMode AND wd.complaintType =:complaintType "
            + "AND wd.complaintSubType =:complaintSubType AND wd.isDeleted =:isdeleted")
    public WorkflowDefEntity findWorkDefEntry(@Param("orgId") Long orgId,
            @Param("deptId") Long deptId,
            @Param("serviceId") Long serviceId,
            @Param("wardZone1") Long wardZone1,
            @Param("wardZone2") Long wardZone2,
            @Param("wardZone3") Long wardZone3,
            @Param("wardZone4") Long wardZone4,
            @Param("wardZone5") Long wardZone5,
            @Param("payMode") String payMode,
            @Param("complaintType") Long complaintType,
            @Param("complaintSubType") Long complaintSubType,
            @Param("isdeleted") String isdeleted);

    @Query("SELECT wd FROM WorkflowDefEntity wd  WHERE wd.orgId.orgid=:orgId and  wd.wdServiceId =:serviceId  and wd.wdMode =:payMode and wd.isDeleted = 'N'")
    public List<WorkflowDefEntity> findWorkDefEntry(@Param("orgId") Long orgId, @Param("serviceId") Long serviceId,
            @Param("payMode") String payMode);

    @Query("SELECT se FROM ServicesEventEntity se where se.serviceEventId =:id")
    public ServicesEventEntity findAllEventsById(@Param("id") long id);

    // User Story #105470 I have added this becuase of there is no domain class for tb_workflow_action table
    @Query(value = "select COMMENTS from tb_workflow_action where APM_APPLICATION_ID=:applicationId and EMPID=:empId and ORGID=:orgId  ", nativeQuery = true)
    List<String> findWorkflowActionCommentsByApplicationIdAndEmpId(@Param("applicationId") Long applicationId,
            @Param("empId") Long empId, @Param("orgId") Long orgId);
    
    @Query(value = "select wa.COMMENTS,wa.DECISION  from tb_workflow_action wa where APM_APPLICATION_ID=:applicationId  and ORGID=:orgId  ", nativeQuery = true)
    public List<Object[]> findWorkflowActionCommentAndDecisionByAppId(@Param("applicationId") Long applicationId,
           @Param("orgId") Long orgId);
    
    @Query(value = "SELECT wa.LAST_DECISION, wa.STATUS FROM tb_workflow_request wa WHERE wa.APM_APPLICATION_ID = :applicationId AND wa.ORGID = :orgId", nativeQuery = true)
    List<Object[]> findWorkflowRequestCommentAndDecisionByAppId(@Param("applicationId") Long applicationId,
             @Param("orgId") Long orgId);
    
    @Query(value = "SELECT wa.LAST_DECISION, wa.STATUS FROM tb_workflow_request wa WHERE wa.APM_APPLICATION_ID = :applicationId AND REFERENCE_ID is  null AND wa.ORGID = :orgId", nativeQuery = true)
    List<Object[]> findWorkflowActionRequest(@Param("applicationId") Long applicationId,
             @Param("orgId") Long orgId);

}
