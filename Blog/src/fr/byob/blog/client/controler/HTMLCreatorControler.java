package fr.byob.blog.client.controler;

import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import fr.byob.blog.client.service.BYOBService;
import fr.byob.blog.client.service.BYOBServiceAsync;
import fr.byob.blog.client.view.panel.HTMLCreatorPanel;
import fr.byob.client.exception.BYOBException;

public class HTMLCreatorControler {

    private BYOBServiceAsync service;

    private HTMLCreatorPanel view;

    private static HTMLCreatorControler instance = new HTMLCreatorControler();
    
    private HTMLCreatorControler() {
        service = (BYOBServiceAsync) GWT.create(BYOBService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "byobService";
        endpoint.setServiceEntryPoint(moduleRelativeURL);

    }
    
    public void setHTMLCreatorPanel (HTMLCreatorPanel view){
        this.view = view;
    }
    public static HTMLCreatorControler getInstance(){
        return instance;
    }

    public ClickHandler getClickCreatePostListener() {
        return new ClickHandler() {
            public void onClick(ClickEvent event) {
                createPost();
            }
        };
    }
    public ClickHandler getClickCreateLinkListener() {
        return new ClickHandler() {
            public void onClick(ClickEvent event) {
                createLink();
            }
        };
    }
    public ClickHandler getClickCreateUserListener() {
        return new ClickHandler() {
            public void onClick(ClickEvent event) {
                createUser();
            }
        };
    }
    public ClickHandler getClickCreateCategoryListener() {
        return new ClickHandler() {
            public void onClick(ClickEvent event) {
                createCategory();
            }
        };
    }

    private void createPost() {
        AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
                view.setConsoleText(BYOBException.getStringList(caught));
            }
            public void onSuccess(Boolean result) {
                view.setConsoleText(Arrays.asList( new String [] {"HTML créé"}));
            }
        };
        service.createHTMLPost(view.getPostId(), callback);
    }
    private void createUser() {
        AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
                view.setConsoleText(BYOBException.getStringList(caught));
            }
            public void onSuccess(Boolean result) {
                view.setConsoleText(Arrays.asList( new String [] {"HTML créé"}));
            }
        };
        service.createHTMLUser(view.getUserId(), callback);
    }
    private void createLink() {
        AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
                view.setConsoleText(BYOBException.getStringList(caught));
            }
            public void onSuccess(Boolean result) {
                view.setConsoleText(Arrays.asList( new String [] {"HTML créé"}));
            }
        };
        service.createHTMLLink(view.getLinkId(), callback);
    }
    private void createCategory() {
        AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
                view.setConsoleText(BYOBException.getStringList(caught));
            }
            public void onSuccess(Boolean result) {
                view.setConsoleText(Arrays.asList( new String [] {"HTML créé"}));
            }
        };
        service.createHTMLCategory(view.getCategoryId(), callback);
    }
}
