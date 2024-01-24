package com.abm.mainet.bpm.utility;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.abm.mainet.bpm.common.dto.TaskSearchRequest;
import com.abm.mainet.bpm.domain.TaskDetailView;
import com.abm.mainet.constant.MainetConstants;
import com.abm.mainet.constant.MainetConstants.WorkFlow.Status;

/**
 * Custom JPA Specifications for all entities.
 * 
 * @author sanket.joshi
 *
 */
public class CustomJpaSpecifications {

    /**
     * WorkflowUserTask JPA Specifications
     * 
     * @author sanket.joshi
     *
     */
    public static class TaskSpecification {

        /**
         * This method will return JPA specification to fetch task by using search request data.
         * 
         * @param taskSearchRequest filter for where clause.
         * @return
         */
        public static Specification<TaskDetailView> likeTaskSearchRequest(TaskSearchRequest taskSearchRequest) {
            return new Specification<TaskDetailView>() {
                public Predicate toPredicate(Root<TaskDetailView> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

                    // Apply search criteria
                    List<Predicate> whereClause = new ArrayList<>();
                    if (taskSearchRequest.getOrgId() != null)
                        whereClause.add(builder.equal(root.get(MainetConstants.EntityFields.ORG_ID),
                                taskSearchRequest.getOrgId()));

                    if (taskSearchRequest.getEventId() != null)
                        whereClause.add(builder.equal(root.get(MainetConstants.EntityFields.SERVICE_EVENT_ID),
                                taskSearchRequest.getEventId()));

                    if (taskSearchRequest.getEventName() != null)
                        whereClause.add(builder.equal(root.get(MainetConstants.EntityFields.SERVICE_EVENT_NAME),
                                taskSearchRequest.getEventName()));

                    if (taskSearchRequest.getStatus() != null
                            && CommonUtils.isValidEnum(Status.class, taskSearchRequest.getStatus()))
                        whereClause.add(builder.equal(root.get(MainetConstants.EntityFields.TASK_STATUS),
                                taskSearchRequest.getStatus()));

                    if (taskSearchRequest.getDecision()!= null)
                        whereClause.add(builder.equal(root.get(MainetConstants.EntityFields.DECISION),
                                taskSearchRequest.getDecision()));

                    if (taskSearchRequest.getEmpId() != null)
                        whereClause.add(builder.equal(root.get(MainetConstants.EntityFields.EMPID),
                                taskSearchRequest.getEmpId()));

                    // query.orderBy(builder.desc(root.get(MainetConstants.EntityFields.DATE_OF_ASSIGNMENT)));
                    return builder.and(whereClause.toArray(new Predicate[0]));

                }
            };
        }

    }

}
