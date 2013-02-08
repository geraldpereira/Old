package fr.byob.server.util;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class InternationalizationUtils {

	private static Locale currentLocale =  new Locale("fr", "FR");
	
	private static ResourceBundle messages = ResourceBundle.getBundle("fr/byob/server/util/MessagesBundle",
                                       currentLocale);
	
	public static Locale getLocale (){
		return currentLocale;
	}
	
	public static ResourceBundle getMessages (){
		return messages;
	}
        
}
