package fr.byob.blog.client.view.panel;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import fr.byob.client.application.controler.IPageSelectionControler;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;
import fr.byob.client.view.panel.RoundedPanel;
/**
 * Construit un onglet du menu
 * @author Akemi
 *
 */
public class MenuPanel extends Composite {

	/**
	 * Lien du menu
	 */
	private final Label link;
	/**
	 * Cadre du menu 
	 */
	private final VerticalPanel menu;
	/**
	 * Titre du menu
	 */
	private final String menuTitle;
	/**
	 * Selection ou non
	 */
	private boolean isSelected;
	/**
	 * Cadre arrondi
	 */
	private final RoundedPanel rp;
	/**
	 * Cadre du menu
	 */
	private final SimplePanel linkPanel;
	
	
	/**
	 * Constructeur
	 * @param menuIndex position
	 * @param menuTitle titre
	 * @param isSelected s'il est sélectionné
	 */
	protected MenuPanel(IPageSelectionControler controler,int menuIndex,String menuTitle, boolean isSelected){
		this.menuTitle = menuTitle;
		
		link = new Label(menuTitle);
		link.addClickHandler(Utils.getLinkPage(menuTitle));
		link.addStyleName(Css.INSTANCE.cursor());
		link.addStyleName(Css.INSTANCE.titleMenu());
		link.addClickHandler(controler.getMenuSelectionControler(menuIndex));
		link.addStyleName(Css.INSTANCE.menuSelectText());
		
		linkPanel = new SimplePanel();
		linkPanel.addStyleName(Css.INSTANCE.cbgRPmenu());
		linkPanel.addStyleName(Css.INSTANCE.ajoutTitle());
		linkPanel.addStyleName(Css.INSTANCE.borderMenuSelect());
		linkPanel.add(link);
		rp = new RoundedPanel(linkPanel, RoundedPanel.LEFT, 2,
		Css.INSTANCE.cbgRPBorderMenuSelect());
		
		menu = new VerticalPanel();
		
		menu.addStyleName(Css.INSTANCE.menuSelect());
		menu.addStyleName(Css.INSTANCE.mrgt10());
		menu.addStyleName(Css.INSTANCE.mrgb10());
		menu.add(rp);
		setSelected (isSelected);
		initWidget(menu);
	}
	/**
	 * Met à jour la sélection
	 * @param isSelected s'il est sélectionné
	 */
	protected void setSelected (boolean isSelected){
		if (isSelected && !this.isSelected){
			menu.clear();
			linkPanel.clear();
			rp.clear();
			link.removeStyleName(Css.INSTANCE.titleMenuB());
			link.addStyleName(Css.INSTANCE.titleMenu());
			linkPanel.add(link);
			rp.add(linkPanel);
			menu.removeStyleName(Css.INSTANCE.sec());
			menu.add(rp);
			this.isSelected = true;
		}else if (!isSelected && this.isSelected){
			menu.clear();
			menu.addStyleName(Css.INSTANCE.sec());
			link.removeStyleName(Css.INSTANCE.titleMenu());
			link.addStyleName(Css.INSTANCE.titleMenuB());
			menu.add(link);	
			this.isSelected = false;
		}else if (!isSelected && !this.isSelected){
			menu.clear();
			menu.addStyleName(Css.INSTANCE.sec());
			link.removeStyleName(Css.INSTANCE.titleMenu());
			link.addStyleName(Css.INSTANCE.titleMenuB());
			menu.add(link);
			this.isSelected = false;
		}
	}
	/**
	 * Retourne s'il est s�lectionn�
	 * @return s�lectionn�
	 */
	protected boolean isSelected (){
		return isSelected;
	}
	/**
	 * Retourn le titre du menu
	 * @return le titre
	 */
	protected String getMenuTitle() {
		return menuTitle;
	}
}
