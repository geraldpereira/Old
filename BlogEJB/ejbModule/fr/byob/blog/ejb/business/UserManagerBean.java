package fr.byob.blog.ejb.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.annotation.security.SecurityDomain;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.BlogInternationalizationUtils;
import fr.byob.blog.ejb.BlogUtils;
import fr.byob.blog.ejb.deploy.ProfilManagerRemote;
import fr.byob.blog.ejb.deploy.UserManagerRemote;
import fr.byob.blog.ejb.entity.Profil;
import fr.byob.blog.ejb.entity.Role;
import fr.byob.blog.ejb.entity.User;
import fr.byob.blog.ejb.entity.UserRole;
import fr.byob.blog.ejb.entity.UserRolePK;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.NameFactory;
import fr.byob.server.util.PasswordUtils;
import fr.byob.validator.EntityValidator;
import fr.byob.validator.exception.InternalValidationException;

/**
 * This class manages the user entities and its associated class likes profile
 * entities and preferences entities. It can add, remove, modify and fetch
 * users. It can also modify its preferences and profile.
 * 
 * @author Gerald Pereira
 */
@Stateless
@SecurityDomain(unauthenticatedPrincipal=Role.ROLE_GUEST,value="BYOBSecurity")
@DeclareRoles({ Role.ROLE_ADMIN, Role.ROLE_USER, Role.ROLE_GUEST })
public class UserManagerBean implements UserManagerLocal, UserManagerRemote {

	ResourceBundle messages = BlogInternationalizationUtils.getMessages();
	
	ProfilManagerRemote profilBean ;
	
	@PersistenceContext
	private EntityManager em;

	// Roles constant, jboss compliance
	private final static String ROLES = "Roles";

	// Default role
	private final static String DEFAULT_ROLE = Role.ROLE_USER;

	public UserManagerBean() {
	    Context context;
        try {
            context = new InitialContext();
            profilBean = (ProfilManagerRemote) context.lookup(NameFactory.getName("BlogEAR/ProfilManagerBean/remote"));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
	/**
	 *Adds a user to the database
	 * 
	 *@param user
	 *            the user to add
	 * @throws AdminException
	 * @throws ValidationException
	 */
	@RolesAllowed(Role.ROLE_ADMIN)
	public User addUser(final User user) throws BlogException,
			ValidationException {
		return this.addUser(user, DEFAULT_ROLE);
	}

	@RolesAllowed(Role.ROLE_ADMIN)
	public User addUser(final User user, final String roleStr)
			throws BlogException, ValidationException {
		try{
			EntityValidator.getInstance().validateEntity(user);
		} catch (InternalValidationException e1) {
			throw e1.validationExceptionFactory(BlogInternationalizationUtils.getMessages());
		}
		try {
			user.setPassword(PasswordUtils.hashPassword(user.getPassword()));
		} catch (Exception e) {
			throw new BlogException(e,messages.getString("user.pwd.error"));
		}
		try {
			em.persist(user);
			Role role = (Role) em.createQuery(
					"select object(o) from Role as o where o.role=\'" + roleStr
							+ "\'").getSingleResult();
			if (role == null) {
				role = new Role();
				role.setRole(roleStr);
				em.persist(role);
			}

			UserRolePK pk = new UserRolePK();
			pk.setUserid(user.getUserid());
			pk.setRolegroup(ROLES);

			UserRole userRole = new UserRole();
			userRole.setPk(pk);
			userRole.setRoleid(role);

			em.persist(userRole);
			
			Profil profil = new Profil();
			profil.setProfilcss("");
			profil.setProfildate(new Date());
			profil.setProfilinscription(new Date());
			profil.setProfilmail("");
			profil.setProfilphoto("");
			profil.setProfilpresentation("");
			profil.setUserid(user);
			em.persist(profil);
			BlogUtils.createProfilHTMLSitemap(profil);
			return user;
		} catch (Exception e) {
			throw new BlogException(e,messages.getString("user.add.error"));
		}
	}

	/**
	 * Searches for an user by id
	 * 
	 * @param the
	 *            searched user's id
	 * @return the UserEntity found or null if there is no user with the id
	 *         given in parameter in the database
	 */
	@PermitAll
	public User findUser(final String id) {
		return (User) em.find(User.class, id);
	}
	@RolesAllowed( { Role.ROLE_ADMIN })
	public void removeUser(final String id) throws BlogException {
	    try {
		User user = this.findUser(id);
		UserRolePK pk = new UserRolePK();
		pk.setUserid(id);
		pk.setRolegroup(ROLES);
		UserRole userRole = em.find(UserRole.class, pk);
		em.remove(userRole);
		Profil profil = profilBean.findProfilUser(id);
		Profil profil1 = em.find(Profil.class, profil.getProfilid());
		em.remove(profil1);
		BlogUtils.deleteProfilHTMLSitemap(profil1);
		em.remove(user);
	    } catch (Exception e) {
            throw new BlogException(e,messages.getString("user.del.error"));
        }
	}
	
	@PermitAll
	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() throws BlogException {
		try {
			return (List<User>) em.createQuery(
					"select object(o) from User as o order by o.userid ASC").getResultList();
		} catch (Exception e) {
			throw new BlogException(e,messages.getString("user.find.error"));
		}
	}

	@RolesAllowed( { Role.ROLE_ADMIN })
	public User editUser(final User user) throws BlogException, ValidationException {
		try{
			EntityValidator.getInstance().validateEntity(user);
		} catch (InternalValidationException e1) {
			throw e1.validationExceptionFactory(BlogInternationalizationUtils.getMessages());
		}
		try {
			user.setPassword(PasswordUtils.hashPassword(user.getPassword()));
		} catch (Exception e) {
			throw new BlogException(e,messages.getString("user.pwd.error"));
		}
		try {
			em.merge(user);
		} catch (Exception e) {
			throw new BlogException(e,messages.getString("user.mod.error"));
		}
		return user;
	}

	@PermitAll
	public Role getUserRole(final User user) throws BlogException {
		try {
			UserRolePK pk = new UserRolePK();
			pk.setUserid(user.getUserid());
			pk.setRolegroup(ROLES);
			UserRole userRole = em.find(UserRole.class, pk);
			if (userRole != null && userRole.getRoleid() != null){
				Role role = em.find(Role.class, userRole.getRoleid().getRoleid());
				return role;
			}
			return null;
		} catch (Exception e) {
			throw new BlogException(e,messages.getString("user.role.error"));
		}
	}
	
	@SuppressWarnings("unchecked")
	@PermitAll
	public List<User> findUsers(int start, int end)
			throws BlogException {
		if (start >= end) {
			return new ArrayList<User>();
		}
		try {
			return (List<User>) em.createQuery(
					"select object(o) from User as o order by o.userid ASC").setFirstResult(start)
					.setMaxResults(end - start).getResultList();
		} catch (Exception e) {
			throw new BlogException(e,
					messages.getString("user.find.error"));
		}
	}

	@PermitAll
	public int countUsers() throws BlogException {
		try {
			return ((Long) em.createQuery("select count(*) from User")
					.getSingleResult()).intValue();
		} catch (Exception e) {
			throw new BlogException(e,
					messages.getString("user.count.error"));
		}
	}

}
