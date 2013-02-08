package fr.byob.client.view.widget;

import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.view.adapter.IFormAdapter;
import fr.byob.client.view.model.IFormModel;

public class SimpleFormWidget<T> extends FormWidget<T> {
	
//	private int nbRows = 0;
	
	public SimpleFormWidget(IFormModel<T> model, IFormAdapter<T> adapter) {
		super(model,adapter);
	}

	
	public void add(Integer id, String label, Widget widget) {
	    super.add(id, label, widget, flex.getRowCount(), 0, 1, 1);
//	    Label att1 = new Label(label);
//		att1.addStyleName(css.formlabel());
//		textfield.addStyleName(css.formlabel());
//		forms.put(id, textfield);
//		flex.setWidget(nbRows, 0, att1);
//		flex.setWidget(nbRows, 1, textfield);
//		flex.getFlexCellFormatter().setHorizontalAlignment(nbRows, 0,
//				HasHorizontalAlignment.ALIGN_RIGHT);
//		nbRows++;
	}

	public void add(Integer id, Widget widget) {
	    super.add(id, widget, flex.getRowCount(), 0, 1, 1);
//		forms.put(id, textfield);
//		flex.setWidget(nbRows, 0, textfield);
//		flex.getFlexCellFormatter().setColSpan(nbRows, 0, 2);
//		nbRows++;
	}
}
