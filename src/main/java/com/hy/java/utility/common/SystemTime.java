package com.hy.java.utility.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 可以获取特定格式的系统时间
 * 
 * @author chiefeweight
 */
public class SystemTime {
	/**
	 * 获取系统时间，格式为“年.月.日(星期),时:分:秒”
	 *
	 * @return 特定格式的系统时间
	 */
	public static String getCurrentTime() {
		SimpleDateFormat simple_date_format = new SimpleDateFormat("yyyy.MM.dd(EE),HH:mm:ss", Locale.CHINA);
		return simple_date_format.format(new Date());
	}

	/**
	 * 获取系统时间，格式为用户指定
	 *
	 * @param simple_date_format
	 *            时间格式
	 * @return 特定格式的系统时间
	 */
	public static String getCurrentTime(SimpleDateFormat simple_date_format) {
		return simple_date_format.format(new Date());
	}

	/**
	 * 获取系统时间，格式、时区为用户指定
	 *
	 * @param time_pattern
	 *            时间格式
	 * @param locale
	 *            时区
	 * @return 特定格式、时区的系统时间
	 */
	public static String getCurrentTime(String time_pattern, Locale locale) {
		SimpleDateFormat simple_date_format = new SimpleDateFormat(time_pattern, locale);
		return simple_date_format.format(new Date());
	}
}
