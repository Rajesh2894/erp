package com.abm.mainet.workManagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

/**
 * @author vishwajeet.kumar
 * @since 8 March 2018
 */
@Component
public class WorkEstimateApprovalValidator extends BaseEntityValidator<WorkflowTaskAction> {

    final ApplicationSession session = ApplicationSession.getInstance();

    @Override
    protected void performValidations(WorkflowTaskAction workFlowTaskDto,
            EntityValidationContext<WorkflowTaskAction> entityValidationContext) {

        if (workFlowTaskDto.getDecision() == null || workFlowTaskDto.getDecision().equals("")) {
            entityValidationContext.addOptionConstraint(session.getMessage("workflow.checkAct.decision"));
        }

        if (workFlowTaskDto.getDecision() != null) {
            if (workFlowTaskDto.getDecision().equals(MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE)) {
                if (workFlowTaskDto.getForwardToEmployee() == null
                        || workFlowTaskDto.getForwardToEmployee().equals("")) {
                    entityValidationContext.addOptionConstraint(session.getMessage("workflow.checkAct.forward.emp"));
                }
            }
        } else if (workFlowTaskDto.getDecision() != null
                && workFlowTaskDto.getDecision().equals(MainetConstants.WorkFlow.Decision.SEND_BACK)) {
            if (workFlowTaskDto.getEventId() == null || workFlowTaskDto.getEventId() == 0) {
                entityValidationContext.addOptionConstraint(session.getMessage("workflow.checkAct.sendBack.event"));
            }
            if (workFlowTaskDto.getForwardToEmployee() == null || workFlowTaskDto.getForwardToEmployee().equals("")) {
                entityValidationContext.addOptionConstraint(session.getMessage("workflow.checkAct.sendBack.emp"));
            }

        }

        if (workFlowTaskDto.getComments() == null || workFlowTaskDto.getComments().equals("")) {
            entityValidationContext.addOptionConstraint(session.getMessage("workflow.checkAct.commnet"));
        }

    }

}
