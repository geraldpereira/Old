package fr.byob.blog.server;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBAccessException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.NoResultException;

import net.sf.dozer.util.mapping.MapperIF;
import net.sf.dozer.util.mapping.MappingException;

import org.jboss.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.byob.blog.client.Constants;
import fr.byob.blog.client.IProfil;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.client.model.ProfilGWT;
import fr.byob.blog.client.service.ProfilService;
import fr.byob.blog.ejb.BlogInternationalizationUtils;
import fr.byob.blog.ejb.deploy.ProfilManagerRemote;
import fr.byob.blog.ejb.entity.Profil;
import fr.byob.blog.server.dozer.Mapper;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.NameFactory;
import fr.byob.server.util.PasswordUtils;

/**
 * Impl�mentation permettant des actions sur les articles
 * 
 * @author akemi
 * 
 */
public class ProfilServiceImpl extends RemoteServiceServlet implements ProfilService {
//TODO à modifier
    private static final long serialVersionUID = 1L;

    private final MapperIF mapper = Mapper.getMapper();

    private final Logger log = Logger.getLogger(this.getClass());
    
    private ProfilManagerRemote bean;

    /**
     * Constructeur
     */
    public ProfilServiceImpl() {
        Context context;
        try {
            context = new InitialContext();
            bean = (ProfilManagerRemote) context.lookup(NameFactory.getName("BlogEAR/ProfilManagerBean/remote"));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public IProfil editProfil(IProfil profil) throws BlogException, ValidationException {
        Profil profilSrv = (Profil) mapper.map(profil, Profil.class);
        try {
            profilSrv = bean.editProfil(profilSrv);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        return (IProfil) mapper.map(profilSrv, ProfilGWT.class);
    }

    public boolean verifPassword(String oldPassword,String confOldPassword) throws BlogException, ValidationException {
        try {
            log.debug("oldPassword : "+oldPassword);
            log.debug("confOldPassword : "+PasswordUtils.hashPassword(confOldPassword));
            if(oldPassword.equals(PasswordUtils.hashPassword(confOldPassword))){
                return true;
            }else{
                throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("user.pwdConf.error"), Constants.HOME_PAGE);
            }
        } catch (NoSuchAlgorithmException e1) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("user.pwd.error"), Constants.HOME_PAGE);
        } catch (UnsupportedEncodingException e1) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("user.pwd.error"), Constants.HOME_PAGE);
        }
    }

    public List<IProfil> findAllProfils() throws BlogException {
        List<Profil> profilsSrv = null;
        try {
            profilsSrv = bean.findAllProfils();
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        List<IProfil> posts = converterProfilList(profilsSrv);
        return posts;
    }

    public int countProfils() throws BlogException {
        try {
            return bean.countProfils();
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
    }

    public List<IProfil> findProfils(int start, int end) throws BlogException {
        List<Profil> profilsSrv = null;
        try {
            profilsSrv = bean.findProfils(start, end);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        List<IProfil> profils = converterProfilList(profilsSrv);
        return profils;
    }

    private List<IProfil> converterProfilList(List<Profil> profils) throws BlogException {
        ArrayList<IProfil> profilsGWT = new ArrayList<IProfil>();
        for (IProfil current : profils) {
            profilsGWT.add((ProfilGWT) mapper.map(current, ProfilGWT.class));
        }
        return profilsGWT;
    }

    public IProfil findProfil(String userid) throws BlogException {
        Profil profilSrv = null;
        try {
            profilSrv = bean.findProfilUser(userid);
            return (IProfil) mapper.map(profilSrv, ProfilGWT.class);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        } catch (MappingException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("profil.nouser.error"), Constants.HOME_PAGE);
        } catch (NoResultException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("profil.nouser.error"), Constants.HOME_PAGE);
        }
        
    }
   
}
