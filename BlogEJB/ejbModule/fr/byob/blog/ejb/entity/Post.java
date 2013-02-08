package fr.byob.blog.ejb.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.IComment;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.IUser;
import fr.byob.validator.annotation.NotEmpty;
import fr.byob.validator.annotation.Required;
import fr.byob.validator.annotation.Sized;
import fr.byob.validator.annotation.Validable;

@Entity
@Validable
@Table(name="Post")
public class Post implements Serializable, IPost {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int postid;

	@Required
	private Date postdate;

	@Required
	private Date postlastupdate;
	
	@Required
	private String posttext;
	
	@Required
	private boolean postisprivate;

	@Required
	@Sized(max = 100)
	private String posttitle;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "userid")
	@Required
	private IUser userid;

	@OneToMany(targetEntity = Comment.class, mappedBy = "postid", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private Set<IComment> commentCollection;

	@NotEmpty
	@ManyToMany(targetEntity = Category.class, fetch = FetchType.EAGER)
	@JoinTable(joinColumns = @JoinColumn(name = "postid"), inverseJoinColumns = @JoinColumn(name = "categoryid"))
	private Set<ICategory> categoryCollection;
	
	private static final long serialVersionUID = 1L;

	public Post() {
		super();
		commentCollection = new HashSet<IComment>();
		categoryCollection = new HashSet<ICategory>();
	}

	public int getPostid() {
		return this.postid;
	}

	public void setPostid(int postid) {
		this.postid = postid;
	}

	public Date getPostdate() {
		return this.postdate;
	}

	public void setPostdate(Date postdate) {
		this.postdate = postdate;
	}

	public Date getPostlastupdate() {
		return postlastupdate;
	}

	public void setPostlastupdate(Date postlastupdate) {
		this.postlastupdate = postlastupdate;
	}

	public String getPosttext() {
		return this.posttext;
	}

	public void setPosttext(String posttext) {
		this.posttext = posttext;
	}

	public boolean getPostisprivate(){
	    return this.postisprivate;
	}
    
    public void setPostisprivate(boolean postisprivate){
        this.postisprivate = postisprivate;
    }
	
	public IUser getUserid() {
		return this.userid;
	}

	public void setUserid(IUser userid) {
		this.userid = userid;
	}

	public Set<IComment> getCommentCollection() {
		return this.commentCollection;
	}

	public void setCommentCollection(Set<IComment> commentCollection) {
		this.commentCollection = commentCollection;
	}

	public Set<ICategory> getCategoryCollection() {
		return this.categoryCollection;
	}

	public void setCategoryCollection(Set<ICategory> categoryCollection) {
		this.categoryCollection = categoryCollection;
	}

	public String getPosttitle() {
		return posttitle;
	}

	public void setPosttitle(String posttitle) {
		this.posttitle = posttitle;
	}

}
