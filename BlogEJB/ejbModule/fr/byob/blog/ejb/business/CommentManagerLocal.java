package fr.byob.blog.ejb.business;

import javax.ejb.Local;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.entity.Post;

@Local
public interface CommentManagerLocal {
	public void removeAllComments(final Post post) throws BlogException;
}
