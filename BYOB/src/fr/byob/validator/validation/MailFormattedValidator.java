package fr.byob.validator.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.byob.validator.IValidator;
import fr.byob.validator.exception.InternalValidationException;

public class MailFormattedValidator implements IValidator {

	public void isValid(Annotation annotation, Object object, AccessibleObject accessibleObject, InternalValidationException internal) {

		if (object != null) {
			String mail = (String) object;
			try {
				String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
				Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(mail);
				if(!matcher.matches()){
					internal.new ValidationError(accessibleObject,annotation);
				}
			}catch (ClassCastException e) {
				internal.new ValidationError(accessibleObject,annotation);
			}

		}

	}

}
