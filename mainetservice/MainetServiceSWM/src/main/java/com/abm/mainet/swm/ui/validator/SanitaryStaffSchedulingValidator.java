package com.abm.mainet.swm.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.swm.dto.EmployeeScheduleDTO;
import com.abm.mainet.swm.dto.EmployeeScheduleDetailDTO;

@Component
public class SanitaryStaffSchedulingValidator extends BaseEntityValidator<EmployeeScheduleDTO> {

    @Override
    protected void performValidations(EmployeeScheduleDTO entity,
            EntityValidationContext<EmployeeScheduleDTO> entityValidationContext) {

        if (entity.getEmsType() != null) {
            entityValidationContext.rejectIfNotSelected(entity.getEmsType(), "emsType");
        }
        if (entity.getEmsFromdate() != null) {
            entityValidationContext.rejectIfEmpty(entity.getEmsFromdate(), "emsFromdate");
        }
        if (entity.getEmsTodate() != null) {
            entityValidationContext.rejectIfEmpty(entity.getEmsTodate(), "emsTodate");
        }

        if (entity.getEmsReocc() != null) {
            entityValidationContext.rejectIfEmpty(entity.getEmsReocc(), "emsReocc");
        }
        if (entity.getTbSwEmployeeScheddets() != null && !entity.getTbSwEmployeeScheddets().isEmpty()) {
            for (EmployeeScheduleDetailDTO detDto : entity.getTbSwEmployeeScheddets()) {
                if (detDto.getEmpid() != null) {
                    entityValidationContext.rejectIfNotSelected(detDto.getEmpid(), "empid");
                }
                if (detDto.getDeId() != null) {
                    entityValidationContext.rejectIfNotSelected(detDto.getDeId(), "deId");
                }
                if (detDto.getEmsdCollType() != null) {
                    entityValidationContext.rejectIfNotSelected(detDto.getEmsdCollType(), "emsdCollType");
                }
                if (detDto.getLocId() != null) {
                    entityValidationContext.rejectIfNotSelected(detDto.getLocId(), "locId");
                }

                if (detDto.getVeId() != null) {
                    entityValidationContext.rejectIfNotSelected(detDto.getVeId(), "veId");
                }
                if (detDto.getBeatId() != null) {
                    entityValidationContext.rejectIfNotSelected(detDto.getBeatId(), "beatId");
                }
                if (detDto.getCodWard1() != null) {
                    entityValidationContext.rejectIfNotSelected(detDto.getCodWard1(), "codWard1");
                }
                if (detDto.getCodWard2() != null) {
                    entityValidationContext.rejectIfNotSelected(detDto.getCodWard2(), "codWard2");
                }
                if (detDto.getStartTime() != null) {
                    entityValidationContext.rejectIfEmpty(detDto.getStartTime(), "startTime");
                }
                if (detDto.getEndTime() != null) {
                    entityValidationContext.rejectIfEmpty(detDto.getEndTime(), "endtime");
                }
            }
            String previousFrom = null;
            String previousTo = null;
            int mainCount = 0;
            boolean found = false;
            for (EmployeeScheduleDetailDTO detDtofor : entity.getTbSwEmployeeScheddets()) {
                previousFrom = detDtofor.getStartTime();
                previousTo = detDtofor.getEndTime();
                int detCount = 0;
                if (!found) {
                    for (EmployeeScheduleDetailDTO detDto : entity.getTbSwEmployeeScheddets()) {
                        if (mainCount != detCount) {
                            if (previousFrom.equals(detDto.getStartTime())) {
                                entityValidationContext.addOptionConstraint("From time must not be same.");
                                found = true;
                            } else if (previousTo.equals(detDto.getEndTime())) {
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
