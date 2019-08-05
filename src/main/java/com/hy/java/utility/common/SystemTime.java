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
	 * 获取系统时间，格式为“年-月-日T时:分:秒.毫秒”
	 *
	 * @return 特定格式的系统时间
	 */
	public static String currentFormattedTime() {
		SimpleDateFormat simple_date_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.CHINA);
		return simple_date_format.format(new Date());
	}

	/**
	 * 获取系统时间，格式为用户指定
	 *
	 * @param simple_date_format 时间格式
	 * @return 特定格式的系统时间
	 */
	public static String currentFormattedTime(SimpleDateFormat simple_date_format) {
		return simple_date_format.format(new Date());
	}

	/**
	 * 获取系统时间，格式、时区为用户指定
	 *
	 * 建议格式为"yyyy-MM-dd'T'HH:mm:ss.SSS(EEE)"
	 *
	 * @param time_pattern 时间格式
	 * @param locale       时区
	 * @return 特定格式、时区的系统时间
	 */
	public static String currentFormattedTime(String time_pattern, Locale locale) {
		SimpleDateFormat simple_date_format = new SimpleDateFormat(time_pattern, locale);
		return simple_date_format.format(new Date());
	}

	/**
	 * 获取系统时间秒数
	 *
	 * @return System.currentTimeMillis()
	 */
	public static long currentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 格式化时间，格式为“年-月-日T时:分:秒.毫秒”
	 *
	 * @param time_millis 时间
	 * @return 格式化时间
	 */
	public static String formatTime(long time_millis) {
		SimpleDateFormat simple_date_format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.CHINA);
		return simple_date_format.format(new Date(time_millis));
	}

	/**
	 * 获取系统时间，格式为用户指定
	 * 
	 * @param time_millis 时间
	 * @param simple_date_format 时间格式
	 * @return 特定格式的格式化时间
	 */
	public static String formatTime(long time_millis, SimpleDateFormat simple_date_format) {
		return simple_date_format.format(new Date(time_millis));
	}

	/**
	 * 获取系统时间，格式、时区为用户指定
	 * 
	 * 建议格式为"yyyy-MM-dd'T'HH:mm:ss.SSS(EEE)"
	 * 
	 * @param time_millis  时间
	 * @param time_pattern 时间格式
	 * @param locale 时区
	 * @return 特定格式、时区的格式化时间
	 */
	public static String formatTime(long time_millis, String time_pattern, Locale locale) {
		SimpleDateFormat simple_date_format = new SimpleDateFormat(time_pattern, locale);
		return simple_date_format.format(new Date(time_millis));
	}
}
