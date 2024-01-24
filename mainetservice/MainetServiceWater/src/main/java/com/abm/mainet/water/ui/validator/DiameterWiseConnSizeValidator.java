package com.abm.mainet.water.ui.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.abm.mainet.water.dto.TbCcnsizePrm;
import com.abm.mainet.water.dto.TbSlopePrm;

/**
 * @author prasant.sahu
 *
 */
public class DiameterWiseConnSizeValidator implements Validator {

    /*
     * (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(final Class<?> clazz) {
        return TbSlopePrm.class.equals(clazz);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    @Override
    public void validate(final Object target, final Errors errors) {
        final TbCcnsizePrm connSizeBean = (TbCcnsizePrm) target;

        if (connSizeBean.getCnsFrmdt() == null) {
            ValidationUtils.rejectIfEmpty(errors, "cnsFrmdt", "scrutiny.level.empty.err", "From Date must not empty");
        }
        if (connSizeBean.getCnsTodt() == null) {
            ValidationUtils.rejectIfEmpty(errors, "cnsTodt", "scrutiny.role.empty.err", "To Date must not empty");
        }
        if (connSizeBean.getCnsFrom() == null) {
            ValidationUtils.rejectIfEmpty(errors, "cnsFrom", "scrutiny.mode.empty.err", "Slope From must not empty");
        }
        if (connSizeBean.getCnsTo() == null) {
            ValidationUtils.rejectIfEmpty(errors, "cnsTo", "scrutiny.dadatype.empty.err", "Slope To must not empty");
        } 
        if (connSizeBean.getCnsValue() == null) {
            ValidationUtils.rejectIfEmpty(errors, "cnsValue", "scrutiny.label.empty.err", "Label must not empty");
        }
    }

}
