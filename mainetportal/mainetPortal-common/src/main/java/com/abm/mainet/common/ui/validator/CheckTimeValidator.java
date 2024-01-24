package com.abm.mainet.common.ui.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckTimeValidator implements ConstraintValidator<CheckTime, String> {

    @Override
    public void initialize(final CheckTime arg0) {
        

    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return true;
        }
        boolean isValid = false;
        final boolean result = object.matches("((1[012]|[1-9]):[0-5][0-9](\\s){1}(?i)(am|pm))");

        if (result) {
            isValid = true;
        } else {
            isValid = false;
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate("not a valid time").addConstraintViolation();

        }
        return isValid;
    }

}
