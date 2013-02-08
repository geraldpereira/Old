package fr.byob.client.view.model;

import java.util.List;

import fr.byob.client.IListener;

/**
 * Model de données contenant des managed object.
 * 
 * @author pereirag
 *
 * @param <T> Le type des objets gérés
 */
public interface IConsoleModel {

	public static final String ADDED = "added";
	public static final String MODIFIED = "modified";
	public static final String DELETED = "deleted";
	public static final String NODATA = "noData";
	
	/**
	 * Ajoute une listener
	 * @param listener
	 */
	public void addListener(IListener listener);
	/**
	 * Supprime un listener
	 * @param listener
	 */
	public void removeListener(IListener listener);

	public List<String>  getConsoleText();
	public void setConsoleText(List<String>  text);
	public void setConsoleText(String  text);

	
}
