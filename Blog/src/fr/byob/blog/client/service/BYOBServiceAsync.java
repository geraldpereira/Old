package fr.byob.blog.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Service permettant des actions sur les utilisateurs
 * @author akemi
 *
 */
public interface BYOBServiceAsync{
	/**
	 * Ajoute un utilisateur
	 * @param login son identifiant
	 * @param password son mot de passe
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public void createHTMLPost(final int id, AsyncCallback<?> callback);
	/**
	 * Supprime un utilisateur par rapport à son id
	 * @param login l'id de l'utilisateur
	 * @throws BlogException
	 */
	public void createHTMLUser(final String id, AsyncCallback<?> callback);
	/**
	 * Retourne tous les utilisateurs de l'application
	 * @return tous les utilisateurs
	 * @throws BlogException
	 */
	public void createHTMLCategory(final int id, AsyncCallback<?> callback);

	public void createHTMLLink(final int id, AsyncCallback<?> callback);
	public void getAlbums(AsyncCallback<List<String>> callback);
	public void getPhotos(String album, AsyncCallback<List<String>> callback);
	public void addPhotoAlbum(String photo,String album, AsyncCallback<?> callback);
	public void addVideoAlbum(String video,String album, AsyncCallback<?> callback);
	public void deleteMediaAlbum(String media, AsyncCallback<?> callback);
	public void addAlbum(String album, AsyncCallback<?> callback);
	public void deleteAlbum(String album, AsyncCallback<?> callback);
	public void modifyAlbum(String oldDir,String newDir, AsyncCallback<Boolean> callback);
	public void modifyMedia(String oldName,String newName, AsyncCallback<Boolean> callback);
}
