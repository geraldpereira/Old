package fr.byob.blog.client.model;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

import fr.byob.blog.client.IComment;
import fr.byob.blog.client.IPost;
/**
 * Cette classe d�crit le model d'un commentaire
 * @author akemi
 *
 */
public class CommentGWT implements IComment,IsSerializable{
	/**
	 * Le num�ro id du commentaire
	 */
	private int commentid;
	/**
	 * Le texte du commentaire
	 */
	private String commenttext;
	/**
	 * Le mail de celui qui �crit un comentaire
	 */
	private String commentmail;
	/**
	 * Le pseudo de celui qui �crit un commentaire
	 */
	private String commentautor;
	/**
	 * La date du commentaire
	 */
	private Date commentdate;
	/**
	 * L'article auquel le commentaire se rattache
	 */
	private IPost postid;

	private static final long serialVersionUID = 1L;
	/**
	 * Constructor
	 */
	public CommentGWT() {
		super();
	}
	/**
	 * retourne l'id du commentaire
	 * @return id
	 */
	public int getCommentid() {
		return this.commentid;
	}
	/**
	 * met � jour l'id
	 * @param commentid l'id 
	 */
	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}
	/**
	 * Retourn le texte du commentaire
	 * @return le texte
	 */
	public String getCommenttext() {
		return this.commenttext;
	}
	/**
	 * Met � jour le texte du commentaire
	 * @param commenttext le texte
	 */
	public void setCommenttext(String commenttext) {
		this.commenttext = commenttext;
	}
	/**
	 * retourne le mail de l'auteur
	 * @return mail
	 */
	public String getCommentmail() {
		return this.commentmail;
	}
	/**
	 * Met � jour le mail
	 * @param commentmail le mail
	 */
	public void setCommentmail(String commentmail) {
		this.commentmail = commentmail;
	}
	/**
	 * Retourne le nom de l'auteur
	 * @return auteur
	 */
	public String getCommentautor() {
		return this.commentautor;
	}
	/**
	 * Met � jour l'auteur
	 * @param commentautor l'auteur
	 */
	public void setCommentautor(String commentautor) {
		this.commentautor = commentautor;
	}
	/**
	 * Retourne l'id de l'article
	 * @return l'id 
	 */
	public IPost getPostid() {
		return this.postid;
	}
	/**
	 * Met � jour l'id de l'article
	 * @param postid id
	 */
	public void setPostid(IPost postid) {
		this.postid = postid;
	}
	/**
	 * Retourne la date de l'article
	 * @return la date
	 */
	public Date getCommentdate() {
		return commentdate;
	}
	/**
	 * Met � jour la date de l'article
	 * @param commentdate la date
	 */
	public void setCommentdate(Date commentdate) {
		this.commentdate = commentdate;
	}
	/**
     * Redéfinit la classe compareTo pour classer les articles suivants la date
     */
    public int compareTo(IComment arg0) {
        if(this.commentdate.after(arg0.getCommentdate())){
            return -1;
        }else if(this.commentdate.before(arg0.getCommentdate())){
            return 1;
        }else {
            return 0;
        }
    }
}
