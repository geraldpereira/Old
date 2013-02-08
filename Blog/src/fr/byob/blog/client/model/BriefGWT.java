package fr.byob.blog.client.model;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Cette classe décrit le model d'une brève
 * 
 * @author akemi
 * 
 */
public class BriefGWT implements IsSerializable {

    private int briefid;
    
    private String userid;
    
    private Date date;
    private String text;


    public int getBriefid() {
        return briefid;
    }

    public void setBriefid(int briefid) {
        this.briefid = briefid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * Constructuer
     */
    public BriefGWT() {
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


    public String toString() {
        return getUserid()+" / "+text;
    }

}
