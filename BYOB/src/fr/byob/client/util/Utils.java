package fr.byob.client.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DeckPanel;

import fr.byob.client.application.model.IApplicationModel;
import fr.byob.client.application.page.view.IPage;

/**
 * Classe d'utilité publique.
 * 
 * @author pereirag
 */
public abstract class Utils {
	/**
	 * Affiche un set d'objets sous forme de chaine de caractères (via la
	 * méthode toString())
	 * 
	 * @param objects
	 * @return
	 */
	public static String getString(Set<? extends Object> objects) {
		String retour = "";
		if (objects != null && objects.size() != 0) {
			for (Object cat : objects) {
				retour += cat.toString() + " ";
			}
		}
		return retour;
	}

	public static String getLinkHTMLNewPage(String url,String text){
	    return "<a href=\""+url+"\" target=\"_blank\" >"+text+"</a>";
	}
	public static String getLinkHTML(String url,String text){
        return "<a href=\""+url+"\" >"+text+"</a>";
    }
	/**
	 * Format une date en String
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		DateTimeFormat format = DateTimeFormat
				.getFormat("dd'/'MM'/'yyyy 'à' HH'h'mm");
		return format.format(date);
	}
	public static String formatDateDay(Date date) {
        DateTimeFormat format = DateTimeFormat
                .getFormat("dd'/'MM'/'yyyy");
        return format.format(date);
    }
	/**
	 * Format une date en String pour l'heure uniquement
	 * 
	 * @param date
	 * @return l'heure de la date
	 */
	public static String formatTime(Date date) {
		DateTimeFormat format = DateTimeFormat.getFormat("HH':'mm':'ss");
		return format.format(date);
	}

	public static String createImage(String url,final int maxHeight,final int maxWidth,final String alt){
	    String img = "";
	    if(maxHeight != 0 && maxWidth == 0){
	        img = "<img src=\""+url+"\" alt=\""+alt+"\" height="+maxHeight+">";
	    }else if(maxHeight == 0 && maxWidth != 0){
	        img = "<img src=\""+url+"\" alt=\""+alt+"\" width="+maxWidth+">";
	    }else if(maxHeight != 0 && maxWidth != 0){
	        img = "<img src=\""+url+"\" alt=\""+alt+"\" height="+maxHeight+" width="+maxWidth+">";
	    }else if(maxHeight == 0 && maxWidth == 0){
	        img = "<img src=\""+url+"\" alt=\""+alt+"\" >";
        }
	    return img;
	}
	
	public static String getUrlImage(String html){
	    int begin = html.indexOf("src=\"")+5;
	    int end = html.indexOf("\"", begin);
	    return html.substring(begin, end);
	}
	
//	public static void resizeImageOnLoad(final Image image, final int max) {
//		Utils.resizeImage(image, max);
//		// image.setUrlAndVisibleRect(image.getUrl(), 0, 0, max, max);
//		// Utils.resizeImage(image, max);
//		image.addLoadListener(new LoadListener() {
//			public void onError(Widget sender) {
//
//			}
//
//			public void onLoad(Widget sender) {
//				Utils.resizeImage(image, max);
//			}
//		});
//	}

//	public static void resizeImage(final Image image, final int max) {
//		int width = image.getWidth();
//		int height = image.getHeight();
//		if (width > max || height > max) {
//			int newWidth = width;
//			int newHeight = height;
//			if (width > max && height > max) {
//				if (width > height) {
//					newWidth = max;
//					double coef = (double) max / width;
//					newHeight = (int) (coef * (double) height);
//				} else {
//					newHeight = max;
//					double coef = (double) max / height;
//					newWidth = (int) (coef * (double) width);
//				}
//			} else {
//				if (height > max) {
//					newHeight = max;
//					double coef = (double) max / height;
//					newWidth = (int) (coef * (double) coef * width);
//				}
//				if (width > max) {
//					newWidth = max;
//					double coef = (double) max / width;
//					newHeight = (int) (coef * (double) coef * height);
//				}
//			}
//			image.setWidth(newWidth + "px");
//			image.setHeight(newHeight + "px");
//		}
//	}
	/**
	 * Permet de rediriger au chargement de l'application vers la page entrer en paramètre
	 * @param applicationModel
	 * @param deckPanel
	 * @param params
	 * @return
	 */
	public static int redirectUrl(IApplicationModel applicationModel,DeckPanel deckPanel,List<Object> params){
	    for (int i = 0; i < applicationModel.getPages().size(); i++) {
            if(params.get(0).equals(((IPage)applicationModel.getPages().get(i)).getPageTitle())){
                applicationModel.getPages().get(i).chargedPage();
                deckPanel.showWidget(i);
                return i;
            }
	    }
	    return 0;
	}

	public static String getIdUrl(HashMap<String, String> params,String param){
	    for(String para :params.keySet()){
	        if(para.equals(param)){
	            return params.get(para);
	        }
	    }
	    return null;
	}
	/**
	 * Retourne les paramètres d'adresse entrer sans le dièse
	 * @return
	 */
	public static String getParamHref() {
        String href = Window.Location.getHref();
        int nbDiese =href.indexOf('#');
        String param = href.substring(nbDiese+1, href.length());
        return param;
    }
	
	public static ClickHandler getLinkPage(final String title){
	    return new ClickHandler(){
            public void onClick(ClickEvent event) {
                String path = Window.Location.getPath();
                Window.Location.replace(path+"#"+title);
            }
        };
	}
	public static void changeUrl(final String title){
	    String path = Window.Location.getPath();
        Window.Location.replace(path+"#"+title);
	}
	/**
	 * Méthode utilitaire javascript
	 * 
	 * @param url
	 */
	public static native void redirect(String url)/*-{
					      $wnd.location = url;
					}-*/;

	/**
	 * Valide une url en JS
	 * v.compile("^[A-Za-z]+://[A-Za-z0-9-_]+\\.[A-Za-z0-9-_%\+&\?\/.=|#:]+$");
	 * v.compile("^[A-Za-z]+://[A-Za-z0-9-_]+\\.[A-Za-z0-9-_%\+&\?\[\/.=|#:!\"\'\$()\*,;\{\}\\\~<>@`\\]]+$");
	 * @param s
	 * @return
	 */
	public static native boolean isURL(String s) /*-{
	 	var v = new RegExp();
		v.compile("^[A-Za-z]+://[A-Za-z0-9-_]+\\.[A-Za-z0-9-_%\+&\?\[\/.=|#:!\"\'\$()\*,;\{\}\\\~<>@`\\]]+$");
		return v.test(s); 
	}-*/;

	public static native boolean isFFBrowser()/*-{
		 	var ua = navigator.userAgent.toLowerCase();
		 	var isSafari = (/webkit|khtml/).test(ua);
		 	var isGecko = !isSafari && ua.indexOf("gecko") > -1;
		 	return isGecko;
		}-*/;

	public static native boolean isIEBrowser()/*-{
	 	var ua = navigator.userAgent.toLowerCase();
	 	var isOpera = ua.indexOf("opera") > -1;
	 	var isIE = !isOpera && ua.indexOf("msie") > -1;
	 	return isIE;
	}-*/;

	public static native boolean isIE7Browser()/*-{
	 	var ua = navigator.userAgent.toLowerCase();
	 	var isOpera = ua.indexOf("opera") > -1;
	 	var isIE7 = !isOpera && ua.indexOf("msie 7") > -1;
	 	return isIE7;
	}-*/;

	// var idSeed = 0;
	//
	// var ua = navigator.userAgent.toLowerCase();
	//
	// var isSafari = (/webkit|khtml/).test(ua);
	//
	// var isSafari3 = isSafari && ua.indexOf('webkit/5') != -1;
	//
	//
	// var isWindows = (ua.indexOf("windows") != -1 || ua.indexOf("win32") !=
	// -1);
	//
	// var isMac = (ua.indexOf("macintosh") != -1 || ua.indexOf("mac os x") !=
	// -1);
	//
	// var isAir = (ua.indexOf("adobeair") != -1);
	//
	// var isLinux = (ua.indexOf("linux") != -1);
}
