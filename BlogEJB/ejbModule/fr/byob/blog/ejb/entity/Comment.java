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

import fr.byob.blog.client.IComment;
import fr.byob.blog.client.IPost;
import fr.byob.validator.annotation.MailFormatted;
import fr.byob.validator.annotation.Required;
import fr.byob.validator.annotation.Sized;
import fr.byob.validator.annotation.Validable;


@Entity
@Validable
@Table(name="Comment")
public class Comment implements Serializable, IComment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int commentid;

	@Required
	private String commenttext;

	@MailFormatted
	@Sized(max=40)
	@Required
	private String commentmail;

	@Required
	@Sized(max=12)
	private String commentautor;
	
	@Required
	private Date commentdate;

	@Required
	@ManyToOne(targetEntity=Post.class)
	@JoinColumn(name="postid")
	private IPost postid;

	private static final long serialVersionUID = 1L;

	public Comment() {
		super();
	}

	public int getCommentid() {
		return this.commentid;
	}

	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}

	public String getCommenttext() {
		return this.commenttext;
	}

	public void setCommenttext(String commenttext) {
		this.commenttext = commenttext;
	}

	public String getCommentmail() {
		return this.commentmail;
	}

	public void setCommentmail(String commentmail) {
		this.commentmail = commentmail;
	}

	public String getCommentautor() {
		return this.commentautor;
	}

	public void setCommentautor(String commentautor) {
		this.commentautor = commentautor;
	}

	public IPost getPostid() {
		return this.postid;
	}

	public void setPostid(IPost postid) {
		this.postid = postid;
	}

	public Date getCommentdate() {
		return commentdate;
	}

	public void setCommentdate(Date commentdate) {
		this.commentdate = commentdate;
	}

}
