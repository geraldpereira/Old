package fr.byob.blog.ejb.test.business;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.deploy.CategoryManagerRemote;
import fr.byob.blog.ejb.deploy.CommentManagerRemote;
import fr.byob.blog.ejb.deploy.PostManagerRemote;
import fr.byob.blog.ejb.deploy.UserManagerRemote;
import fr.byob.blog.ejb.entity.Post;
import fr.byob.blog.ejb.test.EJBTest;
import fr.byob.server.util.NameFactory;

public class TestUtils extends EJBTest {
	private PostManagerRemote postManager;
	private UserManagerRemote userManager;
	private CategoryManagerRemote categoryManager;
	private CommentManagerRemote commentManager;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		commentManager = (CommentManagerRemote) context.lookup(NameFactory.getName("BYOB/CommentManagerBean/remote"));
		postManager = (PostManagerRemote) context.lookup(NameFactory.getName("BYOB/PostManagerBean/remote"));
		userManager = (UserManagerRemote) context.lookup(NameFactory.getName("BYOB/UserManagerBean/remote"));
		categoryManager = (CategoryManagerRemote) context.lookup(NameFactory.getName("BYOB/CategoryManagerBean/remote"));

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testutils() {
		try {
			Post post = postManager.findPost("title");
			postManager.removePost(post.getPostid());
		} catch (BlogException e) {	}
		
		try {
			Post post = postManager.findPost("title2");
			postManager.removePost(post.getPostid());
			userManager.removeUser("userid");
	        userManager.removeUser("adminid");
		} catch (BlogException e) {	}
		
		try {
			categoryManager.removeCategory(categoryManager.findCategory("categorylabel").getCategoryid());
		} catch (BlogException e) {
		}
		try {
			categoryManager.removeCategory(categoryManager.findCategory("categorylabel2").getCategoryid());
		} catch (BlogException e) {
		}
		try {
			categoryManager.removeCategory(categoryManager.findCategory("testCategory").getCategoryid());
		} catch (BlogException e) {
		}
		
		
	}
}
