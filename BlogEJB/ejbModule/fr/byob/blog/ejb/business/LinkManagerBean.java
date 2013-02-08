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

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.BlogInternationalizationUtils;
import fr.byob.blog.ejb.BlogUtils;
import fr.byob.blog.ejb.deploy.LinkManagerRemote;
import fr.byob.blog.ejb.entity.Link;
import fr.byob.blog.ejb.entity.Role;
import fr.byob.client.exception.ValidationException;
import fr.byob.validator.EntityValidator;
import fr.byob.validator.exception.InternalValidationException;

@Stateless
@SecurityDomain(unauthenticatedPrincipal = Role.ROLE_GUEST, value = "BYOBSecurity")
@DeclareRoles( { Role.ROLE_USER, Role.ROLE_GUEST })
public class LinkManagerBean implements LinkManagerRemote {

	ResourceBundle messages = BlogInternationalizationUtils.getMessages();
	
	@PersistenceContext
	private EntityManager em;

	@RolesAllowed(Role.ROLE_USER)
	public Link addLink(final Link link) throws BlogException,
			ValidationException {
		try{
			EntityValidator.getInstance().validateEntity(link);
		} catch (InternalValidationException e1) {
			throw e1.validationExceptionFactory(BlogInternationalizationUtils.getMessages());
		}
		try {
			em.persist(link);
			BlogUtils.createLinkHTMLSitemap(link);
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("link.add.error"));
		}
		return link;
	}

	/**
	 * Warning : don't change the link'id !
	 * 
	 * @param link
	 * @throws ValidationException
	 */
	@RolesAllowed(Role.ROLE_USER)
	public Link editLink(final Link link) throws BlogException,
			ValidationException {
		try{
			EntityValidator.getInstance().validateEntity(link);
		} catch (InternalValidationException e1) {
			throw e1.validationExceptionFactory(BlogInternationalizationUtils.getMessages());
		}
		try {
			em.merge(link);
			BlogUtils.editLinkHTMLSitemap(link);
		} catch (Exception e) {
			throw new BlogException(e,
					messages.getString("link.mod.error"));
		}
		return link;
	}

	@RolesAllowed(Role.ROLE_USER)
	public void removeLink(final int linkId) throws BlogException {
		try {
		    Link link = em.find(Link.class, linkId);
			for (ICategory category : link.getCategoryCollection()){
				category.getLinkCollection().remove(link);
				em.merge(category);
			}
			em.remove(link);
			BlogUtils.deleteLinkHTMLSitemap(link);
		} catch (Exception e) {
			throw new BlogException(e,
					messages.getString("link.del.error"));
		}
	}

	@SuppressWarnings("unchecked")
	@PermitAll
	public List<Link> findAllLinks() throws BlogException {
		try {
			return (List<Link>) em.createQuery(
					"select object(o) from Link as o order by o.linktitle ASC").getResultList();
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("link.find.error"));
		}
	}

	@SuppressWarnings("unchecked")
	@PermitAll
	public List<Link> findLinks(int start, int end) throws BlogException {
		if (start >= end) {
			return new ArrayList<Link>();
		}
		try {
			return (List<Link>) em.createQuery(
					"select object(o) from Link as o  order by o.linktitle ASC").setFirstResult(start)
					.setMaxResults(end - start).getResultList();
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("link.find.error"));
		}
	}

	@PermitAll
	public int countLinks() throws BlogException {
		try {
			return ((Long) em.createQuery("select count(*) from Link")
					.getSingleResult()).intValue();
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("link.count.error"));
		}
	}

	@PermitAll
	public Link findLink(final String title) throws BlogException {
		try {
			return (Link) em.createQuery(
					"select object(o) from Link as o where o.linktitle='"
							+ title + "'").getSingleResult();
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("link.findtitle.error")	+ title);
		}
	}
	
	@PermitAll
    public Link findLinkId(final int linkId) throws BlogException {
        try {
            return (Link) em.createQuery(
                    "select object(o) from Link as o where o.linkid='"
                            + linkId + "'").getSingleResult();
        } catch (Exception e) {
            throw new BlogException(e, messages.getString("link.findtitle.error")   + linkId);
        }
    }
	
	@SuppressWarnings("unchecked")
    @RolesAllowed(Role.ROLE_USER)
    public List<Link> findUserLinks(final String userid, final int start,
            final int end) throws BlogException {
        try {
            return (List<Link>) em
                    .createQuery(
                            "select object(p) from Link as p,User as userid where p.userid.userid = userid.userid and userid.userid = '"
                                    + userid + "'").setFirstResult(start)
                    .setMaxResults(end - start).getResultList();
        } catch (Exception e) {
            throw new BlogException(e, messages.getString("link.find.error"));
        }
    }
}
