package fr.byob.blog.client.proxy;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import fr.byob.blog.client.ILink;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.model.LinkGWT;
import fr.byob.blog.client.service.LinkService;
import fr.byob.blog.client.service.LinkServiceAsync;
import fr.byob.client.om.service.IObjectToolbarManagementServiceProxy;

public class LinkServiceProxy implements IObjectToolbarManagementServiceProxy<ILink>{

    private LinkServiceAsync service;
    
    private final static LinkServiceProxy instance = new LinkServiceProxy();
    
    public static LinkServiceProxy getInstance(){
        return instance;
    }
    
    private LinkServiceProxy (){
        service = (LinkServiceAsync) GWT.create(LinkService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "linkService";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
    }
    
    public void addObject(ILink object, AsyncCallback<ILink> callback) {
        LinkGWT link = (LinkGWT)object;
    	IUser user = ConnectedUserModel.getInstance().getConnectedUser();
    	link.setUserid(user);
        service.addLink(link,callback);
    }

    public void findAllObjects(AsyncCallback<List<ILink>> callback) {
        service.findAllLinks(callback);
    }

    public void removeObject(ILink object, AsyncCallback<?> callback) {
        service.removeLinkByTitle(((ILink)object).getLinktitle(), callback);
    }

    public void modifyObject(ILink object, AsyncCallback<ILink> callback) {
        LinkGWT link = (LinkGWT)object;
        service.editLink(link, callback);
    }

    public void findObjects(int start, int end, AsyncCallback<List<ILink>> callback) {
        service.findLinks(start, end, callback);
    }

    public void countObjects(AsyncCallback<Integer> callback) {
        service.countLinks(callback);
    }
    public Object getServiceAsync (){
        return service;
    }

}
