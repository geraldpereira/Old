package fr.byob.blog.client.view.factory;

import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.view.panel.AlbumPanel;
import fr.byob.client.application.page.model.IPageContentFactory;

public class AlbumPanelFactory implements IPageContentFactory{
    private static AlbumPanelFactory instance = new AlbumPanelFactory();

    private AlbumPanelFactory() {
    }

    public static AlbumPanelFactory getInstance() {
        return instance;
    }

    public Widget createPageContent() {
        return new AlbumPanel();
    }
}
