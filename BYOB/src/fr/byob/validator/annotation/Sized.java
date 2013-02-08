package fr.byob.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Only for string. Allows to specify min and max size values.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.METHOD, ElementType.FIELD} )
public @interface Sized{

	int min() default 0;
	int max() default 255;

}
