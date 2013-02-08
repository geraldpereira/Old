package fr.byob.client.application.page.controler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import fr.byob.client.application.model.IIndexSelectionModel;

public class SubPageSelectionControler implements ISubPageSelectionControler{

    private final IIndexSelectionModel model;

    public SubPageSelectionControler(final IIndexSelectionModel model) {
        this.model = model;
    }

    public ClickHandler getSubPageSelectionControler(final int subPageIndex) {
        return new ClickHandler(){
            public void onClick(ClickEvent event) {
                model.selectIndex(subPageIndex);
             // Rafraichi les éventuelles pubs
//            AdRefreshControler.getInstance().refreshAds();
            }
            
        };
    }
    
    
}
