
package com.abm.mainet.common.workflow.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abm.mainet.common.workflow.domain.WorkflowEventEntity;

/**
 * @author umashanker.kanaujiya
 *
 */
@Repository
public interface WorkFlowEventEntityRepository extends CrudRepository<WorkflowEventEntity, Long> {

    @Query("SELECT wd FROM WorkflowEventEntity wd WHERE wd.workflowDefEntity.wdId=:workflowId and wd.weOrgid =:orgId and wd.weStepNo =:stepNo")
    public WorkflowEventEntity findWorkflowEventEntity(@Param("workflowId") Long workflowId, @Param("orgId") Long orgId,
            @Param("stepNo") Long stepNo);

}
