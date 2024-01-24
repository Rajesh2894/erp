package com.abm.mainet.swm.ui.validator;

import com.abm.mainet.common.ui.validator.BaseEntityValidator;
import com.abm.mainet.common.ui.validator.EntityValidationContext;
import com.abm.mainet.swm.dto.WasteRateMasterDTO;

public class WasteRateValidator extends BaseEntityValidator<WasteRateMasterDTO> {

    @Override
    protected void performValidations(WasteRateMasterDTO entity,
            EntityValidationContext<WasteRateMasterDTO> entityValidationContext) {

        /*
         * entityValidationContext.rejectIfNull(entity.getwFromDate(), "Select From Date ");
         * entityValidationContext.rejectIfNull(entity.getwToDate(), "Select To Date");
         * entityValidationContext.rejectIfNotSelected(entity.getCodWast1(), "Select Waste Type");
         */

    }

}
