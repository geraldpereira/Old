package fr.byob.blog.ejb.test;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

import fr.byob.blog.ejb.entity.Post;
import fr.byob.server.util.ejbql.ICriteria;

@Remote
public interface TestManagerRemote {
	
	public final static String USER_CRITERIA = "userid";
	public final static String TEXT_CRITERIA = "posttext";
	public final static String TITLE_CRITERIA = "posttitle";
	public final static String CATEGORY_CRITERIA = "categoryCollection";
	public final static String DATE_CRITERIA = "postdate";
	
	/**
	 * 
	 * @param criterias
	 * @param maxResult null si ne doit pas être pri en compte 
	 * @return
	 */
	public List<Post> findPosts(final HashMap<String, Object> criterias, Integer maxResult);
	public List<Post> findPostsByCriteria (final HashMap<String, ICriteria> criterias, final Integer maxResult);
}
