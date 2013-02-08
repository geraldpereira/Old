package fr.byob.blog.client.view.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;
/**
 * Cr�e des images
 * @author akemi
 *
 */
public interface BlogVideo extends ImageBundle {

	public final static BlogVideo INSTANCE = (BlogVideo) GWT
			.create(BlogVideo.class);

	public AbstractImagePrototype video();
    
	
}