package fr.byob.blog.client.service;

import java.util.List;

import fr.byob.blog.client.IUser;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Service permettant des actions sur les utilisateurs
 * @author akemi
 *
 */
public interface UserServiceAsync{
	/**
	 * Ajoute un utilisateur
	 * @param login son identifiant
	 * @param password son mot de passe
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public void addUser (IUser user, AsyncCallback<IUser> callback);
	/**
	 * Supprime un utilisateur par rapport à son id
	 * @param login l'id de l'utilisateur
	 * @throws BlogException
	 */
	public void removeUser(String login, AsyncCallback<?> callback);
	/**
	 * Retourne tous les utilisateurs de l'application
	 * @return tous les utilisateurs
	 * @throws BlogException
	 */
	public void findUsers(AsyncCallback<List<IUser>> callback);

	public void findUsers(final int start,final int end, AsyncCallback<List<IUser>> callback );
	/**
	 * Met à jour l'utilisateur
	 * @param login identifiant
	 * @param password mot de passe
	 * @throws ValidationException 
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public void modifyUser(IUser user, AsyncCallback<IUser> callback);

	public void countUsers(AsyncCallback<Integer> callback );
	
	public void getConnectedUser(AsyncCallback<IUser> callback);
    
	public void logout(final String statut, AsyncCallback<?> callback);
}
