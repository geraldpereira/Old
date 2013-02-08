package fr.byob.client.view.widget;

import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class BYOBDialogBox extends Composite implements HasWidgets{
    
    private final DialogBox popup = new DialogBox();
    private FlexTable pop = new FlexTable();
    
    public BYOBDialogBox() {
        initView();
        initWidget(popup);
    }
    private void initView(){
        popup.setAnimationEnabled(true);
        popup.add(pop);
    }
    
    public void addLabel(String label,int row,int col){
        pop.setWidget(row, col, new Label(label));
    }
    public void addTextBox(TextBox box,int row,int col){
        pop.setWidget(row, col,box);
    }
    public void addButton(String label,ClickHandler clickListener,int row,int col){
        Button button = new Button(label);
        button.addClickHandler(clickListener);
        pop.setWidget(row, col,button);
    }
    public void setPopupStyleName(String style){
        popup.setStyleName(style);
        pop.setStyleName(style);
    }
    public void setCellWidth(int row,int col,String width){
        pop.getFlexCellFormatter().setWidth(row, col, width);
    }
    public void hide(){
        popup.hide();
    }
    public void show(){
        popup.show();
    }
    public void setPopupPosition(int left,int top){
        popup.setPopupPosition(left, top);
    }
    
    
    public void add(Widget w) {
    }
    public void clear() {
        this.removeFromParent();
    }
    public Iterator<Widget> iterator() {
        return null;
    }
    public boolean remove(Widget w) {
        return true;
    }
}
