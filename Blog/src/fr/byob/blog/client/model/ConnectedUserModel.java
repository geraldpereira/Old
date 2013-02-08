package fr.byob.blog.client.model;

import java.util.ArrayList;
import java.util.List;

import fr.byob.blog.client.IConnectedUserListener;
import fr.byob.blog.client.IProfil;
import fr.byob.blog.client.IUser;

public class ConnectedUserModel {
	public static final int ROLE_ADMIN = 0;
	public static final int ROLE_GUEST = 1;
	public static final int ROLE_USER = 2;
	/**
	 * Liste des ilistener écoutant le model pour toutes modifications de celui ci
	 */
	private List<IConnectedUserListener> listeners;
	/**
	 * L'utilisateur connecté
	 */
	private IUser  connectedUser;
	
	private IProfil connectedUserProfil;

	private static ConnectedUserModel connectedUserModel = new ConnectedUserModel();
	/**
	 * Constructeur
	 */
	private ConnectedUserModel() {
		listeners = new ArrayList<IConnectedUserListener>();
	}

	public static ConnectedUserModel getInstance(){
		return connectedUserModel;
	}
	/**
	 * Ajout d'un listener au model
	 * @param listener le listener
	 */
	public void addListener(IConnectedUserListener listener) {
		this.listeners.add(listener);
	}
	/**
	 *  Avertit tous les listners que le model a été modifié
	 * @param loggedInUser l'utilisateur connecté
	 */
	public void logginUser(IUser loggedInUser) {
		this.connectedUser = loggedInUser;
		/*for (int i = 0; i < listeners.size(); i++) {
			((IConnectedUserListener) listeners.get(i)).connectedUserUpdate(connectedUser != null);
		}*/
	}
	
	public void logginUserProfil(IProfil loggedInUser) {
        this.connectedUserProfil = loggedInUser;
        for (int i = 0; i < listeners.size(); i++) {
            ((IConnectedUserListener) listeners.get(i)).connectedUserUpdate(connectedUser != null);
        }
    }
	/**
     * Retourne le profil de l'utilisateur connect�
     * @return
     */
    public IProfil getConnectedUserProfil() {
        return this.connectedUserProfil;
    }
	/**
	 * Retourne l'utilisateur connecté
	 * @return
	 */
	public IUser getConnectedUser() {
		return this.connectedUser;
	}
	
	/**
	 * Retourne le rôle de l'utilisateur connecté
	 * @return le rôle de l'utilisateur connecté
	 */
	public int getConnectedUserRole(){
		if(connectedUser == null){
			return ROLE_GUEST;
		}else{
			UserGWT user = (UserGWT)connectedUser;
			if(user.isAdmin()){
				return ROLE_ADMIN;
			}else {
				return ROLE_USER;
			}
		}
	}
}
