package com.abm.mainet.common.ui.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckCharWithSpaceValidator implements ConstraintValidator<CheckCharWithSpace, String> {

    @Override
    public void initialize(final CheckCharWithSpace arg0) {
        

    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return true;
        }
        boolean isValid = false;
        final boolean result = object.matches("[a-zA-Z ]+$");

        if (result) {
            isValid = true;
        } else {
            isValid = false;
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate("please enter only characters").addConstraintViolation();

        }
        return isValid;
    }

}
