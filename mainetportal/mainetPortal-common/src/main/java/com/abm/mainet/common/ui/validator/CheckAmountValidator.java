package com.abm.mainet.common.ui.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckAmountValidator implements ConstraintValidator<CheckAmount, String> {

    private static final String AMOUNT_PATTERN = "^[0-9]+(.[0-9]{1,2})?$";

    @Override
    public void initialize(final CheckAmount arg0) {
        

    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return true;
        }
        boolean isValid = false;
        final boolean result = object.matches(AMOUNT_PATTERN);

        if (result) {
            isValid = true;
        } else {
            isValid = false;
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate("not a valid amount").addConstraintViolation();

        }
        return isValid;
    }

}
