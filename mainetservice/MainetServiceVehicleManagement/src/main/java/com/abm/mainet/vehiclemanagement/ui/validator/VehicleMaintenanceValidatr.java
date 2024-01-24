package com.abm.mainet.vehiclemanagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.vehiclemanagement.dto.VehicleMaintenanceDTO;

@Component
public class VehicleMaintenanceValidatr extends BaseEntityValidator<VehicleMaintenanceDTO> {

    @Override
    public void performValidations(VehicleMaintenanceDTO entity,
            EntityValidationContext<VehicleMaintenanceDTO> entityValidationContext) {

        entityValidationContext.rejectIfNotSelected(entity.getVeVetype(), "veVetype");
        entityValidationContext.rejectIfNotSelected(entity.getVemMetype(), "vemMetype");
        entityValidationContext.rejectIfEmpty(entity.getVemDate(), "vemDate");
        entityValidationContext.rejectIfEmpty(entity.getVemDowntime(), "vemEstDowntime");
        entityValidationContext.rejectIfEmpty(entity.getVemReceiptdate(), "vemReceiptdate");
        entityValidationContext.rejectIfEmpty(entity.getVemCostincurred(), "vemCostincurred");
        entityValidationContext.rejectIfEmpty(entity.getVeId(), "veId");
        entityValidationContext.rejectIfEmpty(entity.getVemReading(), "vemReading");
        entityValidationContext.rejectIfEmpty(entity.getVemReceiptno(), "vemReceiptno");

    }

}
