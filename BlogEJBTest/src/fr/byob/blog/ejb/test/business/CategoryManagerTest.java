package fr.byob.blog.ejb.test.business;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.deploy.CategoryManagerRemote;
import fr.byob.blog.ejb.entity.Category;
import fr.byob.blog.ejb.test.EJBTest;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.NameFactory;

public class CategoryManagerTest extends EJBTest {

	private CategoryManagerRemote categoryManager;

	private final static String CATEGORYLABEL = "categorylabel";
	private final static String CATEGORYLABEL2 = "categorylabel2";

	@Before
	public void setUp() throws Exception {
		super.setUp();
		categoryManager = (CategoryManagerRemote) context.lookup(NameFactory.getName("BYOB/CategoryManagerBean/remote"));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testAddCategory() {
		Category category = new Category();
		category.setCategorylabel(CATEGORYLABEL);
		try {
			categoryManager.addCategory(category);
		} catch (BlogException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (ValidationException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public final void testFindCategory() {
		try {
			assertNotNull(categoryManager.findCategory(CATEGORYLABEL));
		} catch (BlogException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public final void testFindAllCategories() {
		try {
			assertFalse(categoryManager.findAllCategories().isEmpty());
		} catch (BlogException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public final void testUpdateCategory() {
		try {
			Category found = categoryManager.findCategory(CATEGORYLABEL);
			found.setCategorylabel(CATEGORYLABEL2);
			categoryManager.updateCategory(found);
			assertNotNull(categoryManager.findCategory(CATEGORYLABEL2));
		} catch (BlogException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (ValidationException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public final void testRemoveCategory() {

		try {
			Category found = categoryManager.findCategory(CATEGORYLABEL2);
			categoryManager.removeCategory(found.getCategoryid());
			assertNull(categoryManager.findCategory(CATEGORYLABEL2));
		} catch (BlogException e) {
//			if (!(e.getCause() instanceof NoResultException)){
				e.printStackTrace();
				fail(e.getMessage());
//			}
		}

	}

	

}
