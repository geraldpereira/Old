package fr.byob.client.view.widget.smiley;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;



public interface SmileyAngry2  extends ImageBundle {
    public final static SmileyAngry2 INSTANCE = (SmileyAngry2) GWT
    .create(SmileyAngry2.class);
    public AbstractImagePrototype angry2();
}
