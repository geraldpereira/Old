package fr.byob.blog.server;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBAccessException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.sf.dozer.util.mapping.MapperIF;

import org.jboss.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.byob.blog.client.Constants;
import fr.byob.blog.client.ILink;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.client.model.LinkGWT;
import fr.byob.blog.client.service.LinkService;
import fr.byob.blog.ejb.BlogInternationalizationUtils;
import fr.byob.blog.ejb.deploy.LinkManagerRemote;
import fr.byob.blog.ejb.entity.Link;
import fr.byob.blog.server.dozer.Mapper;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.NameFactory;

/**
 * Impl�mentation permettant des actions sur les articles
 * 
 * @author akemi
 * 
 */
public class LinkServiceImpl extends RemoteServiceServlet implements LinkService {

    private static final long serialVersionUID = 1L;

    private final MapperIF mapper = Mapper.getMapper();

    private final Logger log = Logger.getLogger(this.getClass());
    
    private LinkManagerRemote bean;

    /**
     * Constructeur
     */
    public LinkServiceImpl() {
        Context context;
        try {
            context = new InitialContext();
            bean = (LinkManagerRemote) context.lookup(NameFactory.getName("BlogEAR/LinkManagerBean/remote"));
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
    public ILink addLink(ILink link) throws BlogException, ValidationException {
        Link linkSrv = (Link) mapper.map(link, Link.class);
        try {
            linkSrv = bean.addLink(linkSrv);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        return (LinkGWT) mapper.map(linkSrv, LinkGWT.class);
    }

    /**
     * Met � jour l'article
     * 
     * @param post
     *            l'article
     * @throws BlogException
     * @throws ValidationException
     */
    public ILink editLink(ILink link) throws BlogException, ValidationException {
        Link linkSrv = (Link) mapper.map(link, Link.class);
        try {
            linkSrv = bean.editLink(linkSrv);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        return (ILink) mapper.map(linkSrv, LinkGWT.class);
    }

    /**
     * Retourne tous les articles de l'application
     * 
     * @return tous les articles
     * @throws BlogException
     */
    public List<ILink> findAllLinks() throws BlogException {
        List<Link> linksSrv = null;
        try {
            linksSrv = bean.findAllLinks();
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        List<ILink> links = converterLinkList(linksSrv);
        return links;
    }

    /**
     * Retourne un article par rapport � son titre
     * 
     * @param title
     *            le titer
     * @return l'article trouv�
     * @throws BlogException
     */
    public LinkGWT findLink(String title) throws BlogException {
        try {
            Link link = bean.findLink(title);
            return (LinkGWT) mapper.map(link, LinkGWT.class);
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
    public void removeLink(ILink link) throws BlogException {
        try {
            bean.removeLink(link.getLinkid());
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
    public void removeLinkByTitle(String title) throws BlogException {
        try {
            Link link = bean.findLink(title);
            bean.removeLink(link.getLinkid());
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
    }

    public int countLinks() throws BlogException {
        try {
            return bean.countLinks();
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
    }

    public List<ILink> findLinks(int start, int end) throws BlogException {
        List<Link> linksSrv = null;
        try {
            linksSrv = bean.findLinks(start, end);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        List<ILink> links = converterLinkList(linksSrv);
        return links;
    }

    public List<ILink> findUserLinks(IUser user, int start, int end) throws BlogException {
        List<Link> linksSrv = null;
        try {
            linksSrv = bean.findUserLinks(user.getUserid(), start, end);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        List<ILink> links = converterLinkList(linksSrv);
        return links;
    }

    private List<ILink> converterLinkList (List<Link> links) throws BlogException{
        ArrayList<ILink>linksGWT = new ArrayList<ILink>();
        for (Link current : links) {
            linksGWT.add((LinkGWT) mapper.map(current, LinkGWT.class));
        }
        return linksGWT;
    }
}
