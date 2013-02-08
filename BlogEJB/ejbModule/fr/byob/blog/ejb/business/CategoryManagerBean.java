package fr.byob.blog.ejb.business;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.annotation.security.SecurityDomain;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.BlogInternationalizationUtils;
import fr.byob.blog.ejb.BlogUtils;
import fr.byob.blog.ejb.deploy.CategoryManagerRemote;
import fr.byob.blog.ejb.entity.Category;
import fr.byob.blog.ejb.entity.Role;
import fr.byob.client.exception.ValidationException;
import fr.byob.validator.EntityValidator;
import fr.byob.validator.exception.InternalValidationException;

@Stateless
@SecurityDomain(unauthenticatedPrincipal = Role.ROLE_GUEST, value = "BYOBSecurity")
@DeclareRoles( { Role.ROLE_USER, Role.ROLE_GUEST, Role.ROLE_ADMIN })
public class CategoryManagerBean implements CategoryManagerRemote {

	ResourceBundle messages = BlogInternationalizationUtils.getMessages();

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@PermitAll
	public List<Category> findAllCategories() throws BlogException {
		try {
			return (List<Category>) em.createQuery(
					"select object(o) from Category as o order by o.categorylabel ASC").getResultList();
		} catch (Exception e) {
			throw new BlogException(e, messages
					.getString("category.find.error"));
		}
	}

	@SuppressWarnings("unchecked")
	@PermitAll
	public List<Category> findCategories(int start, int end)
			throws BlogException {
		if (start >= end) {
			return new ArrayList<Category>();
		}
		try {
			return (List<Category>) em.createQuery(
					"select object(o) from Category as o order by o.categorylabel ASC").setFirstResult(
					start).setMaxResults(end - start).getResultList();
		} catch (Exception e) {
			throw new BlogException(e, messages
					.getString("category.find.error"));
		}
	}

	@PermitAll
	public int countCategories() throws BlogException {
		try {
			Long longCount = (Long) em.createQuery(
					"select count(*) from Category").getSingleResult();
			return longCount.intValue();
		} catch (Exception e) {
			throw new BlogException(e, messages
					.getString("category.count.error"));
		}
	}

	@PermitAll
	public Category findCategory(String label) throws BlogException {
		try {
			return (Category) em.createQuery(
					"select object(o) from Category as o where o.categorylabel='"
							+ label + "'").getSingleResult();
		} catch (Exception e) {
			throw new BlogException(e, messages
					.getString("category.findlabel.error"));
		}
	}
	@PermitAll
	public Category findCategory(int catagoryid) throws BlogException {
		try {
			return (Category) em.find(Category.class, catagoryid);
		} catch (Exception e) {
			throw new BlogException(e, messages
					.getString("category.find.error"));
		}
	}

	@RolesAllowed(Role.ROLE_ADMIN)
	public Category addCategory(Category category) throws BlogException,
			ValidationException {
		try {
			EntityValidator.getInstance().validateEntity(category);
		} catch (InternalValidationException e1) {
			throw e1.validationExceptionFactory(BlogInternationalizationUtils.getMessages());
		}
		try {
			em.persist(category);
			BlogUtils.createCategoryHTMLSitemap(category);
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("category.add.error"));
		}
		return category;
	}

	/**
	 * Warning the category must have it's old id unchanged and may have other
	 * attributes changed
	 * 
	 * @param category
	 * @throws ValidationException
	 */
	@RolesAllowed(Role.ROLE_ADMIN)
	public Category updateCategory(Category category) throws BlogException,
			ValidationException {
		try{
			EntityValidator.getInstance().validateEntity(category);
		} catch (InternalValidationException e1) {
			throw e1.validationExceptionFactory(BlogInternationalizationUtils.getMessages());
		}
		try {
			em.merge(category);
			BlogUtils.editCategoryHTMLSitemap(category);
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("category.mod.error"));
		}
		return category;
	}

	@RolesAllowed(Role.ROLE_ADMIN)
	public void removeCategory(int categoryid) throws BlogException {
		try {
			Category category = em.find(Category.class, categoryid);
			if (!category.getPostCollection().isEmpty()) {
				throw new BlogException(null, messages
						.getString("category.del.error.notempty"));
			}
			em.remove(category);
			BlogUtils.deleteCategoryHTMLSitemap(category);
		}catch (BlogException e) {
			throw e;
		}catch (Exception e) {
			throw new BlogException(e, messages.getString("category.del.error"));
		}
	}
}
