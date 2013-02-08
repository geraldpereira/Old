package fr.byob.blog.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import fr.byob.blog.client.ILink;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.client.exception.ValidationException;
/**
 * Service permettant des actions sur les articles
 * @author akemi
 *
 */
public interface LinkService extends RemoteService{
	/**
	 * Ajoute un article
	 * @param post article à ajouter
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public ILink addLink(final ILink link) throws BlogException, ValidationException;
	/**
	 * Met à jour l'article
	 * @param post l'article
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public ILink editLink(final ILink link) throws BlogException, ValidationException;
	/**
	 * Supprime un article
	 * @param post article
	 * @throws BlogException
	 */
	public void removeLink(final ILink link) throws BlogException;
	/**
	 * Supprime un article par rapport à son titre
	 * @param title le titre de l'article
	 * @throws BlogException
	 */
	public void removeLinkByTitle(final String title) throws BlogException;
	/**
	 * Retourne tous les articles de l'application
	 * @return tous les articles
	 * @throws BlogException
	 */
	public List<ILink> findAllLinks() throws BlogException;
	/**
	 * Retourne un article par rapport � son titre
	 * @param title le titer
	 * @return l'article trouv�
	 * @throws BlogException
	 */
	public ILink findLink(final String title) throws BlogException;
	public List<ILink> findLinks(final int start,final int end ) throws BlogException;
    public int countLinks( ) throws BlogException;
    public List<ILink> findUserLinks (final IUser user,final int start,final int end) throws BlogException;
}
