package fr.byob.client.view.widget.smiley;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;



public interface SmileyKiss  extends ImageBundle {
    public final static SmileyKiss INSTANCE = (SmileyKiss) GWT
    .create(SmileyKiss.class);
    public AbstractImagePrototype kiss();
}
