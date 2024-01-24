package com.abm.mainet.common.ui.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckPercentValidator implements ConstraintValidator<CheckPercent, String> {

    @Override
    public void initialize(final CheckPercent arg0) {
        

    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return true;
        }
        boolean isValid = false;
        boolean result = false;
        if (object.matches("^[0-9]{1,3}+(.[0-9]{1,2})?$")) {
            // int tempAge=Math.round(Float.parseFloat(object));
            // System.out.println("tempAge---"+tempAge);
            result = ((Float.parseFloat(object) >= 0.0) && (Float.parseFloat(object) <= 100.0));
        }

        if (result) {
            isValid = true;
        } else {
            isValid = false;
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate("not a valid percentage ").addConstraintViolation();

        }
        return isValid;
    }

}
