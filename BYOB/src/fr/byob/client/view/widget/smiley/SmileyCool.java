package fr.byob.client.view.widget.smiley;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;



public interface SmileyCool  extends ImageBundle {
    public final static SmileyCool INSTANCE = (SmileyCool) GWT
    .create(SmileyCool.class);
    public AbstractImagePrototype cool();
}
