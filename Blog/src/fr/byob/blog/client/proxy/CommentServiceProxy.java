package fr.byob.blog.client.proxy;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import fr.byob.blog.client.IComment;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.model.CommentGWT;
import fr.byob.blog.client.service.CommentService;
import fr.byob.blog.client.service.CommentServiceAsync;
import fr.byob.client.om.service.IObjectToolbarManagementServiceProxy;

public class CommentServiceProxy implements IObjectToolbarManagementServiceProxy<IComment>{

    private CommentServiceAsync service;


    private IPost post;
    
    
    private final static CommentServiceProxy instance = new CommentServiceProxy();
    
    public static CommentServiceProxy getInstance(){
        return instance;
    }
    
    private CommentServiceProxy (){
        service = (CommentServiceAsync) GWT.create(CommentService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "commentService";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
        
    }
    
    public void addObject(IComment object, AsyncCallback<IComment> callback) {
        CommentGWT commentGWT = (CommentGWT)object;
        commentGWT.setPostid(post);
        service.addComment(commentGWT,callback);
    }

    public void findAllObjects(AsyncCallback<List<IComment>> callback) {
        service.findAllComments(post,callback);
    }

    public void removeObject(IComment object, AsyncCallback<?> callback) {
        CommentGWT commentGWT = (CommentGWT)object;
        service.removeComment(commentGWT, callback);
    }

    public void modifyObject(IComment object, AsyncCallback<IComment> callback) {
        throw new UnsupportedOperationException();
    }

    public void findObjects(int start, int end, AsyncCallback<List<IComment>> callback) {
        System.out.println("Comment Service Proxy findObjects");
        service.findComments(post,start, end, callback);
    }

    public void countObjects(AsyncCallback<Integer> callback) {
        service.countComments(post,callback);
    }

    public void setPost (IPost post){
//        Window.alert("SetPost : "+post);
        this.post = post;
    }
    
    public Object getServiceAsync (){
        return service;
    }
}
