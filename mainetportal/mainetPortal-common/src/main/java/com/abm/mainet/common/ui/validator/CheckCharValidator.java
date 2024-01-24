package com.abm.mainet.common.ui.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.abm.mainet.common.constant.MainetConstants;

public class CheckCharValidator implements ConstraintValidator<CheckChar, String> {

    @Override
    public void initialize(final CheckChar constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return true;
        }
        boolean isValid = false;
        final boolean result = object.matches(MainetConstants.CHAR_VALIDATOR);

        if (result) {
            isValid = true;
        } else {
            isValid = false;
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate("Please enter characters only").addConstraintViolation();

        }
        return isValid;
    }

}
