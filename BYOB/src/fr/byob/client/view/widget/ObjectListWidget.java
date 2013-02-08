package fr.byob.client.view.widget;

import java.util.HashMap;
import java.util.Set;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ObjectListWidget<T> extends Composite {

	private VerticalPanel list;
	private HashMap<T, Label> model;

	public ObjectListWidget() {
		model = new HashMap<T, Label>();
		initView();
	}

	protected void initView() {
		list = new VerticalPanel();
		list.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		initWidget(list);
	}

	public void addElement(T id, String text) {
		Label label = new Label(text);
		model.put(id, label);
		list.add(label);
	}

	public void removeElement(String id) {
		Label label = model.get(id);
		if (label != null) {
			list.remove(label);
		}
	}

	public String getValue (T id){
		return model.get(id).getText();
	}
	
	public Set<T> getObjects(){
		return model.keySet();
	}

}
