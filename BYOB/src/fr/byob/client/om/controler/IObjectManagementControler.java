package fr.byob.client.om.controler;

import fr.byob.client.om.view.IObjectManagementPanel;

/**
 * Controleur utilisé dans le cadre des Managed Objects
 * Les fonctionnalités proposées sont à enrichir selon les besoins
 * 
 * @author Kojiro
 *
 * @param <T>
 */
public interface IObjectManagementControler<T> {
	
	/**
	 * Affecte une vue au controleur
	 * @param view la vue
	 */
	public void setView(IObjectManagementPanel<T> view);
	
	/**
	 * Rafraichi le(s) model(s) de données
	 */
	public void refreshModel();
	
}
