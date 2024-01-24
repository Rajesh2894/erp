package com.abm.mainet.bpm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.bpm.domain.TaskDetailView;

@Repository
public interface TaskDetailsRepository extends JpaRepository<TaskDetailView, Long>, JpaSpecificationExecutor<TaskDetailView> {

    @Query("select td from TaskDetailView td where td.workflowReqId=:workflowReqId and (td.applicationId=:applicationId or td.referenceId=:referenceId)")
    List<TaskDetailView> findByApplicationIdWithDetails(@Param("applicationId") Long applicationId,
            @Param("referenceId") String referenceId, @Param("workflowReqId") Long workflowReqId);

    @Query("select td from TaskDetailView td where td.applicationId=:applicationId or td.referenceId=:referenceId")
    List<TaskDetailView> findByApplicationIdWithDetails(@Param("applicationId") Long applicationId,
            @Param("referenceId") String referenceId);

    @Query("select td from TaskDetailView td where td.taskStatus='PENDING' and (td.applicationId=:applicationId or td.referenceId=:referenceId) order by td.currentEscalationLevel")
    List<TaskDetailView> findPendingActionByUuid(@Param("applicationId") Long applicationId,
            @Param("referenceId") String referenceId);

}
