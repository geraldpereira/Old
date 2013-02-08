package fr.byob.blog.client.view.application;

import fr.byob.blog.client.view.factory.ArchivePanelFactory;
import fr.byob.blog.client.view.factory.LinkPanelFactory;
import fr.byob.blog.client.view.factory.NewsPanelFactory;
import fr.byob.blog.client.view.factory.ProfilPanelFactory;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.application.page.view.DeckPage;
import fr.byob.client.application.page.view.SimplePage;

/**
 * Class used to build an application. It contains application widgets, a title
 * and the different pages. It is ONLY used to build the application (it's model
 * and view) and not as a model.
 * 
 * @author gpereira
 * 
 */
public class GuestApplication extends BlogApplication{
	
	/**
	 * Constructeur
	 */
	public GuestApplication() {
	    super();
        {
            DeckPage lastNews = new DeckPage(BlogStrings.INSTANCE.applicationLastNews(),NewsPanelFactory.getInstance());
            pages.add(lastNews);
        }
        {
            DeckPage archives = new DeckPage(BlogStrings.INSTANCE.applicationArchives(),ArchivePanelFactory.getInstance());
            pages.add(archives);
        }
        SimplePage linkPage = new SimplePage(BlogStrings.INSTANCE.applicationLinks(), LinkPanelFactory.getInstance());
        pages.add(linkPage);
        
        {
            DeckPage users = new DeckPage(BlogStrings.INSTANCE.applicationUsers(), ProfilPanelFactory.getInstance());
            pages.add(users);
        }

	}
	
	public String getTitle() {
        return BlogStrings.INSTANCE.applicationTitleBlog();
    }
}
