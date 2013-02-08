package fr.byob.blog.client.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.RemoteService;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.client.exception.ValidationException;
/**
 * Service permettant des actions sur les articles
 * @author akemi
 *
 */
public interface PostService extends RemoteService{
	/**
	 * Ajoute un article
	 * @param post article à ajouter
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public IPost addPost(final IPost post) throws BlogException, ValidationException;
	/**
	 * Met à jour l'article
	 * @param post l'article
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public IPost editPost(final IPost post) throws BlogException, ValidationException;
	/**
	 * Supprime un article
	 * @param post article
	 * @throws BlogException
	 */
	public void removePost(final IPost post) throws BlogException;
	/**
	 * Supprime un article par rapport à son titre
	 * @param title le titre de l'article
	 * @throws BlogException
	 */
	public void removePostByTitle(final String title) throws BlogException;
	/**
	 * Retourne tous les articles de l'application
	 * @return tous les articles
	 * @throws BlogException
	 */
	public List<IPost> findAllPosts() throws BlogException;
	/**
	 * Retourne un article par rapport � son titre
	 * @param title le titer
	 * @return l'article trouv�
	 * @throws BlogException
	 */
	public IPost findPost(final String title) throws BlogException;
	public List<IPost> findPosts(final int start,final int end ) throws BlogException;
	public List<IPost> findPostsByCriteria (final Integer startIndice,final Integer endIndice, final Set<IUser> authors,final Set<ICategory> categories, final String text,final String title, final Date start, final Date end) throws BlogException;
	public int countPostsByCriteria (final Integer startIndice,final Integer endIndice, final Set<IUser> authors,final Set<ICategory> categories, final String text,final String title, final Date start, final Date end) throws BlogException;
    public int countPosts( ) throws BlogException;
    public int countUserPosts(final IUser user) throws BlogException;
    public List<IPost> findUserPosts (final IUser user,final int start,final int end) throws BlogException;
    public IPost findPostId(final int postid) throws BlogException;
}
