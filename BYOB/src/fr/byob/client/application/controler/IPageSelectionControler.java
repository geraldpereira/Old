package fr.byob.client.application.controler;

import com.google.gwt.event.dom.client.ClickHandler;

/**
 * Interface de controle de sélection de page (via un index dans un menu)
 * 
 * @author pereirag
 *
 */
public interface IPageSelectionControler {

	/**
	 * Retourne le click listener a appeller lors de la selection d'un menu
	 * @param menuIndex l'index du menu selectionné
	 * @return le click listener
	 */
	public ClickHandler getMenuSelectionControler(final int menuIndex);
}
