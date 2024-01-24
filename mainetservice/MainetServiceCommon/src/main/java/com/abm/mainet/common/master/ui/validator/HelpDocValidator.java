package com.abm.mainet.common.master.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.CommonHelpDocs;
import com.abm.mainet.common.integration.dms.fileUpload.FileUploadUtility;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.common.utility.ApplicationSession;


@Component
public class HelpDocValidator extends BaseEntityValidator<CommonHelpDocs> {
    @Override
    protected void performValidations(final CommonHelpDocs entity,
            final EntityValidationContext<CommonHelpDocs> entityValidationContext) {

        if (entity.getModuleName().equals(MainetConstants.IsDeleted.ZERO)) {
            entityValidationContext.addOptionConstraint(ApplicationSession.getInstance().getMessage("helpDoc.modeleName"));
        }
        if ((FileUploadUtility.getCurrent().getFileMap() != null) && !FileUploadUtility.getCurrent().getFileMap().isEmpty()) {
            if (FileUploadUtility.getCurrent().getFileMap().get(0L) == null) {
                entityValidationContext.addOptionConstraint(getApplicationSession().getMessage("helpDoc.noFile"));
            }
        } else {
            entityValidationContext.addOptionConstraint(getApplicationSession().getMessage("helpDoc.noFile"));
        }

    }

}
