package com.abm.mainet.vehiclemanagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.vehiclemanagement.dto.VehicleFuellingDTO;

@Component
public class VehicleFuelValidator extends BaseEntityValidator<VehicleFuellingDTO> {

    @Override
    protected void performValidations(VehicleFuellingDTO entity,
            EntityValidationContext<VehicleFuellingDTO> entityValidationContext) {
        // TODO Auto-generated method stub

    }

}
