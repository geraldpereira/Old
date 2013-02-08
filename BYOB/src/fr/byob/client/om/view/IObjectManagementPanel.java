package fr.byob.client.om.view;

import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.view.widget.ConsoleWidget;
import fr.byob.client.view.widget.IFormWidget;
import fr.byob.client.view.widget.TableWidget;

/**
 * Panel d'affichage des objets managés
 * Il est composé au plus d'une table, d'un form et d'une console.
 *  
 * 
 * @author pereirag
 *
 * @param <T>
 */
public interface IObjectManagementPanel<T> {

	/**
	 * Retourne la table
	 * @return la table
	 */
	public TableWidget<T> getTable();
	
	/**
	 * Retourne le formulaire
	 * @return le formulaire
	 */
	public IFormWidget<T> getForm();
	
	/**
	 * Retourne la console
	 * @return la console
	 */
	public ConsoleWidget getConsole ();
	
	/**
	 * Initialise la vue
	 */
	public void initView();
	/**
	 * Affecte le stylename
	 * @param style
	 */
	public void setStyleName(String style);
	/**
	 * Supprime le style name
	 * @param style
	 */
	public void removeStyleName(String style);
	/**
	 * Affiche (ou pas) la table. Utilisé par exemple pour masquer une table vide !
	 * @param isVisible
	 */
	public void setVisibleTable(boolean isVisible);
	public void setVisibleAll(boolean isVisible);
	public void addWidget(Widget w);
	public void removeWidget(Widget w);
}
