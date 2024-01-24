package com.abm.mainet.common.ui.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckNumValidator implements ConstraintValidator<CheckNum, String> {

    @Override
    public void initialize(final CheckNum arg0) {
        

    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return true;
        }
        boolean isValid = false;
        final boolean result = object.matches("[0-9]+$");

        if (result) {
            isValid = true;
        } else {
            isValid = false;
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate("Please enter numbers only").addConstraintViolation();

        }
        return isValid;
    }

}
