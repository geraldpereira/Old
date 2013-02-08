package fr.byob.client.view.widget.smiley;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;



public interface SmileyStraightFace  extends ImageBundle {
    public final static SmileyStraightFace INSTANCE = (SmileyStraightFace) GWT
    .create(SmileyStraightFace.class);
    public AbstractImagePrototype straightface();
}
