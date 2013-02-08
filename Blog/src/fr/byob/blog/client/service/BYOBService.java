package fr.byob.blog.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.client.exception.ValidationException;

/**
 * Service permettant des actions sur les utilisateurs
 * @author akemi
 *
 */
public interface BYOBService extends RemoteService{
	/**
	 * Ajoute un utilisateur
	 * @param login son identifiant
	 * @param password son mot de passe
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public void createHTMLPost(final int id) throws BlogException;
	/**
	 * Supprime un utilisateur par rapport à son id
	 * @param login l'id de l'utilisateur
	 * @throws BlogException
	 */
	public void createHTMLUser(final String id) throws BlogException;
	/**
	 * Retourne tous les utilisateurs de l'application
	 * @return tous les utilisateurs
	 * @throws BlogException
	 */
	public void createHTMLCategory(final int id) throws BlogException;

	public void createHTMLLink(final int id) throws BlogException;
	public List<String> getAlbums();
	public List<String> getPhotos(String album);
	public void addPhotoAlbum(String photo,String album);
	public void addVideoAlbum(String video,String album);
	public void deleteMediaAlbum(String media);
	public void addAlbum(String album);
	public void deleteAlbum(String album);
	public boolean modifyAlbum(String oldDir,String newDir);
	public boolean modifyMedia(String oldName,String newName);
}
