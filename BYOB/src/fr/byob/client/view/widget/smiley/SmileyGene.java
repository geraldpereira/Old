package fr.byob.client.view.widget.smiley;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;



public interface SmileyGene  extends ImageBundle {
    public final static SmileyGene INSTANCE = (SmileyGene) GWT
    .create(SmileyGene.class);
    public AbstractImagePrototype gene();
}
