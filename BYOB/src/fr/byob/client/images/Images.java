package fr.byob.client.images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
     * Cet {@link ImageBundle} est utilisé pour les icones de boutons
     */
    public interface Images extends ImageBundle {
    	public final static Images INSTANCE = (Images) GWT
    	.create(Images.class);
    	
        AbstractImagePrototype exit();
    }