package fr.byob.blog.ejb.test.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.deploy.UserManagerRemote;
import fr.byob.blog.ejb.entity.Role;
import fr.byob.blog.ejb.entity.User;
import fr.byob.blog.ejb.test.EJBTest;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.NameFactory;
import fr.byob.server.util.PasswordUtils;

public class UserManagerTest extends EJBTest {

	private UserManagerRemote userManager;

	private final static String USERID = "userid";
	private final static String ADMINID = "adminid";
	private final static String PWD = "password24";

	
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		userManager = (UserManagerRemote) context.lookup(NameFactory.getName("BlogEAR/UserManagerBean/remote"));
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public final void testAddUserUser() {
		try {

			User user = new User();
			user.setUserid(USERID);
			user.setPassword(PWD);
			userManager.addUser(user);

		} catch (BlogException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (ValidationException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public final void testAddUserUserString() {
		try {

			User user = new User();
			user.setUserid(ADMINID);
			user.setPassword(PWD);
			userManager.addUser(user, Role.ROLE_ADMIN);

		} catch (BlogException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (ValidationException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public final void testFind() {

		User user = userManager.findUser(USERID);
		User admin = userManager.findUser(ADMINID);

		try {
			assertEquals(PasswordUtils.hashPassword(PWD), user.getPassword());
			assertEquals(PasswordUtils.hashPassword(PWD), admin.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

	@Test
	public final void testRemove() {
	    try {
		userManager.removeUser(USERID);
		userManager.removeUser(ADMINID);
	    } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
		assertNull(userManager.findUser(USERID));
		assertNull(userManager.findUser(ADMINID));
	}
}
