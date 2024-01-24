package com.abm.mainet.common.master.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CommonHelpDocs;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;

/**
 * @author vinay.jangir
 *
 */
@Component
public class CommonHelpDocsValidator extends BaseEntityValidator<CommonHelpDocs> {

    @Override
    protected void performValidations(final CommonHelpDocs entity,
            final EntityValidationContext<CommonHelpDocs> entityValidationContext) {
        if (entity.getModuleName().equals(MainetConstants.IsDeleted.ZERO)) {
            entityValidationContext.addOptionConstraint(ApplicationSession.getInstance().getMessage("helpDoc.modeleName"));
        }
        /*if (entity.getDeptCode().equals(MainetConstants.IsDeleted.ZERO)) {
            entityValidationContext.addOptionConstraint(ApplicationSession.getInstance().getMessage("helpDoc.deptName"));
        }*/
    }

}
