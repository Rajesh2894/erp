package com.abm.mainet.water.ui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.water.dto.TbSlopePrm;

/**
 * @author prasant.sahu
 *
 */
public class SlopeEntryValidator implements Validator {

    @Override
    public boolean supports(final Class<?> aClass) {
        return TbSlopePrm.class.equals(aClass);
    }

    @Override
    public void validate(final Object object, final Errors errors) {
        final TbSlopePrm slopeBean = (TbSlopePrm) object;

        if (slopeBean.getSpFrmdtStringFormat() == MainetConstants.BLANK) {
            ValidationUtils.rejectIfEmpty(errors, "spFrmdtStringFormat", "scrutiny.level.empty.err", "From Date must not empty");
        }
        if (slopeBean.getSpTodtStringFormat() == MainetConstants.BLANK) {
            ValidationUtils.rejectIfEmpty(errors, "spTodtStringFormat", "scrutiny.role.empty.err", "To Date must not empty");
        }
        if (slopeBean.getSpFrom() == null) {
            ValidationUtils.rejectIfEmpty(errors, "spFrom", "scrutiny.mode.empty.err", "Slope From must not empty");
        }
        if (slopeBean.getSpTo() == null) {
            ValidationUtils.rejectIfEmpty(errors, "spTo", "scrutiny.dadatype.empty.err", "Slope To must not empty");
        }
        if (slopeBean.getSpValue() == null) {
            ValidationUtils.rejectIfEmpty(errors, "spValue", "scrutiny.label.empty.err", "Label must not empty");
        }

    }
}
