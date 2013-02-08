package fr.byob.blog.client;

import java.util.Date;
import java.util.Set;

public interface IPost {
	public int getPostid();

	public void setPostid(int postid);

	public Date getPostdate();

	public void setPostdate(Date postdate);
	
	public Date getPostlastupdate() ;

	public void setPostlastupdate(Date postlastupdate);
	
	public String getPosttitle();

	public void setPosttitle(String title);

	public String getPosttext();

	public void setPosttext(String posttext);

	public IUser getUserid();

	public void setUserid(IUser userid);

	public Set<IComment> getCommentCollection();

	public void setCommentCollection(Set<IComment> commentCollection);

	public Set<ICategory> getCategoryCollection();

	public void setCategoryCollection(Set<ICategory> categoryCollection);
	
	public boolean getPostisprivate();
	
	public void setPostisprivate(boolean postisprivate);
}
