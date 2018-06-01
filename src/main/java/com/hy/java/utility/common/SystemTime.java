package com.hy.java.utility.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SystemTime {

	public static String getCurrentTime() {
		SimpleDateFormat simple_date_format = new SimpleDateFormat("yyyy.MM.dd(EE),HH:mm:ss", Locale.CHINA);
		return simple_date_format.format(new Date());
	}

	public static String getCurrentTime(SimpleDateFormat simple_date_format) {
		return simple_date_format.format(new Date());
	}

	public static String getCurrentTime(String time_pattern, Locale locale) {
		SimpleDateFormat simple_date_format = new SimpleDateFormat(time_pattern, locale);
		return simple_date_format.format(new Date());
	}
}
