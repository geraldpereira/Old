package fr.byob.blog.client.view.application;

import java.util.ArrayList;

import fr.byob.blog.client.view.panel.EastPanel;
import fr.byob.blog.client.view.panel.HeaderPanel;
import fr.byob.blog.client.view.panel.LoginPanel;
import fr.byob.blog.client.view.panel.SouthPanel;
import fr.byob.blog.client.view.panel.VerticalMenusPanel;
import fr.byob.client.application.model.AApplicationModel;
import fr.byob.client.application.page.view.IPage;

public abstract class BlogApplication extends AApplicationModel{

    public BlogApplication() {
        eastWidget = new EastPanel();
        southWidget = new SouthPanel();
        headerWidget = new HeaderPanel();
        overHeaderWidget = new LoginPanel();
        westWidget = new VerticalMenusPanel();
        pages = new ArrayList<IPage>();
    }
}
