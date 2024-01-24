package com.abm.mainet.cms.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.cms.domain.EIPHome;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;

/**
 * @author Pranit.Mhatre
 */
@Component
public class AdminEIPHomeValidator extends BaseEntityValidator<EIPHome> {

    @Override
    protected void performValidations(final EIPHome entity, final EntityValidationContext<EIPHome> context) {
        context.rejectIfEmpty(entity.getDescriptionEn(), "descriptionEn");
        context.rejectIfEmpty(entity.getDescriptionReg(), "descriptionReg");
    }

}
