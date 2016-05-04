package com.niconicocalendar;

public class Feelings {

	private int feelingsId;
	private int userId;
	private int year;
	private int month;
	private int day;
	private int feelingsNum;
	private String feelings;
	
	public Feelings(){}
	
	public Feelings(int feelingsNum, String feelings) {
		super();
		this.feelingsNum = feelingsNum;
		this.feelings = feelings;
	}

	public Feelings(int userId, int day, int feelingsId) {
		super();
		this.userId = userId;
		this.day = day;
		this.feelingsId = feelingsId;
	}
	
	public Feelings(int feelingsId, int userId, int year, int month, int day, int feelingsNum) {
		super();
		this.feelingsId = feelingsId;
		this.userId = userId;
		this.year = year;
		this.month = month;
		this.day = day;
		this.feelingsNum = feelingsNum;
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

	public int getFeelingsNum() {
		return feelingsNum;
	}

	public void setFeelingsNum(int feelingsNum) {
		this.feelingsNum = feelingsNum;
	}

	public String getFeelings() {
		return feelings;
	}

	public void setFeelings(String feelings) {
		this.feelings = feelings;
	}
}
