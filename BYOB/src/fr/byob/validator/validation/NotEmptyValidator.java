package fr.byob.validator.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.util.Collection;

import fr.byob.validator.IValidator;
import fr.byob.validator.exception.InternalValidationException;

public class NotEmptyValidator implements IValidator {

	public void isValid(Annotation annotation, Object object, AccessibleObject accessibleObject, InternalValidationException internal){

		if (object == null || ((Collection) object).isEmpty()) {
			internal.new ValidationError(accessibleObject,annotation);
		}
	}

}
