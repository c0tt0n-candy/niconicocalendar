package com.niconicocalendar;

public class Feelings {

	private int userId;
	private int year;
	private int month;
	private int day;
	private int feelingId;

	public Feelings(int userId, int year, int month, int day, int feelingId) {
		super();
		this.userId = userId;
		this.year = year;
		this.month = month;
		this.day = day;
		this.feelingId = feelingId;
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

	public int getFeelingId() {
		return feelingId;
	}

	public void setFeelingId(int feelingId) {
		this.feelingId = feelingId;
	}

}
