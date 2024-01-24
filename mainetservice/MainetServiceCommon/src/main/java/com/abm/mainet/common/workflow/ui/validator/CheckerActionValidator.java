package com.abm.mainet.common.workflow.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.workflow.dto.WorkflowTaskAction;

@Component
public class CheckerActionValidator extends BaseEntityValidator<WorkflowTaskAction> {

    final ApplicationSession session = ApplicationSession.getInstance();

    @Override
    protected void performValidations(WorkflowTaskAction workActionDto,
            EntityValidationContext<WorkflowTaskAction> entityValidationContext) {

        if ((workActionDto.getComments() == null)
                || workActionDto.getComments().equals("")) {
            entityValidationContext.addOptionConstraint(session.getMessage("workflow.checkAct.commnet"));
        }

        if ((workActionDto.getDecision() == null)
                || workActionDto.getDecision().equals("")) {
            entityValidationContext.addOptionConstraint(session.getMessage("workflow.checkAct.decision"));
        }

        if ((workActionDto.getDecision() != null)) {
            if (workActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.FORWARD_TO_EMPLOYEE)) {
                if (workActionDto.getForwardToEmployee() == null || workActionDto.getForwardToEmployee().equals("")
                        || workActionDto.getForwardToEmployee().equals("0")) {
                    entityValidationContext.addOptionConstraint(session.getMessage("workflow.checkAct.forward.emp"));
                }
            } else if (workActionDto.getDecision().equals(MainetConstants.WorkFlow.Decision.SEND_BACK)) {
                if (workActionDto.getSendBackToGroup() == null
                        || workActionDto.getSendBackToLevel() == null) {
                    entityValidationContext.addOptionConstraint(session.getMessage("workflow.checkAct.sendBack.event"));
                }
                /*
                 * if (workActionDto.getSendBackToEmployee() == null || workActionDto.getSendBackToEmployee().equals("")) {
                 * entityValidationContext.addOptionConstraint(session.getMessage("workflow.checkAct.sendBack.emp")); }
                 */
            }
        }

    }

}
