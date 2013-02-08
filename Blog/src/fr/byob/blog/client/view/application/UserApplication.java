package fr.byob.blog.client.view.application;

import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.view.factory.AlbumPanelFactory;
import fr.byob.blog.client.view.factory.ArchivePanelFactory;
import fr.byob.blog.client.view.factory.CategoryPanelFactory;
import fr.byob.blog.client.view.factory.ChatPanelFactory;
import fr.byob.blog.client.view.factory.LinkPanelFactory;
import fr.byob.blog.client.view.factory.NewsPanelFactory;
import fr.byob.blog.client.view.factory.PostPanelFactory;
import fr.byob.blog.client.view.factory.ProfilPanelFactory;
import fr.byob.blog.client.view.panel.BriefPanel;
import fr.byob.blog.client.view.panel.ProfilParamPanel;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.application.page.model.IPageContentFactory;
import fr.byob.client.application.page.view.DeckPage;
import fr.byob.client.application.page.view.HiddenPage;
import fr.byob.client.application.page.view.SimplePage;

/**
 * Class used to build an application. It contains application widgets, a title and the different pages. It is ONLY used to build the application
 * (it's model and view) and not as a model.
 * 
 * @author gpereira
 * 
 */
public class UserApplication extends BlogApplication {

    public UserApplication() {
        super();

        {
            DeckPage lastNews = new DeckPage(BlogStrings.INSTANCE.applicationLastNews(), NewsPanelFactory.getInstance());
            pages.add(lastNews);
        }
        {
            DeckPage archives = new DeckPage(BlogStrings.INSTANCE.applicationArchives(), ArchivePanelFactory.getInstance());
            pages.add(archives);
        }
        SimplePage postPage = new SimplePage(BlogStrings.INSTANCE.applicationAddPost(), PostPanelFactory.getInstance());
        pages.add(postPage);

        SimplePage albumPage = new SimplePage(BlogStrings.INSTANCE.applicationAlbum(), AlbumPanelFactory.getInstance());
        pages.add(albumPage);
        
//        SimplePage photoPage = new SimplePage(BlogStrings.INSTANCE.applicationPhoto(), PhotoPanelFactory.getInstance());
//        pages.add(photoPage);
        
        SimplePage linkPage = new SimplePage(BlogStrings.INSTANCE.applicationLinks(), LinkPanelFactory.getInstance());
        pages.add(linkPage);

        {
            DeckPage users = new DeckPage(BlogStrings.INSTANCE.applicationUsers(), ProfilPanelFactory.getInstance());
            pages.add(users);
        }

        SimplePage categoryPage = new SimplePage(BlogStrings.INSTANCE.applicationCategories(), CategoryPanelFactory.getInstance());
        pages.add(categoryPage);

        SimplePage chatPage = new SimplePage(BlogStrings.INSTANCE.applicationChat(), ChatPanelFactory.getInstance());
        pages.add(chatPage);
        
        HiddenPage paramUser = new HiddenPage(new IPageContentFactory(){
            public Widget createPageContent() {
                return new ProfilParamPanel();
            }
        });
        pages.add(paramUser);

        HiddenPage brief = new HiddenPage(new IPageContentFactory(){
            public Widget createPageContent() {
                return new BriefPanel();
            }
        });
        pages.add(brief);
    }

    public String getTitle() {
        return BlogStrings.INSTANCE.applicationTitleConnected();
    }
}
