package fr.byob.blog.server;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBAccessException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import net.sf.dozer.util.mapping.MapperIF;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.byob.blog.client.Constants;
import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.exception.BlogException;
import fr.byob.blog.client.model.CategoryGWT;
import fr.byob.blog.client.service.CategoryService;
import fr.byob.blog.ejb.BlogInternationalizationUtils;
import fr.byob.blog.ejb.deploy.CategoryManagerRemote;
import fr.byob.blog.ejb.entity.Category;
import fr.byob.blog.server.dozer.Mapper;
import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.NameFactory;

/**
 * Implementation permettant des actions sur les cat�gories
 * 
 * @author akemi
 * 
 */
public class CategoryServiceImpl extends RemoteServiceServlet implements CategoryService {

    private static final long serialVersionUID = 1L;

    private CategoryManagerRemote bean;
    private final MapperIF mapper = Mapper.getMapper();

    /**
     * Constructeur
     */
    public CategoryServiceImpl() {
        Context context;
        try {
            context = new InitialContext();
            bean = (CategoryManagerRemote) context.lookup(NameFactory.getName("BlogEAR/CategoryManagerBean/remote"));
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ajoute une categorie
     * 
     * @param category
     *            categorie a ajouter
     * @throws BlogException
     * @throws ValidationException
     */
    public ICategory addCategory(final ICategory category) throws BlogException, ValidationException {
        // transformer la catégorieGWT en catégorie coté serveur
        Category categorySrv = (Category) mapper.map(category, Category.class);
        try {
            // Ajouter la catégorie
            categorySrv = bean.addCategory(categorySrv);

        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"),Constants.HOME_PAGE);
        }
        // Récupérer la catégorie
        return (CategoryGWT) mapper.map(categorySrv, CategoryGWT.class);
    }

    /**
     * Retourne toutes les categories de l'application
     * 
     * @return toutes les categories
     * @throws BlogException
     */
    public List<ICategory> findAllCategories() throws BlogException {
        List<Category> categoriesSrv = null;
        try {
            // recupérer la liste de catégories
            categoriesSrv = bean.findAllCategories();
        } catch (EJBAccessException e) {
            throw new BlogException(null,BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"),Constants.HOME_PAGE);
        }
        // La transformer
        List<ICategory> categories = converterCategoryList(categoriesSrv);
        // La retourner
        return categories;
    }

    /**
     * Retourne une catgorie par rapport à son titreé
     * 
     * @param label
     *            le titer
     * @return la catgorie trouvée
     * @throws BlogException
     */
    public ICategory findCategory(String label) throws BlogException {
        try {
            // recupérer la catégorie
            Category categorySrv = bean.findCategory(label);
            // La transformer
            CategoryGWT category = (CategoryGWT) mapper.map(categorySrv, CategoryGWT.class);
            // La retourner
            return category;
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"),Constants.HOME_PAGE);
        }
    }

    /**
     * Supprime une catégorie par rapport à son id
     * 
     * @param categoryid
     *            l'id de la catégorie
     * @throws BlogException
     */
    public void removeCategory(int categoryid) throws BlogException {
        try {
            bean.removeCategory(categoryid);
        } catch (EJBAccessException e) {
            throw new BlogException(null,BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"),Constants.HOME_PAGE);
        }
    }

    /**
     * Met à jour la catégorie
     * 
     * @param category
     *            la catégorie
     * @throws BlogException
     * @throws ValidationException
     */
    public ICategory updateCategory(ICategory category) throws BlogException, ValidationException {
        // transformer la catégorieGWT en catégorie coté serveur
        Category categorySrv = (Category) mapper.map(category, Category.class);
        try {
            // modifier la catégorie
            categorySrv = bean.updateCategory(categorySrv);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"),Constants.HOME_PAGE);
        }
        return (CategoryGWT) mapper.map(categorySrv, CategoryGWT.class);

    }

    /**
     * Supprime une catégorie par rapport à son titre
     * 
     * @param categoryLabel
     *            le titre de la catégorie
     * @throws BlogException
     */
    public void removeCategory(final String categoryLabel) throws BlogException {
        try {
            Category categorySrv = bean.findCategory(categoryLabel);
            CategoryGWT category = (CategoryGWT) mapper.map(categorySrv, CategoryGWT.class);
            bean.removeCategory(category.getCategoryid());
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"),Constants.HOME_PAGE);
        }
    }

    public List<ICategory> findCategories(int start, int end) throws BlogException {
        List<Category> categoriesSrv = null;
        try {
            // recupérer la liste de catégories
            categoriesSrv = bean.findCategories(start,end);
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"),Constants.HOME_PAGE);
        }
        // La transformer
        List<ICategory> categories = converterCategoryList(categoriesSrv);
        // La retourner
        return categories;
    }

    public int countCategories() throws BlogException {
        try {
            return bean.countCategories();
        } catch (EJBAccessException e) {
            throw new BlogException(null, BlogInternationalizationUtils.getMessages().getString("acces.non.autorise"),Constants.HOME_PAGE);
        }
    }
    
    private List<ICategory> converterCategoryList (List<Category> categories) throws BlogException{
        ArrayList<ICategory>categoriesGWT = new ArrayList<ICategory>();
        for (Category current : categories) {
            categoriesGWT.add((CategoryGWT) mapper.map(current, CategoryGWT.class));
        }
        return categoriesGWT;
    }
}
