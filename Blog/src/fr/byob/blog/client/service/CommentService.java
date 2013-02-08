package fr.byob.blog.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import fr.byob.blog.client.IComment;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.client.exception.ValidationException;
/**
 * Service permettant des actions sur les commentaires
 * @author akemi
 *
 */
public interface CommentService extends RemoteService{
	/**
	 * Ajoute un commentaire
	 * @param comment commentaire � ajouter
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public IComment addComment(final IComment comment) throws BlogException, ValidationException;
	/**
	 * Supprime un commentaire
	 * @param comment le commentaire
	 * @throws BlogException
	 */
	public void removeComment(final IComment comment) throws BlogException;
	/**
	 * Supprime tous les commentaires d'un article
	 * @param post le post
	 * @throws BlogException
	 */
	public void removeAllComments(final IPost post) throws BlogException;
	/**
	 * Retourne tous les commentaires de l'application
	 * @return tous les commentaires
	 * @throws BlogException
	 */
	public List<IComment> findAllComments(final IPost post) throws BlogException;
	public List<IComment> findComments(final IPost post,final int start,final int end ) throws BlogException;
    public int countComments(final IPost post ) throws BlogException;
}
