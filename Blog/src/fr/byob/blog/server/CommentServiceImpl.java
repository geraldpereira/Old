package fr.byob.blog.server;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBAccessException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import net.sf.dozer.util.mapping.MapperIF;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.byob.blog.client.Constants;
import fr.byob.blog.client.IComment;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.client.model.CommentGWT;
import fr.byob.blog.client.service.CommentService;
import fr.byob.blog.ejb.BlogInternationalizationUtils;
import fr.byob.blog.ejb.deploy.CommentManagerRemote;
import fr.byob.blog.ejb.entity.Comment;
import fr.byob.blog.ejb.entity.Post;
import fr.byob.blog.server.dozer.Mapper;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.NameFactory;

/**
 * Impl�mentation permettant des actions sur les commentaires
 * 
 * @author akemi
 * 
 */
public class CommentServiceImpl extends RemoteServiceServlet implements CommentService {

    Logger log = Logger.getLogger(this.getClass());
    
    private static final long serialVersionUID = 1L;

    private final MapperIF mapper = Mapper.getMapper();
    private CommentManagerRemote bean;

    /**
     * Constructeur
     */
    public CommentServiceImpl() {
        Context context;
        try {
            context = new InitialContext();
            bean = (CommentManagerRemote) context.lookup(NameFactory.getName("BlogEAR/CommentManagerBean/remote"));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajoute un commentaire
     * 
     * @param comment
     *            commentaire � ajouter
     * @throws BlogException
     * @throws ValidationException
     */
    // @Override
    public IComment addComment(final IComment comment) throws BlogException, ValidationException {
        Comment commentSrv = (Comment) mapper.map(comment, Comment.class);
        try {
            commentSrv = bean.addComment(commentSrv);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"),Constants.HOME_PAGE);
        }
        return (CommentGWT) mapper.map(commentSrv, CommentGWT.class);
    }

    /**
     * Retourne tous les commentaires de l'application
     * 
     * @return tous les commentaires
     * @throws BlogException
     */
    // @Override
    public List<IComment> findAllComments(IPost post) throws BlogException {
        List<Comment> commentsSrv = null;
        try {
            Post postSrv = (Post) mapper.map(post, Post.class);
            commentsSrv = bean.findAllComments(postSrv);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"),Constants.HOME_PAGE);
        }
        List<IComment> comments = converterCommentList(commentsSrv);
        return comments;
    }

    public List<IComment> findAllComments() throws BlogException {
        return null;
    }

    /**
     * Supprime tous les commentaires d'un article
     * 
     * @param post
     *            le post
     * @throws BlogException
     */
    // @Override
    public void removeAllComments(IPost post) throws BlogException {
        try {
            Post postSrv = (Post) mapper.map(post, Post.class);
            bean.removeAllComments(postSrv);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"),Constants.HOME_PAGE);
        }
    }

    /**
     * Supprime un commentaire
     * 
     * @param comment
     *            le commentaire
     * @throws BlogException
     */
    // @Override
    public void removeComment(IComment comment) throws BlogException {
        
        try {
            bean.removeComment( comment.getCommentid());
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"),Constants.HOME_PAGE);
        }
    }

    public List<IComment> findComments(IPost post, int start, int end) throws BlogException {
        log.debug("findComments post : "+post.getPostid()+" / "+start+" / "+end);
        if (post == null){
            return new ArrayList<IComment>();
        }
        log.debug("findComments post : 0");
        List<Comment> commentsSrv = null;
        try {
            log.debug("findComments post : 1");
            commentsSrv = bean.findComments(post.getPostid(),start,end);
            log.debug("findComments post : 2");
            
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"),Constants.HOME_PAGE);
        }
        log.debug("findComments post : 3");
        List<IComment> comments = converterCommentList(commentsSrv);
        log.debug("findComments post : 4");
        return comments;
    }

    public int countComments(IPost post) throws BlogException {
        if (post == null){
            return 0;
        }
        try {
            Post postSrv = (Post) mapper.map(post, Post.class);
            return bean.countComments(postSrv);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"),Constants.HOME_PAGE);
        }
    }
    
    private List<IComment> converterCommentList (List<Comment> comments) throws BlogException{
        ArrayList<IComment>commentsGWT = new ArrayList<IComment>();
        for (Comment current : comments) {
            commentsGWT.add((CommentGWT) mapper.map(current, CommentGWT.class));
        }
        return commentsGWT;
    }

}
