package fr.byob.client.view.widget.smiley;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;



public interface SmileyAngry  extends ImageBundle {
    public final static SmileyAngry INSTANCE = (SmileyAngry) GWT
    .create(SmileyAngry.class);
    public AbstractImagePrototype angry();
}
