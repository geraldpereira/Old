package fr.byob.client.view.widget.smiley;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;



public interface SmileyNoseSmile  extends ImageBundle {
    public final static SmileyNoseSmile INSTANCE = (SmileyNoseSmile) GWT
    .create(SmileyNoseSmile.class);
    public AbstractImagePrototype nosesmile();
}
