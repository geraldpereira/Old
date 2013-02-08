package fr.byob.blog.client;

import java.util.Date;

public interface IComment {
	public int getCommentid();

	public void setCommentid(int commentid);

	public String getCommenttext();

	public void setCommenttext(String commenttext);

	public String getCommentmail();

	public void setCommentmail(String commentmail);

	public String getCommentautor();

	public void setCommentautor(String commentautor);

	public IPost getPostid();

	public void setPostid(IPost postid);

	public Date getCommentdate();

	public void setCommentdate(Date commentdate);
}
