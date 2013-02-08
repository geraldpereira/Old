package fr.byob.blog.server.test;

import java.util.ArrayList;
import java.util.List;

import net.sf.dozer.util.mapping.DozerBeanMapper;
import net.sf.dozer.util.mapping.MapperIF;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.byob.blog.client.IUser;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.client.model.UserGWT;
import fr.byob.blog.client.service.UserService;
import fr.byob.blog.ejb.BlogInternationalizationUtils;
import fr.byob.blog.ejb.entity.User;
import fr.byob.client.exception.ValidationException;
import fr.byob.validator.EntityValidator;
import fr.byob.validator.exception.InternalValidationException;

public class UserServiceImpl extends RemoteServiceServlet implements UserService {

    private static final long serialVersionUID = 5518535747328421858L;
    private final MapperIF mapper = new DozerBeanMapper();

    ArrayList<IUser> users = new ArrayList<IUser>();

    public UserServiceImpl() {
        createUser();
    }

    public synchronized IUser addUser(IUser user) throws BlogException, ValidationException {
        User userSrv = (User) mapper.map(user, User.class);
        try {
            EntityValidator.getInstance().validateEntity(userSrv);
        } catch (InternalValidationException e1) {
            throw e1.validationExceptionFactory(BlogInternationalizationUtils.getMessages());
        }
        users.add(user);
        return user;
    }

    public synchronized void removeUser(String login) {
        int i = 0;
        int index = -1;
        for (IUser user : users) {
            if (user.getUserid().equals(login)) {
                index = i;
                break;
            }
            i++;
        }
        if (index != -1) {
            users.remove(index);
        }
    }

    public synchronized IUser modifyUser(IUser user)throws ValidationException {
        User userSrv = (User) mapper.map(user, User.class);
        try {
            EntityValidator.getInstance().validateEntity(userSrv);
        } catch (InternalValidationException e1) {
            throw e1.validationExceptionFactory(BlogInternationalizationUtils.getMessages());
        }

        int i = 0;
        int index = -1;
        for (IUser curuser : users) {
            if (curuser.getUserid().equals(user.getUserid())) {
                index = i;
                break;
            }
            i++;
        }
        if (index != -1) {
            users.remove(index);
            users.add(index, user);
        }
        return user;
    }

    public synchronized List<IUser> findUsers() {
        return users;
    }
    public synchronized List<IUser> findUsers(final int start,final int end ) throws BlogException{
        List<IUser> usersSub = new ArrayList<IUser>();
        for(int i=start;i<end;i++){
         usersSub.add(users.get(i));   
        }
        return usersSub;
    }
    public synchronized int countUsers( ) throws BlogException{
        return users.size();
    }
    private void createUser() {
        UserGWT user = new UserGWT();
        user.setUserid("toto"+ (""+Math.random() * 1000).substring(0, 2));
        user.setPassword("vianca");
        UserGWT user1 = new UserGWT();
        user1.setUserid("toto"+ (""+Math.random() * 1000).substring(0, 2));
        user1.setPassword("iro");
        UserGWT user2 = new UserGWT();
        user2.setUserid("toto"+ (""+Math.random() * 1000).substring(0, 2));
        user2.setPassword("kojiro");
        UserGWT user3 = new UserGWT();
        user3.setUserid("toto"+ (""+Math.random() * 1000).substring(0, 2));
        user3.setPassword("gantoo");
        UserGWT user4 = new UserGWT();
        user4.setUserid("toto"+ (""+Math.random() * 1000).substring(0, 2));
        user4.setPassword("marekh");
        UserGWT user5 = new UserGWT();
        user5.setUserid("toto"+ (""+Math.random() * 1000).substring(0, 2));
        user5.setPassword("calbo");
//        UserGWT user6 = new UserGWT();
//        user6.setUserid("luna");
//        user6.setPassword("akmei");
//        UserGWT user7 = new UserGWT();
//        user7.setUserid("olympe");
//        user7.setPassword("luna");
//        UserGWT user8 = new UserGWT();
//        user8.setUserid("pioupiou");
//        user8.setPassword("olympe");
        users.add(user);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        // users.add(user6);
        // users.add(user7);
        // users.add(user8);
    }

    public IUser getConnectedUser() {
        UserGWT user = new UserGWT();
        double d = Math.random() * 1000;
        String dStr = (""+d).substring(0, 2);
        user.setUserid("toto"+ dStr);
        user.setAdmin(false);
        return user;
    }

    public void logout(String statut) {
        // TODO Auto-generated method stub
        
    }
}
