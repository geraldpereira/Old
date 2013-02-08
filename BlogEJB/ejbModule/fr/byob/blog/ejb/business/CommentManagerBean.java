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
import org.jboss.logging.Logger;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.IComment;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.BlogInternationalizationUtils;
import fr.byob.blog.ejb.BlogUtils;
import fr.byob.blog.ejb.deploy.CommentManagerRemote;
import fr.byob.blog.ejb.deploy.ProfilManagerRemote;
import fr.byob.blog.ejb.entity.Comment;
import fr.byob.blog.ejb.entity.Post;
import fr.byob.blog.ejb.entity.Profil;
import fr.byob.blog.ejb.entity.Role;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.MailerUtils;
import fr.byob.server.util.NameFactory;
import fr.byob.validator.EntityValidator;
import fr.byob.validator.exception.InternalValidationException;

@Stateless
@SecurityDomain(unauthenticatedPrincipal = Role.ROLE_GUEST, value = "BYOBSecurity")
@DeclareRoles( { Role.ROLE_USER, Role.ROLE_GUEST })
public class CommentManagerBean implements CommentManagerLocal,
		CommentManagerRemote {

	ResourceBundle messages = BlogInternationalizationUtils.getMessages();

	private final Logger log = Logger.getLogger(this.getClass());
	
	ProfilManagerRemote profilBean ;
	
	@PersistenceContext
	private EntityManager em;
	public CommentManagerBean() {
        Context context;
        try {
            context = new InitialContext();
            profilBean = (ProfilManagerRemote) context.lookup(NameFactory.getName("BlogEAR/ProfilManagerBean/remote"));
        } catch (NamingException e) {
            e.printStackTrace();
        }
}
	/**
	 * Adds a comment
	 * 
	 * @param comment
	 * @throws ValidationException
	 * @throws AdminException
	 */
	@PermitAll
	public Comment addComment(final Comment comment) throws BlogException,
			ValidationException {
		try {
			EntityValidator.getInstance().validateEntity(comment);
		} catch (InternalValidationException e1) {
			throw e1.validationExceptionFactory(BlogInternationalizationUtils
					.getMessages());
		}
		try {
			em.persist(comment);
			IPost post = comment.getPostid();
			post.setPostlastupdate(comment.getCommentdate());
			em.merge(post);
			sendMail(post,comment.getCommentautor());
			BlogUtils.editPostHTMLSitemap(post);
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("comment.add.error"));
		}
		return comment;
	}

	private void sendMail(IPost post,String author){
        List<String> cats = new ArrayList<String>();
        for(ICategory cat : post.getCategoryCollection()){
            cats.add(cat.getCategorylabel());
        }
        Profil profil;
        try {
            profil = profilBean.findProfilUser(post.getUserid().getUserid());
            if(profil.getProfilmail() != null){
                MailerUtils.sendmailForNewComment(post.getPosttitle(), author, post.getPostdate(),profil.getProfilmail(),"#Post="+post.getPostid());
                }
        } catch (BlogException e) {
            
        }
    }
	
	/**
	 * 
	 * @param comment
	 */
	@RolesAllowed(Role.ROLE_USER)
	public void removeComment(final int commentId) throws BlogException {
		try {
			Comment comment = em.find(Comment.class, commentId);
			IPost post = comment.getPostid();
			post.getCommentCollection().remove(comment);
			post.setPostlastupdate(new Date());
			em.merge(post);
			em.remove(comment);
			BlogUtils.editPostHTMLSitemap(post);
		} catch (Exception e) {
			log.info(e);
			throw new BlogException(e, messages.getString("comment.del.error"));
		}

	}

	/**
	 * Warning : local access only !!!
	 * 
	 * @param post
	 */
	@RolesAllowed(Role.ROLE_USER)
	public void removeAllComments(final Post post) throws BlogException {
		for (IComment curComment : post.getCommentCollection()) {
			this.removeComment(curComment.getCommentid());
		}
	}

	/**
	 * 
	 * @param post
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PermitAll
	public List<Comment> findAllComments(final Post post) throws BlogException {
	    if (log.isInfoEnabled()){
            log.info("=================>Recherche des commentaires du Post numéro : "+post.getPostid());
        }
		try {
			return (List<Comment>) em.createQuery(
					"select object(o) from Comment as o where o.postid=\'"
							+ post.getPostid()
							+ "\' ORDER BY o.commentdate DESC").getResultList();
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("comment.find.error"));
		}

	}

	@SuppressWarnings("unchecked")
	@PermitAll
	public List<Comment> findComments(final int postid, int start, int end)
			throws BlogException {
	        log.debug("=================>Recherche des commentaires du Post numéro : "+postid+"-"+start+"-"+end);
	    if (start >= end) {
			return new ArrayList<Comment>();
		}
		try {
			return (List<Comment>) em.createQuery(
					"select object(o) from Comment as o where o.postid=\'"
							+ postid + "\' ORDER BY o.commentdate DESC").setFirstResult(start)
					.setMaxResults(end - start).getResultList();
		} catch (Exception e) {
			throw new BlogException(e, messages.getString("comment.find.error"));
		}
	}

	@PermitAll
	public int countComments(final Post post) throws BlogException {
		try {
		    return ((Long) em.createQuery("select count(*) from Comment as c where c.postid=\'"+ post.getPostid() + "\'")
                    .getSingleResult()).intValue();
		} catch (Exception e) {
			throw new BlogException(e, messages
					.getString("comment.count.error"));
		}
	}

}
