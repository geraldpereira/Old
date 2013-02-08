package fr.byob.blog.ejb.deploy;

import java.util.List;

import javax.ejb.Remote;

import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.ejb.entity.Category;
import fr.byob.client.exception.ValidationException;

@Remote
public interface CategoryManagerRemote {
	
	public List<Category> findAllCategories() throws BlogException;

	public Category addCategory(final Category category) throws BlogException, ValidationException;

	public Category updateCategory(final Category category) throws BlogException, ValidationException;
	
	public void removeCategory(final int categoryid)  throws BlogException;
	
	public Category findCategory(final String label) throws BlogException;
	
	public int countCategories() throws BlogException;
	
	public List<Category> findCategories(int start, int end) throws BlogException;
	
	public Category findCategory(int catagoryid) throws BlogException;
}
