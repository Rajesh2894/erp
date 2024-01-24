package com.abm.mainet.common.ui.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sandeep.Nagpure
 * @since 17 December,2013
 */
public final class PatternValidator {
    private final Pattern pattern;
    private Matcher matcher;

    public PatternValidator(final String expressionPatter) {
        pattern = Pattern.compile(expressionPatter);
    }

    public boolean matchPattern(final String value) {
        matcher = pattern.matcher(value);
        return matcher.matches();
    }

}
