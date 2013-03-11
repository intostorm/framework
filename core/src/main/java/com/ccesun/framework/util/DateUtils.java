package com.ccesun.framework.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils extends org.apache.commons.lang.time.DateUtils{
	
	public static final String PATTERN_DATE = "yyyyMMdd";
	
	public static final String PATTERN_YEARMONTH = "yyyyMM";
	
	public static final String PATTERN_DATETIME = "yyyyMMdd HH:mm:ss";
	
	
	public static final String currentYearMonth(){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_YEARMONTH);
		return dateFormat.format(date);
	}
	public static final String currentDate(){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_DATE);
		return dateFormat.format(date);
	}
	public static final String currentDateTime(){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN_DATETIME);
		return dateFormat.format(date);
	}

}
