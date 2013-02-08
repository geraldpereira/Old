package fr.byob.blog.client.view.panel;

import java.util.Date;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.IConnectedUserListener;
import fr.byob.blog.client.IProfil;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.controler.ProfilControler;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.view.image.profil.BlogEmptyProfileImages;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.application.IDeckListener;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;
import fr.byob.client.view.panel.RoundedPanel;
import fr.byob.client.view.widget.BYOBPasswordTextBox;
import fr.byob.client.view.widget.BYOBTextBox;
import fr.byob.client.view.widget.ImageButton;
import fr.byob.client.view.widget.richtext.BYOBRichText;


public class ProfilParamPanel extends Composite implements IConnectedUserListener,IDeckListener{
    
    private final static String radioCSSKey = "css";
    
    private Label login = new Label();
    private Label oldPassword = new Label();
    private BYOBPasswordTextBox password = new BYOBPasswordTextBox();
    private BYOBPasswordTextBox newPassword = new BYOBPasswordTextBox();
    private BYOBPasswordTextBox newPasswordConf = new BYOBPasswordTextBox();
    private BYOBTextBox mail = new BYOBTextBox();
    private BYOBTextBox site = new BYOBTextBox();
    private Label lastVisite = new Label();
    private Label inscription = new Label();
    private RadioButton bleuet = new RadioButton(radioCSSKey);
    private RadioButton blanc = new RadioButton(radioCSSKey);
    private RadioButton gris = new RadioButton(radioCSSKey);
    private RadioButton fonce = new RadioButton(radioCSSKey);
    private IProfil profil;
    private HTML tPhoto = new HTML();
    private BYOBRichText presentation;
    private Label console = new Label();
    private IUser user;
    private ScrollPanel panelPhoto; 
    
    public static String WRONG_PWD = "wrongPwd";
    
    public ProfilParamPanel() {
        ConnectedUserModel.getInstance().addListener(this);
        initView();
    }

    private void initView(){
        console.setStyleName(Css.INSTANCE.console());
        VerticalPanel global = new VerticalPanel();
        RoundedPanel rGlobal = new RoundedPanel(global, RoundedPanel.ALL, 8, Css.INSTANCE.settings());
        final FlexTable desc = new FlexTable();
        desc.setWidth("100%");
        global.setSpacing(10);
        login.addStyleName(Css.INSTANCE.loginSettings());
        login.addStyleName(Css.INSTANCE.padl5());
        RoundedPanel rLogin = new RoundedPanel(login, RoundedPanel.ALL, 2, Css.INSTANCE.cbgRPLoginSettings());
        rLogin.setWidth("100%");
        
        mail.setWidth("300px");
        site.setWidth("300px");
        
        Label labPass = new Label(BlogStrings.INSTANCE.userLastPassword());
        labPass.addStyleName(Css.INSTANCE.LinkSettings());
        Label labNewPass = new Label(BlogStrings.INSTANCE.userNewPassword());
        labNewPass.addStyleName(Css.INSTANCE.LinkSettings());
        Label labNewPassConf = new Label(BlogStrings.INSTANCE.userNewPasswordConf());
        labNewPassConf.addStyleName(Css.INSTANCE.LinkSettings());
        
        HTML hrpwd = new HTML("<HR>");
        desc.setWidget(1, 0, hrpwd);
        desc.setWidget(2, 0, labPass);
        desc.setWidget(2, 1,password);
        desc.setWidget(3, 0, labNewPass);
        desc.setWidget(3, 1,newPassword);
        desc.setWidget(4, 0, labNewPassConf);
        desc.setWidget(4, 1,newPasswordConf);
        
        HTML hrpwd2 = new HTML("<HR>");
        desc.setWidget(5, 0,hrpwd2);
        desc.getFlexCellFormatter().setColSpan(1, 0, 2);
        desc.getFlexCellFormatter().setColSpan(5,0,2);
        Label labMail = new Label(BlogStrings.INSTANCE.userMail());
        labMail.addStyleName(Css.INSTANCE.LinkSettings());
        desc.setWidget(6, 0,labMail);
        desc.setWidget(6, 1,mail);
        
        HorizontalPanel pDate1 = new HorizontalPanel();
        pDate1.setStyleName(Css.INSTANCE.dateSettings());
        pDate1.add(inscription);
        HorizontalPanel pDate2 = new HorizontalPanel();
        pDate2.add(lastVisite);
        pDate2.setStyleName(Css.INSTANCE.dateSettings());
        desc.setWidget(0, 0,pDate1);
        desc.setWidget(0, 1,pDate2);
        
        Button bPhoto = new Button(BlogStrings.INSTANCE.buttonAvatar());
        panelPhoto = new ScrollPanel();
        panelPhoto.setSize("310px", "310px");
        panelPhoto.add(tPhoto);
        bPhoto.addClickHandler(new ClickHandler(){
//           public void onClick(Widget sender) {
//               URLDiablog popup = new URLDiablog(sender);
//               popup.show();
//            }

        public void onClick(ClickEvent event) {
            URLDiablog popup = new URLDiablog((Widget)event.getSource());
            popup.show();
        } 
        });
        desc.setWidget(9, 0,bPhoto);
        desc.setWidget(9, 1,panelPhoto);
        
        HorizontalPanel css = new HorizontalPanel();
        Label filler2 = new Label();
        filler2.setWidth("8px");
        Label filler3 = new Label();
        filler3.setWidth("8px");
        Label filler4 = new Label();
        filler4.setWidth("8px");
        css.add(bleuet);
        css.add(createButtonCss(Css.INSTANCE.backgroundColorBlue()));
        css.add(filler2);
        css.add(gris);
        css.add(createButtonCss(Css.INSTANCE.backgroundColorGrey()));
        css.add(filler3);
        css.add(fonce);
        css.add(createButtonCss(Css.INSTANCE.backgroundColorGris()));
        css.add(filler4);
        css.add(blanc);
        css.add(createButtonCss(Css.INSTANCE.backgroundColorWhite()));
        Label labCss = new Label(BlogStrings.INSTANCE.userCSS());
        labCss.addStyleName(Css.INSTANCE.LinkSettings());
        desc.setWidget(7, 0,labCss);
        desc.setWidget(7, 1,css);
        Label labSite = new Label(BlogStrings.INSTANCE.userSite());
        labSite.addStyleName(Css.INSTANCE.LinkSettings());
        desc.setWidget(8, 0,labSite);
        desc.setWidget(8, 1,site);
        
        desc.getFlexCellFormatter().setWidth(2, 0, "25%");
        desc.getFlexCellFormatter().setWidth(2, 1, "75%");
        global.add(rLogin);
        global.add(desc);
        presentation = new BYOBRichText();
        global.add(presentation);
        global.addStyleName(Css.INSTANCE.settings());
        global.setWidth("100%");
        ProfilControler.getInstance().setProfilParamPanel(this);
        Button modif = new Button(BlogStrings.INSTANCE.buttonModify(),ProfilControler.getInstance().getClickOnModListener());
        global.add(modif);
        global.add(oldPassword);
        oldPassword.setVisible(false);
        global.setCellHorizontalAlignment(modif, HasHorizontalAlignment.ALIGN_CENTER);
        global.add(console);
        global.setCellHorizontalAlignment(console, HasHorizontalAlignment.ALIGN_CENTER);
        password.setTabIndex(1);
        newPassword.setTabIndex(2);
        newPasswordConf.setTabIndex(3);
        mail.setTabIndex(4);
        bleuet.setTabIndex(5);
        gris.setTabIndex(6);
        fonce.setTabIndex(7);
        blanc.setTabIndex(8);
        site.setTabIndex(9);
        bPhoto.setTabIndex(10);
        presentation.setTabIndex(11);
        modif.setTabIndex(12);
        initWidget(rGlobal);
    }
    private ImageButton createButtonCss(String style){
        ImageButton button = new ImageButton();
        button.setStyleName(style);
        button.setSize("18px", "18px");
        return button;
    }
    
//    private void updatePhoto(String url){
//        if (tPhoto != null){
//            panelPhoto.remove(tPhoto);
//        }
//        if(url != null && !url.equals(BlogStrings.INSTANCE.divHttp()) && !url.trim().equals("")){
//            tPhoto = new Image(url);
//        }else{
//            tPhoto = BlogImages.INSTANCE.emptyProfile().createImage();
//        }
//        Utils.resizeImageOnLoad(tPhoto,300);
//        panelPhoto.add(tPhoto);
//    }

    public void connectedUserUpdate(boolean isConnected) {
        if(isConnected){
            user = ConnectedUserModel.getInstance().getConnectedUser();
            profil = ConnectedUserModel.getInstance().getConnectedUserProfil();
            if(user != null){
                login.setText(user.getUserid());
                oldPassword.setText(user.getPassword());
            }
            if(profil != null){
                mail.setText(profil.getProfilmail());
                lastVisite.setText(BlogStrings.INSTANCE.userLastConn()+" "+Utils.formatDate(profil.getProfildate()));
                inscription.setText(BlogStrings.INSTANCE.userInscription()+" "+Utils.formatDate(profil.getProfilinscription()));
                if(profil.getProfilcss().equals(IProfil.CSS_BLEUET)){
                    bleuet.setValue(true);
                }else if(profil.getProfilcss().equals(IProfil.CSS_BLANC)){
                    blanc.setValue(true);
                }else if(profil.getProfilcss().equals(IProfil.CSS_GRIS)){
                    gris.setValue(true);
                }else if(profil.getProfilcss().equals(IProfil.CSS_FONCE)){
                    fonce.setValue(true);
                }
                site.setText(profil.getProfilsite());
                presentation.setText(profil.getProfilpresentation());
                if(profil.getProfilphoto() != null && !profil.getProfilphoto().equals(BlogStrings.INSTANCE.divHttp()) && !profil.getProfilphoto().equals("")){
                    tPhoto.setHTML(Utils.createImage(profil.getProfilphoto(), 0, 300,BlogStrings.INSTANCE.applicationUsers()));
                }else{
                    tPhoto.setHTML(Utils.createImage(BlogEmptyProfileImages.INSTANCE.emptyProfile().createImage().getUrl(),0,300,BlogStrings.INSTANCE.applicationUsers()));
                }
                //            updatePhoto(profil.getProfilphoto());
            }
        }
    }
    public String getLogin(){
        return login.getText();
    }
    public String getOldPassword(){
        return oldPassword.getText();
    }
    public String getOldPasswordConf(){
        return password.getText();
    }
    public String getPassword(){
        if(newPassword.getText().equals(newPasswordConf.getText()) || (newPassword.getText().equals("") && newPasswordConf.getText().equals("") && password.getText().equals(""))){
            return newPassword.getText();
        }else{
            console.setText(BlogStrings.INSTANCE.userErreurConfMdp());
            return WRONG_PWD;
        }
    }
    public String getMail(){
        return mail.getText();
    }
    public String getSite(){
        return site.getText();
    }
    public String getCss(){
        if(gris.getValue()){
            return IProfil.CSS_GRIS; 
        }else if(blanc.getValue()){
            return IProfil.CSS_BLANC;
        }else if(bleuet.getValue()){
            return IProfil.CSS_BLEUET;
        }else{
            return IProfil.CSS_FONCE;
        }
    }
    public Date getLastVisite(){
        return profil.getProfildate();
    }
    public Date getInscription(){
        return profil.getProfilinscription();
    }
    public String getPresentation(){
        return presentation.getText();
    }
    public String getPhoto(){
        if(! Utils.getUrlImage(tPhoto.getHTML()).equals(BlogEmptyProfileImages.INSTANCE.emptyProfile().createImage().getUrl())){
            return Utils.getUrlImage(tPhoto.getHTML());
        }else{
            return null;
        }
        
    }
    public void setConsoleText(List<String> texts){
        StringBuilder consoleText = new StringBuilder();
        for (String curText : texts){
            consoleText.append(curText);
        }
        console.setText(consoleText.toString());
    }

    public void deckCharged() {
        console.setText("");
        connectedUserUpdate(true);
    }

    private class URLDiablog {
        
        final DialogBox popup; 
        
        public URLDiablog(final Widget sender){
            popup = new DialogBox();
            popup.setAnimationEnabled(true);
            FlexTable pop = new FlexTable();
            pop.setStyleName(Css.INSTANCE.backgroundColorMain());
            Label enterImage = new Label(BlogStrings.INSTANCE.userSaisieURLAvatar());
            enterImage.setStyleName(Css.INSTANCE.fontColorMain());
            final TextBox url = new TextBox();
            url.setWidth("100%");
            Button ok = new Button(BlogStrings.INSTANCE.buttonOK());
            ok.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                    if(url.getText() != null && !url.getText().equals(BlogStrings.INSTANCE.divHttp()) && !url.getText().equals("")){
                        tPhoto.setHTML(Utils.createImage(url.getText(), 0, 300,BlogStrings.INSTANCE.applicationUsers()));
                    }else{
                        tPhoto.setHTML(Utils.createImage(BlogEmptyProfileImages.INSTANCE.emptyProfile().createImage().getUrl(),0,300,BlogStrings.INSTANCE.applicationUsers()));
                    }
//                    tPhoto.setHTML(Utils.createImage(url.getText(), 0, 300));
//                    updatePhoto(url.getText());
                    popup.hide();
                }
            });
            Button cancel = new Button(BlogStrings.INSTANCE.buttonCancel());
            cancel.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                    popup.hide();
                }
            });
            
            pop.setWidget(0, 0,enterImage);
            pop.setWidget(1, 0,url);
            pop.setWidget(0, 1,ok);
            pop.setWidget(1, 1,cancel);
            pop.getFlexCellFormatter().setWidth(0, 1, "10%");
            pop.getFlexCellFormatter().setWidth(0, 0, "90%");
            pop.setWidth("500px");
            RoundedPanel rounded = new RoundedPanel(pop,RoundedPanel.ALL, 5, Css.INSTANCE.backgroundColorMain());
            popup.add(rounded);
            
            int left = sender.getAbsoluteLeft() + 10;
            int top = sender.getAbsoluteTop() + 10;
            popup.setPopupPosition(left, top);
            
            
        }
        
        public void show (){
            popup.show();
        }
    }
}
