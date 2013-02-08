package fr.byob.client.view.widget.anim;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;
/**
 * Cr�e des images
 * @author akemi
 *
 */
public interface AnimImages extends ImageBundle {

	public final static AnimImages INSTANCE = (AnimImages) GWT
			.create(AnimImages.class);

	public AbstractImagePrototype load();
	public AbstractImagePrototype load45();
	public AbstractImagePrototype load90();
	public AbstractImagePrototype load135();
	public AbstractImagePrototype load180();
	public AbstractImagePrototype load225();
	public AbstractImagePrototype load270();
	public AbstractImagePrototype load315();
	
}