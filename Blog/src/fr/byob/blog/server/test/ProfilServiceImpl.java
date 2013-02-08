package fr.byob.blog.server.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.byob.blog.client.IProfil;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.client.model.ProfilGWT;
import fr.byob.blog.client.service.ProfilService;
import fr.byob.client.exception.ValidationException;

/**
 * Impl�mentation permettant des actions sur les articles
 * 
 * @author akemi
 * 
 */
public class ProfilServiceImpl extends RemoteServiceServlet implements ProfilService {

    private static final long serialVersionUID = 1L;

    private final ArrayList<IProfil> profils = new ArrayList<IProfil>();
    
    private final UserServiceImpl userService = new UserServiceImpl();
    private int id = 0;

    /**
     * Constructeur
     */
    public ProfilServiceImpl() {
        for (id = 0; id <5 ; id++){
            ProfilGWT profilGWT = new ProfilGWT();
            profilGWT.setProfilcss("blanc");
            profilGWT.setProfildate(new Date());
            profilGWT.setProfilinscription(new Date());
            profilGWT.setProfilid(id);
            profilGWT.setProfilsite("http://www.site.fr");
            profilGWT.setProfilmail("byob@byob.fr");
//            profilGWT.setProfilphoto("http://www.univers-plongee.com/upload/191207_113706_PEEL_s3CR2T.jpg");
            profilGWT.setProfilpresentation("Le weekend  <3 :) ;) x-( :-o a en fait "+id);
            //Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimme uuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! 
            profilGWT.setUserid(userService.findUsers().get(id%6));

            profils.add(profilGWT);
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
    public IProfil addProfil(IProfil profil) throws BlogException, ValidationException {
        profil.setProfilid(++id);
        profils.add(profil);
        return profil;
    }

    /**
     * Met � jour l'article
     * 
     * @param post
     *            l'article
     * @throws BlogException
     * @throws ValidationException
     */
    public IProfil editProfil(IProfil profil) throws BlogException, ValidationException {
        for (IProfil profilt : profils){
            if (profilt.getProfilid()== profil.getProfilid()){
                profils.remove(profilt);
                profils.add(profil);
                return profil;
            }
        }   
        return null;
    }

    /**
     * Retourne tous les articles de l'application
     * 
     * @return tous les articles
     * @throws BlogException
     */
    public List<IProfil> findAllProfils() throws BlogException {
        return profils;
    }

    /**
     * Retourne un article par rapport � son titre
     * 
     * @param title
     *            le titer
     * @return l'article trouv�
     * @throws BlogException
     */
//    public ProfilGWT findProfil(String title) throws BlogException {
//        try {
//            Profil post = bean.findProfil(title);
//            return (ProfilGWT) mapper.map(post, ProfilGWT.class);
//        } catch (EJBAccessException e) {
//            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
//        }
//    }

    /**
     * Supprime un article
     * 
     * @param post
     *            article
     * @throws BlogException
     */
    public void removeProfil(IProfil profil) throws BlogException {
        int i = 0;
        int index = -1;
        for (IProfil p : profils) {
            if (p.getProfilid()== profil.getProfilid()) {
                index = i;
                break;
            }
            i++;
        }
        if (index != -1) {
            profils.remove(index);
        }   
    }

    /**
     * Supprime un article par rapport � son titre
     * 
     * @param title
     *            le titre de l'article
     * @throws BlogException
     */
//    public void removePostByTitle(String title) throws BlogException {
//        try {
//            Profil profil = bean.findProfil(title);
//            bean.removeProfil(profil.getProfilid());
//        } catch (EJBAccessException e) {
//            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
//        }
//    }

    public int countProfils() throws BlogException {
        return profils.size();
    }

    public List<IProfil> findProfils(int start, int end) throws BlogException {
        List<IProfil> profilsSub = new ArrayList<IProfil>();
        for(int i=start;i<end;i++){
            profilsSub.add(profils.get(i));   
        }
        return profilsSub;
    }

    public IProfil findProfil(String userid) throws BlogException {
        ProfilGWT profilGWT = new ProfilGWT();
        profilGWT.setProfilcss("blanc");
        profilGWT.setProfildate(new Date());
        profilGWT.setProfilinscription(new Date());
        profilGWT.setProfilid(id);
        profilGWT.setProfilmail("byob@byob.fr");
//        profilGWT.setProfilphoto("http://www.univers-plongee.com/upload/191207_113706_PEEL_s3CR2T.jpg");
//        profilGWT.setProfilpresentation("Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! Emilie je t'aiiiimmeuuuuu ! "+id);
        profilGWT.setUserid(userService.findUsers().get((int)(Math.random()*6)));
        return profilGWT;
    }

    public boolean verifPassword(String oldPassword, String confOldPassword) throws BlogException, ValidationException {
        if(oldPassword.equals(confOldPassword)){
            return true;
        }
        return false;
    }

//    public List<IPost> findUserPosts(IUser user, int start, int end) throws BlogException {
//        List<Post> postsSrv = null;
//        try {
//            postsSrv = bean.findUserPosts(user.getUserid(), start, end);
//        } catch (EJBAccessException e) {
//            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
//        }
//        List<IPost> posts = converterPostList(postsSrv);
//        return posts;
//    }

   
}
