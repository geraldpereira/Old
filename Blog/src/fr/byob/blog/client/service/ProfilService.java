package fr.byob.blog.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;

import fr.byob.blog.client.IProfil;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.client.exception.ValidationException;
/**
 * Service permettant des actions sur les articles
 * @author akemi
 *
 */
public interface ProfilService extends RemoteService{
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
	public IProfil editProfil(final IProfil profil) throws BlogException, ValidationException;
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
	public List<IProfil> findAllProfils() throws BlogException;
	/**
	 * Retourne un article par rapport � son titre
	 * @param title le titer
	 * @return l'article trouv�
	 * @throws BlogException
	 */
//	public ILink findLink(final String title) throws BlogException;
	public List<IProfil> findProfils(final int start,final int end ) throws BlogException;
    public int countProfils( ) throws BlogException;
    public IProfil findProfil(final String userid) throws BlogException;
    public boolean verifPassword(String oldPassword,String confOldPassword)throws BlogException, ValidationException;
//    public List<IProfil> findUserLinks (final IUser user,final int start,final int end) throws BlogException;
}
