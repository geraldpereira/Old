package fr.byob.blog.client;

import java.util.Set;

public interface ILink {
	public int getLinkid();

	public void setLinkid(int linkid);

	public String getLinktitle();

	public void setLinktitle(String title);

	public String getLinktext();

	public void setLinktext(String posttext);

	public String getLinkurl();

	public void setLinkurl(String url);
	
	public IUser getUserid();
	
	public void setUserid(IUser userid);

	public Set<ICategory> getCategoryCollection();

	public void setCategoryCollection(Set<ICategory> categoryCollection);

}
