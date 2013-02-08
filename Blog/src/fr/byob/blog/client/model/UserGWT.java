package fr.byob.blog.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;

import fr.byob.blog.client.IUser;

/**
 * Cette classe décrit le model d'un utilisateur
 * 
 * @author akemi
 * 
 */
public class UserGWT implements IUser, IsSerializable {

    /**
     * Le login de l'utilisateur
     */
    private String userid;
    /**
     * Le mot de passe
     */
    private String password;

    private boolean admin;

    /**
     * Constructuer
     */
    public UserGWT() {
        super();
    }

    /**
     * Retourne le login de l'utlisateur
     * 
     * @return le login
     */
    public String getUserid() {
        return this.userid;
    }

    /**
     * Met à jour le login
     * 
     * @param userid
     *            le login
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * Retourne le mot de passe de l'utlisateur
     * 
     * @return le mot de passe
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Met à jour le mot de passe
     * 
     * @param password
     *            le mot de passe
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String toString() {
        return getUserid();
    }

}
