package fr.byob.client.view.widget.smiley;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;



public interface SmileyWink  extends ImageBundle {
    public final static SmileyWink INSTANCE = (SmileyWink) GWT
    .create(SmileyWink.class);
    public AbstractImagePrototype wink();
}
