package fr.byob.client.view.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.byob.client.IListener;

public class TableModel<T> implements ITableModel<T> {

	private final List<IListener> listeners;

	protected List<T> objects;

	private int selectedObjectIndex = ITableModel.UNSELECTED_INDEX;

	private int lastOperation;

	private final Object filler = new Object();

	private final ArrayList<T> fillers;
	
	private List<String>  consoleText = new ArrayList<String>();
	
	private boolean isEmpty = true;
	
	private T addedObject;

	// Permet de définir un décalage depuis le début du tableau
	private int offset;

	public TableModel(final int offset) {
		this.offset = offset;
		listeners = new ArrayList<IListener>();
		fillers = new ArrayList<T>(offset);
		for (int i = 0; i < offset; i++) {
			fillers.add((T) filler);
		}
	}

	public void addListener(final IListener listener) {
	    this.listeners.add(listener);
	}

	public void removeListener(final IListener listener) {
		this.listeners.remove(listener);
	}

	protected void fireModelChanged() {
		for (IListener listener : listeners) {
			listener.manageModelModification();
		}
	}

	public void setObjects(final List<T> list) {
		this.objects = new ArrayList<T>(offset + list.size());
		objects.addAll(fillers);
		selectedObjectIndex = TableModel.UNSELECTED_INDEX;
		for(T object : list){
		    addObject(object);
		}
		lastOperation = TableModel.REFRESH_OPERATION;
		
		if(list.size() == 0){
		    isEmpty = false;
		}else{
		    isEmpty = true;
		}
//		Window.alert("TableModel.setObjects()");
		fireModelChanged();
	}

	public List<T> getObjects() {
		ArrayList<T> retour = new ArrayList<T>(objects);
		retour.removeAll(fillers);
		// Ne pas utiliser le sublist (plante à l'execution en déploiement Jboss)
		// return objects.subList(offset, objects.size());
		return retour;
	}
	

	public void selectObject(final int index) {
		lastOperation = TableModel.NO_OPERATION;
		this.selectedObjectIndex = index;
//      Window.alert("TableModel.selectObject()");
		fireModelChanged();

	}
	

	public int getSelectedObjectIndex() {
		return selectedObjectIndex;
	}
	
	public T getSelectedObject(){
		return objects.get(selectedObjectIndex);	
	}
	
	public T getAddedObject() {
		return addedObject;
	}
	
	public void addObject(final T object) {
	    objects.add(object);
		addedObject = object;
		lastOperation = TableModel.ADD_OPERATION;
		isEmpty = true;
//		Window.alert("TableModel.addObject()");
		fireModelChanged();
	}
	

	public void modifyObject(final T object) {
		objects.set(selectedObjectIndex, object);
		lastOperation = TableModel.MODIFY_OPERATION;
//		Window.alert("TableModel.modifyObject()");
		fireModelChanged();
	}
	
	public void deleteObject() {
		objects.remove(selectedObjectIndex);
		lastOperation = TableModel.DELETE_OPERATION;
		if(getObjects().size() == 0){
		    isEmpty = false;
		}
//		Window.alert("TableModel.deleteObject()");
		fireModelChanged();
	}
	
	
	public int getLastOperation() {
		return lastOperation;
	}

    public List<String> getConsoleText() {
        return consoleText;
    }

    public void setConsoleText(List<String> text) {
        consoleText = text;
//        Window.alert("TableModel.setConsoleText()");
        fireModelChanged();
    }

    public void setConsoleText(String text) {
        consoleText = Arrays.asList(new String[]{text});
//        Window.alert("TableModel.setConsoleText()");
        fireModelChanged();
    }
    
    public boolean isNotEmpty(){
        return isEmpty;
    }

	public int getOffset() {
		return offset;
	}

}
