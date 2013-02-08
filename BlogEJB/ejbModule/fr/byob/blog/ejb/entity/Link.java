package fr.byob.blog.ejb.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.ILink;
import fr.byob.blog.client.IUser;
import fr.byob.validator.annotation.NotEmpty;
import fr.byob.validator.annotation.Required;
import fr.byob.validator.annotation.Sized;
import fr.byob.validator.annotation.UrlFormatted;
import fr.byob.validator.annotation.Validable;

@Entity
@Validable
@Table(name="Link")
public class Link implements Serializable, ILink {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int linkid;

	@Required
	private String linktext;

	@Required
	@Sized(max = 100)
	private String linktitle;

	@UrlFormatted
    @Sized(max = 255)
    private String linkurl;
	
	@ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "userid")
    @Required
    private IUser userid;
	
	@NotEmpty
	@ManyToMany(targetEntity = Category.class, fetch = FetchType.EAGER)
	@JoinTable(joinColumns = @JoinColumn(name = "linkid"), inverseJoinColumns = @JoinColumn(name = "categoryid"))
	private Set<ICategory> categoryCollection;

	private static final long serialVersionUID = 1L;

	public Link() {
		super();
		categoryCollection = new HashSet<ICategory>();
	}

	public int getLinkid() {
		return this.linkid;
	}

	public void setLinkid(int linkid) {
		this.linkid = linkid;
	}

	public String getLinktext() {
		return this.linktext;
	}

	public void setLinktext(String linktext) {
		this.linktext = linktext;
	}

	public Set<ICategory> getCategoryCollection() {
		return this.categoryCollection;
	}

	public void setCategoryCollection(Set<ICategory> categoryCollection) {
		this.categoryCollection = categoryCollection;
	}

	public String getLinktitle() {
		return linktitle;
	}

	public void setLinktitle(String linktitle) {
		this.linktitle = linktitle;
	}
	public String getLinkurl() {
        return linkurl;
    }

    public void setLinkurl(String linkurl) {
        this.linkurl = linkurl;
    }
    public IUser getUserid() {
        return this.userid;
    }

    public void setUserid(IUser userid) {
        this.userid = userid;
    }
}
