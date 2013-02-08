package fr.byob.client.chat.command;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

public interface HelpCommandStrings extends Constants {
	public final static HelpCommandStrings INSTANCE = (HelpCommandStrings) GWT.create(HelpCommandStrings.class);
	
	String clear();
	String time();
	String scroll();
	String sound();
	String on();
	String off();
	String status();
	String me();
	String brief();
	String help();
}
