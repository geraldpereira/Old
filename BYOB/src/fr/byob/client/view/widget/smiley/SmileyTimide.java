package fr.byob.client.view.widget.smiley;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;



public interface SmileyTimide  extends ImageBundle {
    public final static SmileyTimide INSTANCE = (SmileyTimide) GWT
    .create(SmileyTimide.class);
    public AbstractImagePrototype timide();
}
