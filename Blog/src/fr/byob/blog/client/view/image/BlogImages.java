package fr.byob.blog.client.view.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;
/**
 * Cr�e des images
 * @author akemi
 *
 */
public interface BlogImages extends ImageBundle {

	public final static BlogImages INSTANCE = (BlogImages) GWT
			.create(BlogImages.class);

	public AbstractImagePrototype BYOBLogo();
    
	
}