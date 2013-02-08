package fr.byob.blog.client.proxy;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import fr.byob.blog.client.IProfil;
import fr.byob.blog.client.service.ProfilService;
import fr.byob.blog.client.service.ProfilServiceAsync;
import fr.byob.client.om.service.IObjectToolbarManagementServiceProxy;

public class ProfilServiceProxy implements IObjectToolbarManagementServiceProxy<IProfil>{

    private ProfilServiceAsync serviceUser;
    
    private final static ProfilServiceProxy instance = new ProfilServiceProxy();
    
    public static ProfilServiceProxy getInstance(){
        return instance;
    }
    
    private ProfilServiceProxy (){
        serviceUser = (ProfilServiceAsync) GWT.create(ProfilService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) serviceUser;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "profilService";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
    }
    
    public void addObject(IProfil object, AsyncCallback<IProfil> callback) {
    }

    public void findAllObjects(AsyncCallback<List<IProfil>> callback) {
        serviceUser.findAllProfils(callback);
    }

    public void removeObject(IProfil object, AsyncCallback<?> callback) {
    }

    public void modifyObject(IProfil object, AsyncCallback<IProfil> callback) {
        serviceUser.editProfil(object, callback);
    }

    public void findObjects(int nbStart, int nbEnd, AsyncCallback<List<IProfil>> callback) {
        serviceUser.findProfils(nbStart, nbEnd, callback);
     }

     public void countObjects(AsyncCallback<Integer> callback) {
         serviceUser.countProfils(callback);
     }
     public Object getServiceAsync (){
         return serviceUser;
     }
}
