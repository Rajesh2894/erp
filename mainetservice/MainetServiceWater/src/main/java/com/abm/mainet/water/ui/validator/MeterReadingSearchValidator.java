package com.abm.mainet.water.ui.validator;

import org.springframework.stereotype.Component;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.water.dto.MeterReadingDTO;

/**
 * @author Rahul.Yadav
 *
 */

@Component
public class MeterReadingSearchValidator extends BaseEntityValidator<MeterReadingDTO> {

    /*
     * (non-Javadoc)
     * @see com.abm.mainetservice.web.core.validator.BaseEntityValidator#performValidations(java.lang.Object,
     * com.abm.mainetservice.web.core.validator.EntityValidationContext)
     */
    @Override
    protected void performValidations(final MeterReadingDTO entity,
            final EntityValidationContext<MeterReadingDTO> entityValidationContext) {

        entityValidationContext.rejectIfNotSelected(entity.getMeterType(), "meterType");
        if (MainetConstants.FlagS.equals(entity.getMeterType())) {
            entityValidationContext.rejectIfEmpty(entity.getCsCcn(), "csCcn");
        } else {
            performGroupValidation("wardZone");
            performGroupValidation("tariff");
            entityValidationContext.rejectIfNotSelected(entity.getConSize(), "conSize");
        }
    }

}
