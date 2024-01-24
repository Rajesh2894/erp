package com.abm.mainet.swm.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.swm.dto.VehicleMaintenanceMasterDTO;

@Component
public class VehicleMaintenanceMasterValidator extends BaseEntityValidator<VehicleMaintenanceMasterDTO> {

    @Override
    protected void performValidations(VehicleMaintenanceMasterDTO entity,
            EntityValidationContext<VehicleMaintenanceMasterDTO> entityValidationContext) {

        entityValidationContext.rejectIfNotSelected(entity.getVeVetype(), "veVetype");
        entityValidationContext.rejectIfEmpty(entity.getVeMainday(), "veMainday");
        entityValidationContext.rejectIfNotSelected(entity.getVeMainUnit(), "veMainUnit");
        entityValidationContext.rejectIfEmpty(entity.getVeDowntime(), "veDowntime");
        entityValidationContext.rejectIfNotSelected(entity.getVeDowntimeUnit(), "veDowntimeUnit");

    }

}
