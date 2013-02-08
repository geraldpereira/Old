package fr.byob.client.view.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ImageBundle;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.util.Css;

public class BoxListWidget extends Composite{
    
    private DialogBox box ;
    private FlexTable list = new FlexTable();
    private int nbRow = 0;
    private List<String> values = new ArrayList<String>();
    private int valueSelected = -1;
    private Images images = (Images) GWT.create(Images.class);
    private Css css = Css.INSTANCE;
    
    public BoxListWidget(String caption) {
         initView(caption);
    }
    public void initView(String caption){
        final HorizontalPanel globTitle = new HorizontalPanel();
        final Label title = new Label();
        title.setStyleName(css.titleListWidget());
        title.setText(caption);
        globTitle.add(title);
        globTitle.addStyleName(css.titleListWidget());
        Image btn = images.btnArrow().createImage();
        globTitle.add(btn);
        box = new DialogBox();
        box.setStyleName(css.listWidget());
        box.add(list);
        btn.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                box.setPopupPosition(globTitle.getAbsoluteLeft(), title.getAbsoluteTop()+title.getOffsetHeight());
                box.show();
            }
        });
        title.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                box.setPopupPosition(globTitle.getAbsoluteLeft(), title.getAbsoluteTop()+title.getOffsetHeight());
                box.show();
            }
        });
        initWidget(globTitle);
    }
    public void addItem(Widget item) {
        values.add(item.getTitle());
        list.setWidget(nbRow, 0,item);
        nbRow ++;
    }
    public void addItem(final Label item,String label,ClickHandler listener) {
        values.add(label);
        list.setWidget(nbRow, 0,item);
        item.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                box.hide();
            }
        });
        item.addMouseOverHandler(new MouseOverHandler(){
            public void onMouseOver(MouseOverEvent event) {
                item.addStyleName(css.selectedList());
            }
        });
        item.addMouseOutHandler(new MouseOutHandler(){
            public void onMouseOut(MouseOutEvent event) {
                item.removeStyleName(css.selectedList());
            }
        });
        item.addClickHandler(listener);
        nbRow ++;
    }
    public int getSelectedIndex(){
        return valueSelected;
    }
    public String getValue(int index){
        return values.get(index);
    }
    public void setSelectedIndex(int index){
        valueSelected =index;
    }
    public void addChangeListener(ClickHandler listener){
    }
    public interface Images extends ImageBundle {

        AbstractImagePrototype btnArrow();
    }
    public void setBoxHide(){
        box.hide();
    }
}
