package fr.byob.blog.client.view.image.profil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;
/**
 * Cr�e des images
 * @author akemi
 *
 */
public interface BlogEmptyProfileImages extends ImageBundle {

	public final static BlogEmptyProfileImages INSTANCE = (BlogEmptyProfileImages) GWT
			.create(BlogEmptyProfileImages.class);

    AbstractImagePrototype emptyProfile();
	
}