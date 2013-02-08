package fr.byob.blog.client.controler;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import fr.byob.blog.client.Constants;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.service.PostService;
import fr.byob.blog.client.service.PostServiceAsync;
import fr.byob.blog.client.view.panel.PostPanel;
import fr.byob.client.exception.BYOBException;
import fr.byob.client.util.Utils;
import fr.byob.client.view.model.ITableModel;

public class PostControler {

    private PostServiceAsync service;

    private static PostControler instance = new PostControler();
    
    public static PostControler getInstance(){
        return instance;
    }
    
    
    private PostControler() {
        service = (PostServiceAsync) GWT.create(PostService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "postService";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
    }

    
    public void findPost(int postid,final PostPanel view){
        AsyncCallback<IPost> callback = new AsyncCallback<IPost>() {
            public void onSuccess(IPost result) {
                if( !result.getPostisprivate()){
                    ITableModel<IPost> model = view.getTableModel();
                    ArrayList<IPost> singleList = new ArrayList<IPost>(1);
                    singleList.add(result);
                    model.setObjects(singleList);
                    model.selectObject(0);
                    view.deckCharged();
                }else{
                    Utils.redirect(Constants.NOT_LOGGED_PAGE);
                }
            }

            public void onFailure(Throwable caught) {
                view.setConsoleText(BYOBException.getStringList(caught));
            }
        };
        service.findPostId(postid, callback);
    }
}
