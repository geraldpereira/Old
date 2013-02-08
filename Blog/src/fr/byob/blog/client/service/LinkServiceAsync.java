package fr.byob.blog.client.service;

import java.util.List;

import fr.byob.blog.client.ILink;
import fr.byob.blog.client.IUser;
import com.google.gwt.user.client.rpc.AsyncCallback;
/**
 * Service permettant des actions sur les articles
 * @author akemi
 *
 */
public interface LinkServiceAsync{
	/**
	 * Ajoute un article
	 * @param post article à ajouter
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public void addLink(final ILink link, AsyncCallback<ILink> callback);
	/**
	 * Met à jour l'article
	 * @param post l'article
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public void editLink(final ILink link, AsyncCallback<ILink> callback);
	/**
	 * Supprime un article
	 * @param post article
	 * @throws BlogException
	 */
	public void removeLink(final ILink link, AsyncCallback<?> callback);
	/**
	 * Supprime un article par rapport à son titre
	 * @param title le titre de l'article
	 * @throws BlogException
	 */
	public void removeLinkByTitle(final String title, AsyncCallback<?> callback);
	/**
	 * Retourne tous les articles de l'application
	 * @return tous les articles
	 * @throws BlogException
	 */
	public void findAllLinks(AsyncCallback<List<ILink>> callback);
	/**
	 * Retourne un article par rapport � son titre
	 * @param title le titer
	 * @return l'article trouv�
	 * @throws BlogException
	 */
	public void findLink(final String title, AsyncCallback<ILink> callback);
	public void findLinks(final int start,final int end, AsyncCallback<List<ILink>> callback );
    public void countLinks(AsyncCallback<Integer> callback );
    public void findUserLinks (final IUser user,final int start,final int end, AsyncCallback<List<ILink>> callback);
}
