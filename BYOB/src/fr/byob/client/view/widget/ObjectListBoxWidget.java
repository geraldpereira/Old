package fr.byob.client.view.widget;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

/**
 * Tention au toString. Avec sélection.
 * @author Emilie
 *
 * @param <T>
 */
public class ObjectListBoxWidget<T> extends Composite {

	private ListBox list;
	private final HashMap<String,T> model;
	
	public ObjectListBoxWidget() {
		model = new HashMap<String, T>();
		initView();
	}

	protected void initView() {
		list = new ListBox(true);
		list.setVisibleItemCount(4);
		initWidget(list);
	}

	public void addElement(T t) {
		model.put(t.toString(),t);
		list.addItem(t.toString());
	}
	
	public Set<T> getSelectedObjects(){
		HashSet<T> set = new HashSet<T>();
		for(int i = 0 ; i < list.getItemCount() ; i++){
			if (list.isItemSelected(i)){
				set.add(model.get(list.getItemText(i)));
			}
		}
		return set;
	}
	
	/**
	 * Attention au toString()
	 * @param objects
	 */
	public void setSelectedObjects (Set<T> objects){
		for (int i = 0; i < list.getItemCount(); i++) {
			list.setItemSelected(i, false);
			for (T t : objects) {
                if (list.getItemText(i).equals(t.toString())) {
                    list.setItemSelected(i, true);
                    break;
                }
            }
        }
	}
	
	public void unselectedObjects (){
		for (int i = 0; i < list.getItemCount(); i++) {
			list.setItemSelected(i, false);
        }
	}

	public void setEnabled(boolean isEnabled) {
		list.setEnabled(isEnabled);
	}
	public void setTabIndex(int index){
        list.setTabIndex(index);
    }
	public void removeObject(int index){
	    model.remove(list.getItemText(index));
	    list.removeItem(index);
	}
	public int getObjectCount(){
	    return list.getItemCount();
	}
	public void addClickListener(ClickHandler listener){
	    list.addClickHandler(listener);
	}
}
