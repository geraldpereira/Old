package fr.byob.blog.ejb.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.ILink;
import fr.byob.blog.client.IPost;
import fr.byob.validator.annotation.Required;
import fr.byob.validator.annotation.Sized;
import fr.byob.validator.annotation.Validable;

@Entity
@Validable
@Table(name="Category")
public class Category implements Serializable, ICategory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int categoryid;

	@Required
	@Sized(min=4,max=20)
	private String categorylabel;

	@ManyToMany(targetEntity=Post.class,mappedBy="categoryCollection",cascade={CascadeType.ALL},fetch=FetchType.EAGER)
	private Set<IPost> postCollection;
	
	@ManyToMany(targetEntity=Link.class,mappedBy="categoryCollection",cascade={CascadeType.ALL},fetch=FetchType.EAGER)
    private Set<ILink> linkCollection;

	private static final long serialVersionUID = 1L;

	public Category() {
		super();
		postCollection = new HashSet<IPost>();
	}

	public int getCategoryid() {
		return this.categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategorylabel() {
		return this.categorylabel;
	}

	public void setCategorylabel(String categorylabel) {
		this.categorylabel = categorylabel;
	}

	public Set<IPost> getPostCollection() {
		return this.postCollection;
	}

	public void setPostCollection(Set<IPost> postCollection) {
		this.postCollection = postCollection;
	}
	public Set<ILink> getLinkCollection() {
        return this.linkCollection;
    }

    public void setLinkCollection(Set<ILink> linkCollection) {
        this.linkCollection = linkCollection;
    }
}
