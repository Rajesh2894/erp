package com.abm.mainet.swm.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.swm.dto.VehicleScheduleDTO;
import com.abm.mainet.swm.dto.VehicleScheduleDetDTO;

@Component
public class VehicleSchedulingValidator extends BaseEntityValidator<VehicleScheduleDTO> {
    @Override
    protected void performValidations(VehicleScheduleDTO entity,
            EntityValidationContext<VehicleScheduleDTO> entityValidationContext) {
        if (entity.getVeVetype() != null) {
            entityValidationContext.rejectIfNotSelected(entity.getVeVetype(), "veVetype");
        }
        if (entity.getVeId() != null) {
            entityValidationContext.rejectIfEmpty(entity.getVeId(), "veId");
        }
        if (entity.getVesFromdt() != null) {
            entityValidationContext.rejectIfEmpty(entity.getVesFromdt(), "vesFromdt");
        }
        if (entity.getVesTodt() != null) {
            entityValidationContext.rejectIfEmpty(entity.getVesTodt(), "vesTodt");
        }
        if (entity.getVesReocc() != null) {
            entityValidationContext.rejectIfEmpty(entity.getVesReocc(), "vesReocc");
        }
        if (entity.getTbSwVehicleScheddets() != null && !entity.getTbSwVehicleScheddets().isEmpty()) {
            for (VehicleScheduleDetDTO detDto : entity.getTbSwVehicleScheddets()) {
                if (detDto.getBeatId() != null) {
                    entityValidationContext.rejectIfNotSelected(detDto.getBeatId(), "beatId");
                }
                if (detDto.getVesCollType() != null) {
                    entityValidationContext.rejectIfNotSelected(detDto.getVesCollType(), "vesCollType");
                }
                if (detDto.getStartime() != null) {
                    entityValidationContext.rejectIfEmpty(detDto.getStartime(), "startime");
                }
                if (detDto.getEndtime() != null) {
                    entityValidationContext.rejectIfEmpty(detDto.getEndtime(), "endtime");
                }
            }
            String previousFrom = null;
            String previousTo = null;
            int mainCount = 0;
            boolean found = false;
            for (VehicleScheduleDetDTO detDtofor : entity.getTbSwVehicleScheddets()) {
                previousFrom = detDtofor.getStartime();
                previousTo = detDtofor.getEndtime();
                int detCount = 0;
                if (!found) {
                    for (VehicleScheduleDetDTO detDto : entity.getTbSwVehicleScheddets()) {
                        if (mainCount != detCount) {
                            if (previousFrom.equals(detDto.getStartime())) {
                                entityValidationContext.addOptionConstraint("From time must not be same.");
                                found = true;
                            } else if (previousTo.equals(detDto.getEndtime())) {
                                entityValidationContext.addOptionConstraint("To time must not be same.");
                                found = true;

                            }
                            if (found) {
                                break;
                            }
                        }
                        detCount++;
                    }
                } else {
                    break;
                }
                mainCount++;
            }

        }

    }

}
