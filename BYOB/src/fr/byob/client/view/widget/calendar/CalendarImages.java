package fr.byob.client.view.widget.calendar;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface CalendarImages extends ImageBundle{
	
	public final static CalendarImages INSTANCE = (CalendarImages) GWT
	.create(CalendarImages.class);
	
    AbstractImagePrototype nextMonth();
    AbstractImagePrototype nextYear();
    AbstractImagePrototype prevMonth();
    AbstractImagePrototype prevYear();
}
