package fr.byob.blog.client.view.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.model.BriefGWT;
import fr.byob.client.application.controler.IPageSelectionControler;
import fr.byob.client.chat.link.LinkAnalyser;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;
import fr.byob.client.view.panel.RoundedPanel;

public class BriefWidget extends Composite {
    
    private FlowPanel global;
    private IPageSelectionControler pageControler;
    private List<ToolTip> tooltips = new ArrayList<ToolTip>();
    
    public BriefWidget(IPageSelectionControler pageControler) {
        global = new FlowPanel();
        global.setSize("490px", "100%");
        global.addStyleName(Css.INSTANCE.mrgr10());
        this.pageControler = pageControler;
        Label noBreves = new Label("Aucune brève pour le moment");
        noBreves.addStyleName("titleBrief");
        noBreves.addClickHandler(pageControler.getMenuSelectionControler(9));
        global.add(noBreves);
//        global.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        initWidget(global);
    }
    
    public void setDatas(BriefGWT brief){
        HTML briefHtml = new HTML();
        briefHtml.addStyleName("Brief");
        Object[] links = LinkAnalyser.getInstance().trimLinksSaveNb(brief.getText());
        String msgBrief = (String)links[0];
        int nbString = (Integer) links[1];
//        System.out.println("nbString : "+nbString);
        if(links.length ==2){
            if(nbString>60 ){
                briefHtml.setHTML("<i>le "+Utils.formatDate(brief.getDate())+" par <b>"+brief.getUserid()+"</b> : </i>"+msgBrief.substring(0, 57)+"...");
            }else{
                briefHtml.setHTML("<i>le "+Utils.formatDate(brief.getDate())+" par <b>"+brief.getUserid()+"</b> : </i>"+msgBrief);
            }
        }else{
            int nbFirstUrl = (Integer) links[2];
            briefHtml.setHTML("<i>le "+Utils.formatDate(brief.getDate())+" par <b>"+brief.getUserid()+"</b> : </i>"+msgBrief.substring(0, nbFirstUrl)+"...");
            System.out.println(nbFirstUrl + " / "+msgBrief);
            System.out.println("<i>le "+Utils.formatDate(brief.getDate())+" par <b>"+brief.getUserid()+"</b> : </i>"+msgBrief.substring(0, nbFirstUrl)+"...");
        }
        briefHtml.addStyleName(Css.INSTANCE.padb3());
        if(pageControler != null){
            briefHtml.addClickHandler(pageControler.getMenuSelectionControler(9));
        }
        final FocusPanel focus = new FocusPanel();
        focus.add(briefHtml);
        global.add(focus);
        final ToolTip toolTip = new ToolTip(msgBrief);
        tooltips.add(toolTip);
        focus.addMouseOverHandler(new MouseOverHandler(){
            public void onMouseOver(MouseOverEvent event) {
                for(ToolTip tip : tooltips){
                    tip.hide();
                }
                toolTip.show(focus);
            }
        });
        
    }
    public void clearWidget(){
        for(int i=global.getWidgetCount()-1;i>=0;i--){
            global.remove(i);
        }
    }
    private class ToolTip {
        DialogBox popup = new DialogBox();
        FocusPanel pop = new FocusPanel();
        public ToolTip(final String text) {
            
            pop.addStyleName("BriefToolTip");
            pop.setWidth("500px");
            pop.add(new HTML(text));
            pop.addStyleName(Css.INSTANCE.padl5());
            RoundedPanel rounded = new RoundedPanel(pop,RoundedPanel.ALL, 5, "BriefToolTip");
            popup.add(rounded);
            popup.setModal(false);
            this.addMouseOutHandler(new MouseOutHandler(){
                public void onMouseOut(MouseOutEvent event) {
                    hide();
                }
            });
            if(pageControler != null){
                addClickHandler(pageControler.getMenuSelectionControler(9));
            }
        }
        public void show(Widget sender){
            int left = sender.getAbsoluteLeft() - 5;
            int top = sender.getAbsoluteTop() -6;
            popup.setPopupPosition(left, top);
            pop.setFocus(true);
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
}
