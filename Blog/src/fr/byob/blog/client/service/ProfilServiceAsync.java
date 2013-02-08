package fr.byob.blog.client.service;

import java.util.List;

import fr.byob.blog.client.IProfil;
import com.google.gwt.user.client.rpc.AsyncCallback;
/**
 * Service permettant des actions sur les articles
 * @author akemi
 *
 */
public interface ProfilServiceAsync{
	/**
	 * Ajoute un article
	 * @param post article à ajouter
	 * @throws BlogException
	 * @throws ValidationException
	 */
//	public IProfil addProfil(final IProfil profil) throws BlogException, ValidationException;
	/**
	 * Met à jour l'article
	 * @param post l'article
	 * @throws BlogException
	 * @throws ValidationException
	 */
	public void editProfil(final IProfil profil, AsyncCallback<IProfil> callback);
	/**
	 * Supprime un article
	 * @param post article
	 * @throws BlogException
	 */
//	public void removeProfil(final IProfil profil) throws BlogException;
	/**
	 * Supprime un article par rapport à son titre
	 * @param title le titre de l'article
	 * @throws BlogException
	 */
//	public void removeLinkByTitle(final String title) throws BlogException;
	/**
	 * Retourne tous les articles de l'application
	 * @return tous les articles
	 * @throws BlogException
	 */
	public void findAllProfils(AsyncCallback<List<IProfil>> callback);
	/**
	 * Retourne un article par rapport � son titre
	 * @param title le titer
	 * @return l'article trouv�
	 * @throws BlogException
	 */
//	public ILink findLink(final String title) throws BlogException;
	public void findProfils(final int start,final int end, AsyncCallback<List<IProfil>> callback );
    public void countProfils(AsyncCallback<Integer> callback );
    public void findProfil(final String userid, AsyncCallback<IProfil> callback);
    public void verifPassword(String oldPassword,String confOldPassword, AsyncCallback<Boolean> callback);
//    public List<IProfil> findUserLinks (final IUser user,final int start,final int end) throws BlogException;
}
