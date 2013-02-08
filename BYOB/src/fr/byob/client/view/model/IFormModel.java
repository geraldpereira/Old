package fr.byob.client.view.model;

import fr.byob.client.IListener;

/**
 * Attention !!! Ne refraichi que lorsque l'objet est modifié !!!!
 * 
 * @author pereirag
 * 
 * @param <T>
 *            Le type des objets gérés
 */
public interface IFormModel<T> {

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
	
	public boolean isEnabled();

	public void setEnabled(boolean enabled);

	public boolean isButtonsEnabled();

	public void setButtonsEnabled(boolean buttonsEnabled);
	
	public boolean isAddVisible();

	public void setAddVisible(boolean addVisible);

	public boolean isModVisible();

	public void setModVisible(boolean modVisible);

	public boolean isDelVisible();

	public void setDelVisible(boolean delVisible);

	public T getObject();

	public void setObject(T object);

	public void setAddOnlyMode();

	public void setModDelMode(T object);

	public void setDelOnlyMode(T object);

	public void setModOnlyMode(T object);

	public void setInitMode();
	
}
