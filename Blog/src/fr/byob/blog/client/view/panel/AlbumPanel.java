package fr.byob.blog.client.view.panel;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;

import fr.byob.blog.client.controler.AlbumControler;
import fr.byob.blog.client.view.image.BlogVideo;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.application.IDeckListener;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;
import fr.byob.client.view.panel.AnimHorizontalDisclosurePanel;
import fr.byob.client.view.panel.HorizontalDisclosurePanel;
import fr.byob.client.view.widget.BYOBTextBox;
import fr.byob.client.view.widget.anim.AnimView;

public class AlbumPanel extends Composite  implements IDeckListener{
    private static SimplePanel photoContainer = new SimplePanel();
    private static List<Widget> thumbsnail = new ArrayList<Widget>();
    private final HorizontalPanel thumbs = new HorizontalPanel();
    private ListBox choose;
    final Label console = new Label();
    final FormPanel form = new FormPanel();
    BYOBTextBox modifNameAlbum = new BYOBTextBox();
    BYOBTextBox modifNameMedia = new BYOBTextBox();
    
    private AnimHorizontalDisclosurePanel posts;
    
    private final static String IMAGE_TYPE_JPEG = "jpeg";
    private final static String IMAGE_TYPE_JPG = "jpg"; 
    private final static String IMAGE_TYPE_GIF = "gif";
    private final static String IMAGE_TYPE_PNG = "png";
    private static Label addrMedia = new Label();
    
    public AlbumPanel() {
        form.setAction(GWT.getModuleBaseURL() +"UploadServlet");
        form.setEncoding(FormPanel.ENCODING_MULTIPART);
        form.setMethod(FormPanel.METHOD_POST);
        AlbumControler.getInstance().setThumbsPanel(thumbs);
        AlbumControler.getInstance().setPhotoContainer(photoContainer);
        VerticalPanel global = new VerticalPanel();
        Label title = new Label("BYOB Album");
        Label chooseAlbum = new Label("Choisir l'album à visualiser : ");
        choose = new ListBox();
        AlbumControler.getInstance().setChooseAlbumView(choose);
        AlbumControler.getInstance().getAlbums();
        choose.setName("chooseAlbum");
        choose.addChangeHandler(AlbumControler.getInstance().getAlbumChangeListener(thumbs,addrMedia));
        HorizontalPanel albums = new HorizontalPanel();
        albums.add(chooseAlbum);
        albums.add(choose);
        title.addStyleName(Css.INSTANCE.ajoutTitleArtCat());
        HorizontalPanel photo = new HorizontalPanel();
        photo.add(photoContainer);
        photoContainer.addStyleName("view");
        photoContainer.setHeight("450px");
        HorizontalPanel nav = new HorizontalPanel();
        ScrollPanel scroll = new ScrollPanel();
        scroll.setSize("600px", "130px");
        
        global.add(title);
        global.add(albums);
        global.add(photo);
        global.add(nav);
        scroll.add(thumbs);
        global.add(scroll);
        photo.setCellHorizontalAlignment(photoContainer, HasHorizontalAlignment.ALIGN_CENTER);
        global.setCellHorizontalAlignment(photo, HasHorizontalAlignment.ALIGN_CENTER);
        global.setCellHorizontalAlignment(scroll, HasHorizontalAlignment.ALIGN_CENTER);
        global.setSize("100%", "100%");
        
        posts = new AnimHorizontalDisclosurePanel(global, createDisclosurePhotoPanel(), HorizontalDisclosurePanel.RIGHT, 400,600,false,1000);
        posts.setWidth("99%");
        
        AlbumControler.getInstance().setAlbumPanel(this);
        form.setWidget(posts);
        initWidget(form);
    }
    public static HTML createThumbPhoto(String url){
        final HTML html = new HTML();
        thumbsnail.add(html);
        html.addStyleName("thumbs");
        url = url.replaceAll("\\\\", "/");
//        url = url.replaceAll("//", "/");
//        url = url.replaceAll("/", "\\\\");
//        url = url.replaceAll("//", "/");
        html.setHTML(Utils.createImage(url, 100, 0,BlogStrings.INSTANCE.applicationAlbum()));
        html.addClickHandler(AlbumControler.getInstance().getAlbumClickListener(html, url, thumbsnail, photoContainer));
        html.addClickHandler(AlbumControler.getInstance().getAddrMediaGestionListener(url,addrMedia));
        html.addClickHandler(AlbumControler.getInstance().setSelectedThumbs(url));
        return html;
    }
    public static Widget createPlayer(String url){
        final HTML html = new HTML();
        thumbsnail.add(html);
        html.addStyleName("thumbs");
//        url = url.replaceAll("\\\\", "/");
        html.setWidth("100px");
        html.setHTML(url.substring(url.lastIndexOf("/")+1)+Utils.createImage(BlogVideo.INSTANCE.video().createImage().getUrl(), 83, 0,BlogStrings.INSTANCE.applicationAlbum()));
        html.addClickHandler(AlbumControler.getInstance().getAlbumClickListener(html, url, thumbsnail, photoContainer));
        html.addClickHandler(AlbumControler.getInstance().getAddrMediaGestionListener(url,addrMedia));
        html.addClickHandler(AlbumControler.getInstance().setSelectedThumbs(url));
        return html;
    }
    public SimplePanel getPhotoContainer(){
        return photoContainer;
    }
    public void deckCharged() {
//        choose.setSelectedIndex(0);
        if(photoContainer.getWidget() != null){
            photoContainer.remove(photoContainer.getWidget());//setHTML("");
        }
        for(int i=thumbs.getWidgetCount()-1;i>=0;i--){
            thumbs.remove(i);
        }
        AlbumControler.getInstance().getAlbums();
        System.out.println("deckCharged !!!");
        AlbumControler.getInstance().getPhotos("");
        modifNameAlbum.setText("");
        modifNameMedia.setText("");
        addrMedia.setText("");
        System.out.println("deckCharged !!! fin");
//        if(choose.getItemCount()>0){
//            AlbumControler.getInstance().getPhotos(choose.getItemText(0));
//        }
    }
    public void selectChoose(String album){
        int select = 0;
        for(int i=0;i<choose.getItemCount();i++){
            System.out.println("Select choose : "+choose.getItemText(i)+" / "+album);
            if(choose.getItemText(i).equals(album)){
                select = i;
            }
        }
        System.out.println("select : "+select);
        choose.setSelectedIndex(select);
    }
    
    public static boolean isImg(String url){
        String ext = url.substring(url.lastIndexOf(".")+1).toLowerCase();
        if(ext.equals(IMAGE_TYPE_GIF) || ext.equals(IMAGE_TYPE_JPEG) || ext.equals(IMAGE_TYPE_JPG)|| ext.equals(IMAGE_TYPE_PNG)){
            return true;
        }
        return false;
    }
    private VerticalPanel createDisclosurePhotoPanel(){
        
        final FileUpload uploadPhoto;
        final FileUpload uploadVideo;
        
        FlexTable flexGlobal = new FlexTable();
        final VerticalPanel global = new VerticalPanel();
        
        /*Initialisation de la servlet pour l'upload de fichier*/
        
        
        console.addStyleName(Css.INSTANCE.msgconsole());
        
        /*Titre de la page*/
        Label title = new Label(BlogStrings.INSTANCE.gestionAlbum());
        title.addStyleName(Css.INSTANCE.ajoutTitleArtCat());
        global.add(title);
        
        /*Création d'un album*/
        Label addAlbumtitle = new Label(BlogStrings.INSTANCE.createAlbum());
        addAlbumtitle.addStyleName(Css.INSTANCE.msgcatLink());
        Label createAlbum = new Label(BlogStrings.INSTANCE.newAlbum());
        BYOBTextBox newNameAlbum = new BYOBTextBox();
        Button addAlbum = new Button(BlogStrings.INSTANCE.buttonAdd());
        addAlbum.addClickHandler(AlbumControler.getInstance().addAlbum(newNameAlbum));
        flexGlobal.setWidget(0,0,addAlbumtitle);
        flexGlobal.getFlexCellFormatter().setColSpan(0, 0, 3);
        flexGlobal.setWidget(1,0,createAlbum);
        flexGlobal.setWidget(1,1,newNameAlbum);
        flexGlobal.setWidget(2,2,addAlbum);
        
        /*Modif d'un album*/
        Label modifAlbumtitle = new Label("Modification d'un album");
        modifAlbumtitle.addStyleName(Css.INSTANCE.msgcatLink());
        Label modifAlbumL = new Label(BlogStrings.INSTANCE.newAlbum());
        
        Button modifAlbum = new Button(BlogStrings.INSTANCE.buttonModify());
        modifAlbum.addClickHandler(AlbumControler.getInstance().modifyAlbum(modifNameAlbum));
        flexGlobal.setWidget(3,0,modifAlbumtitle);
        flexGlobal.getFlexCellFormatter().setColSpan(3, 0, 3);
        flexGlobal.setWidget(4,0,modifAlbumL);
        flexGlobal.setWidget(4,1,modifNameAlbum);
        flexGlobal.setWidget(5,2,modifAlbum);
        
        /*Suppression d'un album photo*/
         Label deleteAlbumtitle = new Label(BlogStrings.INSTANCE.deleteAlbum());
         deleteAlbumtitle.addStyleName(Css.INSTANCE.msgcatLink());
         deleteAlbumtitle.addStyleName(Css.INSTANCE.padt10());
         AlbumControler.getInstance().getAlbums();
         Button deleteAlbum = new Button(BlogStrings.INSTANCE.buttonDelete());
         flexGlobal.setWidget(6, 0, deleteAlbumtitle);
         flexGlobal.getFlexCellFormatter().setColSpan(6, 0, 3);
         flexGlobal.setWidget(7,2,deleteAlbum);
         deleteAlbum.addClickHandler(AlbumControler.getInstance().deleteAlbum(choose));
        
        /*Création de l'ajout des photos et des videos*/
        /*ajout des photos*/
        Label addtitle = new Label(BlogStrings.INSTANCE.addPhoto());
        addtitle.addStyleName(Css.INSTANCE.msgcatLink());
        flexGlobal.setWidget(8,0,addtitle);
        flexGlobal.getFlexCellFormatter().setColSpan(8, 0, 3);
        Label choosePhoto = new Label(BlogStrings.INSTANCE.photo());
        flexGlobal.setWidget(9,0,choosePhoto);
        Label size = new Label(BlogStrings.INSTANCE.sizePhoto());
        size.addStyleName(Css.INSTANCE.entryStatus());
        AlbumControler.getInstance().getAlbums();
        uploadPhoto = new FileUpload();
        uploadPhoto.setName("uploadPhoto");
        flexGlobal.setWidget(9,1, uploadPhoto);
        flexGlobal.setWidget(10,0, size);
        flexGlobal.getFlexCellFormatter().setColSpan(10, 0, 3);
        Button addPhoto = new Button(BlogStrings.INSTANCE.buttonAdd());
        addPhoto.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                form.submit();
            } 
        });
        addPhoto.addStyleName(Css.INSTANCE.mrgl10());
        flexGlobal.setWidget(11,2,addPhoto);
        
        /*Ajout des videos*/
        Label addVideotitle = new Label(BlogStrings.INSTANCE.addVideo());
        addVideotitle.addStyleName(Css.INSTANCE.msgcatLink());
        flexGlobal.setWidget(12,0,addVideotitle);
        flexGlobal.getFlexCellFormatter().setColSpan(8, 0, 3);
        Label chooseVideoPhoto = new Label(BlogStrings.INSTANCE.video());
        flexGlobal.setWidget(13,0,chooseVideoPhoto);
        AlbumControler.getInstance().getAlbums();
        uploadVideo = new FileUpload();
        uploadVideo.setName("uploadVideo");
        flexGlobal.setWidget(13,1, uploadVideo);
        Label sizeVideo = new Label(BlogStrings.INSTANCE.sizeVideo());
        sizeVideo.addStyleName(Css.INSTANCE.entryStatus());
        flexGlobal.setWidget(14,0, sizeVideo);
        flexGlobal.getFlexCellFormatter().setColSpan(14, 0, 3);
        Button addVideo = new Button(BlogStrings.INSTANCE.buttonAdd());
        addVideo.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                form.submit();
            } 
        });
        addVideo.addStyleName(Css.INSTANCE.mrgl10());
        flexGlobal.setWidget(15,2,addVideo);
        /*Fin du panel d'ajout*/
        
        /*Modif d'un media*/
        Label modifMediatitle = new Label("Modification d'un media");
        modifMediatitle.addStyleName(Css.INSTANCE.msgcatLink());
        Label modifMediaL = new Label(BlogStrings.INSTANCE.photo());
        
        Button modifMedia = new Button(BlogStrings.INSTANCE.buttonModify());
        //TODO regarder comment modifier un répertoire
        modifMedia.addClickHandler(AlbumControler.getInstance().modifyMedia(modifNameMedia));
        flexGlobal.setWidget(16,0,modifMediatitle);
        flexGlobal.getFlexCellFormatter().setColSpan(16, 0, 3);
        flexGlobal.setWidget(17,0,modifMediaL);
        flexGlobal.setWidget(17,1,modifNameMedia);
        flexGlobal.setWidget(18,2,modifMedia);

        /*Gestion adresse média*/
//        FlexTable gestionPanel = new FlexTable();
        Label gestionMediaTitle = new Label(BlogStrings.INSTANCE.gestionMedia());
        gestionMediaTitle.addStyleName(Css.INSTANCE.msgcatLink());
        gestionMediaTitle.addStyleName(Css.INSTANCE.padt1ex());
        flexGlobal.setWidget(19,0,gestionMediaTitle);
        flexGlobal.getFlexCellFormatter().setColSpan(19, 0, 3);
        Label addr = new Label(BlogStrings.INSTANCE.addMedia());
        
        flexGlobal.setWidget(20, 0, addr);
        flexGlobal.setWidget(20, 1, addrMedia);
        flexGlobal.getFlexCellFormatter().setColSpan(20, 1, 2);
        addrMedia.addStyleName(Css.INSTANCE.entryStatus());
        
        /*Supression de photos ou videos*/
        Label deletetitle = new Label(BlogStrings.INSTANCE.deleteMedia());
        deletetitle.addStyleName(Css.INSTANCE.msgcatLink());
        deletetitle.addStyleName(Css.INSTANCE.padt10());
        flexGlobal.setWidget(21,0,deletetitle);
        Button deletePhoto = new Button(BlogStrings.INSTANCE.buttonDelete());
        deletePhoto.addStyleName(Css.INSTANCE.mrgl10());
        flexGlobal.setWidget(22,2,deletePhoto);
        deletePhoto.addStyleName(Css.INSTANCE.mrgall3());
        deletePhoto.addClickHandler(AlbumControler.getInstance().deletePhoto());
        
        flexGlobal.addStyleName(Css.INSTANCE.mrgt10());
        
        global.add(flexGlobal);
        global.add(console);
        
        
        
        form.addSubmitHandler(new FormPanel.SubmitHandler() {
            public void onSubmit(SubmitEvent event) {
                System.out.println((uploadPhoto.getFilename() == null )+" / "+ uploadPhoto.getFilename().equals("") +" / "+( uploadVideo.getFilename() == null) +" / "+ uploadVideo.getFilename().equals(""));
                if(uploadPhoto.getFilename() == null && uploadPhoto.getFilename().equals("") && uploadVideo.getFilename() == null && uploadVideo.getFilename().equals("")){
                    console.setText("Veuillez ajouter un média");
                    event.cancel();
                }else{
                    global.insert(AnimView.getInstance(), 0);
                    AnimView.getInstance().startTimer();
                }
            }
        });
        form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            public void onSubmitComplete(SubmitCompleteEvent event) {
//                    if(chooseAddV.getItemText(chooseAddV.getSelectedIndex()).equals(chooseDeletePhoto.getItemText(chooseDeletePhoto.getSelectedIndex()))){
//                        AlbumControler.getInstance().getPhotosDelete(chooseAddV.getItemText(chooseAddV.getSelectedIndex()));
//                        viewer.setHTML("");
//                    }
//                    if(chooseAddP.getItemText(chooseAddP.getSelectedIndex()).equals(chooseDeletePhoto.getItemText(chooseDeletePhoto.getSelectedIndex()))){
//                        AlbumControler.getInstance().getPhotosDelete(chooseAddP.getItemText(chooseAddP.getSelectedIndex()));
//                        viewer.setHTML("");
//                    }
                global.remove(AnimView.getInstance());
                AnimView.getInstance().stopTimer();
                AlbumControler.getInstance().getPhotos("");
                    form.reset();
                    console.setText("Média ajoutée");
                    
            }
        });

        global.addStyleName(Css.INSTANCE.padall10());
        return global;
    }
    public void setConsoleText(List<String> texts){
      StringBuilder consoleText = new StringBuilder();
      for (String curText : texts){
          consoleText.append(curText);
      }
      console.setText(consoleText.toString());
  }
}
