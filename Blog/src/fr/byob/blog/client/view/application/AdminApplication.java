package fr.byob.blog.client.view.application;

import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.view.factory.AdminPanelFactory;
import fr.byob.blog.client.view.factory.CategoryPanelFactory;
import fr.byob.blog.client.view.panel.HTMLCreatorPanel;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.application.page.model.IPageContentFactory;
import fr.byob.client.application.page.view.SimplePage;

/**
 * Class used to build an application. It contains application widgets, a title
 * and the different pages. It is ONLY used to build the application (it's model
 * and view) and not as a model.
 * 
 * @author gpereira
 * 
 */
public class AdminApplication extends BlogApplication{
		
	/**
	 * Constructeur
	 */
	public AdminApplication() {
	    super();
	    
	    SimplePage adminPage = new SimplePage(BlogStrings.INSTANCE.applicationUsers(), AdminPanelFactory.getInstance());
		pages.add(adminPage);
		

        SimplePage categoryPage = new SimplePage(BlogStrings.INSTANCE.applicationCategories(), CategoryPanelFactory.getInstance());
        pages.add(categoryPage);
        
        SimplePage htmlCreator = new SimplePage(BlogStrings.INSTANCE.applicationHTMLCreator(),new IPageContentFactory(){
            public Widget createPageContent() {
                return new HTMLCreatorPanel();
            }
        });
        pages.add(htmlCreator);
	}

    public String getTitle() {
        return BlogStrings.INSTANCE.applicationTitleAdmin();
    }
}
