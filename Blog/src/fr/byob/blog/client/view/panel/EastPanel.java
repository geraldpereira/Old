package fr.byob.blog.client.view.panel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

import fr.byob.client.view.widget.ad.IAdRefreshListener;

/**
 * Décrit le Panel est
 * 
 * @author Kojir0
 */
public class EastPanel extends Composite implements IAdRefreshListener {
    /**
     * Constructor
     */
    public EastPanel() {
        initView();
    }
    
//    private EastAdWidget ad;
//    private AdSenseWidget ad;
    private VerticalPanel eastPanel;
    
    /**
     * Initialise la vue
     */
    protected void initView() {
        eastPanel = new VerticalPanel();
//        ad = new EastAdWidget("myadsense");
//        ad = new AdSenseWidget("myadsense");
//        eastPanel.add(ad);
        
        // Init the panel properties
        eastPanel.setBorderWidth(0);
        eastPanel.setSpacing(0);
        eastPanel.setWidth("100%");
        eastPanel.setHeight("100%");

        initWidget(eastPanel);
    }

    public void adRefreshed() {
//        ad.refresh();
//        eastPanel.remove(ad);
//        ad = new AdSenseWidget("myadsense");
//        eastPanel.add(ad);
    }
}