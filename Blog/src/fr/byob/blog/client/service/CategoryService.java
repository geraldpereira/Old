package fr.byob.blog.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.client.exception.ValidationException;
/**
 * Service permettant des actions sur les cat�gories
 * @author akemi
 *
 */
public interface CategoryService extends RemoteService{
	/**
	 * Retourne toutes les catégories de l'application
	 * @return toutes les catégories
	 * @throws BlogException
	 */
	public List<ICategory> findAllCategories() throws BlogException;
	/**
	 * Ajoute une catégorie
	 * @param category catégorie à ajouter
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public ICategory addCategory(final ICategory category) throws BlogException, ValidationException;
	/**
	 * Met à jour la catégorie
	 * @param category la catégorie
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public ICategory updateCategory(final ICategory category) throws BlogException, ValidationException;
	/**
	 * Supprime une catégorie par rapport à son id
	 * @param categoryid l'id de la catégorie
	 * @throws BlogException
	 */
	public void removeCategory(final int categoryid)  throws BlogException;
	/**
	 * Supprime une catégorie par rapport à son titre
	 * @param categoryLabel le titre de la catégorie
	 * @throws BlogException
	 */
	public void removeCategory(final String categoryLabel)  throws BlogException;
	/**
	 * Retourne une catégorie par rapport à son titre
	 * @param label le titer
	 * @return la catgorie trouvée
	 * @throws BlogException
	 */
	public ICategory findCategory(final String label) throws BlogException;
	
	public List<ICategory> findCategories(final int start,final int end ) throws BlogException;
    public int countCategories( ) throws BlogException;
}
