package com.abm.mainet.quartz.util;

import com.abm.mainet.common.constant.MainetConstants;

/**
 *
 * @author Vivek.Kumar
 * @since 15-May-2015
 */
public class CronExpressionUtility {

    public static String cronExpressionForDailyJobFrequency(final String second, final String min, final String hour,
            final String repeatDay) {

        final StringBuilder builder = new StringBuilder();

        builder.append(second)
                .append(MainetConstants.WHITE_SPACE)
                .append(min)
                .append(MainetConstants.WHITE_SPACE)
                .append(hour)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.CRON_EXPRESSION_DAILY)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.FORWARD_SLACE)
                .append(repeatDay)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.QUESTION_MARK)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK);

        return builder.toString();
    }

    public static String cronExpressionForWeeklyJobFrequency(final String second, final String min, final String hour,
            final String weekDay) {

        // 0 0 12 ? * MON,TUE,WED,THU,FRI,SAT,SUN *

        final StringBuilder builder = new StringBuilder();

        builder.append(second)
                .append(MainetConstants.WHITE_SPACE)
                .append(min)
                .append(MainetConstants.WHITE_SPACE)
                .append(hour)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.QUESTION_MARK)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK)
                .append(MainetConstants.WHITE_SPACE)
                .append(weekDay)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK);

        return builder.toString();

    }

    public static String cronExpressionForMonthlyJobFrequency(final String second, final String min, final String hour,
            final String fireTriggerOn, final String dateOfMonth, final String repeatOnLastDayOfMonth) {

        // 0 0 12 10 8/1 ? * --Fire at 12:00 PM on 10th August and repeat of every month on 10th
        // 0 0 0 L * ? --Fire at 00:00am on the last day of every month

        final StringBuilder builder = new StringBuilder();
        if ((repeatOnLastDayOfMonth != null)
                && repeatOnLastDayOfMonth.equalsIgnoreCase(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.REPEAT_LAST_DAY_OF_MONTH)) {

            builder.append(second)
                    .append(MainetConstants.WHITE_SPACE)
                    .append(min)
                    .append(MainetConstants.WHITE_SPACE)
                    .append(hour)
                    .append(MainetConstants.WHITE_SPACE)
                    .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.REPEAT_LAST_DAY_OF_MONTH)
                    .append(MainetConstants.WHITE_SPACE)
                    .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK)
                    .append(MainetConstants.WHITE_SPACE)
                    .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.QUESTION_MARK);
        } else {
            builder.append(second)
                    .append(MainetConstants.WHITE_SPACE)
                    .append(min)
                    .append(MainetConstants.WHITE_SPACE)
                    .append(hour)
                    .append(MainetConstants.WHITE_SPACE)
                    .append(dateOfMonth)
                    .append(MainetConstants.WHITE_SPACE)
                    .append(fireTriggerOn)
                    .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.FORWARD_SLACE)
                    .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.REPEAT_EVERY_MONTH)
                    .append(MainetConstants.WHITE_SPACE)
                    .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.QUESTION_MARK)
                    .append(MainetConstants.WHITE_SPACE)
                    .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK);
        }

        return builder.toString();

    }

    public static String cronExpressionForYearlyJobFrequency(final String second, final String min, final String hour,
            final String dateOfMonth, final String month) {
        // 0 0 12 17 5 ? *

        final StringBuilder builder = new StringBuilder();

        builder.append(second)
                .append(MainetConstants.WHITE_SPACE)
                .append(min)
                .append(MainetConstants.WHITE_SPACE)
                .append(hour)
                .append(MainetConstants.WHITE_SPACE)
                .append(dateOfMonth)
                .append(MainetConstants.WHITE_SPACE)
                .append(month)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.QUESTION_MARK)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK);

        return builder.toString();

    }

    public static String cronExpressionForOneTimeJobFrequency(final String second, final String min, final String hour,
            final String dateOfMonth, final String monthName, final String year) {

        // 0 45 13 3 APR ? 2012

        final StringBuilder builder = new StringBuilder();

        builder.append(second)
                .append(MainetConstants.WHITE_SPACE)
                .append(min)
                .append(MainetConstants.WHITE_SPACE)
                .append(hour)
                .append(MainetConstants.WHITE_SPACE)
                .append(dateOfMonth)
                .append(MainetConstants.WHITE_SPACE)
                .append(monthName)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.QUESTION_MARK)
                .append(MainetConstants.WHITE_SPACE)
                .append(year);

        return builder.toString();
    }

    public static String cronExpressionForQuarterlyJobFrequency(final String second, final String min, final String hour,
            final String fireTriggerOn, final String dateOfMonth) {

        // 0 0 12 10 8/3 ? * --Fire at 12:00 PM on 10th August and repeat of every 3 month on 10th

        final StringBuilder builder = new StringBuilder();

        builder.append(second)
                .append(MainetConstants.WHITE_SPACE)
                .append(min)
                .append(MainetConstants.WHITE_SPACE)
                .append(hour)
                .append(MainetConstants.WHITE_SPACE)
                .append(dateOfMonth)
                .append(MainetConstants.WHITE_SPACE)
                .append(fireTriggerOn)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.FORWARD_SLACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.REPEAT_QUARTERLY)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.QUESTION_MARK)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK);

        return builder.toString();
    }

    public static String cronExpressionForHalfYearlyJobFrequency(final String second, final String min, final String hour,
            final String fireTriggerOn, final String dateOfMonth) {

        // 0 0 12 10 8/6 ? * --Fire at 12:00 PM on 10th August and repeat of every 6 month on 10th

        final StringBuilder builder = new StringBuilder();

        builder.append(second)
                .append(MainetConstants.WHITE_SPACE)
                .append(min)
                .append(MainetConstants.WHITE_SPACE)
                .append(hour)
                .append(MainetConstants.WHITE_SPACE)
                .append(dateOfMonth)
                .append(MainetConstants.WHITE_SPACE)
                .append(fireTriggerOn)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.FORWARD_SLACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.JobFrequency.REPEAT_HALF_YEARLY)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.QUESTION_MARK)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK);

        return builder.toString();
    }

    public static String cronExpressionForMinutesJobFrequency(final String second, final String min, final String repeatMinTime) {

        final StringBuilder builder = new StringBuilder();

        builder.append(second)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK)                
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.FORWARD_SLACE)
                .append(repeatMinTime)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.QUESTION_MARK)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK);

        return builder.toString();
    }
    
    public static String cronExpressionForHourlyJobFrequency(final String second, final String min, final String hour, final String repeatHour) {

        final StringBuilder builder = new StringBuilder();

        builder.append(second)
                .append(MainetConstants.WHITE_SPACE)
                .append(min) 
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.FORWARD_SLACE)
                .append(repeatHour)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.QUESTION_MARK)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK)
                .append(MainetConstants.WHITE_SPACE)
                .append(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.ASTERISK);

        return builder.toString();
    }
    
    public static String removeZeroPrefixFromTimeHrOrMin(final String hrOrMin) {

        String resultTime = null;

        switch (hrOrMin) {

        case MainetConstants.Common_Constant.NUMBER.ZERO_ZERO:
            resultTime = MainetConstants.Common_Constant.NUMBER.ZERO;
            break;

        case MainetConstants.Common_Constant.NUMBER.ZERO_ONE:
            resultTime = MainetConstants.Common_Constant.NUMBER.ONE;
            break;

        case MainetConstants.Common_Constant.NUMBER.ZERO_TWO:
            resultTime = MainetConstants.Common_Constant.NUMBER.TWO;
            break;

        case MainetConstants.Common_Constant.NUMBER.ZERO_THREE:
            resultTime = MainetConstants.Common_Constant.NUMBER.THREE;
            break;

        case MainetConstants.Common_Constant.NUMBER.ZERO_FOUR:
            resultTime = MainetConstants.Common_Constant.NUMBER.FOUR;
            break;

        case MainetConstants.Common_Constant.NUMBER.ZERO_FIVE:
            resultTime = MainetConstants.Common_Constant.NUMBER.FIVE;
            break;

        case MainetConstants.Common_Constant.NUMBER.ZERO_SIX:
            resultTime = MainetConstants.Common_Constant.NUMBER.SIX;
            break;
        case MainetConstants.Common_Constant.NUMBER.ZERO_SEVEN:
            resultTime = MainetConstants.Common_Constant.NUMBER.SEVEN;
            break;

        case MainetConstants.Common_Constant.NUMBER.ZERO_EIGHT:
            resultTime = MainetConstants.Common_Constant.NUMBER.EIGHT;
            break;

        case MainetConstants.Common_Constant.NUMBER.ZERO_NINE:
            resultTime = MainetConstants.Common_Constant.NUMBER.NINE;
            break;

        default:
            resultTime = hrOrMin;
            break;
        }

        return resultTime;
    }

    public static String findTimeFromCronExpression(final String cronExpression) {

        String min;
        String hr;
        String minute = null;
        String hour = null;
        final StringBuilder builder = new StringBuilder();

        if (cronExpression != null) {

            final String[] trimedStr = cronExpression.split(MainetConstants.WHITE_SPACE);
            min = trimedStr[1];
            hr = trimedStr[2];

            minute = findTimeFromMinOrHour(min);
            hour = findTimeFromMinOrHour(hr);

            builder.append(hour)
                    .append(MainetConstants.operator.COLON)
                    .append(minute);
        }

        return builder.toString();
    }

    private static String findTimeFromMinOrHour(final String min) {

        String timeMinOrHr = null;

        switch (min) {
        case MainetConstants.Common_Constant.NUMBER.ZERO:
            timeMinOrHr = MainetConstants.Common_Constant.NUMBER.ZERO_ZERO;
            break;
        case MainetConstants.Common_Constant.NUMBER.ONE:
            timeMinOrHr = MainetConstants.Common_Constant.NUMBER.ZERO_ONE;
            break;
        case MainetConstants.Common_Constant.NUMBER.TWO:
            timeMinOrHr = MainetConstants.Common_Constant.NUMBER.ZERO_TWO;
            break;
        case MainetConstants.Common_Constant.NUMBER.THREE:
            timeMinOrHr = MainetConstants.Common_Constant.NUMBER.ZERO_THREE;
            break;
        case MainetConstants.Common_Constant.NUMBER.FOUR:
            timeMinOrHr = MainetConstants.Common_Constant.NUMBER.ZERO_FOUR;
            break;
        case MainetConstants.Common_Constant.NUMBER.FIVE:
            timeMinOrHr = MainetConstants.Common_Constant.NUMBER.ZERO_FIVE;
            break;
        case MainetConstants.Common_Constant.NUMBER.SIX:
            timeMinOrHr = MainetConstants.Common_Constant.NUMBER.ZERO_SIX;
            break;
        case MainetConstants.Common_Constant.NUMBER.SEVEN:
            timeMinOrHr = MainetConstants.Common_Constant.NUMBER.ZERO_SEVEN;
            break;
        case MainetConstants.Common_Constant.NUMBER.EIGHT:
            timeMinOrHr = MainetConstants.Common_Constant.NUMBER.ZERO_EIGHT;
            break;
        case MainetConstants.Common_Constant.NUMBER.NINE:
            timeMinOrHr = MainetConstants.Common_Constant.NUMBER.ZERO_NINE;
            break;

        default:
            timeMinOrHr = min;
            break;
        }

        return timeMinOrHr;
    }

 // Method used for getting Hours Interval from Cron Expression
    public static String findHourFromCronExpression(final String cronExpression) {
        String repeatedHour = null;
        String hour = null;
        if (cronExpression != null) {

            final String[] trimedStr = cronExpression.split(MainetConstants.WHITE_SPACE);
            hour = trimedStr[2];
			final String[] trimedCombineStr = hour.split(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.FORWARD_SLACE);
			repeatedHour = trimedCombineStr[1];
        }

        return repeatedHour;
    }
	
    // Method used for getting Minutes Interval from Cron Expression
	   public static String findMinuteFromCronExpression(final String cronExpression) {
        String repeatedMin = null;
        String minute = null;
        if (cronExpression != null) {

            final String[] trimedStr = cronExpression.split(MainetConstants.WHITE_SPACE);
            minute = trimedStr[1];
			final String[] trimedCombineStr = minute.split(MainetConstants.QUARTZ_SCHEDULE.CRON_EXPR.FORWARD_SLACE);
			repeatedMin = trimedCombineStr[1];
        }

        return repeatedMin;
    }
}
