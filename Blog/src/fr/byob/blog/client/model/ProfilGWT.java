package fr.byob.blog.client.model;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

import fr.byob.blog.client.IProfil;
import fr.byob.blog.client.IUser;
import fr.byob.client.chat.model.IChatUser;
/**
 * Cette classe d�crit le model d'une Article
 * @author akemi
 *
 */
public class ProfilGWT implements IProfil,IsSerializable,IChatUser{
	/**
	 * L'id de l'article
	 */
	private int profilid;
	/**
	 * Le texte de l'article
	 */
	private String profilcss;
	/**
	 * Le titre de l'article
	 */
	private Date profildate;
	
	private Date profilinscription;
	/**
     * Le titre de l'article
     */
    private String profilmail;
    
    private String profilphoto;
    
    private String profilpresentation;
    
    private String profilsite;
    
    private String profilstatut;
    
	/**
	 * L'utilisateur ayant �crit l'article
	 */
	private IUser userid;
	/**
	 * Constructeur
	 */
	public ProfilGWT() {
		super();
	}

    public String getProfilcss() {
        return profilcss;
    }

    public Date getProfildate() {
        return profildate;
    }

    public int getProfilid() {
        return profilid;
    }

    public String getProfilmail() {
        return profilmail;
    }

    public String getProfilphoto() {
        return profilphoto;
    }

    public String getProfilpresentation() {
        return profilpresentation;
    }

    public IUser getUserid() {
        return userid;
    }

    public void setProfilcss(String profilcss) {
       this.profilcss = profilcss;
    }

    public void setProfildate(Date profildate) {
        this.profildate = profildate;
    }

    public void setProfilid(int profilid) {
        this.profilid = profilid;
    }

    public void setProfilmail(String profilmail) {
        this.profilmail = profilmail;
    }

    public void setProfilphoto(String profilphoto) {
        this.profilphoto = profilphoto;
    }

    public void setProfilpresentation(String profilpresentation) {
        this.profilpresentation = profilpresentation;
    }

    public void setUserid(IUser userid) {
        this.userid = userid;
    }

    public Date getProfilinscription() {
        return profilinscription;
    }

    public void setProfilinscription(Date profilinscription) {
        this.profilinscription = profilinscription;
    }

    public String getProfilsite() {
        return profilsite;
    }

    public void setProfilsite(String profilsite) {
       this.profilsite = profilsite;
    }
    
    public String getProfilstatut() {
        return profilstatut;
    }

    public void setProfilstatut(String profilstatut) {
        this.profilstatut = profilstatut;
    }
    
    public String toString() {
        return getUserid().getUserid();
    }

    public String getName() {
        return getUserid().getUserid();
    }

    public String getStatusMessage() {
        return profilstatut;
    }

    public void setStatusMessage(String statusMessage) {
        this.profilstatut = statusMessage;
    }

    public boolean equals(Object obj) {
        if (obj instanceof IChatUser) {
            IChatUser user = (IChatUser) obj;
            return user.getName().equals(this.getName());
        }
        return false;
    }

    public int hashCode() {
        return this.getName().hashCode();
    }
}
