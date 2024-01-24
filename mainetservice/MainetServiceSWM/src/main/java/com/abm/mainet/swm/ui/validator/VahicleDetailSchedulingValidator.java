package com.abm.mainet.swm.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.swm.dto.VehicleScheduleDetDTO;

@Component
public class VahicleDetailSchedulingValidator extends BaseEntityValidator<VehicleScheduleDetDTO> {
    @Override
    protected void performValidations(VehicleScheduleDetDTO entity,
            EntityValidationContext<VehicleScheduleDetDTO> entityValidationContext) {
        if (entity.getBeatId() != null) {
            entityValidationContext.rejectIfNotSelected(entity.getBeatId(), "beatId");
        }
        if (entity.getVesCollType() != null) {
            entityValidationContext.rejectIfNotSelected(entity.getVesCollType(), "vesCollType");
        }
        if (entity.getStartime() != null) {
            entityValidationContext.rejectIfEmpty(entity.getStartime(), "startime");
        }
        if (entity.getEndtime() != null) {
            entityValidationContext.rejectIfEmpty(entity.getEndtime(), "endtime");
        }
    }

}
