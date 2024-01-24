package com.abm.mainet.authentication.admin.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.dms.domain.CFCAttachment;

/**
 * @author prasant.sahu
 *
 */
@Component
public class DeptAuthFormValidation extends BaseEntityValidator<Employee> {

    @Override
    protected void performValidations(final Employee entity, final EntityValidationContext<Employee> entityValidationContext) {

        int count = 0;
        if ((entity.getAuthStatus() == MainetConstants.WHITE_SPACE) || (entity.getAuthStatus() == null)
                || entity.getAuthStatus().equals("D")) {
            entityValidationContext.addOptionConstraint("eip.dept.authFlagStatus");
        } else if (entity.getAuthStatus().equals(MainetConstants.STATUS.ACTIVE) || entity.getAuthStatus().equals("deptA")) {
            if (entity.getGmid() == 0) {
                entityValidationContext.addOptionConstraint("Please Assign Group..");
            }
            if (entity.getAuthStatus().equals("deptA")) {
                entity.setAuthStatus("A");
            } else {
                for (final CFCAttachment cf : entity.getCfcAttachments()) {
                    if (cf.getAttPath() != null) {
                        count++;
                    }
                }
                if (count == 0) {
                    entityValidationContext.addOptionConstraint("dept.attachment.approve.validation");
                }

            }
        }

        else if (entity.getAuthStatus().equals("R") || entity.getAuthStatus().equals("deptR")) {
            if (entity.getAuthStatus().equals("deptR")) {
                entity.setAuthStatus("R");
            } else {
                for (final CFCAttachment cf : entity.getCfcAttachments()) {
                    if (cf.getAttPath() != null) {
                        count++;
                    }
                }
                if (count == 0) {
                    entityValidationContext.addOptionConstraint("dept.attachment.Reject.validation");
                }
            }
        }
        if ((entity.getAuthStatus() != null) && entity.getAuthStatus().equals("A")) {
            if (entity.getGmid() == 0) {
                entityValidationContext.addOptionConstraint("Please Assign Group..");
            }
        }

        entityValidationContext.rejectIfNotSelected(entity.getAuthStatus(), "Radio Button");

    }

}
