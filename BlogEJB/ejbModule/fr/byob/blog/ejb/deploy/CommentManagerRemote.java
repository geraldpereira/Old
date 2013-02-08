package fr.byob.blog.ejb.deploy;

import java.util.List;

import javax.ejb.Remote;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.entity.Comment;
import fr.byob.blog.ejb.entity.Post;
import fr.byob.client.exception.ValidationException;

@Remote
public interface CommentManagerRemote {
	
	public Comment addComment(final Comment comment) throws BlogException, ValidationException;

	public void removeComment(final int commentId) throws BlogException;

	public void removeAllComments(final Post post) throws BlogException;

	public List<Comment> findAllComments(final Post post) throws BlogException;
	
	public int countComments(final Post post) throws BlogException ;
	
	public List<Comment> findComments(final int postid, int start, int end)	throws BlogException; 
}
