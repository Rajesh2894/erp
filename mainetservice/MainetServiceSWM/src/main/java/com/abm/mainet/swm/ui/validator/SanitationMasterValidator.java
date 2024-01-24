package com.abm.mainet.swm.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.swm.dto.SanitationMasterDTO;

@Component
public class SanitationMasterValidator extends BaseEntityValidator<SanitationMasterDTO> {

    @Override
    protected void performValidations(SanitationMasterDTO entity,
            EntityValidationContext<SanitationMasterDTO> entityValidationContext) {

        performGroupValidation("codWard");
        entityValidationContext.rejectIfNotSelected(entity.getSanType(), "sanType");
        entityValidationContext.rejectIfEmpty(entity.getSanSeatCnt(), "sanSeatCnt");
        entityValidationContext.rejectIfNotSelected(entity.getSanLocId(), "sanLocId");
    }

}
