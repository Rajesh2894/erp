package com.abm.mainet.common.ui.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.abm.mainet.common.constant.MainetConstants;

public class CheckAgeValidator implements ConstraintValidator<CheckAge, String> {

    @Override
    public void initialize(final CheckAge arg0) {

    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return true;
        }
        boolean isValid = false;
        boolean result = false;
        if (object.matches(MainetConstants.AGE_PATTERN)) {
            result = ((Integer.parseInt(object) >= 18) && (Integer.parseInt(object) <= 100));
        }

        if (result) {
            isValid = true;
        } else {
            isValid = false;
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate("age should be between 18 and 100 years ")
                    .addConstraintViolation();

        }
        return isValid;
    }

}
