package fr.byob.blog.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import fr.byob.blog.client.view.panel.ApplicationPanel;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BYOBBlog implements EntryPoint {

    private static RootPanel root;
    public static String BLANC_THEME_STYLE = Css.INSTANCE.blanc();
    public static String DEFAULT_THEME_STYLE = Css.INSTANCE.bleuet();
    public static String GRIS_THEME_STYLE = Css.INSTANCE.gris();
    public static String FONCE_THEME_STYLE = Css.INSTANCE.fonce();
    
    public static String POST = "Post=";
    public static String USER = "User=";
    public static String PHOTO = "Album=";
    public static String NOUVEAUTE = BlogStrings.INSTANCE.applicationLastNews();
    public static String ARTICLES = BlogStrings.INSTANCE.applicationArchives();
    public static String PUBLICATION = BlogStrings.INSTANCE.applicationAddPost();
    public static String MEMBRES = BlogStrings.INSTANCE.applicationUsers();
    public static String CATEGORIES = BlogStrings.INSTANCE.applicationCategories();
    public static String LIENS = BlogStrings.INSTANCE.applicationLinks();
    public static String CHAT = BlogStrings.INSTANCE.applicationChat();
    public static String ALBUM = BlogStrings.INSTANCE.applicationAlbum();

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() { 
        root = RootPanel.get();
        root.setStyleName(DEFAULT_THEME_STYLE); 
        root.add(new ApplicationPanel());
        Window.setMargin("1px");
    }
    public static RootPanel getRootPanel(){
        return root;
    }
    
    public static List<Object> getIdObject(){
        
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("Nouveaut%C3%A9s", NOUVEAUTE);
        params.put("Articles", ARTICLES);
        params.put("Publication", PUBLICATION);
        params.put("Liens", LIENS);
        params.put("Membres", MEMBRES);
        params.put("Cat%C3%A9gories",CATEGORIES);
        params.put("Chat", CHAT);
        params.put("Album%20Photos", ALBUM);
        
        String param = Utils.getParamHref();
        
        String menu = Utils.getIdUrl(params, param);
        List<Object> paramsList = new ArrayList<Object>();
        if(menu == null){
            if(param.contains(POST)){
                String [] paramsplit = param.split(POST);
                if(paramsplit.length == 2){
                    paramsList.add(POST);
                    paramsList.add(Integer.parseInt(paramsplit[1]));
                }
            }else if(param.contains(USER)){
                String [] paramsplit = param.split(USER);
                if(paramsplit.length == 2){
                    paramsList.add(USER);
                    paramsList.add(paramsplit[1]);
                }
            }else if(param.contains(PHOTO)){
                String [] paramsplit = param.split(PHOTO);
                if(paramsplit.length == 2){
                    paramsList.add(PHOTO);
                    paramsList.add(paramsplit[1]);
                }
            }
        }else{
            paramsList.add(menu);
        }
        return paramsList;
    }
}
