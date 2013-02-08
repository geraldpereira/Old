package fr.byob.blog.client.view.panel;

import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import fr.byob.blog.client.BYOBBlog;
import fr.byob.blog.client.IProfil;
import fr.byob.blog.client.view.image.profil.BlogEmptyProfileImages;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.IListener;
import fr.byob.client.application.IDeckListener;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;
import fr.byob.client.view.model.ITableModel;
import fr.byob.client.view.widget.smiley.SmileyUtils;

/**
 * Décrit le Panel pour l'affichage des articles
 * 
 * @author Akemi
 */
public class ProfilPanel extends Composite implements IDeckListener, IListener {
    /**
     * Le model de données à afficher
     */
    private ITableModel<IProfil> tableModel;
    /**
     * Titre
     */
    private Label login = new Label();
    /**
     * Date de l'article
     */
    private Label dateVisite = new Label();
    /**
     * Date de l'article
     */
    private Label dateInscription = new Label();
    /**
     * Texte
     */
    private HTML presentation = new HTML();
    /**
     * Texte
     */
    private HTML photo = new HTML();
    /**
     * Auteur
     */
    private HTML mail = new HTML();
    private HTML siteL = new HTML();
    private Label present;
    private Label site;

    private ScrollPanel photoPanel; 
    private DockPanel profilPanel = new DockPanel();
    private VerticalPanel console = new VerticalPanel();
    /**
     * Constructor
     * 
     * @param model
     */
    public ProfilPanel(ITableModel<IProfil> tableModel) {
        this.tableModel = tableModel;
        tableModel.addListener(this);
        initView();
    }

    /**
     * Initialise la vue
     */
    protected void initView() {
        FlexTable gridUser = new FlexTable();
        // HorizontalPanel desc = new HorizontalPanel();
        this.login.addStyleName(Css.INSTANCE.msgtitleLink());
        mail.addStyleName(Css.INSTANCE.msgcatLink());
       
        
        photoPanel = new ScrollPanel();
        photoPanel.setSize("310px", "310px");
        photoPanel.add(photo);
        dateInscription.addStyleName(Css.INSTANCE.msgdate());
        dateVisite.addStyleName(Css.INSTANCE.msgdate());
        

        HTML split = new HTML();
        split.setHTML("<hr>");
       
        site = new Label(BlogStrings.INSTANCE.userSite());
        site.addStyleName(Css.INSTANCE.msgcatLink());
        siteL.addStyleName(Css.INSTANCE.msgdate());
        VerticalPanel sitePanel = new VerticalPanel();
        sitePanel.add(site);
        sitePanel.add(siteL);
       
        present = new Label(BlogStrings.INSTANCE.userPresentation());
        present.addStyleName(Css.INSTANCE.msgcatLink());
        presentation.addStyleName(Css.INSTANCE.msgdate());
        VerticalPanel presentationPanel = new VerticalPanel();
        presentationPanel.add(present);
        presentationPanel.add(presentation);
        
        VerticalPanel centerPanel = new VerticalPanel();
        centerPanel.add(sitePanel);
        centerPanel.add(presentationPanel);
        
        gridUser.setWidget(0, 0, login);
        gridUser.setWidget(0, 1, mail);
        gridUser.setWidget(1, 0, dateInscription);
        gridUser.setWidget(1, 1, dateVisite);
        gridUser.setWidget(2, 0, split);
        gridUser.getCellFormatter().setWidth(0, 0, "50%");
        gridUser.getCellFormatter().setWidth(1, 0, "50%");
        gridUser.getFlexCellFormatter().setRowSpan(3, 0, 2);
        gridUser.getFlexCellFormatter().setColSpan(2, 0, 2);
        gridUser.setWidth("100%");
        
        
        profilPanel.add(gridUser,DockPanel.NORTH);
        profilPanel.add(photoPanel,DockPanel.WEST);
        profilPanel.add(centerPanel,DockPanel.CENTER);
        console.setWidth("100%");
        profilPanel.add(console, DockPanel.SOUTH);
        profilPanel.setCellWidth(gridUser, "100%");
        profilPanel.setCellWidth(photoPanel, "300px");
        profilPanel.setCellWidth(centerPanel, "100%");
        profilPanel.setCellHorizontalAlignment(centerPanel, HasHorizontalAlignment.ALIGN_LEFT);

        initWidget(profilPanel);
    }

    /**
     * Construit les donnees sous la forme appropriee pour la grille
     * 
     * @param category
     *            list des categories de l'article
     * @return les donnees
     */

    /**
     * Raffraichit la vue quand le model est modifi�
     */
    public void manageModelModification() {
        int selectedIndex = tableModel.getSelectedObjectIndex();
        if (selectedIndex != ITableModel.NEW_SELECTED_INDEX && selectedIndex != ITableModel.UNSELECTED_INDEX) { // Pour ne prendre en compte que les
            IProfil profil = tableModel.getSelectedObject();
            // Affecter le post courant à l'adapteur des commentaires.
            if (profil != null) {
                if (profil.getUserid() != null) {
                    login.setText(profil.getUserid().getUserid());
                    Utils.changeUrl(BYOBBlog.USER+profil.getUserid().getUserid());
                    if(profil.getProfilmail() != null){
                        mail.setHTML(Utils.getLinkHTML("mailto:"+profil.getProfilmail(), profil.getProfilmail()));
                        mail.setVisible(true);
                    }else{
                        mail.setVisible(false);
                    }
                    dateVisite.setText(BlogStrings.INSTANCE.userLastConn() + " "+Utils.formatDate(profil.getProfildate()));
                    dateInscription.setText(BlogStrings.INSTANCE.userInscription() + " "+Utils.formatDate(profil.getProfilinscription()));
//                    updatePhoto(profil.getProfilphoto());
                    if(profil.getProfilphoto() != null && !profil.getProfilphoto().equals(BlogStrings.INSTANCE.divHttp()) && !profil.getProfilphoto().equals("")){
                        photo.setHTML(Utils.createImage(profil.getProfilphoto(), 0, 300,BlogStrings.INSTANCE.applicationUsers()));
                    }else{
                        photo.setHTML(Utils.createImage(BlogEmptyProfileImages.INSTANCE.emptyProfile().createImage().getUrl(),0,300,BlogStrings.INSTANCE.applicationUsers()));
                    }
                    if (profil.getProfilpresentation() != null && !profil.getProfilpresentation().equals("")) {
                        presentation.setHTML(SmileyUtils.getTextWithSmiley(profil.getProfilpresentation()));
                        present.setVisible(true);
                        presentation.setVisible(true);
                    } else {
                        present.setVisible(false);
                        presentation.setVisible(false);
                    }
                    if (profil.getProfilsite() != null && !profil.getProfilsite().equals("")) {
                        siteL.setHTML(Utils.getLinkHTMLNewPage(profil.getProfilsite(),profil.getProfilsite()));
                        site.setVisible(true);
                        siteL.setVisible(true);
                    } else {
                        site.setVisible(false);
                        siteL.setVisible(false);
                    }
                    for(int i =0;i<profilPanel.getWidgetCount();i++){
                        profilPanel.getWidget(i).setVisible(true);
                    }
                    console.setVisible(false);
                }
            }
        }
    }
    public void chargedModel(IProfil newUser) {
            IProfil profil = newUser;
            // Affecter le post courant à l'adapteur des commentaires.
            if (profil != null) {
                if (profil.getUserid() != null) {
                    login.setText(profil.getUserid().getUserid());
                    if(profil.getProfilmail() != null){
                        mail.setHTML(Utils.getLinkHTML("mailto:"+profil.getProfilmail(),profil.getProfilmail()));
                        mail.setVisible(true);
                    }else{
                        mail.setVisible(false);
                    }
                    dateVisite.setText(BlogStrings.INSTANCE.userLastConn() + " "+Utils.formatDate(profil.getProfildate()));
                    dateInscription.setText(BlogStrings.INSTANCE.userInscription() + " "+Utils.formatDate(profil.getProfilinscription()));
//                    updatePhoto(profil.getProfilphoto());
                    if(profil.getProfilphoto() != null && !profil.getProfilphoto().equals(BlogStrings.INSTANCE.divHttp()) && !profil.getProfilphoto().equals("")){
                        photo.setHTML(Utils.createImage(profil.getProfilphoto(), 0, 300,BlogStrings.INSTANCE.applicationUsers()));
                    }else{
                        photo.setHTML(Utils.createImage(BlogEmptyProfileImages.INSTANCE.emptyProfile().createImage().getUrl(),0,300,BlogStrings.INSTANCE.applicationUsers()));
                    }
                    if (profil.getProfilpresentation() != null && !profil.getProfilpresentation().equals("")) {
                        presentation.setHTML(SmileyUtils.getTextWithSmiley(profil.getProfilpresentation()));
                        present.setVisible(true);
                        presentation.setVisible(true);
                    } else {
                        present.setVisible(false);
                        presentation.setVisible(false);
                    }
                    if (profil.getProfilsite() != null && !profil.getProfilsite().equals("")) {
                        siteL.setHTML(Utils.getLinkHTMLNewPage(profil.getProfilsite(),profil.getProfilsite()));
                        site.setVisible(true);
                        siteL.setVisible(true);
                    } else {
                        site.setVisible(false);
                        siteL.setVisible(false);
                    }
                    for(int i =0;i<profilPanel.getWidgetCount();i++){
                        profilPanel.getWidget(i).setVisible(true);
                    }
                    console.setVisible(false);
                }
        }
    }
    
    public void setConsoleText(List<String> texts){
        
        for(String text:texts){
            Label label = new Label(text);
            label.addStyleName(Css.INSTANCE.msgconsole());
            console.add(label);
            console.setCellHorizontalAlignment(label,HasHorizontalAlignment.ALIGN_CENTER);
        }
        for(int i =0;i<profilPanel.getWidgetCount();i++){
            profilPanel.getWidget(i).setVisible(false);
        }
        console.setVisible(true);
    }
    
    public void deckCharged() {
        manageModelModification();
    }
    
//    private void updatePhoto(String url){
//        if (photo != null){
//            photoPanel.remove(photo);
//        }
//        if(url != null && !url.equals(BlogStrings.INSTANCE.divHttp()) && !url.equals("")){
//            photo = new Image(url);
//        }else{
//            photo = BlogImages.INSTANCE.emptyProfile().createImage();
//        }
//        Utils.resizeImageOnLoad(photo,300);
//        photoPanel.add(photo);
//    }

}
