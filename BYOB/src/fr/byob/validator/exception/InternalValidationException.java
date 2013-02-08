package fr.byob.validator.exception;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fr.byob.client.exception.ValidationException;
import fr.byob.server.util.InternationalizationUtils;
import fr.byob.validator.annotation.Sized;

public class InternalValidationException extends Exception{

	private static final long serialVersionUID = 1L;

	private List<ValidationError> errors = new ArrayList<ValidationError>();
	
	private void addError (final ValidationError error){
		errors.add(error);
	}
	
	public List<ValidationError> getErrors(){
		return errors;
	}
	
	public boolean hasErrors (){
		return !errors.isEmpty();
	}
	
	public ValidationException validationExceptionFactory(final ResourceBundle bundle){
		ArrayList<String> trace = new ArrayList<String>();
		for (int i = 0 ; i < errors.size() ; i++){
			ValidationError curError = errors.get(i);
			trace.add(curError.toString(bundle));
		}
		String message = InternationalizationUtils.getMessages().getString("validation.message");
		ValidationException exception = new ValidationException(message,trace.toArray(new String[0]));
		return exception;
	}
	
	private String getAccessibleObjectAsString (final AccessibleObject object){
		if (object instanceof Method){
			return ((Method)object).getName();
		}else if (object instanceof Field){
			return ((Field)object).getName();
		}else{
			return object.toString();
		}
	}
	
	private String getAnnotationAsString (final Annotation anno){
		return anno.annotationType().getCanonicalName();
		
	}
	
	public class ValidationError {
		
		protected final String fieldStr;
		
		protected final String annotationStr;
		
		public ValidationError(final AccessibleObject field,final  Annotation annotation){
			this.fieldStr = getAccessibleObjectAsString(field);
			this.annotationStr = getAnnotationAsString(annotation);
			addError(this);
		}
		
		public String toString(ResourceBundle bundle) {
			return bundle.getString(fieldStr)+" "+InternationalizationUtils.getMessages().getString(annotationStr);
		}
	}
	
	public class SizedValidationError extends ValidationError{
		
		private final Annotation annotation;
		
		public SizedValidationError(AccessibleObject field, Sized annotation){
			super(field, annotation);
			this.annotation = annotation;
		}
		
		public String toString(ResourceBundle bundle) {
			StringBuilder sb = new StringBuilder(super.toString(bundle));
			Sized sized = (Sized) annotation;
			sb.append(InternationalizationUtils.getMessages().getString("validation.sized.un")).append(" ");
			sb.append(sized.min()).append(" ");
			sb.append(InternationalizationUtils.getMessages().getString("validation.sized.deux")).append(" ");
			sb.append(sized.max()).append(" ");
			sb.append(InternationalizationUtils.getMessages().getString("validation.sized.trois"));
			return sb.toString();
		}
		
	}
	
}
