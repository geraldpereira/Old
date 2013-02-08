package fr.byob.blog.ejb;

import java.util.Locale;
import java.util.ResourceBundle;

public abstract class BlogInternationalizationUtils {

	private static Locale currentLocale =  new Locale("fr", "FR");
	
	private static ResourceBundle messages = ResourceBundle.getBundle("fr/byob/blog/ejb/MessagesBundle",
                                       currentLocale);
	
	public static Locale getLocale (){
		return currentLocale;
	}
	
	public static ResourceBundle getMessages (){
		return messages;
	}
        
}
