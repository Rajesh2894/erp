package com.abm.mainet.swm.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.swm.dto.VehicleFuelReconciationDTO;

@Component
public class RefuelingAdviceReconcilationValidator extends BaseEntityValidator<VehicleFuelReconciationDTO> {

    @Override
    protected void performValidations(VehicleFuelReconciationDTO entity,
            EntityValidationContext<VehicleFuelReconciationDTO> entityValidationContext) {

    }

}
