package com.abm.mainet.water.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.water.dto.ChangeOfUsageRequestDTO;

@Component
public class ChangeOfUsageValidator extends BaseEntityValidator<ChangeOfUsageRequestDTO> {

    @Override
    protected void performValidations(final ChangeOfUsageRequestDTO model,
            final EntityValidationContext<ChangeOfUsageRequestDTO> entityValidationContext) {
        entityValidationContext.rejectIfEmpty(model.getChangeOfUsage().getRemark(), "changeOfUsage.remark");
        performGroupValidation("changeOfUsage.newTrmGroup");
    }

}