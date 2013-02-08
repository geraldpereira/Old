package fr.byob.client.application.page.view;

import fr.byob.client.application.page.model.IPageContentFactory;

public class HiddenPage extends ALazyPage {
    
    public final static String defaultTitle ="default title";
    
	/**
	 * Constructeur
	 * 
	 */
	public HiddenPage(IPageContentFactory factory) {
	    super(factory);
	}

    public String getPageTitle() {
        return null;
    }
}
