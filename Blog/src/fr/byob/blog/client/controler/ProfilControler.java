package fr.byob.blog.client.controler;

import java.util.Arrays;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import fr.byob.blog.client.IProfil;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.model.ProfilGWT;
import fr.byob.blog.client.model.UserGWT;
import fr.byob.blog.client.service.ProfilService;
import fr.byob.blog.client.service.ProfilServiceAsync;
import fr.byob.blog.client.view.panel.ColorSelectionPanel;
import fr.byob.blog.client.view.panel.ProfilPanel;
import fr.byob.blog.client.view.panel.ProfilParamPanel;
import fr.byob.client.exception.BYOBException;

public class ProfilControler {

    private ProfilServiceAsync serviceUser;

    private ProfilParamPanel view;

    private static ProfilControler instance = new ProfilControler();
    
    private ProfilPanel profilView;
    
    private ProfilControler() {
        serviceUser = (ProfilServiceAsync) GWT.create(ProfilService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) serviceUser;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "profilService";
        endpoint.setServiceEntryPoint(moduleRelativeURL);

    }
    
    public void setProfilParamPanel (ProfilParamPanel view){
        this.view = view;
    }
    public void setProfilPanel (ProfilPanel profilView){
        this.profilView = profilView;
    }
    public static ProfilControler getInstance(){
        return instance;
    }

    public ClickHandler getClickOnModListener() {
        return new ClickHandler() {
            public void onClick(ClickEvent event) {
                modifyObject();
            }
        };
    }

    private void modifyObject() {
        if(!view.getOldPasswordConf().equals("")){
            AsyncCallback<Boolean> callbackB = new AsyncCallback<Boolean>() {
                public void onFailure(Throwable caught) {
                    view.setConsoleText(BYOBException.getStringList(caught));
                }
                public void onSuccess(Boolean result) {
                    editUser();
                }
            };
            serviceUser.verifPassword(view.getOldPassword(), view.getOldPasswordConf(),callbackB);
        }else{
            editUser();
        }
    }
    private void editUser(){
        AsyncCallback<IProfil> callback = new AsyncCallback<IProfil>() {
            public void onSuccess(IProfil result) {
                view.setConsoleText(Arrays.asList(new String[]{"Profil modifié"}));
                if(result != null && result.getProfilcss() != null){
                    ColorSelectionPanel.getInstance().changeColor(result.getProfilcss());
                }
                ConnectedUserModel.getInstance().logginUserProfil(result);
            }

            public void onFailure(Throwable caught) {
                view.setConsoleText(BYOBException.getStringList(caught));
            }
        };
        IProfil profil = new ProfilGWT();
        profil.setProfilcss(view.getCss());
        profil.setProfildate(view.getLastVisite());
        profil.setProfilinscription(view.getInscription());
        profil.setProfilmail(view.getMail());
        profil.setProfilphoto(view.getPhoto());
        profil.setProfilpresentation(view.getPresentation());
        profil.setProfilsite(view.getSite());
        IUser user= new UserGWT();
        user.setUserid(view.getLogin());
        user.setPassword(view.getPassword());
        profil.setUserid(user);
        if(view.getPassword() != ProfilParamPanel.WRONG_PWD ){
            serviceUser.editProfil(profil, callback);
        }
    }
    public void findProfil(String userid){
        AsyncCallback<IProfil> callback = new AsyncCallback<IProfil>() {
            public void onSuccess(IProfil result) {
                profilView.chargedModel(result);
            }

            public void onFailure(Throwable caught) {
                profilView.setConsoleText(BYOBException.getStringList(caught));
            }
        };
        serviceUser.findProfil(userid, callback);
    }
}
