package fr.byob.client.view.widget.smiley;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;



public interface SmileySkeptical  extends ImageBundle {
    public final static SmileySkeptical INSTANCE = (SmileySkeptical) GWT
    .create(SmileySkeptical.class);
    public AbstractImagePrototype skeptical();
}
