/**
 * 
 */
package com.abm.mainet.property.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.property.dto.BillingScheduleDto;

/**
 * @author hiren.poriya
 * @since 22-Nov-2017
 */
@Component
public class BillingScheduleValidator extends BaseEntityValidator<BillingScheduleDto> {

    @Override
    protected void performValidations(BillingScheduleDto entity,
            EntityValidationContext<BillingScheduleDto> validationContext) {

        if (entity.getTbFinancialyears() == null || entity.getTbFinancialyears().isEmpty()) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("billschedule.select.finYear"));
        }
        if (entity.getAsBillFrequency() == null || entity.getAsBillFrequency() == 0) {
            validationContext.addOptionConstraint(getApplicationSession().getMessage("billschedule.select.frequency"));
        }
        /*
         * if (entity.getAsFrequencyFrom() == null || entity.getAsFrequencyFrom() == 0) {
         * validationContext.addOptionConstraint(getApplicationSession().getMessage("billschedule.select.froMonth")); } if
         * (entity.getAsFrequencyTo() == null || entity.getAsFrequencyTo() == 0) {
         * validationContext.addOptionConstraint(getApplicationSession().getMessage("billscheduleselect.toMonth")); }
         */

    }

}
