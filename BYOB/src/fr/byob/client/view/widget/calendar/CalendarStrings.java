package fr.byob.client.view.widget.calendar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

public interface CalendarStrings extends Constants{

	public final static CalendarStrings INSTANCE = (CalendarStrings) GWT
	.create(CalendarStrings.class);
	
	String Sunday ();
	String Monday ();
	String Tuesday ();
	String Wednesday ();
	String Thursday ();
	String Friday ();
	String Saturday ();

	String January ();
	String February ();
	String March ();
	String April ();
	String May ();
	String June ();
	String July ();
	String August ();
	String September ();
	String October ();
	String November ();
	String December ();

}
