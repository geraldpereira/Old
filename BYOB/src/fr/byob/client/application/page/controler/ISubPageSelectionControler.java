package fr.byob.client.application.page.controler;

import com.google.gwt.event.dom.client.ClickHandler;

/**
 * Interface du controleur de sélection de sous page. Ce controleur est utilisé
 * dans les cadre d'une DeckPage.
 */
public interface ISubPageSelectionControler {

	/**
	 * Retourne un tableListener. Il doit écouter touts sélection d'une table
	 * d'objet et afficher la sous page correspondante.
	 * 
	 * Par exemple pour le cas des articles : on utilisera ce Listener pour
	 * afficher un article détaillé et ses commentaires lorsqu'un utilisateur clic sur
	 * un article de la page de news ou d'archives (contenus alors dans un TableWidget)
	 * 
	 * @param subPageIndex
	 *            Index de la sous page
	 * @return le table Listener
	 */
	public ClickHandler getSubPageSelectionControler(final int subPageIndex);
}
