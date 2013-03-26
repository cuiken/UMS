package com.tp.utils;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateUtil {
    private static final String format6chars = "yyyyMM";
    private static final String formatIso8601Day = "yyyy-MM-dd";
    private static final String formatFriendlyTimestamp = "yyyy-MM-dd HH:mm:ss";

    /**
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String convert(Date date) {
        DateTime dt = new DateTime(date);
        return dt.toString(getFriendlyTimestampFormat());
    }

    /**
     * @param date
     * @return yyyy-MM-dd
     */
    public static String convertDate(Date date) {
        DateTime dt = new DateTime(date);
        return dt.toString(getIso86010DateFormat());
    }

    public static String getPerDate(String date) {
        DateTime dt = new DateTime(date);
        return dt.plus(Period.days(-1)).toString(getIso86010DateFormat());
    }

    public static String getNextDate(String date) {
        DateTime dt = new DateTime(date);
        return dt.plus(Period.days(1)).toString(getIso86010DateFormat());
    }

    public static String getPerMonthDate(String date) {
        DateTime dt = new DateTime(date);
        return dt.plus(Period.months(-1)).toString(getIso86010DateFormat());
    }

    public static String get6charDateString(String date) {
        DateTime dt = new DateTime(date);
        return dt.toString(getFormat6chars());
    }

    public static DateTimeFormatter getIso86010DateFormat() {
        return DateTimeFormat.forPattern(formatIso8601Day);
    }

    public static DateTimeFormatter getFriendlyTimestampFormat() {
        return DateTimeFormat.forPattern(formatFriendlyTimestamp);
    }

    public static DateTimeFormatter getFormat6chars() {
        return DateTimeFormat.forPattern(format6chars);
    }
}
