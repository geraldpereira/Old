package fr.byob.client.view.widget;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gwt.user.client.ui.ButtonBase;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.IListener;
import fr.byob.client.util.Css;
import fr.byob.client.view.adapter.IFormAdapter;
import fr.byob.client.view.model.IFormModel;
import fr.byob.client.view.panel.RoundedPanel;
import fr.byob.client.view.widget.richtext.BYOBRichText;

public class FormWidget<T> extends Composite implements IListener,IFormWidget<T> {
	
	private Label title;
	private HashMap<Integer, Widget> forms;
	private HashMap<Integer, ButtonBase> buttons;
	private VerticalPanel formPanel;
	protected FlexTable flex;
	private HorizontalPanel btnPanel;
	private final VerticalPanel fieldsAndButtons;	
	private final IFormModel<T> model;
	private final IFormAdapter<T> adapter;
	private Css css = Css.INSTANCE;
	
	public FormWidget(IFormModel<T> model, IFormAdapter<T> adapter) {
	    fieldsAndButtons = new VerticalPanel();
	    
	    this.model = model;
		this.adapter = adapter;
		formPanel = new VerticalPanel();
		forms = new HashMap<Integer,Widget>();
		buttons= new HashMap<Integer, ButtonBase>();
		this.title = new Label(adapter.getFormTitle());
		this.title.addStyleName(css.formtitle());
		formPanel.add(this.title);
		flex = new FlexTable();
		flex.setTitle(adapter.getFormTitle());
		
		btnPanel = new HorizontalPanel();  
		btnPanel.setSpacing(5);
        fieldsAndButtons.add(flex);
        fieldsAndButtons.setCellWidth(flex, "100%");
        fieldsAndButtons.add(btnPanel);
        fieldsAndButtons.setCellWidth(btnPanel, "100%");
        fieldsAndButtons.setCellHorizontalAlignment(btnPanel,HasHorizontalAlignment.ALIGN_CENTER);
        fieldsAndButtons.addStyleName(css.form());
        fieldsAndButtons.setWidth("100%");
        
		RoundedPanel rounded = new RoundedPanel(fieldsAndButtons, RoundedPanel.ALL, 5,
				css.cbgRPForm());
		formPanel.add(rounded);
		initWidget(formPanel);
		this.setSize("100%", "98%");
	}

	public void add(Integer id, String labelStr, Widget widget, int row, int col, int rowSpan,int colSpan) {
	    int realColLabel = col*2;
        int realCol = col*2 +1;
        int realColSpan = colSpan * 2 -1;
	    
	    Label label = new Label(labelStr);
        label.addStyleName(css.formlabel());
        widget.addStyleName(css.formlabel());
        forms.put(id, widget);
        flex.setWidget(row, realColLabel, label);
        flex.getCellFormatter().setHorizontalAlignment(row, realColLabel,HasHorizontalAlignment.ALIGN_RIGHT);
        flex.getFlexCellFormatter().setRowSpan(row, realColLabel, rowSpan);
        flex.setWidget(row, realCol, widget);
        flex.getFlexCellFormatter().setRowSpan(row, realCol, rowSpan);  
        flex.getFlexCellFormatter().setColSpan(row, realCol, realColSpan);
        flex.getCellFormatter().setHorizontalAlignment(row, realCol,HasHorizontalAlignment.ALIGN_LEFT);
    }

    public void add(Integer id, Widget widget, int row, int col,  int rowSpan,int colSpan) {
        int realColSpan = colSpan * 2;
        int realCol = col*2;
        
        widget.addStyleName(css.formlabel());
        forms.put(id, widget);
        flex.setWidget(row, realCol, widget);
        flex.getFlexCellFormatter().setRowSpan(row, realCol, rowSpan);  
        flex.getFlexCellFormatter().setColSpan(row, realCol, realColSpan);
        flex.getCellFormatter().setHorizontalAlignment(row, realCol,HasHorizontalAlignment.ALIGN_CENTER);
    }
	

    /**
     * L'ajout du premier bouton définit la position de tous les boutons !
     * @param buttonId
     * @param btn
     */
	public void addButton(Integer buttonId, ButtonBase btn) {
		btnPanel.add(btn);
		buttons.put(buttonId, btn);
	}

	public boolean isButtonPresent (Integer buttonId){
		return buttons.get(buttonId) != null;
	}
	
	public void setSize(String width, String height) {
		flex.setSize(width, height);
	}

	public void setTextElement(Integer key, String text) {
		if (forms.get(key) instanceof TextBoxBase) {
			((TextBoxBase) forms.get(key)).setText(text);
		} else if (forms.get(key) instanceof BYOBRichText) {
			((BYOBRichText) forms.get(key)).setText(text);
		}else if(forms.get(key) instanceof CheckBox){
		    if(text.equals("true")){
		        ((CheckBox) forms.get(key)).setValue(true);
		    }else{
		        ((CheckBox) forms.get(key)).setValue(false);
		    }
		}
	}

	public String getTextElement(Integer key) {
		if (forms.get(key) instanceof TextBoxBase) {
			return (((TextBoxBase) forms.get(key)).getText());
		} else if (forms.get(key) instanceof BYOBRichText) {
			return (((BYOBRichText) forms.get(key)).getText());
		}else if(forms.get(key) instanceof CheckBox){
            return ""+((CheckBox) forms.get(key)).getValue();
        }
		return null;
	}
	public void setEnabled(Integer key, boolean isEnabled){
	    if(model.isEnabled()){
            if (forms.get(key) instanceof TextBoxBase) {
                ((TextBoxBase) forms.get(key)).setEnabled(isEnabled);
            } else if (forms.get(key) instanceof BYOBRichText) {
                ((BYOBRichText) forms.get(key)).setEnabled(isEnabled);
            } else if (forms.get(key) instanceof ObjectListBoxWidget) {
                ((ObjectListBoxWidget<?>) forms.get(key)).setEnabled(isEnabled);
            }
	    }
    }
	public Widget getElement(Integer key) {
		return forms.get(key);
	}

	private void setEnabled(boolean isEnabled) {
		    for (Entry<Integer, Widget> entry : forms.entrySet()) {
			Widget value = entry.getValue();
			if (value instanceof TextBoxBase) {
				((TextBoxBase) value).setEnabled(isEnabled);
			} else if (value instanceof BYOBRichText) {
				((BYOBRichText) value).setEnabled(isEnabled);
			} else if (value instanceof ObjectListBoxWidget) {
				((ObjectListBoxWidget<?>) value).setEnabled(isEnabled);
			}
		}
	}
	
	
	public void manageModelModification() {
	    setEnabled(model.isEnabled());
	    if (model.getObject() != null){
			adapter.fillForm(this, model.getObject());
		}else{
			adapter.reinitForm(this);
		}
		ButtonBase add = buttons.get(ADD_BUTTON_ID);
		if (add != null){
			add.setVisible(model.isAddVisible());
			add.setEnabled(model.isButtonsEnabled());
		}
		ButtonBase mod = buttons.get(MOD_BUTTON_ID);
		if (mod != null){
			mod.setVisible(model.isModVisible());
			mod.setEnabled(model.isButtonsEnabled());
		}
		ButtonBase del = buttons.get(DEL_BUTTON_ID);
		if (del != null){
			del.setVisible(model.isDelVisible());
			del.setEnabled(model.isButtonsEnabled());
		}
	}
	
	public T getNewObject (){
		return adapter.getManagedObject(this);
	}

	public T getModifiedObject(T oldObject){
		return adapter.getManagedObject(this, oldObject);
	}
	public void setVisible(boolean isVisible) {
	    formPanel.setVisible(isVisible);
	}
}
