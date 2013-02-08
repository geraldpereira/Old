package fr.byob.blog.client.view.widget;

import java.util.Date;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;

import fr.byob.blog.client.BYOBBlog;
import fr.byob.blog.client.view.image.profil.BlogEmptyProfileSmallImages;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;
import fr.byob.client.view.widget.smiley.SmileyUtils;

public class ProfilWidget extends Composite {
    private FlexTable profil;
    private String login;
    private String mail;
    private Date date;
    private String presentation;

    public ProfilWidget() {
        profil = new FlexTable();
        profil.setWidth("100%");
        initWidget(profil);
    }

    public void setData(String slogin, String mail,  Date sdate, String presentation,String sphoto) {
        for (int row = 0; row < profil.getRowCount(); row++) {
            profil.removeRow(row);
        }
        this.login = slogin;
        this.mail = mail;
        this.date = sdate;
        this.presentation = presentation;
        
        Label llogin = new Label(login);
        llogin.addClickHandler(Utils.getLinkPage(BYOBBlog.USER+slogin));
        
        llogin.addStyleName(Css.INSTANCE.msgtitleLink());
        Label lmail = new Label(this.mail);
        lmail.addStyleName(Css.INSTANCE.msgcatLink());
        String dateStr = Utils.formatDate(date);
        Label date = new Label(BlogStrings.INSTANCE.userInscription() + " " + dateStr);
        date.addStyleName(Css.INSTANCE.msgdate());
        HTML photo = new HTML();
        if(sphoto != null && !sphoto.equals(BlogStrings.INSTANCE.divHttp()) && !sphoto.equals("")){
            photo.setHTML(Utils.createImage(sphoto, 0, 100,BlogStrings.INSTANCE.applicationUsers()));
        }else{
            photo.setHTML(Utils.createImage(BlogEmptyProfileSmallImages.INSTANCE.emptyProfileSmall().createImage().getUrl(),0,100,BlogStrings.INSTANCE.applicationUsers()));
        }
//        HTML photo = new HTML(Utils.createImage(sphoto, 100, 0));//createImage(sphoto);
        ScrollPanel panelPhoto = new ScrollPanel();
        panelPhoto.setSize("110px", "110px");
        panelPhoto.add(photo);
        
        HTML content = new HTML();
        content.setHTML(SmileyUtils.getTextWithSmiley(presentation));
        content.setWidth("100%");
        content.addStyleName(Css.INSTANCE.msglabel());
        profil.setWidget(0, 0, llogin);
        profil.setWidget(1, 0, lmail);
        profil.setWidget(1, 1, date);
        profil.getFlexCellFormatter().setColSpan(0, 0, 2);
        profil.setWidget(2, 0, panelPhoto);
        profil.setWidget(2, 1, content);
        profil.getCellFormatter().setWidth(1, 0, "25%");
        profil.getCellFormatter().setWidth(1, 1, "75%");
        profil.getCellFormatter().setWidth(2, 0, "25%");
        profil.getCellFormatter().setWidth(2, 1, "75%");
        
        profil.addStyleName(Css.INSTANCE.cursor());
        
    }

//    private Image createImage(String sphoto){
//        Image photo;
//        if(sphoto != null && !sphoto.equals("http://") && !sphoto.trim().equals("")){
//            photo = new Image(sphoto);
//        }else{
//            photo = BlogImages.INSTANCE.emptyProfileSmall().createImage();
//        }
//        Utils.resizeImageOnLoad(photo,100);
//        return photo;
//    }
    
    public String getLogin() {
        return login;
    }

    public String getMail() {
        return mail;
    }

    public Date getDate() {
        return date;
    }

    public String getPresentation() {
        return presentation;
    }

}
