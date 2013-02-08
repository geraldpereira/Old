package fr.byob.client.view.widget;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import fr.byob.client.IListener;
import fr.byob.client.om.IObjectManagementStrings;
import fr.byob.client.util.Css;
import fr.byob.client.view.adapter.IConsoleAdapter;
import fr.byob.client.view.model.IConsoleModel;

public class ConsoleWidget extends Composite implements IListener {

	private VerticalPanel console;

	private final IConsoleModel model;
	private final IConsoleAdapter adapter;

	private IObjectManagementStrings strings;

	public ConsoleWidget(IConsoleModel model, IConsoleAdapter adapter) {
		strings = (IObjectManagementStrings) GWT
				.create(IObjectManagementStrings.class);
		this.adapter = adapter;
		this.model = model;
		initView();
	}

	protected void initView() {
		Css css = Css.INSTANCE;
		console = new VerticalPanel();
		console.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		console.addStyleName(css.msgconsole());
		initWidget(console);
	}

	private void setText(String text) {
		console.clear();
		appendText(text);
	}

	private void appendText(String text) {
		console.add(new Label(text));
	}

	private void setText(List<String> texts) {
		console.clear();
		for (String curText : texts) {
			appendText(curText);
		}
	}

	private void clearText() {
		console.clear();
	}

	public void manageModelModification() {
		if (model.getConsoleText() == null || model.getConsoleText().isEmpty()) {
			clearText();
		} else {
			String text = model.getConsoleText().get(0);
			if (IConsoleModel.ADDED.equals(text)) {
				setText(adapter.getObjectName() + " " + strings.added());
			} else if (IConsoleModel.MODIFIED.equals(text)) {
				setText(adapter.getObjectName() + " " + strings.modified());
			} else if (IConsoleModel.DELETED.equals(text)) {
				setText(adapter.getObjectName() + " " + strings.deleted());
			}else if (IConsoleModel.NODATA.equals(text)) {
                setText(strings.noData()+" "+adapter.getObjectName());
            } else {
				setText(model.getConsoleText());
			}
		}
	}
}
