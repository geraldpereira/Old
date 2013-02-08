package fr.byob.client.view.model;

import java.util.List;

import fr.byob.client.IListener;

/**
 * Model de données contenant des managed object.
 * 
 * @author pereirag
 *
 * @param <T> Le type des objets gérés
 */
public interface ITableModel<T> {

	public final static int NO_OPERATION = -1;
	public final static int MODIFY_OPERATION = 0;
	public final static int ADD_OPERATION = 1;
	public final static int DELETE_OPERATION = 2;
	public final static int REFRESH_OPERATION = 3;

	public final static int UNSELECTED_INDEX = -3;
	public final static int NEW_SELECTED_INDEX = -2;
	
	/**
	 * Met à jour la liste d'objets du model
	 * @param result la nouvelle liste d'objets du model
	 */
	public void setObjects(final List<T> list);

	/**
	 * REtourne la liste d'objets du model
	 * @return
	 */
	public List<T> getObjects();
	
	/**
	 * Sélectionne un objets particulier du model
	 * @param index l'index dans la liste de l'objet
	 */
	public void selectObject (final int index);
	
	/**
	 * Retourne l'objet couramment sélectionné ou un des index particulier si aucun n'est sélectionné
	 * @return l'objet couramment sélectionné ou un des index particulier si aucun n'est sélectionné
	 */
	public int getSelectedObjectIndex();
	
	/**
	 * Ajoute un objet en fin de liste
	 * @param object l'objet à ajouter
	 */
	public void addObject (final T object);

	public T getAddedObject();
	/**
	 * Modifie l'objet courament sélectionné 
	 * @param object le nouvelle objet
	 */
	public void modifyObject (final T object);

	/**
	 * Supprime l'objet courament sélectionné
	 */
	public void deleteObject ();
	
	/**
	 * Retourne la dernière opération
	 * @return la dernière opération
	 */
	public int getLastOperation ();

	/**
	 * Retourne l'objet sélectionné
	 * @return
	 */
	public T getSelectedObject();
	
	/**
	 * Ajoute une listener
	 * @param listener
	 */
	public void addListener(final IListener listener);
	/**
	 * Supprime un listener
	 * @param listener
	 */
	public void removeListener(final IListener listener);

	public boolean isNotEmpty();
	
	public int getOffset();
	
}
