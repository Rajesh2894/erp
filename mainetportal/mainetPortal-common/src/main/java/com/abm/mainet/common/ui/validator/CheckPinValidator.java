package com.abm.mainet.common.ui.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckPinValidator implements ConstraintValidator<CheckPin, String> {

    @Override
    public void initialize(final CheckPin arg0) {
        

    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return true;
        }
        boolean isValid = false;
        final boolean result = object.matches("[0-9]{6}");

        if (result) {
            isValid = true;
        } else {
            isValid = false;
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate("not a valid pincode").addConstraintViolation();

        }
        return isValid;
    }

}
