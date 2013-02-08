package fr.byob.blog.client.view.panel;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import fr.byob.blog.client.Constants;
import fr.byob.client.application.IIndexSelectionListener;
import fr.byob.client.application.controler.IPageSelectionControler;
import fr.byob.client.application.model.IApplicationModel;
import fr.byob.client.application.page.view.IPage;
import fr.byob.client.util.Css;
import fr.byob.client.view.panel.RoundedPanel;

/**
 * D�crit le Panel pour les menu � gauche  
 * 
 * @author Kojir0
 */
public class VerticalMenusPanel extends Composite implements IIndexSelectionListener{
	/**
	 * List des menus 
	 */
	private final ArrayList<MenuPanel> menus;
	
	
	private SimplePanel westPanel;
	
	private SimplePanel hider;
	private RoundedPanel menuRound ;
	/**
	 * Constructeur
	 * @param application application
	 */
	public VerticalMenusPanel() {
		this.menus = new ArrayList<MenuPanel>();
		this.westPanel = new SimplePanel();
		hider = new SimplePanel();
		menuRound = new RoundedPanel(westPanel, RoundedPanel.BOTTOM, 5, Css.INSTANCE.cbgRPMenuGlob());
        initWidget(menuRound);
	}
	/**
	 * Initialise la vue
	 */
	protected void initView(IApplicationModel application,IPageSelectionControler controler, int width) {
		

		VerticalPanel menusPanel = new VerticalPanel();
		menusPanel.setVerticalAlignment(VerticalPanel.ALIGN_TOP);
		ArrayList<IPage> pages = application.getPages();
		for (int i = 0; i < pages.size(); i++) {
            IPage currentPage = (IPage) pages.get(i);
            MenuPanel curMenu = null;
            if(currentPage.getPageTitle() != null){
            curMenu = new MenuPanel(controler,i, currentPage.getPageTitle(), i==Constants.DEFAULT_PAGE_INDEX);
            curMenu.setWidth(width-10+"px");
            menus.add(curMenu);
            menusPanel.add(curMenu);
            }
        }

		menusPanel.setWidth(width+"px");
		
		hider.add(menusPanel);
		hider.addStyleName(Css.INSTANCE.rnsecs());
		hider.addStyleName(Css.INSTANCE.ajoutMenu());
		hider.setWidth(width+"px");
		menuRound.setWidth(width+"px");
		westPanel.add(hider);
		westPanel.addStyleName(Css.INSTANCE.cbgRP());
		
		westPanel.setHeight(Window.getClientHeight()-200+"px");
		westPanel.setWidth(width+"px");
	}
	public SimplePanel getWestPanel(){
	    return westPanel;
	}

	/**
	 * Modifie la page s�lectionn�e
	 * @param oldPageIndex index de l'ancienne page
	 * @param newPageIndex index de la nouvelle page
	 */
	public void indexSelected(int oldPageIndex, int newPageIndex) {
	    if(oldPageIndex<menus.size()){
	        menus.get(oldPageIndex).setSelected(false);
	    }
	    if(newPageIndex<menus.size()){
	        menus.get(newPageIndex).setSelected(true);
	    }
	}
}
