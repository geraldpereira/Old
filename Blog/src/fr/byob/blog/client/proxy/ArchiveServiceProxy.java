package fr.byob.blog.client.proxy;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import fr.byob.blog.client.IPost;
import fr.byob.blog.client.service.PostService;
import fr.byob.blog.client.service.PostServiceAsync;
import fr.byob.blog.client.view.panel.ArchivesPanel;
import fr.byob.client.om.service.IObjectToolbarManagementServiceProxy;

public class ArchiveServiceProxy implements IObjectToolbarManagementServiceProxy<IPost>{

    private PostServiceAsync service;

    private ArchivesPanel archivesPanel;
    
    private final static ArchiveServiceProxy instance = new ArchiveServiceProxy();
    
    public static ArchiveServiceProxy getInstance(){
        return instance;
    }
    
    private ArchiveServiceProxy (){
        service = (PostServiceAsync) GWT.create(PostService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "postService";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
    }
    
    public void setArchivePanel (ArchivesPanel archivesPanel){
        this.archivesPanel = archivesPanel;
    }
    
    public void addObject(IPost object, AsyncCallback<IPost> callback) {
    }

    public void findAllObjects(AsyncCallback<List<IPost>> callback) {
        service.findPostsByCriteria(null,null, archivesPanel.getSelectedAuthors(),archivesPanel.getSelectedCategories(),archivesPanel.getText(),archivesPanel.getTitle(), archivesPanel.getStartDate(), archivesPanel.getEndDate(), callback);
    }

    public void removeObject(IPost object, AsyncCallback<?> callback) {
    }

    public void modifyObject(IPost object, AsyncCallback<IPost> callback) {
    }

    public void findObjects(int start, int end, AsyncCallback<List<IPost>> callback) {
        service.findPostsByCriteria(start,end, archivesPanel.getSelectedAuthors(),archivesPanel.getSelectedCategories(),archivesPanel.getText(),archivesPanel.getTitle(), archivesPanel.getStartDate(), archivesPanel.getEndDate(), callback);
    }

    public void countObjects(AsyncCallback<Integer> callback) {
        service.countPostsByCriteria(null,null, archivesPanel.getSelectedAuthors(),archivesPanel.getSelectedCategories(),archivesPanel.getText(),archivesPanel.getTitle(), archivesPanel.getStartDate(), archivesPanel.getEndDate(), callback);
    }
    public Object getServiceAsync (){
        return service;
    }

}
