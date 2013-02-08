package fr.byob.validator.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

import fr.byob.validator.IValidator;
import fr.byob.validator.exception.InternalValidationException;

public class RequiredValidator implements IValidator {

	public void isValid(Annotation annotation, Object object, AccessibleObject accessibleObject,InternalValidationException internal) {

		if (object == null) {
			internal.new ValidationError(accessibleObject,annotation);

		}

	}

}
