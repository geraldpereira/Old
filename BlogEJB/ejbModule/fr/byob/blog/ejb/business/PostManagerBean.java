package fr.byob.blog.ejb.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import org.jboss.logging.Logger;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.BlogInternationalizationUtils;
import fr.byob.blog.ejb.BlogUtils;
import fr.byob.blog.ejb.deploy.PostManagerRemote;
import fr.byob.blog.ejb.deploy.ProfilManagerRemote;
import fr.byob.blog.ejb.entity.Post;
import fr.byob.blog.ejb.entity.Profil;
import fr.byob.blog.ejb.entity.Role;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.MailerUtils;
import fr.byob.server.util.NameFactory;
import fr.byob.server.util.ejbql.CriteriaQueryBuilder;
import fr.byob.server.util.ejbql.ICriteria;
import fr.byob.validator.EntityValidator;
import fr.byob.validator.exception.InternalValidationException;

@Stateless
@SecurityDomain(unauthenticatedPrincipal = Role.ROLE_GUEST, value = "BYOBSecurity")
@DeclareRoles( { Role.ROLE_USER, Role.ROLE_GUEST })
public class PostManagerBean implements PostManagerRemote {

	ResourceBundle messages = BlogInternationalizationUtils.getMessages();

	private final Logger log = Logger.getLogger(this.getClass());
	
	ProfilManagerRemote profilBean ;
	
	@PersistenceContext
	private EntityManager em;
	
	public PostManagerBean() {
	        Context context;
	        try {
	            context = new InitialContext();
	            profilBean = (ProfilManagerRemote) context.lookup(NameFactory.getName("BlogEAR/ProfilManagerBean/remote"));
	        } catch (NamingException e) {
	            e.printStackTrace();
	        }
    }

	@RolesAllowed(Role.ROLE_USER)
	public Post addPost(final Post post) throws BlogException,
			ValidationException {
		try {
			post.setPostlastupdate(post.getPostdate());
			EntityValidator.getInstance().validateEntity(post);
		} catch (InternalValidationException e1) {
			throw e1.validationExceptionFactory(BlogInternationalizationUtils
					.getMessages());
		}
		try {
			em.persist(post);
			sendMail(post);
			if(!post.getPostisprivate()){
			    BlogUtils.createPostHTMLSitemap(post);
			}
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("post.add.error"));
		}
		return post;
	}
	
	private void sendMail(Post post){
	    List<String> cats = new ArrayList<String>();
	    for(ICategory cat : post.getCategoryCollection()){
	        cats.add(cat.getCategorylabel());
	    }
        try {
            List<Profil> profils = profilBean.findAllProfils();
            for(Profil profil : profils){
                if(profil.getProfilmail() != null && !profil.getProfilmail().equals("")){
//                    MailerUtils.sendmailForNewPost(post.getPosttitle(), post.getUserid().getUserid(), post.getPostdate(), cats,profil.getProfilmail(),"#Post="+post.getPostid());
                }
            }
        } catch (BlogException e) {
            
        }
	}
	
	/**
	 * Warning : don't change the post'id !
	 * 
	 * @param post
	 * @throws ValidationException
	 */
	@RolesAllowed(Role.ROLE_USER)
	public Post editPost(final Post post) throws BlogException,
			ValidationException {
	    Post oldPost = findPostId(post.getPostid());
	    log.info("editPost : "+oldPost.getPostisprivate()+" / "+post.getPostisprivate());
		try {
			post.setPostlastupdate(new Date());
			EntityValidator.getInstance().validateEntity(post);
			
		} catch (InternalValidationException e1) {
			throw e1.validationExceptionFactory(BlogInternationalizationUtils
					.getMessages());
		}
		try {
			em.merge(post);
			if(!oldPost.getPostisprivate() && !post.getPostisprivate()){
			    BlogUtils.editPostHTMLSitemap(post);
			}else if(oldPost.getPostisprivate() && !post.getPostisprivate()){
			    BlogUtils.createPostHTMLSitemap(post);
			}else if(!oldPost.getPostisprivate() && post.getPostisprivate()){
			    BlogUtils.deletePostHTMLSitemap(post);
			}
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("post.mod.error"));
		}
		return post;
	}

	@RolesAllowed(Role.ROLE_USER)
	public void removePost(final int postId) throws BlogException {
		try {
			Post post = em.find(Post.class, postId);
			for (ICategory category : post.getCategoryCollection()) {
				category.getPostCollection().remove(post);
				em.merge(category);
			}
			em.remove(post);
			BlogUtils.deletePostHTMLSitemap(post);
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("post.del.error"));
		}
	}

	@SuppressWarnings("unchecked")
	@PermitAll
	public List<Post> findAllPosts() throws BlogException {
		try {
			return (List<Post>) em.createQuery(
					"select object(o) from Post as o ").getResultList();
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("post.find.error"));
		}
	}
	
	@SuppressWarnings("unchecked")
    @PermitAll
    public List<Post> findAllPostsNotPrivate() throws BlogException {
        try {
            return (List<Post>) em.createQuery(
                    "select object(o) from Post as o WHERE o.postisprivate=false").getResultList();
        } catch (Exception e) {
            throw new BlogException(e, messages.getString("post.find.error"));
        }
    }

	@PermitAll
    public List<Post> findPostsByCriteriaNotPrivate(
            final HashMap<String, ICriteria> criterias, final Integer start, final Integer end)
            throws BlogException {
        try {
            CriteriaQueryBuilder<Post> builder = new CriteriaQueryBuilder<Post>(
                    "Post", "postdate", criterias);
            log.debug("query : "+builder.toString());
            return (List<Post>) builder.executeQuery(em,start, end - start);
        } catch (Exception e) {
            throw new BlogException(e, messages.getString("post.find.error"));
        }
    }
	
	@PermitAll
	public List<Post> findPostsByCriteria(
			final HashMap<String, ICriteria> criterias, final Integer start, final Integer end)
			throws BlogException {
		try {
			CriteriaQueryBuilder<Post> builder = new CriteriaQueryBuilder<Post>(
					"Post", "postdate", criterias);
			return (List<Post>) builder.executeQuery(em,start, end - start);
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("post.find.error"));
		}
	}
	
	@PermitAll
	public int countPostsByCriteria(
			final HashMap<String, ICriteria> criterias, final Integer start, final Integer end)
			throws BlogException {
		try {
			CriteriaQueryBuilder<Post> builder = new CriteriaQueryBuilder<Post>(
					"Post", "postdate", criterias,true);
			Integer maxResult = null;
			if (end != null && start != null){
				maxResult = end - start;
			}
			return ((Long) builder.executeQuery(em,start,maxResult)).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new BlogException(e, messages.getString("post.count.error"));
		}
	}

	@SuppressWarnings("unchecked")
	@PermitAll
	public List<Post> findPosts(int start, int end) throws BlogException {
		if (start >= end) {
			return new ArrayList<Post>();
		}
		try {
			return (List<Post>) em.createQuery(
					"select object(o) from Post as o ORDER BY o.postlastupdate DESC")
					.setFirstResult(start).setMaxResults(end - start)
					.getResultList();
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("post.find.error"));
		}
	}
	@SuppressWarnings("unchecked")
    @PermitAll
    public List<Post> findPostsNotPrivate(int start, int end) throws BlogException {
        if (start >= end) {
            return new ArrayList<Post>();
        }
        try {
            return (List<Post>) em.createQuery(
                    "select object(o) from Post as o WHERE o.postisprivate=false ORDER BY o.postlastupdate DESC")
                    .setFirstResult(start).setMaxResults(end - start)
                    .getResultList();
        } catch (Exception e) {
            throw new BlogException(e, messages.getString("post.find.error"));
        }
    }
	@PermitAll
	public int countPosts() throws BlogException {
		try {
			return ((Long) em.createQuery("select count(*) from Post")
					.getSingleResult()).intValue();
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("post.count.error"));
		}
	}

	@PermitAll
	public Post findPost(final String title) throws BlogException {
		try {
			return (Post) em.createQuery(
					"select object(o) from Post as o where o.posttitle='"
							+ title + "'").getResultList().get(0);
		} catch (Exception e) {
			throw new BlogException(e, messages
					.getString("post.findtitle.error")
					+ title);
		}
	}
	@PermitAll
    public Post findPostId(final int postid) throws BlogException {
        if (log.isInfoEnabled()){
            log.info("=================> Recherche du Post numéro : "+postid);
        }
	    try {
            return em.find(Post.class, postid);
        } catch (Exception e) {
            throw new BlogException(e, messages
                    .getString("post.find.error"));
        }
    }

	@SuppressWarnings("unchecked")
	@RolesAllowed(Role.ROLE_USER)
	public List<Post> findUserPosts(final String userid, final int start,
			final int end) throws BlogException {
		try {
			return (List<Post>) em
					.createQuery(
							"select object(p) from Post as p,User as userid where p.userid.userid = userid.userid and userid.userid = '"
									+ userid + "'").setFirstResult(start)
					.setMaxResults(end - start).getResultList();
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("post.find.error"));
		}
	}
	@RolesAllowed(Role.ROLE_USER)
    public int countUserPosts(final String userid) throws BlogException {
        try {
            return ((Long) em.createQuery("select count(p) from Post as p,User as userid where p.userid.userid = userid.userid and userid.userid = '"
                    + userid + "'")
                    .getSingleResult()).intValue();
        } catch (Exception e) {
            throw new BlogException(e, messages.getString("post.count.error"));
        }
    }
//	@RolesAllowed(Role.ROLE_USER)
//    public Date findMaxDateByPost(final int postId) throws BlogException {
//        try {
//            Date dateComment = ((Date) em.createQuery("select max(commentdate) from Comment as c where c.postid = '"+postId+"'").getSingleResult());
//            if(dateComment == null){
//                dateComment = ((Date) em.createQuery("select postdate from Post as p where p.postid =  '"+postId+"'").getSingleResult());    
//            }
//            return dateComment;
//        } catch (Exception e) {
//            throw new BlogException(e, messages.getString("post.count.error"));
//        }
//    }
//	@RolesAllowed(Role.ROLE_USER)
//    public Date findLastUpdate() throws BlogException {
//        try {
//            Date dateComment = ((Date) em.createQuery("select max(p.postdate) from Post as p  where p.postdate > EXISTS(select max(c.commentdate) from Comment as c)").getSingleResult());
//            if(dateComment == null){
//                dateComment = ((Date) em.createQuery("select max(c.commentdate) from Comment as c where c.commentdate > (select max(p.postdate) from post as p)").getSingleResult());    
//            }
//            return dateComment;
//        } catch (Exception e) {
//            throw new BlogException(e, messages.getString("post.count.error"));
//        }
//    }
}
