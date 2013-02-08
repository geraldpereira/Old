package fr.byob.blog.client.model;

import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.ILink;
import fr.byob.blog.client.IUser;
/**
 * Cette classe d�crit le model d'une Article
 * @author akemi
 *
 */
public class LinkGWT implements ILink,IsSerializable{
	/**
	 * L'id de l'article
	 */
	private int linkid;
	/**
	 * Le texte de l'article
	 */
	private String linktext;
	/**
	 * Le titre de l'article
	 */
	private String linktitle;
	/**
     * Le titre de l'article
     */
    private String linkurl;
	/**
	 * L'utilisateur ayant �crit l'article
	 */
	private IUser userid;
	/**
	 * Les cat�gories auxquelles l'article appratient
	 */
	private Set<ICategory> categoryCollection;

	private static final long serialVersionUID = 1L;
	/**
	 * Constructeur
	 */
	public LinkGWT() {
		super();
	}
	/**
	 * Retourne l'id de l'article
	 * @return l'id
	 */
	public int getLinkid() {
		return this.linkid;
	}
	/**
	 * Met � jour l'id 
	 * @param postid le nouvel id
	 */
	public void setLinkid(int linkid) {
		this.linkid = linkid;
	}
	/**
	 * Retourne le texte de l'article
	 * @return le texte
	 */
	public String getLinktext() {
		return this.linktext;
	}
	/**
	 * Met � jour le texte
	 * @param posttext le nouveau texte
	 */
	public void setLinktext(String linktext) {
		this.linktext = linktext;
	}
	/**
	 * Retourne l'author de l'article
	 * @return l'auteur
	 */
	public IUser getUserid() {
		return this.userid;
	}
	/**
	 * Met � jour l'auteur 
	 * @param userid le nouvel auteur
	 */
	public void setUserid(IUser userid) {
		this.userid = userid;
	}
	/**
	 * Retourne les catories de l'article
	 * @return les cat�gories
	 */
	public Set<ICategory> getCategoryCollection() {
		return this.categoryCollection;
	}
	/**
	 * Met � jour les catgeories 
	 * @param categoryCollection les cat�gories
	 */
	public void setCategoryCollection(Set<ICategory> categoryCollection) {
		this.categoryCollection = categoryCollection;
	}
	/**
	 * Retourne le titre de l'article
	 * @return le titre
	 */
	public String getLinktitle() {
		return linktitle;
	}
	/**
	 * Met à jour le titre 
	 * @param posttitle le nouveau titre
	 */
	public void setLinktitle(String linktitle) {
		this.linktitle = linktitle;
	}
	/**
     * Retourne le titre de l'article
     * @return le titre
     */
    public String getLinkurl() {
        return linkurl;
    }
    /**
     * Met à jour le titre 
     * @param posttitle le nouveau titre
     */
    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }
}
