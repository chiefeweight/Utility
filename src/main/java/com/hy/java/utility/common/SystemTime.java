package com.hy.java.utility.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class SystemTime implements Runnable {
	private String name;
	private SimpleDateFormat simpleDateFormat;

	public SystemTime(String name) {
		this.name = name;
		simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日（ EE），HH时mm分ss秒", Locale.CHINA);
	}

	public SystemTime(String name, SimpleDateFormat simpleDateFormat) {
		this.name = name;
		this.simpleDateFormat = simpleDateFormat;
	}

	public SystemTime(String name, String timeFormat, Locale locale) {
		this.name = name;
		simpleDateFormat = new SimpleDateFormat(timeFormat, locale);
	}

	public String getName() {
		return name;
	}

	public void run() {
		// TODO Auto-generated method stub
		Timer timer = new Timer();
		MyTimerTask myTimerTask = new MyTimerTask(this.simpleDateFormat, this.name);
		System.out.println(name + "\tstart.");
		timer.schedule(myTimerTask, 0, 1000);
	}

	private class MyTimerTask extends TimerTask {
		private Date date;
		private String name;
		private SimpleDateFormat sdf;

		private MyTimerTask(SimpleDateFormat sdf, String name) {
			// TODO Auto-generated constructor stub
			date = new Date();
			this.sdf = sdf;
			this.name = name;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			date.setTime(System.currentTimeMillis());
			System.out.println(name + "\t" + sdf.format(date));
		}
	}
}
