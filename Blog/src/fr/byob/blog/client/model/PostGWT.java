package fr.byob.blog.client.model;

import java.util.Date;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.IComment;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.IUser;
/**
 * Cette classe d�crit le model d'une Article
 * @author akemi
 *
 */
public class PostGWT implements IPost,IsSerializable{
	/**
	 * L'id de l'article
	 */
	private int postid;
	/**
	 * La date de l'article
	 */
	private Date postdate;
	
	private Date postlastupdate;
	/**
	 * Le texte de l'article
	 */
	private String posttext;
	/**
	 * Le titre de l'article
	 */
	private String posttitle;
	
	private boolean postisprivate;
	/**
	 * L'utilisateur ayant �crit l'article
	 */
	private IUser userid;
	/**
	 * Les commentaires relatifs � l'article
	 */
	private Set<IComment> commentCollection;
	/**
	 * Les cat�gories auxquelles l'article appratient
	 */
	private Set<ICategory> categoryCollection;

	private static final long serialVersionUID = 1L;
	/**
	 * Constructeur
	 */
	public PostGWT() {
		super();
	}
	/**
	 * Retourne l'id de l'article
	 * @return l'id
	 */
	public int getPostid() {
		return this.postid;
	}
	/**
	 * Met � jour l'id 
	 * @param postid le nouvel id
	 */
	public void setPostid(int postid) {
		this.postid = postid;
	}
	/**
	 * Retourne la date de l'article
	 * @return la date
	 */
	public Date getPostdate() {
		return this.postdate;
	}
	/**
	 * Met � jour la date 
	 * @param postdate le nouvelle date
	 */
	public void setPostdate(Date postdate) {
		this.postdate = postdate;
	}
	
	
	public Date getPostlastupdate() {
        return postlastupdate;
    }
    public void setPostlastupdate(Date postlastupdate) {
        this.postlastupdate = postlastupdate;
    }
    /**
	 * Retourne le texte de l'article
	 * @return le texte
	 */
	public String getPosttext() {
		return this.posttext;
	}
	/**
	 * Met � jour le texte
	 * @param posttext le nouveau texte
	 */
	public void setPosttext(String posttext) {
		this.posttext = posttext;
	}
	public boolean getPostisprivate(){
        return this.postisprivate;
    }
    
    public void setPostisprivate(boolean postisprivate){
        this.postisprivate = postisprivate;
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
	 * Retourne les commentaires attahc� � l'article
	 * @return les commentaires
	 */
	public Set<IComment> getCommentCollection() {
		return this.commentCollection;
	}
	/**
	 * Met � jour la liste de commentaires 
	 * @param commentCollection les commentaires
	 */
	public void setCommentCollection(Set<IComment> commentCollection) {
		this.commentCollection = commentCollection;
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
	public String getPosttitle() {
		return posttitle;
	}
	/**
	 * Met à jour le titre 
	 * @param posttitle le nouveau titre
	 */
	public void setPosttitle(String posttitle) {
		this.posttitle = posttitle;
	}
	/**
	 * Redéfinit la classe compareTo pour classer les articles suivants la date
	 */
	public int compareTo(IPost arg0) {
		if(this.postdate.after(arg0.getPostdate())){
			return -1;
		}else if(this.postdate.before(arg0.getPostdate())){
			return 1;
		}else {
			return 0;
		}
	}
	
}
