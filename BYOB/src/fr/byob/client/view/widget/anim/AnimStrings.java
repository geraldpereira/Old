package fr.byob.client.view.widget.anim;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Constants;

public interface AnimStrings extends Constants {
	public final static AnimStrings INSTANCE = (AnimStrings) GWT.create(AnimStrings.class);
	
	String loading();
}
