package fr.byob.client.view.widget.smiley;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;



public interface SmileyCrying  extends ImageBundle {
    public final static SmileyCrying INSTANCE = (SmileyCrying) GWT
    .create(SmileyCrying.class);
    public AbstractImagePrototype crying();
}
