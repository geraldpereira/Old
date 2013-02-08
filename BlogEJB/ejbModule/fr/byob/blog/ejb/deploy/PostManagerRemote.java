package fr.byob.blog.ejb.deploy;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.entity.Post;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.ejbql.ICriteria;

@Remote
public interface PostManagerRemote {

	public final static String USER_CRITERIA = "userid";
	public final static String TEXT_CRITERIA = "posttext";
	public final static String TITLE_CRITERIA = "posttitle";
	public final static String CATEGORY_CRITERIA = "categoryCollection";
	public final static String DATE_CRITERIA = "postdate";
	public final static String PRIVATE_CRITERIA = "postisprivate";
	
	public Post addPost(final Post post) throws BlogException, ValidationException;

	public Post editPost(final Post post) throws BlogException, ValidationException;

	public void removePost(final int postId) throws BlogException;

	public List<Post> findAllPosts() throws BlogException;
	
	public List<Post> findAllPostsNotPrivate() throws BlogException ;

	public Post findPost(final String title) throws BlogException;
	
	public List<Post> findUserPosts (final String userid,final int start,final int end) throws BlogException;
	
	public int countPosts() throws BlogException;
	
	public int countUserPosts(final String userid) throws BlogException;
	
	public List<Post> findPosts(int start, int end) throws BlogException;
	
	public List<Post> findPostsNotPrivate(int start, int end) throws BlogException;
	
	public List<Post> findPostsByCriteria(
			final HashMap<String, ICriteria> criterias, final Integer start, final Integer end)
			throws BlogException;
	public List<Post> findPostsByCriteriaNotPrivate(
            final HashMap<String, ICriteria> criterias, final Integer start, final Integer end)
            throws BlogException;
	public int countPostsByCriteria(
			final HashMap<String, ICriteria> criterias, final Integer start, final Integer end)
			throws BlogException;
	public Post findPostId(final int postid) throws BlogException;
//	public Date findMaxDateByPost(final int postId) throws BlogException;
//	
//	public Date findLastUpdate() throws BlogException;
}
