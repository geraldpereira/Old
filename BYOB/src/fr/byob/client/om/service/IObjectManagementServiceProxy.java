package fr.byob.client.om.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Proxy vers le service utilisé pour la gestion des objets
 * 
 * @author Kojiro
 *
 * @param <T> le type des objets
 */
public interface IObjectManagementServiceProxy<T> {
	/**
	 * Récupère la liste des objets
	 * @param callback
	 */
	public void findAllObjects(final AsyncCallback<List<T>> callback);
	/**
	 * Ajoute une objet
	 * @param object l'objet à ajouté
	 * @param callback
	 */
	public void addObject(final T object, final AsyncCallback<T> callback);
	/**
	 * Modifie un objet
	 * @param object l'objet mis à jour
	 * @param callback
	 */
	public void modifyObject(final T object, final AsyncCallback<T> callback);
	/**
	 * Supprime un objet
	 * @param object l'objet à supprimer
	 * @param callback
	 */
	public void removeObject(final T object, final AsyncCallback<?> callback);

	/**
	 * Retourne le service
	 * @return le service
	 */
	public Object getServiceAsync ();
}
