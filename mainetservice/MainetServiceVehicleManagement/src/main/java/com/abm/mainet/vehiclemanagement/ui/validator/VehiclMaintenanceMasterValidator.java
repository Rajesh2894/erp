package com.abm.mainet.vehiclemanagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.vehiclemanagement.dto.VehicleMaintenanceMasterDTO;

@Component
public class VehiclMaintenanceMasterValidator extends BaseEntityValidator<VehicleMaintenanceMasterDTO> {

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
