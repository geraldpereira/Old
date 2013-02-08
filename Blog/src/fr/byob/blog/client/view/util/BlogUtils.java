package fr.byob.blog.client.view.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.BYOBBlog;
import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.controler.AlbumControler;
import fr.byob.blog.client.controler.PostControler;
import fr.byob.blog.client.controler.ProfilControler;
import fr.byob.blog.client.view.panel.AlbumPanel;
import fr.byob.blog.client.view.panel.PostPanel;
import fr.byob.blog.client.view.panel.ProfilPanel;
import fr.byob.client.application.model.IApplicationModel;
import fr.byob.client.application.model.IIndexSelectionModel;
import fr.byob.client.application.page.view.DeckPage;
import fr.byob.client.application.page.view.IPage;
import fr.byob.client.application.page.view.SimplePage;
import fr.byob.client.lazy.LazyPanel;
import fr.byob.client.util.Css;

public abstract class BlogUtils {
    
    final private static String BREAK = ":break:";
    final private static String START_VIDEO = "<video>";
    final private static String END_VIDEO = "</video>";
    
    private final static int nbCharDisplay = 600;

    public static String getCategoriesString(Set<ICategory> categories) {
        String label = "";
        if (categories.size() > 1) {
            label = BlogStrings.INSTANCE.divCategoriesDot() + " " + fr.byob.client.util.Utils.getString(categories);
        } else {
            label = BlogStrings.INSTANCE.divCategoryDot() + " " + fr.byob.client.util.Utils.getString(categories);
        }
        return label;
    }

    public static Widget getCenteredTextWidget(Widget textWidget, int widthPercentage) {
        SimplePanel texteCenter = new SimplePanel();
        texteCenter.add(textWidget);
        textWidget.setWidth("100%");
        texteCenter.setWidth(widthPercentage + "%");
        texteCenter.setStyleName(Css.INSTANCE.centered());
        texteCenter.addStyleName(Css.INSTANCE.hideOverflow());
        return texteCenter;
    }

    public static void isNotBasicUrl(IApplicationModel applicationModel, DeckPanel deckPanel, List<Object> params, IIndexSelectionModel pageModel) {
        for (int i = 0; i < applicationModel.getPages().size(); i++) {
            if (BYOBBlog.POST.equals(params.get(0))) {
                if (BYOBBlog.NOUVEAUTE.equals(((IPage) applicationModel.getPages().get(i)).getPageTitle())) {
                    PostPanel p = ((PostPanel) ((LazyPanel) ((DeckPage) applicationModel.getPages().get(i)).getCenterWidget().getWidget(1)).ensureWidget());
                    PostControler.getInstance().findPost((Integer) params.get(1), p);
                    pageModel.selectIndex(i);
                    ((DeckPage) applicationModel.getPages().get(i)).indexSelected(0, 1);
                    return;
                }
            } else if (BYOBBlog.USER.equals(params.get(0))) {
                if (BYOBBlog.MEMBRES.equals(((IPage) applicationModel.getPages().get(i)).getPageTitle())) {
                    ProfilPanel p = ((ProfilPanel) ((LazyPanel) ((DeckPage) applicationModel.getPages().get(i)).getCenterWidget().getWidget(1)).ensureWidget());
                    ProfilControler.getInstance().setProfilPanel(p);
                    ProfilControler.getInstance().findProfil((String) params.get(1));
                    pageModel.selectIndex(i);
                    ((DeckPage) applicationModel.getPages().get(i)).indexSelected(0, 1);
                    return;
                }
            }else if (BYOBBlog.PHOTO.equals(params.get(0))) {
                if (BYOBBlog.ALBUM.equals(((IPage) applicationModel.getPages().get(i)).getPageTitle())) {
                    SimplePage simple = (SimplePage) applicationModel.getPages().get(i);
                    Widget w = simple.getCenterWidget();
                    AlbumPanel p = ((AlbumPanel) ( (LazyPanel)w).ensureWidget());
                    pageModel.selectIndex(i);
                    System.out.println((String) params.get(1));
                    AlbumControler.getInstance().setPhotoContainer(p.getPhotoContainer());
                    AlbumControler.getInstance().getPhotos((String) params.get(1));
                    p.selectChoose((String) params.get(1));
                    return;
                }
            }
        }
    }
    
    public static String getBreakenText(String texte){
        String text = null;
        if(texte.contains(BREAK)){
            int start = texte.indexOf(BREAK);
            text = texte.substring(0,start)+" ...";
        }else{
            if(texte.length()>nbCharDisplay){
                text = texte.substring(0,nbCharDisplay)+" ...";
            }else{
                text = texte;
            }
        }
        return text;
    }
    public static String getTextWithoutBreak(String texte){
        String text = "";
        if(texte.contains(BREAK)){
            text = texte.replaceAll(BREAK, "");
        }else{
            text = texte;
        }
        return text;
    }
    public static List<String> getVideoAddress(String texte,List<String> videos){
        List<String> texts = new ArrayList<String>();
        String end = null;
        while(texte.contains(START_VIDEO)){
            int startVideo = texte.indexOf(START_VIDEO);
            int endVideo = texte.indexOf(END_VIDEO);
            String address = texte.substring(startVideo+START_VIDEO.length(), endVideo);
            String start = texte.substring(0, startVideo);
            end = texte.substring(endVideo+END_VIDEO.length());
            texte = end;
            texts.add(start);
            videos.add(address);
        }
        texts.add(end);
        return texts;
    }
}
