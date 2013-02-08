package fr.byob.client.view.controler;

import fr.byob.client.view.model.IConsoleModel;


/**
 * Controleur utilisé dans le cadre des Managed Objects Les fonctionnalités
 * proposées sont à enrichir selon les besoins
 * 
 * @author Kojiro
 * 
 * @param <T>
 */
public interface IConsoleControler {

	/**
	 * Affecte le console model
	 * @param consoleModel
	 */
	public void setConsoleModel (IConsoleModel consoleModel);
}
