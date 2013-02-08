package fr.byob.blog.ejb.test.business;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.deploy.CategoryManagerRemote;
import fr.byob.blog.ejb.deploy.PostManagerRemote;
import fr.byob.blog.ejb.deploy.UserManagerRemote;
import fr.byob.blog.ejb.entity.Category;
import fr.byob.blog.ejb.entity.Post;
import fr.byob.blog.ejb.entity.User;
import fr.byob.blog.ejb.test.EJBTest;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.NameFactory;

public class PostManagerTest extends EJBTest {

	private PostManagerRemote postManager;
	private UserManagerRemote userManager;
	private CategoryManagerRemote categoryManager;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		postManager = (PostManagerRemote) context.lookup(NameFactory.getName("BYOB/PostManagerBean/remote"));
		userManager = (UserManagerRemote) context.lookup(NameFactory.getName("BYOB/UserManagerBean/remote"));
		categoryManager = (CategoryManagerRemote) context.lookup(NameFactory.getName("BYOB/CategoryManagerBean/remote"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testAddPost() {
		
		User testUser = null;
		Category testCategory = null ;
		HashSet<ICategory> testCategories = null;
		try {
			testUser = new User();
			testUser.setUserid("userid");
			testUser.setPassword("paidai");

			testUser = userManager.addUser(testUser);

			testCategory = new Category();
			testCategory.setCategorylabel("testCategory");
			testCategory = categoryManager.addCategory(testCategory);

			testCategories = new HashSet<ICategory>();
			testCategories.add(testCategory);

		} catch (Exception e) {
			testUser = userManager.findUser("userid");
		}

		Post post = new Post();
		post.setPosttext("Test");
		post.setUserid(testUser);
		post.setPosttitle("title");
		post.setPostdate(new Date());
		post.setCategoryCollection(testCategories);
		try {
			postManager.addPost(post);
		} catch (BlogException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (ValidationException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public final void testEditPost() {

		try {
			Post post = postManager.findPost("title");
			post.setPosttext("Test2");
			post.setPosttitle("title2");
			postManager.editPost(post);
		} catch (BlogException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (ValidationException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public final void testFindAllPosts() {
		try {
			assertNotSame(0, postManager.findAllPosts().size());
		} catch (BlogException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/*@Test
	public final void testRemovePost() {
		try {
			Post post = postManager.findPost("title2");
			postManager.removePost(post.getPostid());
		} catch (BlogException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		userManager.removeUser("userid");
		try {
			categoryManager.removeCategory(categoryManager.findCategory("testCategory").getCategoryid());
		} catch (BlogException e) {
			e.printStackTrace();
		}
	}*/
	
	
//	@Test
//    public final void testMaxDatePost() {
//        try {
//            Post post = postManager.findPost("title2");
//            postManager.findMaxDateByPost(post.getPostid());
//        } catch (BlogException e) {
//            e.printStackTrace();
//            fail(e.getMessage());
//        }
//        try {
//            categoryManager.removeCategory(categoryManager.findCategory("testCategory").getCategoryid());
//        } catch (BlogException e) {
//            e.printStackTrace();
//        }
//    }
//	@Test
//    public final void testLastUpdatePost() {
//        try {
//            Date date = postManager.findLastUpdate();
//            System.out.println("Date : "+date);
//        } catch (BlogException e) {
//            e.printStackTrace();
//            fail(e.getMessage());
//        }
//        try {
//            categoryManager.removeCategory(categoryManager.findCategory("testCategory").getCategoryid());
//        } catch (BlogException e) {
//            e.printStackTrace();
//        }
//    }
}
