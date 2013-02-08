package fr.byob.client.application.controler;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import fr.byob.client.application.model.IIndexSelectionModel;

/**
 * Controleur pour la selection de page 
 * Aucune connection au serveur n'étant necessaire ici cette classe ne propose qu'un méthode statique (pas de création de service)
 * 
 * @author gpereira
 *
 */
public class PageSelectionControler implements IPageSelectionControler{

	// Model de sélection d'un index (celui du menu permettant d'afficher une page donnée)
	private final IIndexSelectionModel model;

    public PageSelectionControler (final IIndexSelectionModel model){
        
        this.model = model;
    }
    
	public ClickHandler getMenuSelectionControler(final int menuIndex) {
		return new ClickHandler(){
            public void onClick(ClickEvent event) {
             // Modifie le model
                model.selectIndex(menuIndex);
            }
		};
	}
}
