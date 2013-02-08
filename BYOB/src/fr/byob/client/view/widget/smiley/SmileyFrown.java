package fr.byob.client.view.widget.smiley;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;



public interface SmileyFrown  extends ImageBundle {
    public final static SmileyFrown INSTANCE = (SmileyFrown) GWT
    .create(SmileyFrown.class);
    public AbstractImagePrototype frown();
}
