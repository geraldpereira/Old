package fr.byob.blog.client.proxy;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import fr.byob.blog.client.IPost;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.model.PostGWT;
import fr.byob.blog.client.service.PostService;
import fr.byob.blog.client.service.PostServiceAsync;
import fr.byob.client.om.service.IObjectToolbarManagementServiceProxy;

public class PostServiceProxy implements IObjectToolbarManagementServiceProxy<IPost>{

    private PostServiceAsync service;
    
    private final static PostServiceProxy instance = new PostServiceProxy();
    
    public static PostServiceProxy getInstance(){
        return instance;
    }
    
    private PostServiceProxy (){
        service = (PostServiceAsync) GWT.create(PostService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "postService";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
    }
    
    public void addObject(IPost object, AsyncCallback<IPost> callback) {
    	PostGWT post = (PostGWT)object;
    	IUser user = ConnectedUserModel.getInstance().getConnectedUser();
    	post.setUserid(user);
        service.addPost(post,callback);
    }

    public void findAllObjects(AsyncCallback<List<IPost>> callback) {
        service.findAllPosts(callback);
    }

    public void removeObject(IPost object, AsyncCallback<?> callback) {
        service.removePostByTitle(((IPost)object).getPosttitle(), callback);
    }

    public void modifyObject(IPost object, AsyncCallback<IPost> callback) {
    	PostGWT post = (PostGWT)object;
        service.editPost(post, callback);
    }

    public void findObjects(int start, int end, AsyncCallback<List<IPost>> callback) {
        service.findUserPosts(ConnectedUserModel.getInstance().getConnectedUser(), start, end, callback);
    }

    public void countObjects(AsyncCallback<Integer> callback) {
        service.countUserPosts(ConnectedUserModel.getInstance().getConnectedUser(),callback);
    }
    public Object getServiceAsync (){
        return service;
    }

}
