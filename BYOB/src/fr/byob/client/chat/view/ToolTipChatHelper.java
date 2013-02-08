package fr.byob.client.chat.view;

import java.util.HashMap;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.chat.command.IConversationCommand;
import fr.byob.client.util.Css;
import fr.byob.client.view.panel.RoundedPanel;
import fr.byob.client.view.widget.BYOBTextBox;

public class ToolTipChatHelper {
    private DialogBox popup = new DialogBox();
    private FocusPanel pop = new FocusPanel();
    private VerticalPanel global = new VerticalPanel();
    public ToolTipChatHelper(final HashMap<String, IConversationCommand> cmd,final BYOBTextBox entry) {
        pop.addStyleName("BriefToolTip");
        global.addStyleName("BriefToolTip");
        pop.setWidth("500px");
        for(final String text:cmd.keySet()){
            HTML cmdHelp = new HTML(text);
            cmdHelp.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                    entry.setText("/"+cmd.get(text).getStringCommand()+" ");
                    popup.hide();
                    entry.setFocus(true);
                    
                }
            });
            cmdHelp.addStyleName(Css.INSTANCE.padt3());
            global.add(cmdHelp);
        }
        pop.add(global);
        pop.addStyleName(Css.INSTANCE.padl5());
        RoundedPanel rounded = new RoundedPanel(pop,RoundedPanel.ALL, 5, "BriefToolTip");
        popup.add(rounded);
        popup.setModal(false);
        this.addMouseOutHandler(new MouseOutHandler(){
            public void onMouseOut(MouseOutEvent event) {
                hide();
            }
        });
    }
    public void show(Widget sender){
        int left = sender.getAbsoluteLeft() - 350;
        int top = sender.getAbsoluteTop() - 150;
        popup.setPopupPosition(left, top);
//        pop.setFocus(true);
        popup.setAutoHideEnabled(true);
        popup.show();
    }
    public void hide(){
        popup.hide();
    }
    public void addMouseOverHandler(MouseOverHandler mouseOverHandler){
        pop.addMouseOverHandler(mouseOverHandler);
    }
    public void addMouseOutHandler(MouseOutHandler mouseOutHandler){
        pop.addMouseOutHandler(mouseOutHandler);
    }
    public void addClickHandler(ClickHandler clickHandler){
        pop.addClickHandler(clickHandler);
    }
    public Widget getParent(){
        return popup.getParent();
    }
}
