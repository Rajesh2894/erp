package com.abm.mainet.workManagement.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.workManagement.dto.ScheduleOfRateDetDto;
import com.abm.mainet.workManagement.dto.ScheduleOfRateMstDto;

/**
 * @author hiren.poriya
 * @Since 07-Dec-2017
 */
@Component
public class ScheduleOfRateValidator extends BaseEntityValidator<ScheduleOfRateMstDto> {

    @Override
    protected void performValidations(ScheduleOfRateMstDto dto,
            EntityValidationContext<ScheduleOfRateMstDto> validationContext) {
        if (dto.getSorCpdId() == null || dto.getSorCpdId() == 0) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("sor.select.sorname"));
        }

        if (dto.getSorFromDate() == null || dto.getSorFromDate().toString().isEmpty()) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("sor.select.fromdate"));
        }

        for (ScheduleOfRateDetDto detailDto : dto.getDetDto()) {
            if (detailDto.getSordCategory() == null) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("sor.select.category"));
            }
            if (detailDto.getSorDIteamNo() == null || detailDto.getSorDIteamNo().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("sor.enter.itemno"));
            }
            if (detailDto.getSorDDescription() == null || detailDto.getSorDDescription().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("sor.enter.sordesc"));
            } else if (detailDto.getSorDDescription().length() > 4000) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("sor.desc.length"));
            }
            if (detailDto.getSorIteamUnit() == null) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("sor.select.sorunit"));
            }
            if (detailDto.getSorBasicRate() == null || detailDto.getSorBasicRate().toString().isEmpty()) {
                validationContext.addOptionConstraint(getApplicationSession().getMessage("sor.enter.basicrate"));
            }
        }

    }

}
