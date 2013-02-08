package fr.byob.blog.client.controler;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.BYOBBlog;
import fr.byob.blog.client.service.BYOBService;
import fr.byob.blog.client.service.BYOBServiceAsync;
import fr.byob.blog.client.view.panel.AlbumPanel;
import fr.byob.blog.client.view.panel.HTMLCreatorPanel;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.util.Utils;
import fr.byob.client.view.widget.BYOBTextBox;
import fr.byob.client.view.widget.PlayerWidget;

public class AlbumControler {

    private BYOBServiceAsync service;

    private HorizontalPanel thumbs;
    private AlbumPanel viewUser ;
    private HTMLCreatorPanel viewAdmin;

    private static AlbumControler album = new AlbumControler();

    private ListBox chooseAlbumView;

    private String thumbsSelected ;

    private StringsControler strings = (StringsControler) GWT.create(StringsControler.class);

    private SimplePanel photoContainer;

    private AlbumControler() {
        service = (BYOBServiceAsync) GWT.create(BYOBService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) service;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "byobService";
        endpoint.setServiceEntryPoint(moduleRelativeURL);
    }
    public static AlbumControler getInstance(){
        return album;
    }

    public void setAlbumPanel(AlbumPanel viewUser){
        this.viewUser = viewUser;
    }
    public void setHTMLCreatorPanel(HTMLCreatorPanel viewAdmin){
        this.viewAdmin = viewAdmin;
    }
    public void setPhotoContainer(SimplePanel photoContainer){
        this.photoContainer = photoContainer;
    }

    public ChangeHandler getAlbumChangeListener(final HorizontalPanel thumbs,final Label addrMedia) {
        return new ChangeHandler() {

            public void onChange(ChangeEvent event) {
                ListBox sender = (ListBox)event.getSource();
                int select = sender.getSelectedIndex();
                String item = sender.getItemText(select);
                for(int i=thumbs.getWidgetCount()-1;i>=0;i--){
                    thumbs.remove(i);
                }
                getPhotos(item);
                if(photoContainer.getWidget() != null){
                    photoContainer.remove(photoContainer.getWidget());
                }
                addrMedia.setText("");
                thumbsSelected = "";
                Utils.changeUrl(BYOBBlog.PHOTO+item);
            }
        };
    }
    private String createPhotoCenter(String url){
        return Utils.createImage(url, 450, 0,BlogStrings.INSTANCE.applicationAlbum());
    }
    public ClickHandler getAlbumClickListener(final HTML html,final String url,final List<Widget> thumbsnail,final SimplePanel photoContainer) {
        return new ClickHandler(){
            public void onClick(ClickEvent event) {
                if(AlbumPanel.isImg(url)){
                    if(photoContainer.getWidget() != null){
                        photoContainer.remove(photoContainer.getWidget());
                    }
                    photoContainer.add(new HTML(createPhotoCenter(url)));
                }else{
                    if(photoContainer.getWidget() != null){
                        photoContainer.remove(photoContainer.getWidget());
                    }
                    PlayerWidget player = new PlayerWidget(url);
                    photoContainer.add(player);
                }
                for(Widget thumb : thumbsnail){
                    thumb.removeStyleName("selectThumb");
                }
                html.addStyleName("selectThumb");
            }
        };
    }
    public ClickHandler deletePhoto(){
        return new ClickHandler(){
            public void onClick(ClickEvent event) {
                deletePhotoAlbum();
            }
        };
    }
    public ClickHandler setSelectedThumbs(final String url){
        return new ClickHandler(){
            public void onClick(ClickEvent event) {
                thumbsSelected = url;
            }
        };
    }
    public ClickHandler addAlbum(final BYOBTextBox text){
        return new ClickHandler(){
            public void onClick(ClickEvent event) {
                AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
                    public void onFailure(Throwable caught) {
                        if(viewAdmin != null){
                            viewAdmin.setConsoleText(Arrays.asList(new String[]{strings.pbAddAlbum()}));
                        }
                        if(viewUser != null){
                            viewUser.setConsoleText(Arrays.asList(new String[]{strings.pbAddAlbum()}));
                        }
                    }

                    public void onSuccess(Boolean result) {
                        getAlbums();
                        getPhotos("");
                        thumbsSelected = "";
                        if(viewAdmin != null){
                            viewAdmin.setConsoleText(Arrays.asList(new String[]{strings.addAlbum()}));
                        }
                        if(viewAdmin != null){
                            viewAdmin.setConsoleText(Arrays.asList(new String[]{strings.addAlbum()}));
                        }
                    }
                };
                if(text.getText() == null || text.getText().equals("")){
                    if(viewUser != null){
                        viewUser.setConsoleText(Arrays.asList(new String[]{"Veuillez écrire un nouveau nom"}));
                    }
                }else{
                    service.addAlbum(text.getText(), callback);
                }
            }
        };
    }
    public ClickHandler modifyAlbum(final BYOBTextBox text){
        return new ClickHandler(){
            public void onClick(ClickEvent event) {
                AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
                    public void onFailure(Throwable caught) {
                        text.setText("");
                        if(viewAdmin != null){
                            viewAdmin.setConsoleText(Arrays.asList(new String[]{"Pb Album modifié"}));
                        }
                        if(viewUser != null){
                            viewUser.setConsoleText(Arrays.asList(new String[]{"Pb Album modifié"}));
                        }
                    }

                    public void onSuccess(Boolean result) {
                        getAlbums();
                        getPhotos("");
                        text.setText("");
                        thumbsSelected = "";
                        if(result){
                            if(viewAdmin != null){
                                viewAdmin.setConsoleText(Arrays.asList(new String[]{"Album modifié"}));
                            }
                            if(viewUser != null){
                                viewUser.setConsoleText(Arrays.asList(new String[]{"Album modifié"}));
                            }
                        }else{
                            if(viewAdmin != null){
                                viewAdmin.setConsoleText(Arrays.asList(new String[]{"Impossible de modifier cet album"}));
                            }
                            if(viewUser != null){
                                viewUser.setConsoleText(Arrays.asList(new String[]{"Impossible de modifier cet album"}));
                            }
                        }
                    }
                };
                if(text.getText() == null || text.getText().equals("")){
                    if(viewUser != null){
                        viewUser.setConsoleText(Arrays.asList(new String[]{"Veuillez écrire un nouveau nom"}));
                    }
                }else{
                    System.out.println("old : "+chooseAlbumView.getItemText(chooseAlbumView.getSelectedIndex())+" new : "+text.getText());
                    service.modifyAlbum(chooseAlbumView.getItemText(chooseAlbumView.getSelectedIndex()),text.getText(), callback);
                }
            }
        };
    }
    public ClickHandler modifyMedia(final BYOBTextBox text){
        return new ClickHandler(){
            public void onClick(ClickEvent event) {
                AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
                    public void onFailure(Throwable caught) {
                        text.setText("");
                        if(viewAdmin != null){
                            text.setText("");
                            viewAdmin.setConsoleText(Arrays.asList(new String[]{"Pb Media modifié"}));
                        }
                        if(viewUser != null){
                            text.setText("");
                            viewUser.setConsoleText(Arrays.asList(new String[]{"Pb Media modifié"}));
                        }
                    }

                    public void onSuccess(Boolean result) {
                        getAlbums();
                        getPhotos("");
                        text.setText("");
                        thumbsSelected = "";
                        if(result){
                            if(viewAdmin != null){
                                viewAdmin.setConsoleText(Arrays.asList(new String[]{"Media modifié"}));
                            }
                            if(viewUser != null){
                                viewUser.setConsoleText(Arrays.asList(new String[]{"Media modifié"}));
                            }
                        }else{
                            if(viewAdmin != null){
                                viewAdmin.setConsoleText(Arrays.asList(new String[]{"Impossible de modifier ce media"}));
                            }
                            if(viewUser != null){
                                viewUser.setConsoleText(Arrays.asList(new String[]{"Impossible de modifier ce media"}));
                            }
                        }
                    }
                };
                if(thumbsSelected == null || thumbsSelected.equals("")){
                    if(viewUser != null){
                        viewUser.setConsoleText(Arrays.asList(new String[]{"Veuillez selectionner un média"}));
                    }
                }
                else if(text.getText() == null || text.getText().equals("")){
                    if(viewUser != null){
                        viewUser.setConsoleText(Arrays.asList(new String[]{"Veuillez écrire un nouveau nom"}));
                    }
                }else{
                    System.out.println("old : "+thumbsSelected+" new : "+text.getText());
                    System.out.println("old : "+thumbsSelected+" || new : "+chooseAlbumView.getItemText(chooseAlbumView.getSelectedIndex())+"/"+text.getText());
                    String oldMedia = thumbsSelected.substring(thumbsSelected.indexOf(chooseAlbumView.getItemText(chooseAlbumView.getSelectedIndex())+"/"),thumbsSelected.length());
                    System.out.println("oldMedia : "+oldMedia);
                    service.modifyMedia(oldMedia,chooseAlbumView.getItemText(chooseAlbumView.getSelectedIndex())+"/"+text.getText(), callback);
                }
            }
        };
    }
    public ClickHandler deleteAlbum(final ListBox list){
        return new ClickHandler(){
            public void onClick(ClickEvent event) {
                AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
                    public void onFailure(Throwable caught) {
                        viewUser.setConsoleText(Arrays.asList(new String[]{strings.pbDeleteAlbum()}));
                    }

                    public void onSuccess(Boolean result) {
                        getAlbums();
                        getPhotos("");
                        thumbsSelected = "";
                        viewUser.setConsoleText(Arrays.asList(new String[]{strings.deleteAlbum()}));
                    }
                };
                service.deleteAlbum(list.getItemText(list.getSelectedIndex()), callback);
            }
        };
    }
    public ClickHandler getDeleteViewerListener(final HTML viewer){
        return new ClickHandler(){
            public void onClick(ClickEvent event) {
                int select = ((ListBox)event.getSource()).getSelectedIndex();
                String selected = ((ListBox)event.getSource()).getItemText(select);
                viewer.setHTML("<img src=\""+selected+"\" alt=\"\" height=\"100px\">");
            }
        };
    }
    public ClickHandler getMediaGestionListener(final HTML viewer){
        return new ClickHandler(){
            public void onClick(ClickEvent event) {
                int select = ((ListBox)event.getSource()).getSelectedIndex();
                String selected = ((ListBox)event.getSource()).getItemText(select);
                viewer.setHTML("<img src=\""+selected+"\" alt=\"\" height=\"100px\">");
            }
        };
    }
    public ClickHandler getAddrMediaGestionListener(final String addrString,final Label addr){
        return new ClickHandler(){
            public void onClick(ClickEvent event) {
                addr.setText("http://www.byob.fr"+addrString);
            }
        };
    }
    public void getAlbums(){
        AsyncCallback<List<String>> callback = new AsyncCallback<List<String>>() {
            public void onFailure(Throwable caught) {
                if(viewUser != null)
                    viewUser.setConsoleText(Arrays.asList(new String[]{strings.pbGetAlbum()}));
                if(viewAdmin != null)
                    viewAdmin.setConsoleText(Arrays.asList(new String[]{strings.pbGetAlbum()}));
            }

            public void onSuccess(List<String> result) {
                if(chooseAlbumView != null){
                    for(int i=chooseAlbumView.getItemCount()-1;i>=0;i--){
                        chooseAlbumView.removeItem(i);
                    }
                }
                for(String item :result){
                    if(chooseAlbumView != null){
                        chooseAlbumView.addItem(item);
                    }
                }
            }
        };
        service.getAlbums(callback);
    }
    public void getPhotos(final String album){
        AsyncCallback<List<String>> callback = new AsyncCallback<List<String>>() {
            public void onFailure(Throwable caught) {
                if(viewAdmin != null)
                    viewAdmin.setConsoleText(Arrays.asList(new String[]{strings.pbGetPhoto()}));
                if(viewUser != null)
                    viewUser.setConsoleText(Arrays.asList(new String[]{strings.pbGetPhoto()}));
            }

            public void onSuccess(List<String> result) {
                int select = 0;
                for(int i=0;i<chooseAlbumView.getItemCount();i++){
                    System.out.println(" getPhotosSelect choose  : "+chooseAlbumView.getItemText(i)+" / "+album);
                    if(chooseAlbumView.getItemText(i).equals(album)){
                        select = i;
                    }
                }
                System.out.println("getPhotos select : "+select);
                chooseAlbumView.setSelectedIndex(select);
                for(int rm = thumbs.getWidgetCount()-1;rm>=0;rm--){
                    thumbs.remove(rm);
                }
                for(String item :result){
                    if(AlbumPanel.isImg(item)){
                        thumbs.add(AlbumPanel.createThumbPhoto(item));
                    }else{
                        thumbs.add(AlbumPanel.createPlayer(item));
                    }
                }
                if(result.size() > 0){
                    if(photoContainer.getWidget() != null){
                        photoContainer.remove(photoContainer.getWidget());
                    }
                    photoContainer.add(new HTML(createPhotoCenter(result.get(0))));
                }
            }
        };
        service.getPhotos(album, callback);
    }
    public void deletePhotoAlbum(){
        AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
            public void onFailure(Throwable caught) {
                viewUser.setConsoleText(Arrays.asList(new String[]{strings.pbDeletePhoto()}));
            }

            public void onSuccess(Boolean result) {
                //                getPhotos("", photoContainer)
                viewUser.setConsoleText(Arrays.asList(new String[]{strings.deletePhoto()}));
            }
        };
        if(thumbsSelected == null || thumbsSelected.equals("")){
            if(viewUser != null){
                viewUser.setConsoleText(Arrays.asList(new String[]{"Veuillez selectionner un média"}));
            }
        }
        else{
            service.deleteMediaAlbum(thumbsSelected, callback);
        }
    }

    //    public ChangeHandler getPhotoDeleteChangeListener() {
    //        return new ChangeHandler() {
    //            public void onChange(ChangeEvent event) {
    //                int select = ((ListBox)event.getSource()).getSelectedIndex();
    //                String item = ((ListBox)event.getSource()).getItemText(select);
    //                
    //                for(int i=listPhoto.getObjectCount()-1;i>=0;i--){
    //                    listPhoto.removeObject(i);
    //                }
    //                getPhotosDelete(item);
    //            }
    //        };
    //    }
    //    public ChangeHandler getMediaGestionChangeListener() {
    //        return new ChangeHandler() {
    //            public void onChange(ChangeEvent event) {
    //                int select = ((ListBox)event.getSource()).getSelectedIndex();
    //                String item = ((ListBox)event.getSource()).getItemText(select);
    //                
    //                for(int i=listMediaGestion.getObjectCount()-1;i>=0;i--){
    //                    listMediaGestion.removeObject(i);
    //                }
    //                getMediaGestion(item);
    //            }
    //        };
    //    }
    //    public void getPhotosDelete(String album){
    //        AsyncCallback<List<String>> callback = new AsyncCallback<List<String>>() {
    //            public void onFailure(Throwable caught) {
    //                viewUser.setConsoleText(Arrays.asList(new String[]{strings.pbGetPhoto()}));
    //            }
    //
    //            public void onSuccess(List<String> result) {
    ////                for(int i=listPhoto.getObjectCount()-1;i>=0;i--){
    ////                    listPhoto.removeObject(i);
    ////                }
    ////                for(String item :result){
    ////                    listPhoto.addElement(item);
    ////                }
    //            }
    //        };
    //        service.getPhotos(album, callback);
    //    }
    public void setChooseAlbumView(ListBox choose){
        this.chooseAlbumView = choose;
    }
    public void setThumbsPanel(HorizontalPanel thumbs){
        this.thumbs = thumbs;
    }
}
