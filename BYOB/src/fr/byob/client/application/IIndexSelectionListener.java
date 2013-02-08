package fr.byob.client.application;
/**
 * Interface permettant d'écouter le Model de sélection d'index
 * @author Akemi
 *
 */
public interface IIndexSelectionListener {
	
	/**
	 * Méthode appellée lors de la sélection d'un index
	 * @param oldIndex l'ancien index
	 * @param newIndex le nouvel index
	 */
    public void indexSelected(int oldIndex, int newIndex);
}
