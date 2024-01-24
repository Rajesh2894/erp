package com.abm.mainet.common.ui.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckSpCharValidator implements ConstraintValidator<CheckSpChar, String> {

    @Override
    public void initialize(final CheckSpChar arg0) {
        

    }

    @Override
    public boolean isValid(final String object, final ConstraintValidatorContext constraintContext) {
        if (object == null) {
            return true;
        }
        boolean isValid = false;
        Matcher m = null;
        final Pattern p = Pattern.compile("[\\p{Punct}]");

        m = p.matcher(object);
        if (m.find()) {
            isValid = false;
            constraintContext.disableDefaultConstraintViolation();
            constraintContext.buildConstraintViolationWithTemplate("special characters not allowed").addConstraintViolation();

        } else {
            isValid = true;
        }

        return isValid;
    }

}
