package fr.byob.blog.ejb.test.business;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.deploy.CategoryManagerRemote;
import fr.byob.blog.ejb.deploy.CommentManagerRemote;
import fr.byob.blog.ejb.deploy.PostManagerRemote;
import fr.byob.blog.ejb.deploy.UserManagerRemote;
import fr.byob.blog.ejb.entity.Category;
import fr.byob.blog.ejb.entity.Comment;
import fr.byob.blog.ejb.entity.Post;
import fr.byob.blog.ejb.entity.User;
import fr.byob.blog.ejb.test.EJBTest;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.NameFactory;

public class CommentManagerTest extends EJBTest {
	private PostManagerRemote postManager;
	private UserManagerRemote userManager;
	private CategoryManagerRemote categoryManager;
	private CommentManagerRemote commentManager;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		postManager = (PostManagerRemote) context.lookup(NameFactory.getName("BYOB/PostManagerBean/remote"));
		userManager = (UserManagerRemote) context.lookup(NameFactory.getName("BYOB/UserManagerBean/remote"));
		categoryManager = (CategoryManagerRemote) context.lookup(NameFactory.getName("BYOB/CategoryManagerBean/remote"));
		commentManager = (CommentManagerRemote) context.lookup(NameFactory.getName("BYOB/CommentManagerBean/remote"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testAddComment() {
		User testUser = null;
		Category testCategory = null;
		HashSet<ICategory> testCategories = null;
		Post post = null;
		try {
			testUser = new User();
			testUser.setUserid("userid");
			testUser.setPassword("paidai");
			testUser = userManager.addUser(testUser);
		} catch (Exception e) {
            fail("Initialisation USER failled.");
        }
		try {
			testCategory = new Category();
			testCategory.setCategorylabel("testCategory");
			testCategory = categoryManager.addCategory(testCategory);
		} catch (Exception e) {
            fail("Initialisation CATEGORY failled.");
        }
			testCategories = new HashSet<ICategory>();
			testCategories.add(testCategory);
			try {
			post = new Post();
			post.setPosttext("Test");
			post.setUserid(testUser);
			post.setPosttitle("title");
			post.setPostdate(new Date());
			post.setCategoryCollection(testCategories);
			post = postManager.addPost(post);
			
		} catch (Exception e) {
			fail("Initialisation POST failled.");
		}
//	    Post post = null;
//	    try {
//	    post = postManager.findPost("title");
//	    } catch (BlogException e) {
//            fail("find POST failled.");
//            e.printStackTrace();
//            //            fail(e.getMessage());
//        }
		Comment comment = new Comment();
		comment.setCommentautor("gg");
		comment.setCommentmail("gg@gmail.com");
		comment.setCommenttext("Yeahhhhhh!");
		comment.setCommentdate(new Date());
		comment.setPostid(post);
		try {
			commentManager.addComment(comment);
		} catch (BlogException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (ValidationException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public final void testRemoveComment() {
	    Post post = null;
	    List<Comment> comments = null;
	    try {
	        post = postManager.findPost("title");
	    } catch (BlogException e) {
	        fail("find POST failled.");
	        e.printStackTrace();
	        //            fail(e.getMessage());
	    }
	    try{
	        comments = commentManager.findAllComments(post);

	    } catch (BlogException e) {
	        fail("find COMMENTS failled.");
	        e.printStackTrace();
	        //			fail(e.getMessage());
	    }
	    try{
	        for (Comment comment : comments) {
	            commentManager.removeComment(comment.getCommentid());
	        }
	    }catch (BlogException e) {
	        fail("remove COMMENTS failled.");
	        e.printStackTrace();
//	        fail(e.getMessage());
	    }
//	    try {
//	        postManager.removePost(post.getPostid());
//	        userManager.removeUser("userid");
//	        categoryManager.removeCategory(categoryManager.findCategory("testCategory").getCategoryid());
//	    } catch (BlogException e) {
//	        e.printStackTrace();
//	    }

	}

}
