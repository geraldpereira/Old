package fr.byob.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.InternationalizationUtils;
import fr.byob.validator.annotation.MailFormatted;
import fr.byob.validator.annotation.NotEmpty;
import fr.byob.validator.annotation.Required;
import fr.byob.validator.annotation.Sized;
import fr.byob.validator.annotation.UrlFormatted;
import fr.byob.validator.annotation.Validable;
import fr.byob.validator.exception.InternalValidationException;
import fr.byob.validator.validation.MailFormattedValidator;
import fr.byob.validator.validation.NotEmptyValidator;
import fr.byob.validator.validation.RequiredValidator;
import fr.byob.validator.validation.SizedValidator;
import fr.byob.validator.validation.UrlFormattedValidator;

/**
 * 
 * @author gpe
 * 
 */
public class EntityValidator {

	private final static EntityValidator validator = new EntityValidator();

	private final static HashMap<String, IValidator> validators = new HashMap<String, IValidator>();

	static {
		validators.put(Required.class.getCanonicalName(), new RequiredValidator());
		validators.put(Sized.class.getCanonicalName(), new SizedValidator());
		validators.put(MailFormatted.class.getCanonicalName(), new MailFormattedValidator());
		validators.put(NotEmpty.class.getCanonicalName(), new NotEmptyValidator());
		validators.put(UrlFormatted.class.getCanonicalName(), new UrlFormattedValidator());
	}

	private EntityValidator() {
	}

	public final static EntityValidator getInstance() {
		return validator;
	}

	/**
	 * La classe pass�e en param�tre est � valider.
	 * 
	 * Une anotation @Validable doit �tre pr�sente sur la classe.
	 * Les autres annotations peuvent �tre pr�sentes sur les
	 * param�tres ou les getters (Les getters doivent �tre bien nomm�s !!!)
	 * 
	 * @param o
	 */
	public void validateEntity(Object object) throws ValidationException,InternalValidationException {
		// Vérification que la classe est à valider
		isValidable(object);
		InternalValidationException internal = new InternalValidationException();
		validateFields(object,internal);
		validateGetters(object,internal);
		if (internal.hasErrors()){
			throw internal;
		}
	}

	private void isValidable(final Object object) throws ValidationException {
		Class<?> clazz = object.getClass();

		// Is the Validable annotation present on the class?
		if (clazz.getAnnotation(Validable.class) != null) {
			return;
		}
		throw new ValidationException(InternationalizationUtils.getMessages().getString("validation.erreur.interne"));

	}

	private void validateFields(final Object object,final InternalValidationException internal) throws ValidationException,InternalValidationException {
		// Vérification sur les champs de classe (pas d'héritage bien entendu)
		Class<?> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Annotation[] annotations;
		boolean oldAccesibleValue;
		String key;

		for (Field curField : fields) {
			annotations = curField.getAnnotations();
			for (Annotation curAnnotation : annotations) {
				key = curAnnotation.annotationType().getCanonicalName();
				if (validators.containsKey(key)) {
					oldAccesibleValue = curField.isAccessible();
					if (!oldAccesibleValue) {
						curField.setAccessible(true);
					}
						try {
							validators.get(key).isValid(curAnnotation, curField.get(object), curField, internal);
						} catch (IllegalArgumentException e) {
							throw new ValidationException(InternationalizationUtils.getMessages().getString("validation.erreur.interne"));
						} catch (IllegalAccessException e) {
							throw new ValidationException(InternationalizationUtils.getMessages().getString("validation.erreur.interne"));
						}
					curField.setAccessible(oldAccesibleValue);
				}
			}
		}
	}

	private void validateGetters(final Object object,final InternalValidationException internal) throws ValidationException,InternalValidationException {
		Class<?> clazz = object.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		Annotation[] annotations;
		String key;

		for (Method curMethod : methods) {
			if ((curMethod.getName().startsWith("get") || curMethod.getName().startsWith("is"))) {
				annotations = curMethod.getAnnotations();
				for (Annotation curAnnotation : annotations) {
					key = curAnnotation.annotationType().getCanonicalName();
					if (validators.containsKey(key)) {
							try {
								validators.get(key).isValid(curAnnotation, curMethod.invoke(object), curMethod, internal);
							} catch (IllegalArgumentException e) {
								throw new ValidationException(InternationalizationUtils.getMessages().getString("validation.erreur.interne"));
							} catch (IllegalAccessException e) {
								throw new ValidationException(InternationalizationUtils.getMessages().getString("validation.erreur.interne"));
							} catch (InvocationTargetException e) {
								throw new ValidationException(InternationalizationUtils.getMessages().getString("validation.erreur.interne"));
							}
					}
				}
			}
		}
	}
}
