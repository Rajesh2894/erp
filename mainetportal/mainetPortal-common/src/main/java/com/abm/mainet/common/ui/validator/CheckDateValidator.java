package com.abm.mainet.common.ui.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckDateValidator implements ConstraintValidator<CheckDate, String> {

    @Override
    public void initialize(final CheckDate arg0) {
        

    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return true;
        }
        boolean isValid = false;
        final boolean result = object.matches("(0?[1-9]|[12][0-9]|3[01])(/|-)(0?[1-9]|1[012])(-|/)((19|20)\\d\\d)");

        if (result) {
            isValid = true;
        } else {
            isValid = false;
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate("not a valid date").addConstraintViolation();

        }
        return isValid;
    }

}
