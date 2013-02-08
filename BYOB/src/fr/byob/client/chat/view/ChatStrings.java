package fr.byob.client.chat.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

public interface ChatStrings extends Constants {
	public final static ChatStrings INSTANCE = (ChatStrings) GWT.create(ChatStrings.class);
	
	String changestatus();
	String timeDisplay();
	String timeNoDisplay();
	String scrollOn();
	String scrollOff();
}
