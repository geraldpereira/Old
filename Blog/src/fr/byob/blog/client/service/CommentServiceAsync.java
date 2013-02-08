package fr.byob.blog.client.service;

import java.util.List;

import fr.byob.blog.client.IComment;
import fr.byob.blog.client.IPost;
import com.google.gwt.user.client.rpc.AsyncCallback;
/**
 * Service permettant des actions sur les commentaires
 * @author akemi
 *
 */
public interface CommentServiceAsync{
	/**
	 * Ajoute un commentaire
	 * @param comment commentaire � ajouter
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public void addComment(final IComment comment, AsyncCallback<IComment> callback);
	/**
	 * Supprime un commentaire
	 * @param comment le commentaire
	 * @throws BlogException
	 */
	public void removeComment(final IComment comment, AsyncCallback<?> callback);
	/**
	 * Supprime tous les commentaires d'un article
	 * @param post le post
	 * @throws BlogException
	 */
	public void removeAllComments(final IPost post, AsyncCallback<?> callback);
	/**
	 * Retourne tous les commentaires de l'application
	 * @return tous les commentaires
	 * @throws BlogException
	 */
	public void findAllComments(final IPost post, AsyncCallback<List<IComment>> callback);
	public void findComments(final IPost post,final int start,final int end, AsyncCallback<List<IComment>> callback );
    public void countComments(final IPost post, AsyncCallback<Integer> callback );
}
