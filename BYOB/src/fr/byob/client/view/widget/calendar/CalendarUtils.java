package fr.byob.client.view.widget.calendar;

import java.util.Date;

public class CalendarUtils {
	public static int getCurrentYear (){
		return new Date().getYear() + 1900;
	}
	public static int getCurrentMonth (){
		return new Date().getMonth();
	}
	public static int getCurrentDay (){
		return new Date().getDate();
	}
	
	public static int getFirstDay(final int year, final int month){
		return new Date(year - 1900, month, 1).getDay();	
	}
	
	public static int getDaysInMonth(int year, int month) {
		switch (month) {
		case 1:
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				return 29; // leap year
			else
				return 28;
		case 3:
			return 30;
		case 5:
			return 30;
		case 8:
			return 30;
		case 10:
			return 30;
		default:
			return 31;
		}
	}
}
