package fr.byob.client.chat.command;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

public interface CommandStrings extends Constants {
	public final static CommandStrings INSTANCE = (CommandStrings) GWT.create(CommandStrings.class);
	
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
