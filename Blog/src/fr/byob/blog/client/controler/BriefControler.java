package fr.byob.blog.client.controler;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import fr.byob.blog.client.model.BriefGWT;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.service.BriefService;
import fr.byob.blog.client.service.BriefServiceAsync;
import fr.byob.blog.client.view.panel.BriefPanel;
import fr.byob.blog.client.view.panel.HeaderPanel;
import fr.byob.client.chat.service.IBriefControler;

public class BriefControler implements IBriefControler{
    private BriefServiceAsync service;
    private static BriefControler headerControler = new BriefControler();
    private HeaderPanel header = null;
    
    public void setHeader(HeaderPanel header) {
        this.header = header;
    }
    private BriefControler() {
        service = (BriefServiceAsync) GWT.create(BriefService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "briefService";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
    }
    public static BriefControler getInstance(){
        return headerControler;
    }
    public void getUpdateBriefs(){
        AsyncCallback<List<BriefGWT>> callback = new AsyncCallback<List<BriefGWT>>(){
            public void onFailure(Throwable caught) {
            }
            public void onSuccess(List<BriefGWT> result) {
                header.setBriefs(result);
            }
        };
        service.getBriefs(callback);
    }
    public void getAllBriefs(final BriefPanel briefPanel){
        AsyncCallback<List<BriefGWT>> callback = new AsyncCallback<List<BriefGWT>>(){
            public void onFailure(Throwable caught) {
            }
            public void onSuccess(List<BriefGWT> result) {
                briefPanel.setBriefes(result);
                briefPanel.chargedDatas();
            }
        };
        service.getAllBriefs(callback);
    }
    private void addBrief(BriefGWT brief){
        AsyncCallback<?> callback = new AsyncCallback<?>(){
            public void onFailure(Throwable caught) {
            }
            public void onSuccess(Object result) {
                getUpdateBriefs();
            }
        };
        service.addBrief(brief, callback);
    }
    public ClickHandler getClickAddBrief(final BriefPanel briefPanel){
        return new ClickHandler(){
            public void onClick(ClickEvent event) {
                BriefGWT brief = new BriefGWT();
                brief.setDate(new Date());
                brief.setText(briefPanel.getTextBrief());
                brief.setUserid(ConnectedUserModel.getInstance().getConnectedUser().getUserid());
                AsyncCallback<?> callback = new AsyncCallback<?>(){
                    public void onFailure(Throwable caught) {
                        briefPanel.setTextConsole("Problème dans l'ajout de la brève");
                    }
                    public void onSuccess(Object result) {
                        getAllBriefs(briefPanel);
                        getUpdateBriefs();
                        briefPanel.setTextConsole("Brève ajoutée");
                        briefPanel.clear();
                    }
                };
                service.addBrief(brief, callback);
                
            }
        };
    }
    public void addBriefFromChat(String user, String text) {
        BriefGWT brief = new BriefGWT();
        brief.setDate(new Date());
        brief.setText(text);
        brief.setUserid(user);
        addBrief(brief);
    }
}
