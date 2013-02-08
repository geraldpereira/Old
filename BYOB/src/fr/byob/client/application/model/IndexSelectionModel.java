package fr.byob.client.application.model;

import java.util.ArrayList;
import java.util.List;

import fr.byob.client.application.IIndexSelectionListener;

/**
 * Implémentation du model de sélection d'index
 */
public class IndexSelectionModel implements IIndexSelectionModel{
	
	// Liste des ilistener écoutant le model pour toutes modifications de celui ci
	private List<IIndexSelectionListener> listeners;

	// index sélectionné
	private int selectedIndex;
	
	/**
	 * Constructeur
	 * @param defaultIndex Index sélectionné par défaut
	 */
	public IndexSelectionModel(int defaultIndex) {
		selectedIndex = defaultIndex;
		listeners = new ArrayList<IIndexSelectionListener>();
	}
	public void addListener(IIndexSelectionListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeListener(IIndexSelectionListener listener){
		this.listeners.remove(listener);
	}
	
	public void selectIndex (int newIndex){
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).indexSelected(this.selectedIndex,newIndex);
		}		
		this.selectedIndex = newIndex;
	}
	
	public int getSelectedIndex() {
		return selectedIndex;
	}
}
