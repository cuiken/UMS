package com.tp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;

public class DateFormatUtils {

	/**
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String convert(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}

	/**
	 * 
	 * @param date
	 * @return yyyy-MM-dd
	 */
	public static String convertDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	public static String getPerDate(String date) {
		DateTime dt = new DateTime(date);
		return dt.plus(Period.days(-1)).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
	}

	public static String getNextDate(String date) {
		DateTime dt = new DateTime(date);
		return dt.plus(Period.days(1)).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
	}

}
