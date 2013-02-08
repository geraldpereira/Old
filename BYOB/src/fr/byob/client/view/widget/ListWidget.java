package fr.byob.client.view.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.util.Css;


public class ListWidget extends Composite {
    private FocusPanel listBox = new FocusPanel();
    private ScrollPanel scroll = new ScrollPanel();
    private FlexTable list = new FlexTable();
    private int nbRow = 0;
    private List<String> values = new ArrayList<String>();
    private int scrollPosition= 0;
    private int valueSelected = -1;
    private int nbHeight = 22;
    private boolean variableHeight = false;
    public ListWidget() {
         initView();
    }
    public ListWidget(boolean variableHeight) {
        this.variableHeight = variableHeight;
        initView();
   }
    private void initView(){
        Css css = Css.INSTANCE;
        list.addStyleName(css.listWidget());
        listBox.addStyleName(css.listWidget());
        scroll.add(list);
        scroll.addScrollHandler(new ScrollHandler(){
            public void onScroll(ScrollEvent event) {
                if(((ScrollPanel)event.getSource()).getAbsoluteTop() < scrollPosition){
                    scrollPosition = scrollPosition - nbHeight;
                    if(variableHeight){
                        nbHeight=nbHeight-2;
                        setVisibleItemCount(1);
                    }
                    ((ScrollPanel)event.getSource()).setScrollPosition(scrollPosition);
                }else if(((ScrollPanel)event.getSource()).getAbsoluteTop() > scrollPosition){
                    scrollPosition = scrollPosition + nbHeight;
                    if(variableHeight){
                        nbHeight=nbHeight+2;
                        setVisibleItemCount(1);
                    }
                    ((ScrollPanel)event.getSource()).setScrollPosition(scrollPosition);
                }else if(((ScrollPanel)event.getSource()).getAbsoluteTop() == scrollPosition){
                }
            }
        });
//        scroll.addScrollListener(new ScrollListener(){
//            public void onScroll(Widget widget, int scrollLeft, int scrollTop) {
//                if(scrollTop < scrollPosition){
//                    scrollPosition = scrollPosition - nbHeight;
//                    if(variableHeight){
//                        nbHeight=nbHeight-2;
//                        setVisibleItemCount(1);
//                    }
//                    ((ScrollPanel)widget).setScrollPosition(scrollPosition);
//                }else if(scrollTop > scrollPosition){
//                    scrollPosition = scrollPosition + nbHeight;
//                    if(variableHeight){
//                        nbHeight=nbHeight+2;
//                        setVisibleItemCount(1);
//                    }
//                    ((ScrollPanel)widget).setScrollPosition(scrollPosition);
//                }else if(scrollTop == scrollPosition){
//                }
//                
//            }
//        });
        listBox.add(scroll);
        initWidget(listBox);
    }
    
    public void addItem(Widget item) {
        values.add(item.getTitle());
        list.setWidget(nbRow, 0,item);
        nbRow ++;
    }
    public void addItem(Widget item,String label) {
        values.add(label);
        list.setWidget(nbRow, 0,item);
        nbRow ++;
    }
    public void setVisibleItemCount(int item){
        listBox.setHeight(100/values.size()*item+"px");
        list.setHeight(nbHeight+"px");
        scroll.setHeight(nbHeight+"px");
    }
    public void setNbHeight(int height){
        nbHeight = height;
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
        list.addClickHandler(listener);
    }
    public void setFocus(boolean isFocus){
        listBox.setFocus(isFocus);
    }
}
