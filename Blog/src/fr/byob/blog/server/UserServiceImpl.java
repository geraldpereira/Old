package fr.byob.blog.server;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBAccessException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.sf.dozer.util.mapping.MapperIF;

import org.apache.log4j.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.byob.blog.client.Constants;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.client.model.UserGWT;
import fr.byob.blog.client.service.UserService;
import fr.byob.blog.ejb.BlogInternationalizationUtils;
import fr.byob.blog.ejb.deploy.BYOBSessionRemote;
import fr.byob.blog.ejb.deploy.UserManagerRemote;
import fr.byob.blog.ejb.entity.Role;
import fr.byob.blog.ejb.entity.User;
import fr.byob.blog.server.dozer.Mapper;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.NameFactory;

/**
 * Impl�mentation permettant des actions sur les utilisateurs
 * 
 * @author akemi
 * 
 */
public class UserServiceImpl extends RemoteServiceServlet implements UserService {

    private static final long serialVersionUID = 5518535747328421858L;

    Logger log = Logger.getLogger(this.getClass());
    
    private BYOBSessionRemote session;
    private static UserManagerRemote bean;

    private final static MapperIF mapper = Mapper.getMapper();

    /**
     * Constructeur
     */
    public UserServiceImpl() {
        Context context;
        try {
            context = new InitialContext();
            bean = (UserManagerRemote) context.lookup(NameFactory.getName("BlogEAR/UserManagerBean/remote"));
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
     * Ajoute un utilisateur
     * 
     * @param login
     *            son identifiant
     * @param password
     *            son mot de passe
     * @throws BlogException
     * @throws ValidationException
     */
    public IUser addUser(IUser user) throws BlogException, ValidationException {
        User userSrv = (User) mapper.map(user, User.class);
        try {
            userSrv = bean.addUser(userSrv);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        return (IUser) convertUser(bean.findUser(user.getUserid()));
    }

    /**
     * Supprime un utilisateur par rapport à son id
     * 
     * @param login
     *            l'id de l'utilisateur
     */
    public void removeUser(String login) throws BlogException{
        bean.removeUser(login);
    }

    /**
     * Retourne tous les utilisateurs de l'application
     * 
     * @return tous les utilisateurs
     * @throws BlogException
     */
    public List<IUser> findUsers() throws BlogException {
        List<User> usersSrv = null;
        try {
            usersSrv = bean.findAllUsers();
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        return converterUserList(usersSrv);
    }

    /**
     * Met à jour l'utilisateur
     * 
     * @param login
     *            identifiant
     * @param password
     *            mot de passe
     * @throws AdminException
     * @throws ValidationException
     */
    public IUser modifyUser(IUser user) throws BlogException, ValidationException {
        User userSrv = (User) mapper.map(user, User.class);
        try {
            userSrv = bean.editUser(userSrv);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        return (IUser) convertUser(bean.findUser(user.getUserid()));
    }

    public List<IUser> findUsers(final int start, final int end) throws BlogException {
        List<User> usersSrv = null;
        try {
            usersSrv = bean.findUsers(start, end);

        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
        List<IUser> users = converterUserList(usersSrv);
        return users;
    }

    public int countUsers() throws BlogException {
        try {
            return bean.countUsers();
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"), Constants.HOME_PAGE);
        }
    }

    /**
     * Retourne l'utilisateur connecté
     * 
     * @return l'utilisateur connecté
     */
    public IUser getConnectedUser() {
        User userSrv = session.getConnectedUser();
        try {
            if (userSrv != null){
                return convertUser(userSrv);
            }else{
                return null;
            }
        } catch (BlogException e) {
            return null;
        }
    }
    
    /**
     * Déconnecte l'utilisateur
     */
    public void logout(final String statut) {
        session.logout(statut);
    }
    

    protected static UserGWT convertUser (User user) throws BlogException{
        boolean isAdmin = false;
        UserGWT currentGWT = (UserGWT) mapper.map(user, UserGWT.class);
        Role role = bean.getUserRole(user);
        if (role != null && Role.ROLE_ADMIN.equals(role.getRole())) {
            isAdmin = true;
        }
        currentGWT.setAdmin(isAdmin);
        return currentGWT;
    }
    
    private List<IUser> converterUserList(List<User> users) throws BlogException {
        ArrayList<IUser> usersGWT = new ArrayList<IUser>();
        for (User current : users) {
            usersGWT.add(convertUser(current));
        }
        
        // Suppression des administrateurs pour les non administrateurs
        ArrayList<Integer> toRemove = new ArrayList<Integer>(); 
        
        if (this.getConnectedUser() == null || !((UserGWT)this.getConnectedUser()).isAdmin()){
            for (int i = 0 ; i < usersGWT.size(); i++ ){
                if (((UserGWT)usersGWT.get(i)).isAdmin()){
                    toRemove.add(i);
                }
            }
            for (int rev = toRemove.size() -1; rev >= 0 ; rev --){
                usersGWT.remove(toRemove.get(rev).intValue());
            }
        }
        return usersGWT;
    }
    
}
