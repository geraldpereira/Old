package fr.byob.client.view.controler;

import com.google.gwt.event.dom.client.ClickHandler;

import fr.byob.client.view.model.IToolbarModel;


/**
 * Controleur utilisé dans le cadre des Managed Objects Les fonctionnalités
 * proposées sont à enrichir selon les besoins
 * 
 * @author Kojiro
 * 
 * @param <T>
 */
public interface IToolbarControler {
	// Mettre plutot cela : 	
	public ClickHandler createFirstPageListener();

	public ClickHandler createPrevPageListener();

    public ClickHandler createNextPageListener();

    public ClickHandler createLastPageListener();

	public void setToolbarModel (IToolbarModel toolbarModel);
}
