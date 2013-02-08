package fr.byob.blog.ejb.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.byob.blog.client.IProfil;
import fr.byob.blog.client.IUser;
import fr.byob.validator.annotation.MailFormatted;
import fr.byob.validator.annotation.Required;
import fr.byob.validator.annotation.Sized;
import fr.byob.validator.annotation.UrlFormatted;
import fr.byob.validator.annotation.Validable;

@Entity
@Validable
@Table(name="Profil")
public class Profil implements Serializable, IProfil {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int profilid;
    
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userid")
    @Required
    private IUser userid;
    
    @MailFormatted
    @Sized(max=40)
    @Required
    private String profilmail;
    
    @UrlFormatted
    @Sized(max=250)
    private String profilsite;
    
    @Required
    private Date profildate;
    
    @Required
    private Date profilinscription;

    private String profilpresentation;
    
    private String profilphoto;
    
    @Sized(max=20)
    @Required
    private String profilcss;
    
    @Sized(max=250)
    private String profilstatut;
    
    //TODO abonnement Catégorie + post ou tout
    
    public int getProfilid() {
        return profilid;
    }

    public void setProfilid(int profilid) {
        this.profilid = profilid;
    }

    public IUser getUserid() {
        return userid;
    }

    public void setUserid(IUser userid) {
        this.userid = userid;
    }

    public String getProfilmail() {
        return profilmail;
    }

    public void setProfilmail(String profilmail) {
        this.profilmail = profilmail;
    }

    public Date getProfildate() {
        return profildate;
    }

    public void setProfildate(Date profildate) {
        this.profildate = profildate;
    }

    public String getProfilpresentation() {
        return profilpresentation;
    }

    public void setProfilpresentation(String profilpresentation) {
        this.profilpresentation = profilpresentation;
    }

    public String getProfilphoto() {
        return profilphoto;
    }

    public void setProfilphoto(String profilphoto) {
        this.profilphoto = profilphoto;
    }

    public String getProfilcss() {
        return profilcss;
    }

    public void setProfilcss(String profilcss) {
        this.profilcss = profilcss;
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
    
    
}
