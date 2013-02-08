package fr.byob.blog.client.proxy;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.model.CategoryGWT;
import fr.byob.blog.client.service.CategoryService;
import fr.byob.blog.client.service.CategoryServiceAsync;
import fr.byob.client.om.service.IObjectToolbarManagementServiceProxy;

public class CategoryServiceProxy implements IObjectToolbarManagementServiceProxy<ICategory>{

    private CategoryServiceAsync service;
    
    private final static CategoryServiceProxy instance = new CategoryServiceProxy();
    
    public static CategoryServiceProxy getInstance(){
        return instance;
    }
    
    private CategoryServiceProxy (){
        service = (CategoryServiceAsync) GWT.create(CategoryService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "categoryService";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
    }
    
    public void addObject(ICategory object, AsyncCallback<ICategory> callback) {
    	CategoryGWT category = (CategoryGWT)object;
        service.addCategory(category,callback);
    }

    public void findAllObjects(AsyncCallback<List<ICategory>> callback) {
        service.findAllCategories(callback);
    }

    public void removeObject(ICategory object, AsyncCallback<?> callback) {
        service.removeCategory(((ICategory)object).getCategoryid(), callback);
    }

    public void modifyObject(ICategory object, AsyncCallback<ICategory> callback) {
    	CategoryGWT category = (CategoryGWT)object;
        service.updateCategory(category, callback);
    }

    public void findObjects(int start, int end, AsyncCallback<List<ICategory>> callback) {
        service.findCategories(start, end, callback);
    }

    public void countObjects(AsyncCallback<Integer> callback) {
        service.countCategories(callback);
    }

    public Object getServiceAsync (){
        return service;
    }
}
