package fr.byob.blog.ejb.test.business;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.byob.blog.client.IUser;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.deploy.ProfilManagerRemote;
import fr.byob.blog.ejb.deploy.UserManagerRemote;
import fr.byob.blog.ejb.entity.Profil;
import fr.byob.blog.ejb.entity.User;
import fr.byob.blog.ejb.test.EJBTest;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.NameFactory;

public class ProfilManagerTest extends EJBTest {

	private ProfilManagerRemote profilManager;
	private UserManagerRemote userManager;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		profilManager = (ProfilManagerRemote) context.lookup(NameFactory.getName("BYOB/ProfilManagerBean/remote"));
		userManager = (UserManagerRemote) context.lookup(NameFactory.getName("BYOB/UserManagerBean/remote"));
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
//	public final void testAddProfil() {
//		
//		User user = new User();
//		user.setUserid("profil");
//		user.setPassword("profil");
//		
////		profil.setUserid(testUser);
////		profil.setUserid(user);
//		try {
//		    userManager.addUser(user);
//		} catch (BlogException e) {
//			e.printStackTrace();
//			fail(e.getMessage());
//		} catch (ValidationException e) {
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public final void testEditProfil() {
//
//		try {
//		    Profil profil = profilManager.findProfil("profil");
//	        
//		    profil.setProfilcss("bleuet");
//	        profil.setProfildate(new Date());
//	        profil.setProfilinscription(new Date());
//	        profil.setProfilmail("edeltil@gmail.com");
//	        profil.setProfilphoto("toto");
//	        profil.setProfilpresentation("Tout va bien et vive BYOB et Toulouse");
//		    profil.setProfilcss("blanc");
//		    profil.setProfilmail("toto@toto.fr");
//		    IUser user = profil.getUserid();
//		    user.setPassword("toto");
//		    profil.setUserid(user);
//		    profilManager.editProfil(profil);
//		} catch (BlogException e) {
//			e.printStackTrace();
//			fail(e.getMessage());
//		} catch (ValidationException e) {
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public final void testFindAllProfil() {
//		try {
//			assertNotSame(0, profilManager.findAllProfils().size());
//		} catch (BlogException e) {
//			e.printStackTrace();
//			fail(e.getMessage());
//		}
//	}

	@Test
	public final void testRemoveProfil() {
		try {
//		    User user = userManager.findUser("userid");
		    userManager.removeUser("profil");
		    /*Profil profil = profilManager.findProfil("profil");
		    profilManager.removeProfil(profil.getProfilid());*/
		} catch (BlogException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
//		userManager.removeUser("userid");
	}
	
}
