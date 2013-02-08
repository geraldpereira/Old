package fr.byob.client.view.widget.smiley;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;



public interface SmileyStickingTongueOut  extends ImageBundle {
    public final static SmileyStickingTongueOut INSTANCE = (SmileyStickingTongueOut) GWT
    .create(SmileyStickingTongueOut.class);
    public AbstractImagePrototype stickingtongueout();
}
