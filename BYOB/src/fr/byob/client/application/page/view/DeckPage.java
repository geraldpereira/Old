package fr.byob.client.application.page.view;

import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.application.IDeckListener;
import fr.byob.client.application.IIndexSelectionListener;
import fr.byob.client.application.page.model.IDeckPageContentFactoriesList;
import fr.byob.client.application.page.model.IPageContentFactory;
import fr.byob.client.lazy.LazyPanel;

/**
 * Page avec sélection d'index. Cette page contient donc plusieurs sous page
 * dont une seule est affichée
 */
public class DeckPage implements IPage, IIndexSelectionListener {
	
	// Panneau central
	private DeckPanel deckPanel;

	// Titre de la page
	private final String pageTitle;
	
	/**
	 * Constructeur
	 * 
	 * @param pageTitle
	 *            Le titre de la page
	 * @param centerWidgets
	 *            la liste de widgets. Chaque widget sera ajouté dans une sous
	 *            page.
	 */
	public DeckPage(String pageTitle, IDeckPageContentFactoriesList factories) {
		if (pageTitle == null) {
			pageTitle = "default title";
		}
		this.pageTitle = pageTitle;
		deckPanel = new DeckPanel();
		for (IPageContentFactory factory : factories.getPageContentFactoriesList()) {
			this.deckPanel.add(new DeckPageLazyPanel(factory));
		}
		factories.getSelectionModel().addListener(this);
	}

	private class DeckPageLazyPanel extends LazyPanel<Widget>{
	    
	    private IPageContentFactory factory;
	    
	    public DeckPageLazyPanel(IPageContentFactory factory){
	        this.factory = factory;
	    }
	    
        @Override
        public Widget createWidget() {
            return factory.createPageContent();
        }
	    
	}
	
	public DeckPanel getCenterWidget() {
		return deckPanel;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void chargedPage() {
		indexSelected(0, 0);
	}

	public void indexSelected(int oldIndex, int newIndex) {
	    LazyPanel<Widget> lazyPanel = ((LazyPanel<Widget>)deckPanel.getWidget(newIndex));
	    Widget widget = lazyPanel.ensureWidget();
		if (widget instanceof IDeckListener) {
			((IDeckListener) widget).deckCharged();
		}
		deckPanel.showWidget(newIndex);
	}
	
}
