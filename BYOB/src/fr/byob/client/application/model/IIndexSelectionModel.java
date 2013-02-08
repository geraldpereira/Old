package fr.byob.client.application.model;

import fr.byob.client.application.IIndexSelectionListener;

/**
 * Interface de model de sélection d'index
 */
public interface IIndexSelectionModel {
	/**
	 * Ajout d'un listener au model
	 * 
	 * @param listener
	 *            le listener
	 */
	public void addListener(IIndexSelectionListener listener);

	/**
	 * Suppression d'un listener au model
	 * 
	 * @param listener
	 *            le listener
	 */
	public void removeListener(IIndexSelectionListener listener);

	/**
	 * Sélection un index dans le model. Cette méthode DOIT prevenir les
	 * différents listeners de sa modification
	 * 
	 * @param newIndex
	 *            l'index sélectionné
	 */
	public void selectIndex(int newIndex);

	/**
	 * Retourne l'index courament sélectionner
	 * 
	 * @return
	 */
	public int getSelectedIndex();
}
