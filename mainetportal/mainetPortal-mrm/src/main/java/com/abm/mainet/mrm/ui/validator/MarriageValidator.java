package com.abm.mainet.mrm.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.mrm.ui.model.MarriageRegistrationModel;

@Component
public class MarriageValidator extends BaseEntityValidator<MarriageRegistrationModel> {

    @Override
    protected void performValidations(final MarriageRegistrationModel model,
            final EntityValidationContext<MarriageRegistrationModel> entityValidationContext) {

        performGroupValidation("marriageDTO.ward");

    }

}
