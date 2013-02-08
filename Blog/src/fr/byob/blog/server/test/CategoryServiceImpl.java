package fr.byob.blog.server.test;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.client.model.CategoryGWT;
import fr.byob.blog.client.service.CategoryService;
import fr.byob.client.exception.ValidationException;

public class CategoryServiceImpl extends RemoteServiceServlet implements CategoryService {

	private static final long serialVersionUID = 1L;

	private final ArrayList<ICategory> categories;
	private int id = 0; 
	
	
	public CategoryServiceImpl() {
		categories = new ArrayList<ICategory>();
		for (id = 0; id< 6;id ++){
			CategoryGWT cat = new CategoryGWT();
			cat.setCategoryid(id);
			cat.setCategorylabel("label"+id);
			categories.add(cat);
		}
		
	}
	
	
	public synchronized  ICategory addCategory(final ICategory category) throws BlogException, ValidationException {
		category.setCategoryid(++id);
		categories.add(category);
		return category;
	}

	public synchronized  List<ICategory> findAllCategories() throws BlogException {
		return categories;
	}

	public synchronized ICategory findCategory(String label) throws BlogException {
		for (ICategory cat : categories){
			if (cat.getCategorylabel().equals(label)){
				return cat;
			}
		}
		return null;
	}

	public synchronized void removeCategory(int categoryid) throws BlogException {
	    int i = 0;
        int index = -1;
        for (ICategory cat : categories) {
            if (cat.getCategoryid()== categoryid) {
                index = i;
                break;
            }
            i++;
        }
        if (index != -1) {
            categories.remove(index);
        }
	}

	public synchronized ICategory updateCategory(ICategory category) throws BlogException, ValidationException {
	    int i = 0;
        int index = -1;
        for (ICategory cat : categories) {
            if (cat.getCategoryid()== category.getCategoryid()) {
                index = i;
                break;
            }
            i++;
        }
        if (index != -1) {
            categories.remove(index);
            categories.add(index, category);
        }
		return category;
	}

	public synchronized void removeCategory(final String categoryLabel)  throws BlogException{
		for (ICategory cat : categories){
			if (cat.getCategorylabel() == categoryLabel){
				categories.remove(cat);
			}
		}	
	}


    public List<ICategory> findCategories(int start, int end) throws BlogException {
        List<ICategory> categoriesSub = new ArrayList<ICategory>();
        for(int i=start;i<end;i++){
            categoriesSub.add(categories.get(i));   
        }
        return categoriesSub;
    }


    public int countCategories() throws BlogException {
        return categories.size();
    }
}
