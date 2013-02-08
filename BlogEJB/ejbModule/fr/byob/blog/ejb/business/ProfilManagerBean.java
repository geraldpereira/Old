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

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.BlogInternationalizationUtils;
import fr.byob.blog.ejb.BlogUtils;
import fr.byob.blog.ejb.deploy.ProfilManagerRemote;
import fr.byob.blog.ejb.deploy.UserManagerRemote;
import fr.byob.blog.ejb.entity.Profil;
import fr.byob.blog.ejb.entity.Role;
import fr.byob.blog.ejb.entity.User;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.NameFactory;
import fr.byob.validator.EntityValidator;
import fr.byob.validator.exception.InternalValidationException;

@Stateless
@SecurityDomain(unauthenticatedPrincipal = Role.ROLE_GUEST, value = "BYOBSecurity")
@DeclareRoles( { Role.ROLE_ADMIN,Role.ROLE_USER, Role.ROLE_GUEST })
public class ProfilManagerBean implements ProfilManagerRemote {

    UserManagerRemote userBean ;

    ResourceBundle messages = BlogInternationalizationUtils.getMessages();

    Logger log = Logger.getLogger(this.getClass());

    public ProfilManagerBean() {
        Context context;
        try {
            context = new InitialContext();
            userBean = (UserManagerRemote) context.lookup(NameFactory.getName("BlogEAR/UserManagerBean/remote"));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @PersistenceContext
    private EntityManager em;


    /**
     * Warning : don't change the link'id !
     * 
     * @param link
     * @throws ValidationException
     */
    @RolesAllowed(Role.ROLE_USER)
    public Profil editProfil(final Profil profil) throws BlogException,
    ValidationException {
        try{
            EntityValidator.getInstance().validateEntity(profil);
        } catch (InternalValidationException e1) {
            throw e1.validationExceptionFactory(BlogInternationalizationUtils.getMessages());
        }
        User user = (User) profil.getUserid();
        if(user.getPassword() != null){
            user = userBean.editUser(user);
        }else{
            user = userBean.findUser(user.getUserid());
        }
        profil.setProfilid(this.findProfilUser(user.getUserid()).getProfilid());
        profil.setUserid(user);
        try {
            em.merge(profil);
            BlogUtils.editProfilHTMLSitemap(profil);
        } catch (Exception e) {
            throw new BlogException(e,
                    messages.getString("profil.mod.error"));
        }
        return profil;
    }
    @PermitAll
    public void updateProfil(final String userid, final String profilstatut) throws BlogException {
        Profil profil = this.findProfilUser(userid);
        profil.setProfildate(new Date());
        if(profilstatut != null){
            profil.setProfilstatut(profilstatut);
        }
        try {
            em.merge(profil);
            BlogUtils.editProfilHTMLSitemap(profil);
        } catch (Exception e) {
            throw new BlogException(e,
                    messages.getString("profil.mod.error"));
        }
    }

    @SuppressWarnings("unchecked")
    @PermitAll
    public List<Profil> findAllProfils() throws BlogException {
        try {
            return (List<Profil>) em.createQuery(
            "select object(o) from Profil as o order by o.userid ASC").getResultList();
        } catch (Exception e) {
            throw new BlogException(e, messages.getString("profil.find.error"));
        }
    }

    @SuppressWarnings("unchecked")
    @PermitAll
    public List<Profil> findProfils(int start, int end) throws BlogException {
        if (start >= end) {
            return new ArrayList<Profil>();
        }
        try {
            return (List<Profil>) em.createQuery(
            "select object(o) from Profil as o order by o.userid ASC").setFirstResult(start)
            .setMaxResults(end - start).getResultList();
        } catch (Exception e) {
            throw new BlogException(e, messages.getString("profil.find.error"));
        }
    }

    @PermitAll
    public int countProfils() throws BlogException {
        try {
            return ((Long) em.createQuery("select count(*) from Profil")
                    .getSingleResult()).intValue();
        } catch (Exception e) {
            throw new BlogException(e, messages.getString("profil.count.error"));
        }
    }

    @PermitAll
    public Profil findProfilUser(final String userid) throws BlogException {
        User user = userBean.findUser(userid);
        if(user != null){

            try {
                return (Profil) em.createQuery(
                        "select object(p) from Profil as p,User as userid where p.userid.userid = userid.userid and userid.userid = '"
                        + userid + "'").getSingleResult();
            } catch (Exception e) {
                throw new BlogException(e, messages.getString("profil.finduser.error")	+" "+ userid);
            }
        }else{
            throw new BlogException(null, messages.getString("profil.finduser.error")  +" "+ userid);
        }
    }
    @PermitAll
    public Profil findProfil(final int profilid) throws BlogException {
        try {
            return em.find(Profil.class, profilid);
        } catch (Exception e) {
            throw new BlogException(e, messages.getString("profil.find.error")  + profilid);
        }
    }
    @PermitAll
    public String findUser(final int profilid) throws BlogException {
        try {
            Profil profil = em.find(Profil.class, profilid);
            return profil.getUserid().getUserid();
        } catch (Exception e) {
            throw new BlogException(e, messages.getString("profil.finduser.error")  + profilid);
        }
    }
}
