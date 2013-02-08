package fr.byob.client.application.page.view;

import com.google.gwt.user.client.ui.Widget;

/**
 * Interface Page Un page est composée d'un center widget, possède un titre, et
 * doit pouvoir être prévenue de sont chargement (pour affichage)
 */
public interface IPage {
	/**
	 * Retourne le widget central affiché
	 * @return le widget central affiché
	 */
	public Widget getCenterWidget();

	
	/**
	 * Retourne le titre de la page
	 * @return le titre de la page
	 */
	public String getPageTitle();

	/**
	 * Préviens la page qu'elle est affichée
	 */
	public void chargedPage();
}
