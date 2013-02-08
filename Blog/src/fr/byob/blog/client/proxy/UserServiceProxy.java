package fr.byob.blog.client.proxy;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import fr.byob.blog.client.IUser;
import fr.byob.blog.client.service.UserService;
import fr.byob.blog.client.service.UserServiceAsync;
import fr.byob.client.om.service.IObjectToolbarManagementServiceProxy;

public class UserServiceProxy implements IObjectToolbarManagementServiceProxy<IUser>{

    
    private UserServiceAsync service;
    
    private final static UserServiceProxy instance = new UserServiceProxy();
    
    public static UserServiceProxy getInstance(){
        return instance;
    }
    
    private UserServiceProxy (){
        service = (UserServiceAsync) GWT.create(UserService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "userService";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
    }
    
    public void addObject(IUser object, AsyncCallback<IUser> callback) {
        service.addUser(object,callback);
    }

    public void findAllObjects(AsyncCallback<List<IUser>> callback) {
        service.findUsers(callback);
    }

    public void removeObject(IUser object, AsyncCallback<?> callback) {
        service.removeUser(object.getUserid(), callback);
    }

    public void modifyObject(IUser object, AsyncCallback<IUser> callback) {
        service.modifyUser(object, callback);
    }

    public void findObjects(int nbStart, int nbEnd, AsyncCallback<List<IUser>> callback) {
        service.findUsers(nbStart, nbEnd, callback);
     }

     public void countObjects(AsyncCallback<Integer> callback) {
         service.countUsers(callback);
     }
     public Object getServiceAsync (){
         return service;
     }
}
