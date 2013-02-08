package fr.byob.client.application.page.view;

import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.application.IDeckListener;
import fr.byob.client.application.page.model.IPageContentFactory;
import fr.byob.client.lazy.LazyPanel;

public abstract class ALazyPage implements IPage {

    
    protected IPageContentFactory factory;

    protected PageLazyPanel lazyPanel;
    
    /**
     * Constructeur
     * 
     * @param pageTitle
     *            titre de la page
     * @param centerWidget
     *            panneau
     */
    public ALazyPage(IPageContentFactory factory) {
        this.factory = factory;
        lazyPanel = new PageLazyPanel();
    }

    public PageLazyPanel getCenterWidget() {
        return lazyPanel;
    }
    
    public void chargedPage() {
        Widget widget = lazyPanel.ensureWidget();
        if (widget instanceof IDeckListener) {
            ((IDeckListener) widget).deckCharged();
        }
    }
    
    public class PageLazyPanel extends LazyPanel<Widget>{
        @Override
        public Widget createWidget() {
            return factory.createPageContent();
        }
    }
}
