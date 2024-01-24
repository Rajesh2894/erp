package com.abm.mainet.vehiclemanagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.vehiclemanagement.dto.VehicleFuelReconciationDTO;

@Component
public class RefuelingAdviceReconcilValidator extends BaseEntityValidator<VehicleFuelReconciationDTO> {

    @Override
    protected void performValidations(VehicleFuelReconciationDTO entity,
            EntityValidationContext<VehicleFuelReconciationDTO> entityValidationContext) {

    }

}
