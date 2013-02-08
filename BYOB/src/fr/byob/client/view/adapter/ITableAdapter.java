package fr.byob.client.view.adapter;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.view.widget.TableWidget;

/**
 * Adapter pour un table.
 * 
 * Permet de passer d'une ligne de table a un OM et inverssement.
 * 
 * @author pereirag
 *
 * @param <T>
 */
public interface ITableAdapter<T> {
	/**
	 * Retourne les titres de la table (Entêtes de colonnes, pour affichage)
	 * 
	 * @return les titres de la table sous forme de liste de widget
	 */
	public List<Widget> getTableTitles();

	/**
	 * Retourne l'objet correspondant aux données présentes dans une ligne de
	 * table
	 * 
	 * @param table
	 *            la table source de données
	 * @param row
	 *            la ligne dans laquelle lire ces données
	 * @return l'objet métier correspondant
	 */
	public T getManagedObject(TableWidget<T> table, int row);

	/**
	 * Retourne un liste de widget permettant l'affichage d'un objet métier dans
	 * une table
	 * 
	 * @param object
	 *            L'objet source de données
	 * @return la liste de widgets
	 */
	public List<Widget> getTableWidgets(T object);

}
