package fr.byob.validator.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

import fr.byob.validator.IValidator;
import fr.byob.validator.annotation.Sized;
import fr.byob.validator.exception.InternalValidationException;

public class SizedValidator implements IValidator {

	public void isValid(Annotation annotation, Object object, AccessibleObject accessibleObject, InternalValidationException internal){

		Sized sized = (Sized) annotation;
		try {
			String stringValue = (String) object;
			if (stringValue != null && (stringValue.length() < sized.min() || stringValue.length() > sized.max())) {
				internal.new SizedValidationError(accessibleObject,sized);
			}
		} catch (ClassCastException e) {
			internal.new SizedValidationError(accessibleObject,sized);
		}

	}

}
