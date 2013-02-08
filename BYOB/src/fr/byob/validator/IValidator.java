package fr.byob.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;

import fr.byob.validator.exception.InternalValidationException;

public interface IValidator {
	public void isValid (Annotation annotation,Object object,AccessibleObject accessibleObject, InternalValidationException internal);
}
