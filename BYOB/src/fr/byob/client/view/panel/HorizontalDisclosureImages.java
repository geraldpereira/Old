package fr.byob.client.view.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface HorizontalDisclosureImages extends ImageBundle{

	public final static HorizontalDisclosureImages INSTANCE = (HorizontalDisclosureImages) GWT
	.create(HorizontalDisclosureImages.class);
	
	AbstractImagePrototype left();
    AbstractImagePrototype right();
}
