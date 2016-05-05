package com.niconicocalendar.model;

import java.util.Calendar;

public class getCalendar {
	
	static Calendar calendar = Calendar.getInstance();
	
	public static int getDispYear() {
		return calendar.get(Calendar.YEAR);
	}
	
	public static int getDispMonth() {
		return calendar.get(Calendar.MONTH) + 1;
	}
	
	public static int getLastDay(int year, int month) {
		calendar.set(year, month - 1, 1);
		return calendar.getActualMaximum(Calendar.DATE);
	}	
}