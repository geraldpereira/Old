package fr.byob.blog.server;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.client.service.BYOBService;
import fr.byob.blog.ejb.BlogUtils;
import fr.byob.blog.ejb.deploy.BYOBSessionRemote;
import fr.byob.server.util.NameFactory;

/**
 * Impl�mentation permettant des actions sur les utilisateurs
 * 
 * @author akemi
 * 
 */
public class BYOBServiceImpl extends RemoteServiceServlet implements BYOBService {

    private static final long serialVersionUID = 5518535747328421858L;
    Logger log = Logger.getLogger(this.getClass());
    
    private BYOBSessionRemote session;


    /**
     * Constructeur
     */
    public BYOBServiceImpl() {
        Context context;
        try {
            context = new InitialContext();
            session = (BYOBSessionRemote) context.lookup(NameFactory.getName("BlogEAR/BYOBSessionBean/remote"));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public void createHTMLCategory(final int id) throws BlogException {
        session.createHTMLCategory(id);
    }

    public void createHTMLLink(final int id) throws BlogException {
        session.createHTMLLink(id);
    }

    public void createHTMLPost(final int id) throws BlogException {
        session.createHTMLPost(id);
    }

    public void createHTMLUser(final String id) throws BlogException {
        session.createHTMLUser(id);
    }

    public List<String> getAlbums() {
        return session.getAlbums();
    }

    public List<String> getPhotos(String album) {
        return session.getPhotos(album);
    }
    public void addPhotoAlbum(String photo, String album) {
        log.debug("addPhotoAlbum 0 "+photo+" / "+album+" / ");
        session.addPhotoAlbum(photo, BlogUtils.acces_Photo+"/"+album);
        log.debug("addPhotoAlbum 1");
    }
    public void addVideoAlbum(String video, String album) {
        log.debug("addvideoAlbum 0 "+video+" / "+album+" / ");
        session.addVideoAlbum(video, BlogUtils.acces_Photo+"/"+album);
        log.debug("addPhotoAlbum 1");
    }
    
    public void deleteMediaAlbum(String media) {
        session.deleteMediaAlbum(media);
    }
    public void addAlbum(String album) {
        log.debug("addAlbum byobserviceImpl 0");
       session.addAlbum(album);
       log.debug("addAlbum byobserviceImpl fin");
    }
    public void deleteAlbum(String album) {
        session.deleteAlbum(album);
    }
    public boolean modifyAlbum(String oldDir,String newDir){
        log.debug("modifyAlbum");
        return session.modifyAlbum(oldDir, newDir);
    }
    public boolean modifyMedia(String oldName,String newName){
        log.debug("modifyMedia : old : "+oldName+" / new : "+newName);
        return session.modifyMedia(oldName, newName);
    }
}
