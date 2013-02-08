package fr.byob.blog.client;

import java.util.Set;


public interface ICategory {
	public int getCategoryid();

	public void setCategoryid(int categoryid);

	public String getCategorylabel();

	public void setCategorylabel(String categorylabel);
	
	public Set<IPost> getPostCollection();

	public void setPostCollection(Set<IPost> postCollection);
	
	public Set<ILink> getLinkCollection();

    public void setLinkCollection(Set<ILink> linkCollection);
	
}
