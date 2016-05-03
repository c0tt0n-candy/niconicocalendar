package com.niconicocalendar;

public class Feelings {

	private int feelingsId;
	private int userId;
	private String feelings;
	private int year;
	private int month;
	private int day;

	public Feelings(int feelingsId, String feelings) {
		super();
		this.feelingsId = feelingsId;
		this.feelings = feelings;
	}

	public Feelings(int userId, int day, int feelingsId) {
		super();
		this.userId = userId;
		this.day = day;
		this.feelingsId = feelingsId;
	}

	public int getFeelingsId() {
		return feelingsId;
	}

	public void setFeelingsId(int feelingsId) {
		this.feelingsId = feelingsId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFeelings() {
		return feelings;
	}

	public void setFeelings(String feelings) {
		this.feelings = feelings;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
}
