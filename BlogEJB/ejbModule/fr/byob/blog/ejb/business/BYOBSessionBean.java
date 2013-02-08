package fr.byob.blog.ejb.business;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.security.auth.login.LoginException;

import org.jboss.annotation.security.SecurityDomain;
import org.jboss.logging.Logger;
import org.jboss.web.tomcat.security.login.WebAuthentication;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.BlogUtils;
import fr.byob.blog.ejb.deploy.BYOBSessionRemote;
import fr.byob.blog.ejb.deploy.CategoryManagerRemote;
import fr.byob.blog.ejb.deploy.LinkManagerRemote;
import fr.byob.blog.ejb.deploy.PostManagerRemote;
import fr.byob.blog.ejb.deploy.ProfilManagerRemote;
import fr.byob.blog.ejb.entity.Category;
import fr.byob.blog.ejb.entity.Link;
import fr.byob.blog.ejb.entity.Post;
import fr.byob.blog.ejb.entity.Profil;
import fr.byob.blog.ejb.entity.Role;
import fr.byob.blog.ejb.entity.User;


/**
 * 
 * TODO N'autoriser un accès qu'aux Guest !
 * 
 * Pour cela il faudrait authentifier automatiquement les utilisateurs en
 * invités
 * 
 * @author gpereira
 * 
 */
@Stateless
@SecurityDomain(unauthenticatedPrincipal=Role.ROLE_GUEST,value="BYOBSecurity")
@DeclareRoles(value={Role.ROLE_ADMIN,Role.ROLE_USER,Role.ROLE_GUEST})
public class BYOBSessionBean implements BYOBSessionRemote {

    private static final long serialVersionUID = 7892459251835596718L;

    @EJB
    private UserManagerLocal userManagerBean;

    @EJB
    private ProfilManagerRemote profilManagerBean;
    
    @EJB
    private PostManagerRemote postManagerBean;
    
    @EJB
    private CategoryManagerRemote categoryManagerBean;
    
    @EJB
    private LinkManagerRemote linkManagerBean;

    @Resource
    private EJBContext context;
    Logger log= Logger.getLogger(this.getClass());

    public final static String SECURITY_DOMAIN = "BYOBSecurity";

    public BYOBSessionBean() {
        super();
    }

    /**
     * Retrieves the UserEntity object associated to the currently connected
     * object
     * 
     * @return The user object
     * @throws AdminException
     */
    @PermitAll
    public User getConnectedUser() {
        try {
            String login = context.getCallerPrincipal().getName();		
            return userManagerBean.findUser(login);
        }catch (Exception e){
        }
        return null;
    }

    /**
     * Disconnects the currently connected user, removing the associated EJB
     * session object
     * 
     * @throws LoginException
     */
    //	@RolesAllowed(value={Role.ROLE_ADMIN,Role.ROLE_USER,Role.ROLE_GUEST})
    @PermitAll
    public void logout(final String statut){
        String login = context.getCallerPrincipal().getName();
        if(statut != null){
            try {
                profilManagerBean.updateProfil(login,statut);
            } catch (Exception e) {
            }
        }
        WebAuthentication pwl = new WebAuthentication();
        pwl.logout();
    }
    @RolesAllowed(value={Role.ROLE_ADMIN})
    public void createHTMLCategory(final int id) throws BlogException{
        Category category = categoryManagerBean.findCategory(id);
        BlogUtils.createCategoryHTMLSitemap(category);
    }
    @RolesAllowed(value={Role.ROLE_ADMIN})
    public void createHTMLPost(final int id) throws BlogException {
        Post post = postManagerBean.findPostId(id);
        BlogUtils.createPostHTMLSitemap(post);
    }
    @RolesAllowed(value={Role.ROLE_ADMIN})
    public void createHTMLUser(final String id) throws BlogException {
        Profil profil = profilManagerBean.findProfilUser(id);
        BlogUtils.createProfilHTMLSitemap(profil);
    }
    @RolesAllowed(value={Role.ROLE_ADMIN})
    public void createHTMLLink(final int id) throws BlogException {
        Link link = linkManagerBean.findLinkId(id);
        BlogUtils.createLinkHTMLSitemap(link);
    }
    @PermitAll
    public List<String> getAlbums(){
        return BlogUtils.getDirectoryPhotos();
    }
    @PermitAll
    public List<String> getPhotos(String album){
        return BlogUtils.getPhotos(album);
    }
    @RolesAllowed(value={Role.ROLE_USER})
    public void addPhotoAlbum(String photo, String album) {
//        String login = context.getCallerPrincipal().getName();
        log.info("BYOBSessionBean : addPhoto : "+photo+" / "+album);
        try {
            BlogUtils.redimImage(photo,BlogUtils.acces_Photo+"/"+album);
//            sendMailPhoto(photo, album, login);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RolesAllowed(value={Role.ROLE_USER})
    public void addVideoAlbum(String photo, String album) {
//        String login = context.getCallerPrincipal().getName();
        log.info("BYOBSessionBean : addVideo : "+photo+" / "+album);
        try {
            BlogUtils.addVideo(photo,BlogUtils.acces_Photo+"/"+album);
//            sendMailVideo(photo, album, login);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RolesAllowed(value={Role.ROLE_USER})
    public void deleteMediaAlbum(String media) {
        BlogUtils.deletePhoto(media);
    }
    @RolesAllowed(value={Role.ROLE_USER})
    public void addAlbum(String album) {
        BlogUtils.addAlbum(BlogUtils.acces_Photo+"/"+album);
    }
    @RolesAllowed(value={Role.ROLE_USER})
    public void deleteAlbum(String album) {
        BlogUtils.deleteAlbum(BlogUtils.acces_Photo+"/"+album);
    }
    @RolesAllowed(value={Role.ROLE_USER})
    public boolean modifyAlbum(String oldDir,String newDir){
        log.debug("BYOBBean : modifyAlbum : old : "+(BlogUtils.acces_Photo+"/"+oldDir)+" / "+(BlogUtils.acces_Photo+"/"+newDir));
        return BlogUtils.modifyMedia(BlogUtils.acces_Photo+"/"+oldDir,BlogUtils.acces_Photo+"/"+newDir);
    }
    @RolesAllowed(value={Role.ROLE_USER})
    public boolean modifyMedia(String oldDir,String newDir){
        log.debug("BYOBBean : modifyMedia : old : "+(BlogUtils.acces_Photo+"/"+oldDir)+" / "+(BlogUtils.acces_Photo+"/"+newDir));
        return BlogUtils.modifyMedia(BlogUtils.acces_Photo+"/"+oldDir,BlogUtils.acces_Photo+"/"+newDir);
    }
//    private void sendMailPhoto(String titlePhoto,String album, String login){
//        try {
//            List<Profil> profils = profilManagerBean.findAllProfils();
//            for(Profil profil : profils){
//                if(profil.getProfilmail() != null && !profil.getProfilmail().equals("")){
//                    if(login != null){
//                    log.info("sendmailForNewVideo : "+titlePhoto+" / "+ login+" / "+ profil.getProfilmail()+" / "+ album);
//                    MailerUtils.sendmailForNewPhoto(titlePhoto, login, profil.getProfilmail(), album);
//                    }else{
//                        MailerUtils.sendmailForNewPhoto(titlePhoto, null, profil.getProfilmail(), album);
//                    }
//                }
//            }
//        } catch (BlogException e) {
//            
//        }
//    }
//    private void sendMailVideo(String titleVideo,String album, String login){
//        try {
//            List<Profil> profils = profilManagerBean.findAllProfils();
//            for(Profil profil : profils){
//                if(profil.getProfilmail() != null && !profil.getProfilmail().equals("")){
//                    if(login != null ){
//                        log.info("sendmailForNewVideo : "+titleVideo+" / "+ login+" / "+ profil.getProfilmail()+" / "+ album);
//                    MailerUtils.sendmailForNewVideo(titleVideo, login, profil.getProfilmail(), album);
//                    }else{
//                        MailerUtils.sendmailForNewVideo(titleVideo, null, profil.getProfilmail(), album);
//                    }
//                }
//            }
//        } catch (BlogException e) {
//            
//        }
//    }
}
