package fr.byob.client.application.page.view;

import fr.byob.client.application.page.model.IPageContentFactory;

public class SimplePage extends ALazyPage {
    
    public final static String defaultTitle ="default title";
    
	// Titre de la page
	private final String pageTitle;

	/**
	 * Constructeur
	 * 
	 * @param pageTitle
	 *            titre de la page
	 * @param centerWidget
	 *            panneau
	 */
	public SimplePage(String pageTitle, IPageContentFactory factory) {
		super(factory);
	    if (pageTitle == null) {
			pageTitle = defaultTitle;
		}
		this.pageTitle = pageTitle;
	}

	public String getPageTitle() {
		return pageTitle;
	}
}
