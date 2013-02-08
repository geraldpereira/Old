package fr.byob.blog.client.view.panel;

import java.util.List;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import fr.byob.blog.client.controler.BriefControler;
import fr.byob.blog.client.model.BriefGWT;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.application.IDeckListener;
import fr.byob.client.chat.link.LinkAnalyser;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;


public class BriefPanel extends Composite implements IDeckListener{
    
    private VerticalPanel global = new VerticalPanel();
    private VerticalPanel briefes = new VerticalPanel();
    private List<BriefGWT> briefs ;
    private TextBox text;
    private Label console = new Label();
    
    public BriefPanel() {
        initView();
    }

    private void initView(){
        Label title = new Label("Gestion Brèves");
        title.addStyleName(Css.INSTANCE.ajoutTitleArtCat());
        FlexTable flex = new FlexTable();
        Label addBrief = new Label("Ajout brève : ");
        addBrief.addStyleName(Css.INSTANCE.msgcatLink());
        flex.setWidget(0, 0, addBrief);
        flex.getFlexCellFormatter().setColSpan(0, 0, 3);
        Label textBrief = new Label("Texte : ");
        flex.setWidget(1, 0, textBrief);
        text = new TextBox();
        text.setWidth("400px");
        flex.setWidget(1, 1, text);
        Button addBriefButton = new Button(BlogStrings.INSTANCE.buttonAdd());
        addBriefButton.addClickHandler(BriefControler.getInstance().getClickAddBrief(this));
        flex.setWidget(2, 2, addBriefButton);
        console.addStyleName(Css.INSTANCE.msgconsole());
        flex.setWidget(3, 0, console);
        flex.getFlexCellFormatter().setColSpan(3, 0, 3);
        Label lastBriefes = new Label("Dernières brèves : ");
        lastBriefes.addStyleName(Css.INSTANCE.msgcatLink());
        flex.setWidget(4, 0, lastBriefes);
        flex.getFlexCellFormatter().setColSpan(4, 0, 3);
        flex.setWidget(5, 0, briefes);
        flex.getFlexCellFormatter().setColSpan(5, 0, 3);
        global.add(title);
        global.add(flex);
        initWidget(global);
    }

    public String getTextBrief(){
        return text.getText();
    }
    public void clear(){
        text.setText("");
    }
    public void setTextConsole(String text){
        console.setText(text);
    }
    public void setBriefes(List<BriefGWT> briefes){
        this.briefs = briefes;
    }
    
    public void deckCharged() {
        console.setText("");
        BriefControler.getInstance().getAllBriefs(this);
    }

    public void chargedDatas(){
        for (int row = briefes.getWidgetCount()-1; row >= 0; row--) {
            briefes.remove(row);
        }
        for (int row = 0; row < briefs.size(); row++) {
            BriefWidgetTable briefW = new BriefWidgetTable();
            briefW.setDatas(briefs.get(row));
            briefes.add(briefW);
        }
    }
    private class BriefWidgetTable extends Composite {
        private HorizontalPanel global;
        public BriefWidgetTable() {
            global = new HorizontalPanel();
            initWidget(global);
        }
        public void setDatas(BriefGWT brief){
            HTML briefHtml = new HTML();
            briefHtml.addStyleName("TableBrief");
            String msgBrief = LinkAnalyser.getInstance().trimLinks(brief.getText());
            briefHtml.setHTML("<i>le "+Utils.formatDate(brief.getDate())+" par <b>"+brief.getUserid()+"</b> : </i>"+msgBrief);
            briefHtml.addStyleName(Css.INSTANCE.padb7());
            
            global.add(briefHtml);
        }
    }
}
