package fr.byob.blog.client.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.IUser;
import com.google.gwt.user.client.rpc.AsyncCallback;
/**
 * Service permettant des actions sur les articles
 * @author akemi
 *
 */
public interface PostServiceAsync{
	/**
	 * Ajoute un article
	 * @param post article à ajouter
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public void addPost(final IPost post, AsyncCallback<IPost> callback);
	/**
	 * Met à jour l'article
	 * @param post l'article
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public void editPost(final IPost post, AsyncCallback<IPost> callback);
	/**
	 * Supprime un article
	 * @param post article
	 * @throws BlogException
	 */
	public void removePost(final IPost post, AsyncCallback<?> callback);
	/**
	 * Supprime un article par rapport à son titre
	 * @param title le titre de l'article
	 * @throws BlogException
	 */
	public void removePostByTitle(final String title, AsyncCallback<?> callback);
	/**
	 * Retourne tous les articles de l'application
	 * @return tous les articles
	 * @throws BlogException
	 */
	public void findAllPosts(AsyncCallback<List<IPost>> callback);
	/**
	 * Retourne un article par rapport � son titre
	 * @param title le titer
	 * @return l'article trouv�
	 * @throws BlogException
	 */
	public void findPost(final String title, AsyncCallback<IPost> callback);
	public void findPosts(final int start,final int end, AsyncCallback<List<IPost>> callback );
	public void findPostsByCriteria (final Integer startIndice,final Integer endIndice, final Set<IUser> authors,final Set<ICategory> categories, final String text,final String title, final Date start, final Date end, AsyncCallback<List<IPost>> callback);
	public void countPostsByCriteria (final Integer startIndice,final Integer endIndice, final Set<IUser> authors,final Set<ICategory> categories, final String text,final String title, final Date start, final Date end, AsyncCallback<Integer> callback);
    public void countPosts(AsyncCallback<Integer> callback );
    public void countUserPosts(final IUser user, AsyncCallback<Integer> callback);
    public void findUserPosts (final IUser user,final int start,final int end, AsyncCallback<List<IPost>> callback);
    public void findPostId(final int postid, AsyncCallback<IPost> callback);
}
