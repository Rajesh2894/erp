package com.abm.mainet.common.ui.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckEmailValidator implements ConstraintValidator<CheckEmail, String> {

    @Override
    public void initialize(final CheckEmail arg0) {
        

    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return true;
        }
        boolean isValid = false;
        final boolean result = object
                .matches("([a-zA-Z0-9]+(?:[._+-][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*[.][a-zA-Z]{2,})");

        if (result) {
            isValid = true;
        } else {
            isValid = false;
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate("not a valid email-Id").addConstraintViolation();

        }
        return isValid;
    }

}
