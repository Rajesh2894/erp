package com.abm.mainet.swm.ui.validator;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.swm.dto.PumpFuelDetailsDTO;

public class PumpFuelDetailsValidator extends BaseEntityValidator<PumpFuelDetailsDTO> {

    @Override
    protected void performValidations(PumpFuelDetailsDTO entity,
            EntityValidationContext<PumpFuelDetailsDTO> entityValidationContext) {

        entityValidationContext.rejectIfNotSelected(entity.getPuFuid(), "puFuid");
        entityValidationContext.rejectIfNotSelected(entity.getPuFuunit(), "puFuunit");

    }

}
