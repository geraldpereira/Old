package fr.byob.validator.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

import org.apache.commons.validator.UrlValidator;

import fr.byob.validator.IValidator;
import fr.byob.validator.exception.InternalValidationException;

public class UrlFormattedValidator implements IValidator {

	public void isValid(Annotation annotation, Object object, AccessibleObject accessibleObject, InternalValidationException internal) {

		if (object != null) {
			String url = (String) object;
			try { 
			    String[] schemes = {"http","https"};
			    UrlValidator urlValidator = new UrlValidator(schemes);
			    if (!urlValidator.isValid(url)) {
			        internal.new ValidationError(accessibleObject,annotation);
			    } 
			    
			    
//				String expression =//http://geekswithblogs.net/casualjim/archive/2005/12/01/61722.aspx Mal"^(?#Protocol)(?:(?:ht|f)tp(?:s?)\\:\\/\\/|~/|/)?(?#Username:Password)(?:\\w+:\\w+@)?(?#Subdomains)(?:(?:[-\\w]+\\.)+(?#TopLevel Domains)(?:com|org|net|gov|mil|biz|info|mobi|name|aero|jobs|museum|travel|[a-z]{2}))(?#Port)(?::[\\d]{1,5})?(?#Directories)(?:(?:(?:/(?:[-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|/)+|\\?|#)?(?#Query)(?:(?:\\?(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=(?:[-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)(?:&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=(?:[-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*(?#Anchor)(?:#(?:[-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?$";
//				    "^((ht|f)tp(s?)\\:\\/\\/|~/|/)?([\\w]+:\\w+@)?([a-zA-Z]{1}([\\w\\-]+\\.)+([\\w]{2,5}))(:[\\d]{1,5})?((/?\\w+/)+|/?)(\\w+\\.[\\w]{3,4})?((\\?\\w+(=\\w+)?)(&\\w+(=\\w+)?)*)?";
//				    //"^((ht|f)tp(s?)\\:\\/\\/|~/|/)?([\\w]+:\\w+@)?([a-zA-Z]{1}([\\w\\-]+\\.)+([\\w]{2,5}))(:[\\d]{1,5})?((/?\\w+/)+|/?)(\\w+\\.[\\w]{3,4})?((\\?\\w+=\\w+)?(&\\w+=\\w+)*)?";
//				Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
//				Matcher matcher = pattern.matcher(url);
//				if(!matcher.matches()){
//					internal.new ValidationError(accessibleObject,annotation);
//				}
			}catch (ClassCastException e) {
				internal.new ValidationError(accessibleObject,annotation);
			}

		}

	}

}
