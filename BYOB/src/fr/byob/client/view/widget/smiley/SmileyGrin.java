package fr.byob.client.view.widget.smiley;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;



public interface SmileyGrin  extends ImageBundle {
    public final static SmileyGrin INSTANCE = (SmileyGrin) GWT
    .create(SmileyGrin.class);
    public AbstractImagePrototype grin();
}
