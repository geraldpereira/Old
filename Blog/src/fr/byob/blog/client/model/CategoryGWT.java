package fr.byob.blog.client.model;

import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.ILink;
import fr.byob.blog.client.IPost;
/**
 * Cette classe décrit le model d'une Categorie
 * @author akemi
 *
 */
public class CategoryGWT implements ICategory, IsSerializable{
	/**
	 * Le numéro id de la catégorie
	 */
	private int categoryid;
	/**
	 * Le label de la catégorie
	 */
	private String categorylabel;
	/**
	 * Les posts contenues dans cette catégorie
	 */
	private Set<IPost> postCollection;
	/**
     * Les links contenues dans cette catégorie
     */
    private Set<ILink> linkCollection;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Le constructor
	 */
	public CategoryGWT() {
		super();
	}
	/**
	 * Retourne l'id de la catégorie
	 * @return l'id
	 */
	public int getCategoryid() {
		return this.categoryid;
	}
	/**
	 * Met à jour l'id 
	 * @param categoryid le nouvel id
	 */
	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}
	/**
	 * Retourne le label
	 * @return le label
	 */
	public String getCategorylabel() {
		return this.categorylabel;
	}
	/**
	 * Met à jour le label
	 * @param categorylabel le label
	 */
	public void setCategorylabel(String categorylabel) {
		this.categorylabel = categorylabel;
	}
	/**
	 * Retourne les Link de la catégorie 
	 * @return
	 */
	public Set<ILink> getLinkCollection() {
		return this.linkCollection;
	}
	/**
	 * Met à jour les articles de la catégorie
	 * @param LinkCollection
	 */
	public void setLinkCollection(Set<ILink> linkCollection) {
		this.linkCollection = linkCollection;
	}
	/**
     * Retourne les articles de la catégorie 
     * @return
     */
    public Set<IPost> getPostCollection() {
        return this.postCollection;
    }
    /**
     * Met à jour les articles de la catégorie
     * @param postCollection
     */
    public void setPostCollection(Set<IPost> postCollection) {
        this.postCollection = postCollection;
    }
	public String toString(){
	    return getCategorylabel();
	}
	public boolean equals (Object object){
	    if (!(object instanceof CategoryGWT)){
	        return false;
	    }
	    CategoryGWT cat = (CategoryGWT) object; 
	    return cat.getCategoryid() == this.getCategoryid();
	}
	public int hashCode(){
        return new Integer(categoryid).hashCode();
	}
}
