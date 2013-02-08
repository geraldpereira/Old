package fr.byob.client.view.widget.smiley;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;



public interface SmileyShocked  extends ImageBundle {
    public final static SmileyShocked INSTANCE = (SmileyShocked) GWT
    .create(SmileyShocked.class);
    public AbstractImagePrototype shocked();
}
