package fr.byob.client.chat.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

/**
     * Cet {@link ImageBundle} est utilisé pour les icones de boutons
     */
    public interface ImagesChat extends ImageBundle {
    	public final static ImagesChat INSTANCE = (ImagesChat) GWT
    	.create(ImagesChat.class);
    	
        AbstractImagePrototype help();
    }