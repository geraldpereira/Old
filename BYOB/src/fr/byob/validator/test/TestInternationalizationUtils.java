package fr.byob.validator.test;

import java.util.Locale;
import java.util.ResourceBundle;

public class TestInternationalizationUtils {

	private static Locale currentLocale =  new Locale("fr", "FR");
	
	private static ResourceBundle messages = ResourceBundle.getBundle("fr/byob/validator/test/MessagesBundle",
                                       currentLocale);
	
	public static Locale getLocale (){
		return currentLocale;
	}
	
	public static ResourceBundle getMessages (){
		return messages;
	}
        
}
