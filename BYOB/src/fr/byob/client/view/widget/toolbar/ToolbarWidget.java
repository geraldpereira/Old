package fr.byob.client.view.widget.toolbar;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.Label;

import fr.byob.client.IListener;
import fr.byob.client.util.Css;
import fr.byob.client.view.controler.IToolbarControler;
import fr.byob.client.view.model.IToolbarModel;
import fr.byob.client.view.model.ToolbarModel;
import fr.byob.client.view.widget.ImageButton;

public class ToolbarWidget extends Composite implements IListener{

    private HorizontalPanel toolbar;
    private Images images = (Images) GWT.create(Images.class);
    private Strings strings = (Strings) GWT.create(Strings.class);
    private final IToolbarModel model;

    private ImageButton first;
    private ImageButton prev;
    private ImageButton next;
    private ImageButton last;
    private ImageButton firstDis;
    private ImageButton prevDis;
    private ImageButton nextDis;
    private ImageButton lastDis;
    private Label dataShow;

    public ToolbarWidget(IToolbarControler controler, IToolbarModel model) {
        this.model = model;
        toolbar = new HorizontalPanel();
        dataShow = new Label();
        Css css = Css.INSTANCE;
        dataShow.addStyleName(css.formlabel());
        first =createButton(images.pageFirst(), strings.pageFirst(), controler.createFirstPageListener());
        prev = createButton(images.pagePrev(), strings.pagePrev(), controler.createPrevPageListener());
        next = createButton(images.pageNext(), strings.pageNext(), controler.createNextPageListener());
        last = createButton(images.pageLast(), strings.pageLast(), controler.createLastPageListener());
        firstDis =createButton(images.pageFirstDisabled(), strings.pageFirst());
        prevDis = createButton(images.pagePrevDisabled(), strings.pagePrev());
        nextDis = createButton(images.pageNextDisabled(), strings.pageNext());
        lastDis = createButton(images.pageLastDisabled(), strings.pageLast());
        first.addStyleName(css.cursor());
        prev.addStyleName(css.cursor());
        next.addStyleName(css.cursor());
        last.addStyleName(css.cursor());
        toolbar.add(first);
        toolbar.add(prev);
        toolbar.add(dataShow);
        toolbar.add(nextDis);
        toolbar.add(lastDis);
        
        
        toolbar.setCellHorizontalAlignment(dataShow,HasHorizontalAlignment.ALIGN_CENTER);
        toolbar.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        toolbar.setWidth("100%");
        
        initWidget(toolbar);
    }

    private ImageButton createButton(AbstractImagePrototype img, String tip) {
		ImageButton button = new ImageButton();
		button.setWidget(img.createImage());
		button.setWidth("24px");
		button.setStyleName(Css.INSTANCE.imageButton());
		button.setTitle(tip);
		return button;
	}
    
    private ImageButton createButton(AbstractImagePrototype img, String tip,
			ClickHandler listener) {
		ImageButton button = createButton(img, tip);
		button.addClickHandler(listener);
		return button;
	}
    
//    private ImageButton createToggleButton(AbstractImagePrototype img, String tip, ClickListener listener) {
//    	ImageButton button = new ImageButton(img.createImage());
//        tb.setWidth("12px");
//        tb.addClickListener(listener);
//        tb.setTitle(tip);
//        return tb;
//    }

    public void setEnabledFirst(boolean enabled){
        if(enabled == true){
            if(toolbar.getWidgetIndex(firstDis) != -1){
                toolbar.insert(first, toolbar.getWidgetIndex(firstDis));
                toolbar.remove(firstDis);
                
            }
        }else{
            if(toolbar.getWidgetIndex(first) != -1){
                toolbar.insert(firstDis, toolbar.getWidgetIndex(first));
                toolbar.remove(first);
            }
        }
    }
    public void setEnabledPrev(boolean enabled){
        if(enabled == true){
            if(toolbar.getWidgetIndex(prevDis) != -1){
                toolbar.insert(prev, toolbar.getWidgetIndex(prevDis));
                toolbar.remove(prevDis);
            }
        }else{
            if(toolbar.getWidgetIndex(prev) != -1){
                toolbar.insert(prevDis, toolbar.getWidgetIndex(prev));
                toolbar.remove(prev);
            }
        }
    }
    public void setEnabledNext(boolean enabled){
        if(enabled == true){
            if(toolbar.getWidgetIndex(nextDis) != -1){
                toolbar.insert(next, toolbar.getWidgetIndex(nextDis));
                toolbar.remove(nextDis);
            }
        }else {
            if(toolbar.getWidgetIndex(next) != -1){
                toolbar.insert(nextDis, toolbar.getWidgetIndex(next));
                toolbar.remove(next);
            }
        }
    }
    public void setEnabledLast(boolean enabled){
        if(enabled == true){
            if(toolbar.getWidgetIndex(lastDis) != -1){
                toolbar.insert(last, toolbar.getWidgetIndex(lastDis));
                toolbar.remove(lastDis);
            }
        }else{
            if(toolbar.getWidgetIndex(last) != -1){
                toolbar.insert(lastDis, toolbar.getWidgetIndex(last));
                toolbar.remove(last);
            }
        }
    }
    public void showData(long start,long end,long all){
        dataShow.setText(start+" à "+end+" sur "+all);
    }
    /**
     * Cet {@link ImageBundle} est utilisé pour les icones de boutons
     */
    public interface Images extends ImageBundle {
        AbstractImagePrototype pageFirst();
        AbstractImagePrototype pageFirstDisabled();
        AbstractImagePrototype pageLast();
        AbstractImagePrototype pageLastDisabled();
        AbstractImagePrototype pageNext();
        AbstractImagePrototype pageNextDisabled();
        AbstractImagePrototype pagePrev();
        AbstractImagePrototype pagePrevDisabled();
    }
    /**
     * Cette {@link Constants} interface est utilisée pour l'internationnalisation
     */
    public interface Strings extends Constants {
        String pageFirst();
        String pageLast();
        String pageNext();
        String pagePrev();
    }
    
	public void manageModelModification() {
		List<Integer> values = model.getValuesToolbar();
		showData(values.get(0), values.get(1), values.get(2));
		HashMap<Integer, Boolean> lastOperationToolbar = model
				.getOperationToolbar();
		for (int op : lastOperationToolbar.keySet()) {
			if (op == ToolbarModel.PREV_OPERATION) {
				setEnabledFirst(lastOperationToolbar.get(op));
				setEnabledPrev(lastOperationToolbar.get(op));
			} else if (op == ToolbarModel.NEXT_OPERATION) {
				setEnabledNext(lastOperationToolbar.get(op));
				setEnabledLast(lastOperationToolbar.get(op));
			}
		}
	}
	
	

//	public IListener getModelListenerTable() {
//		return new IListener() {
//			public void manageModelModification() {
//				// Window.alert(".manageModelModification()");
//				if (model.getLastOperation() != ObjectManagementModel.NO_OPERATION) {
//					manageOperation();
//				}
//				manageSelectionChanged();
//			}
//
//			private void manageSelectionChanged() {
//				int index = model.getSelectedObjectIndex();
//				if (index == ObjectManagementModel.UNSELECTED_INDEX) {
//					table.unselect();
//					adapter.reinitForm(form);
//				} else if (index == ObjectManagementModel.NEW_SELECTED_INDEX) {
//					table.selectRow(model.getObjects().size() + 1);
//					adapter.reinitForm(form);
//				} else {
//					table.selectRow(index);
//					adapter.fillForm(form, adapter.getManagedObject(table,
//							index));
//				}
//			}
//
//			private void manageOperation() {
//				int index = model.getSelectedObjectIndex();
//				switch (model.getLastOperation()) {
//				case ObjectManagementModel.ADD_OPERATION:
//					table.addValue(adapter.getTableWidgets(adapter
//							.getManagedObject(form)));
//					break;
//				case ObjectManagementModel.MODIFY_OPERATION:
//					table.modifyValue(adapter.getTableWidgets(model
//							.getSelectedObject()), index);
//					break;
//				case ObjectManagementModel.DELETE_OPERATION:
//					table.removeValue(index);
//					break;
//				case ObjectManagementModel.REFRESH_OPERATION:
//					table.setVisible(model.isNotEmpty());
//					toolbar.setVisible(model.isNotEmpty());
//					if (rounded != null) {
//						rounded.setVisible(model.isNotEmpty());
//					}
//					table.removeAll();
//					for (T t : model.getObjects()) {
//						table.addValue(adapter.getTableWidgets(t));
//					}
//					break;
//				}
//			}
//		};
//	}
}
