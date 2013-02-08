package fr.byob.blog.client.view.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface NewItemImage extends ImageBundle {
    public final static NewItemImage INSTANCE = (NewItemImage) GWT.create(NewItemImage.class);
    
    public AbstractImagePrototype newItem();
}
