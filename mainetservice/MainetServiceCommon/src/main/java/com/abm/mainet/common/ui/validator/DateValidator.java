package com.abm.mainet.common.ui.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.abm.mainet.common.constant.MainetConstants;

/**
 * @author Pranit.Mhatre
 * @since 13 December,2013
 */
@Component
public class DateValidator {

    private final Pattern pattern;
    private Matcher matcher;

    public DateValidator() {
        pattern = Pattern.compile(MainetConstants.DATE_TIME_PATTERN);
    }

    public boolean isValidDate(final String dateToValidate) {
        return this.isValidDate(dateToValidate, null);
    }

    public boolean isValidDate(final String dateToValidate, final String dateFromat) {
        if (!StringUtils.hasText(dateToValidate)) {
            return false;
        }

        SimpleDateFormat simpleDateFormat;

        if (StringUtils.hasText(dateFromat)) {
            simpleDateFormat = new SimpleDateFormat(dateFromat);

        } else {
            simpleDateFormat = new SimpleDateFormat(MainetConstants.DATE_HOUR_FORMAT);
        }

        simpleDateFormat.setLenient(true);

        try {
            // if not valid, it will throw ParseException
            simpleDateFormat.parse(dateToValidate);
            return validateDayForMonth(dateToValidate);

        } catch (final ParseException parseException) {
            return false;
        }
    }

    private boolean validateDayForMonth(final String date) {
        matcher = pattern.matcher(date);

        if (matcher.matches()) {
            matcher.reset();

            if (matcher.find()) {
                final String day = matcher.group(1);
                final String month = matcher.group(2);
                final int year = Integer.parseInt(matcher.group(3));

                if (day.equals("31") && (month.equals("4") || month.equals("6") || month.equals("9") || month.equals("11")
                        || month.equals("04") || month.equals("06") || month.equals("09"))) {
                    return false; // only 1,3,5,7,8,10,12 has 31 days
                } else if (month.equals("2") || month.equals("02")) {
                    // leap year
                    if ((year % 4) == 0) {
                        if (day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    } else {
                        if (day.equals("29") || day.equals("30") || day.equals("31")) {
                            return false;
                        } else {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
