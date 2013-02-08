package fr.byob.client.view.widget.calendar;

import java.util.Date;

public class CalendarDate {

	private int year;
	private int month;
	private int day;

	private int hour;
	private int min;
	private int sec;

	public CalendarDate(final int hour, final int min, final int sec) {
		this.hour = hour;
		this.min = min;
		this.sec = sec;
	}

	public void setDate(int year, int month, int day) {
		setYear(year);
		setMonth(month);
		this.day = day;
	}

	public Date getDate() {
		if (day == 0) {
			return null;
		}
		Date date = new Date(year, month, day, hour, min, sec);
		return date;
	}

	public void setYear(int year) {
		this.year = year - 1900;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return 1900 + year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public void reintDate() {
		year = 0;
		month = 0;
		day = 0;
	}

	public String toString() {
		int realYear = year + 1900;
		return "" + day + "/" + month + "/" + realYear + " " + hour + ":" + min
				+ ":" + sec;
	}

}
