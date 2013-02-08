package fr.byob.client.view.model;

import java.util.HashMap;
import java.util.List;

import fr.byob.client.IListener;

/**
 * Model de données contenant des managed object.
 * 
 * @author pereirag
 *
 * @param <T> Le type des objets gérés
 */
public interface IToolbarModel {
	
    public static int PREV_OPERATION = 1;
    public static int NEXT_OPERATION = 2;
	
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
	
	public int getNbData();
	public int getMaxData(); 
	public void setMaxData(int maxData);
	public void setStartAndEndDate(int start,int end);
	public List<Integer> getValuesToolbar();
	public void setOperationToolbar(int operation,boolean enabled);
	public HashMap<Integer, Boolean> getOperationToolbar();
}
