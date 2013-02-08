package fr.byob.blog.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.ejb.EJBAccessException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.sf.dozer.util.mapping.MapperIF;
import net.sf.dozer.util.mapping.MappingException;

import org.jboss.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.byob.blog.client.Constants;
import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.client.model.PostGWT;
import fr.byob.blog.client.service.PostService;
import fr.byob.blog.ejb.BlogInternationalizationUtils;
import fr.byob.blog.ejb.deploy.BYOBSessionRemote;
import fr.byob.blog.ejb.deploy.PostManagerRemote;
import fr.byob.blog.ejb.entity.Post;
import fr.byob.blog.ejb.entity.User;
import fr.byob.blog.server.dozer.Mapper;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.NameFactory;
import fr.byob.server.util.ejbql.BetweenDatesCriteria;
import fr.byob.server.util.ejbql.BooleanCriteria;
import fr.byob.server.util.ejbql.ICriteria;
import fr.byob.server.util.ejbql.MemberOfCriteria;
import fr.byob.server.util.ejbql.StringListCriteria;

/**
 * Impl�mentation permettant des actions sur les articles
 * 
 * @author akemi
 * 
 */
public class PostServiceImpl extends RemoteServiceServlet implements PostService {

    private static final long serialVersionUID = 1L;

    private final MapperIF mapper = Mapper.getMapper();

    private final Logger log = Logger.getLogger(this.getClass());
    
    private PostManagerRemote bean;
    private BYOBSessionRemote session;
    
    /**
     * Constructeur
     */
    public PostServiceImpl() {
        Context context;
        try {
            context = new InitialContext();
            bean = (PostManagerRemote) context.lookup(NameFactory.getName("BlogEAR/PostManagerBean/remote"));
        } catch (NamingException e) {
            e.printStackTrace();
        }
        try {
            context = new InitialContext();
            session = (BYOBSessionRemote) context.lookup(NameFactory.getName("BlogEAR/BYOBSessionBean/remote"));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajoute un article
     * 
     * @param post
     *            article � ajouter
     * @throws BlogException
     * @throws ValidationException
     */
    public IPost addPost(IPost post) throws BlogException, ValidationException {
        Post postSrv = (Post) mapper.map(post, Post.class);
        try {
            postSrv = bean.addPost(postSrv);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        return (PostGWT) mapper.map(postSrv, PostGWT.class);
    }

    /**
     * Met � jour l'article
     * 
     * @param post
     *            l'article
     * @throws BlogException
     * @throws ValidationException
     */
    public IPost editPost(IPost post) throws BlogException, ValidationException {
        Post postSrv = (Post) mapper.map(post, Post.class);
        try {
            postSrv = bean.editPost(postSrv);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        return (IPost) mapper.map(postSrv, PostGWT.class);
    }

    /**
     * Retourne tous les articles de l'application
     * 
     * @return tous les articles
     * @throws BlogException
     */
    public List<IPost> findAllPosts() throws BlogException {
        List<Post> postsSrv = null;
        try {
            if(getConnectedUser() != null){
                postsSrv = bean.findAllPosts();
            }else{
                postsSrv = bean.findAllPostsNotPrivate();
            }
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        List<IPost> posts = converterPostList(postsSrv);
        return posts;
    }

    /**
     * Retourne un article par rapport � son titre
     * 
     * @param title
     *            le titer
     * @return l'article trouv�
     * @throws BlogException
     */
    public PostGWT findPost(String title) throws BlogException {
        try {
            Post post = bean.findPost(title);
            return (PostGWT) mapper.map(post, PostGWT.class);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
    }

    /**
     * Supprime un article
     * 
     * @param post
     *            article
     * @throws BlogException
     */
    public void removePost(IPost post) throws BlogException {
        try {
            bean.removePost(post.getPostid());
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
    }

    /**
     * Supprime un article par rapport � son titre
     * 
     * @param title
     *            le titre de l'article
     * @throws BlogException
     */
    public void removePostByTitle(String title) throws BlogException {
        try {
            Post post = bean.findPost(title);
            bean.removePost(post.getPostid());
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
    }

    public int countPosts() throws BlogException {
        try {
            return bean.countPosts();
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
    }

    public List<IPost> findPosts(int start, int end) throws BlogException {
        List<Post> postsSrv = null;
        try {
            log.info("connectedUSer findAllPost !!");
            if(getConnectedUser() != null){
                log.info("connectedUSer findAllPost pas null !!");
                postsSrv = bean.findPosts(start, end);
            }else{
                log.info("connectedUSer findAllPost null!!");
                postsSrv = bean.findPostsNotPrivate(start,end);
            }
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        List<IPost> posts = converterPostList(postsSrv);
        return posts;
    }

    private List<IPost> converterPostList(List<Post> posts) throws BlogException {
        ArrayList<IPost> postsGWT = new ArrayList<IPost>();
        for (Post current : posts) {
            postsGWT.add((PostGWT) mapper.map(current, PostGWT.class));
        }
        return postsGWT;
    }

    public List<IPost> findUserPosts(IUser user, int start, int end) throws BlogException {
        List<Post> postsSrv = null;
        try {
            postsSrv = bean.findUserPosts(user.getUserid(), start, end);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        List<IPost> posts = converterPostList(postsSrv);
        return posts;
    }

    public List<IPost> findPostsByCriteria(Integer startIndice, Integer endIndice, Set<IUser> authors, Set<ICategory> categories, String text,
            String title, Date start, Date end) throws BlogException {

        HashMap<String, ICriteria> criterias = getCriterias(authors, categories, text, title, start, end);
        
        List<Post> postsSrv = null;
        try {
            postsSrv = bean.findPostsByCriteriaNotPrivate(criterias, startIndice, endIndice);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        if (postsSrv != null && !postsSrv.isEmpty()) {
            List<IPost> posts = converterPostList(postsSrv);
            return posts;
        }
        return new ArrayList<IPost>();
    }

    public int countPostsByCriteria(Integer startIndice, Integer endIndice, Set<IUser> authors, Set<ICategory> categories, String text, String title,
            Date start, Date end) throws BlogException {
        HashMap<String, ICriteria> criterias = getCriterias(authors, categories, text, title, start, end);
        
        try {
            return bean.countPostsByCriteria(criterias, startIndice, endIndice);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
    }

    private HashMap<String, ICriteria> getCriterias(Set<IUser> authors, Set<ICategory> categories, String text, String title, Date start, Date end) {
        HashMap<String, ICriteria> criterias = new HashMap<String, ICriteria>();

        if (authors != null && !authors.isEmpty()) {
            List<String> authorLogins = new ArrayList<String>(authors.size());
            for (IUser user : authors) {
                authorLogins.add(user.getUserid());
            }
            criterias.put(PostManagerRemote.USER_CRITERIA, new StringListCriteria(authorLogins, StringListCriteria.OR, StringListCriteria.EQUAL));
        }

        if (categories != null && !categories.isEmpty()) {
            List<String> categoriesId = new ArrayList<String>(categories.size());
            for (ICategory category : categories) {
                categoriesId.add("" + category.getCategoryid());
            }
            criterias.put(PostManagerRemote.CATEGORY_CRITERIA, new MemberOfCriteria(categoriesId));
        }

        if (title != null && !"".equals(title.trim())) {
            String[] titles = title.trim().split(" ");
            criterias.put(PostManagerRemote.TITLE_CRITERIA, new StringListCriteria(Arrays.asList(titles), StringListCriteria.AND,
                    StringListCriteria.LIKE));
        }

        if (text != null && !"".equals(text.trim())) {
            String[] texts = text.trim().split(" ");
            criterias.put(PostManagerRemote.TEXT_CRITERIA, new StringListCriteria(Arrays.asList(texts), StringListCriteria.AND,
                    StringListCriteria.LIKE));
        }
       
        if (start != null && end != null) {
            criterias.put(PostManagerRemote.DATE_CRITERIA, new BetweenDatesCriteria(start, end));
        }
        if(getConnectedUser() == null){
            criterias.put(PostManagerRemote.PRIVATE_CRITERIA, new BooleanCriteria(false,BooleanCriteria.AND,BooleanCriteria.EQUAL));
        }
        return criterias;
    }

    public int countUserPosts(IUser user) throws BlogException {
        try {
            return bean.countUserPosts(user.getUserid());
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
    }

    public IPost findPostId(int postid) throws BlogException {
        try {
            Post post = bean.findPostId(postid);
            log.debug("findPaostId : "+post.getPosttext());
            return (PostGWT) mapper.map(post, PostGWT.class);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        } catch (MappingException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("post.no.error"), Constants.HOME_PAGE);
        }
    }
    public IUser getConnectedUser() {
        User userSrv = session.getConnectedUser();
        try {
            if (userSrv != null){
                return UserServiceImpl.convertUser(userSrv);
            }else{
                return null;
            }
        } catch (BlogException e) {
            return null;
        }
    }
}
