package fr.byob.client.view.widget.smiley;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;



public interface SmileyHeart  extends ImageBundle {
    public final static SmileyHeart INSTANCE = (SmileyHeart) GWT
    .create(SmileyHeart.class);
    public AbstractImagePrototype heart();
}
