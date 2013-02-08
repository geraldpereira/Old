package fr.byob.blog.client.service;

import java.util.List;

import fr.byob.blog.client.ICategory;
import com.google.gwt.user.client.rpc.AsyncCallback;
/**
 * Service permettant des actions sur les cat�gories
 * @author akemi
 *
 */
public interface CategoryServiceAsync{
	/**
	 * Retourne toutes les catégories de l'application
	 * @return toutes les catégories
	 * @throws BlogException
	 */
	public void findAllCategories(AsyncCallback<List<ICategory>> callback);
	/**
	 * Ajoute une catégorie
	 * @param category catégorie à ajouter
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public void addCategory(final ICategory category, AsyncCallback<ICategory> callback);
	/**
	 * Met à jour la catégorie
	 * @param category la catégorie
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public void updateCategory(final ICategory category, AsyncCallback<ICategory> callback);
	/**
	 * Supprime une catégorie par rapport à son id
	 * @param categoryid l'id de la catégorie
	 * @throws BlogException
	 */
	public void removeCategory(final int categoryid, AsyncCallback<?> callback);
	/**
	 * Supprime une catégorie par rapport à son titre
	 * @param categoryLabel le titre de la catégorie
	 * @throws BlogException
	 */
	public void removeCategory(final String categoryLabel, AsyncCallback<?> callback);
	/**
	 * Retourne une catégorie par rapport à son titre
	 * @param label le titer
	 * @return la catgorie trouvée
	 * @throws BlogException
	 */
	public void findCategory(final String label, AsyncCallback<ICategory> callback);
	
	public void findCategories(final int start,final int end, AsyncCallback<List<ICategory>> callback );
    public void countCategories(AsyncCallback<Integer> callback );
}
