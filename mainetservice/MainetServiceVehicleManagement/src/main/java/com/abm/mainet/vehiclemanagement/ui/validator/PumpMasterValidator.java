package com.abm.mainet.vehiclemanagement.ui.validator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.vehiclemanagement.dto.PumpMasterDTO;

@Component
public class PumpMasterValidator extends BaseEntityValidator<PumpMasterDTO> {

    @Override
    protected void performValidations(PumpMasterDTO entity,
            EntityValidationContext<PumpMasterDTO> entityValidationContext) {

        entityValidationContext.rejectIfNotSelected(entity.getPuPutype(), "puPutype");
        entityValidationContext.rejectIfEmpty(entity.getPuPumpname(), "puPumpname");
        entityValidationContext.rejectIfEmpty(entity.getPuAddress(), "puAddress");
        entityValidationContext.rejectIfEmpty(entity.getVendorId(), "vendorId");
        List<Long> pumpfuels = new ArrayList<>();
        for (int i = 0; i < entity.getTbSwPumpFuldets().size(); i++) {
            Long pumpFuelId = entity.getTbSwPumpFuldets().get(i).getPuFuid();
            if (pumpfuels.contains(pumpFuelId)) {
                /*
                 * entityValidationContext.rejectIfAlreadySelected(entity.getTbSwPumpFuldets().get(i).getPuFuid().toString(),
                 * "tbSwPumpFuldets.puFuid");
                 */
            } else {
                pumpfuels.add(pumpFuelId);
            }

            entityValidationContext.rejectIfNull(entity.getTbSwPumpFuldets().get(i).getPuFuid(), "tbSwPumpFuldets.puFuid");
            entityValidationContext.rejectIfNull(entity.getTbSwPumpFuldets().get(i).getPuFuunit(), "tbSwPumpFuldets.puFuunit");
            // entityValidationContext.rejectIfNotSelected(entity.getTbSwPumpFuldets().get(i).get, field);
        }
    }

}
