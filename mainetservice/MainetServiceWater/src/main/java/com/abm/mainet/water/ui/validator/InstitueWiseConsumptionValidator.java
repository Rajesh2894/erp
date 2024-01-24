package com.abm.mainet.water.ui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.water.dto.TbWtInstCsmp;

/**
 * @author prasant.sahu
 *
 */

public class InstitueWiseConsumptionValidator implements Validator {
    @Override
    public boolean supports(final Class<?> aClass) {
        return TbWtInstCsmp.class.equals(aClass);
    }

    @Override
    public void validate(final Object object, final Errors errors) {
        final TbWtInstCsmp designationBean = (TbWtInstCsmp) object;

        if ((designationBean.getInstFrmDtStringFormat() == null)
                || designationBean.getInstFrmDtStringFormat().equals(MainetConstants.BLANK)) {
            ValidationUtils.rejectIfEmpty(errors, "instFrmDtStringFormat", "scrutiny.level.empty.err",
                    "From Date must not empty");
        }
        if ((designationBean.getInstToDtStringFormat() == null)
                || designationBean.getInstToDtStringFormat().equals(MainetConstants.BLANK)) {
            ValidationUtils.rejectIfEmpty(errors, "instToDtStringFormat", "scrutiny.role.empty.err",
                    "To Date Reg must not empty");
        }
        if (designationBean.getInstType() == null) {
            ValidationUtils.rejectIfEmpty(errors, "instType", "scrutiny.mode.empty.err", "Institute Type must not empty");
        }
        if (designationBean.getInstLitPerDay() == null) {
            ValidationUtils.rejectIfEmpty(errors, "instLitPerDay", "scrutiny.dadatype.empty.err",
                    "Institute Lit/day must not empty");
        }
    }
}
