package fr.byob.blog.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import fr.byob.blog.client.IUser;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.client.exception.ValidationException;

/**
 * Service permettant des actions sur les utilisateurs
 * @author akemi
 *
 */
public interface UserService extends RemoteService{
	/**
	 * Ajoute un utilisateur
	 * @param login son identifiant
	 * @param password son mot de passe
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public IUser addUser (IUser user) throws BlogException, ValidationException;
	/**
	 * Supprime un utilisateur par rapport à son id
	 * @param login l'id de l'utilisateur
	 * @throws BlogException
	 */
	public void removeUser(String login) throws BlogException;
	/**
	 * Retourne tous les utilisateurs de l'application
	 * @return tous les utilisateurs
	 * @throws BlogException
	 */
	public List<IUser> findUsers() throws BlogException;

	public List<IUser> findUsers(final int start,final int end ) throws BlogException;
	/**
	 * Met à jour l'utilisateur
	 * @param login identifiant
	 * @param password mot de passe
	 * @throws ValidationException 
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public IUser modifyUser(IUser user) throws BlogException, ValidationException;

	public int countUsers( ) throws BlogException;
	
	public IUser getConnectedUser();
    
	public void logout(final String statut);
}
