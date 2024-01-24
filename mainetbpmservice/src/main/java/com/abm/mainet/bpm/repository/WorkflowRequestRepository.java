package com.abm.mainet.bpm.repository;

import java.util.List;

import javax.ws.rs.PathParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bpm.domain.WorkflowRequest;
import com.abm.mainet.bpm.domain.WorkflowUserTask;

@Repository
public interface WorkflowRequestRepository extends JpaRepository<WorkflowRequest, Long> {

    /**
     * used to find WorkflowRequest By ApplicationId
     * @param applicationId
     * @return
     */
    @Query("select c from WorkflowRequest c where c.applicationId =:applicationId")
    WorkflowRequest findByApplicationId(@Param("applicationId") Long applicationId);

    /**
     * used to find WorkflowRequest By Application Id Or ReferenceId
     * @param applicationId
     * @param referenceId
     * @return
     */
    @Query("select c from WorkflowRequest c where c.applicationId =:applicationId or c.referenceId =:referenceId")
    WorkflowRequest findByApplicationIdOrReferenceId(@Param("applicationId") Long applicationId,
            @Param("referenceId") String referenceId);

    @Query("select c from WorkflowRequest c where c.workflowTypeId =:workflowTypeId and (c.applicationId =:applicationId or c.referenceId =:referenceId)")
    WorkflowRequest findByApplicationIdOrReferenceIdAndWorkflowId(@Param("applicationId") Long applicationId,
            @Param("referenceId") String referenceId,  @Param("workflowTypeId") Long workflowTypeId);
    
   
    /**
     * used to find WorkflowRequest By Application Id Or Reference Id And OrgId
     * @param applicationId
     * @param referenceId
     * @param orgId
     * @return
     */
    @Query("select c from WorkflowRequest c where c.orgId=:orgId and (c.applicationId =:applicationId or c.referenceId =:referenceId)")
    WorkflowRequest findByApplicationIdOrReferenceIdAndOrgId(@Param("applicationId") Long applicationId,
            @Param("referenceId") String referenceId, @Param("orgId") Long orgId);

    @Query("select c from WorkflowRequest c where c.orgId=:orgId and c.applicationId =:applicationId and c.referenceId =:referenceId")
    WorkflowRequest findByApplicationIdOrReferenceIdAndOrgIdRTI(@Param("applicationId") Long applicationId,
            @Param("referenceId") String referenceId, @Param("orgId") Long orgId);
    
    
    /**
     * used to find WorkflowRequest By Application Id Or Reference Id And OrgId
     * @param applicationId
     * @param referenceId
     * @param orgId
     * @param workFlowId
     * @return
     */
    @Query("select c from WorkflowRequest c where c.workflowTypeId =:workflowTypeId and c.orgId=:orgId and (c.applicationId =:applicationId or c.referenceId =:referenceId)")
    WorkflowRequest findByApplicationIdOrReferenceIdAndOrgIdAndWorkflowTypeId(
            @Param("applicationId") Long applicationId, @Param("referenceId") String referenceId,
            @Param("orgId") Long orgId, @Param("workflowTypeId") Long workflowTypeId);

    @Query("select c from WorkflowRequest c where c.workflowTypeId in (:workFlowIds) and c.status='PENDING'")
    List<WorkflowRequest> getWorkflowRequestListByWorkFlowIds(@Param("workFlowIds") List<Long> workFlowIds);

    @Query("select c from WorkflowRequest c where c.applicationId in (:appId)")
    List<WorkflowRequest> getWorkflowRequestListByApplicationId(@Param("appId") List<Long> appId);

    /**
     * used to find WorkflowRequest By Application Id and workflowTypeId
     * @param applicationId
     * @param workFlowId
     * @return
     */
    @Query("select c from WorkflowRequest c where c.applicationId =:applicationId and  c.workflowTypeId =:workflowTypeId")
    WorkflowRequest findByApplicationIdAndWorkFlowId(@Param("applicationId") Long applicationId,
            @Param("workflowTypeId") Long workFlowId);

    // getting deployment Id from workflow request table based on reference Id/ Application Id And OrgId
    @Query("select c.deploymentId from WorkflowRequest c where (c.applicationId =:applicationId or c.referenceId =:referenceId)")
    Long findDeploymentIdByApplicationIdOrReferenceIdAndOrgId(
            @Param("applicationId") Long applicationId, @Param("referenceId") String referenceId);
    
    
    
    @Query("select c from WorkflowRequest c where c.workflowTypeId in (:workFlowIds) and c.status='PENDING'")
    List<WorkflowUserTask> getWorkflowTaskListByWorkFlowIds(@Param("workFlowIds") List<Long> workFlowIds);
    
    
    
    @Query(value = "select status from audittaskimpl where taskId=:taskId",  nativeQuery = true)
    String getWorkTaskListBytaskIds(@Param("taskId") Long taskId);
    
    @Query("select c from WorkflowRequest c where c.workflowTypeId =:workflowTypeId and c.orgId=:orgId and (c.applicationId =:applicationId or c.referenceId =:referenceId) order by c.id desc")
    List<WorkflowRequest> findByApplicationIdOrReferenceIdAndOrgIdAndWorkflowTypeIdDesc(
            @Param("applicationId") Long applicationId, @Param("referenceId") String referenceId,
            @Param("orgId") Long orgId, @Param("workflowTypeId") Long workflowTypeId);
    
    //getting deptId and ServiceId from tb_cfc_application_mst table for start task
   /* @Query( value = " select a.sm_service_id,c.dp_deptid from tb_cfc_application_mst a,\r\n" + 
    		"tb_services_mst b,\r\n" + 
    		"tb_department c\r\n" + 
    		"where a.sm_service_id=b.sm_service_id and\r\n" + 
    		"c.dp_deptid=b.cdm_dept_id and (a.APM_APPLICATION_ID =:applicationId or a.REF_NO=:referenceId) " ,  nativeQuery = true)
    List<Object[]> getDeptIdAndServiceIdByAppOrRefNo (@Param ("applicationId") Long applicationId, @Param("referenceId") String referenceId);*/
    
}
