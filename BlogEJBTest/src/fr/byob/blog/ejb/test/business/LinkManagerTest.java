package fr.byob.blog.ejb.test.business;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.deploy.CategoryManagerRemote;
import fr.byob.blog.ejb.deploy.LinkManagerRemote;
import fr.byob.blog.ejb.deploy.UserManagerRemote;
import fr.byob.blog.ejb.entity.Category;
import fr.byob.blog.ejb.entity.Link;
import fr.byob.blog.ejb.entity.User;
import fr.byob.blog.ejb.test.EJBTest;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.NameFactory;

public class LinkManagerTest extends EJBTest {

	private LinkManagerRemote linkManager;
	private UserManagerRemote userManager;
	private CategoryManagerRemote categoryManager;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		linkManager = (LinkManagerRemote) context.lookup(NameFactory.getName("BYOB/LinkManagerBean/remote"));
		userManager = (UserManagerRemote) context.lookup(NameFactory.getName("BYOB/UserManagerBean/remote"));
		categoryManager = (CategoryManagerRemote) context.lookup(NameFactory.getName("BYOB/CategoryManagerBean/remote"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testAddLink() {
		
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

		Link link = new Link();
		link.setLinktext("Super site");
		link.setUserid(testUser);
		link.setLinktitle("lien");
		link.setLinkurl("www.byob.fr");
		link.setCategoryCollection(testCategories);
		try {
		    linkManager.addLink(link);
		} catch (BlogException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (ValidationException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public final void testEditLink() {

		try {
		    Link link = linkManager.findLink("lien");
			link.setLinktext("Test2");
			link.setLinktitle("title2");
			linkManager.editLink(link);
		} catch (BlogException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (ValidationException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public final void testFindAllLinks() {
		try {
			assertNotSame(0, linkManager.findAllLinks().size());
		} catch (BlogException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public final void testRemoveLink() {
		try {
		    Link link = linkManager.findLink("title2");
		    linkManager.removeLink(link.getLinkid());
		    userManager.removeUser("userid");
		} catch (BlogException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		try {
			categoryManager.removeCategory(categoryManager.findCategory("testCategory").getCategoryid());
		} catch (BlogException e) {
			e.printStackTrace();
		}
	}
	
}
