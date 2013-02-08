package fr.byob.client.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Exception de validation pour affichage coté client. Le type
 * InternalValidationException est lui utilisé en interne par le validateur pour
 * composer cet objet.
 * 
 * La stackTrace est utilisée pour affichée la liste des erreurs de validation
 * (pas d'affichage de code / ligne d'erreur ici)
 * 
 * @author pereirag
 * 
 */
public class ValidationException extends BYOBException {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur
	 */
	public ValidationException() {
		super();
	}

	/**
	 * Constructeur
	 * 
	 * @param message
	 *            message
	 */
	public ValidationException(String message) {
		super(message);
	}

	/**
	 * Constructeur
	 * 
	 * @param message
	 *            message
	 * @param stackTrace
	 *            stackTrace
	 */
	public ValidationException(String message, String[] stackTrace) {
		super(message, stackTrace);
	}

	public List<String> toStringList() {
		ArrayList<String> retour = new ArrayList<String>();
		retour.add(super.getMessage());
		String[] trace = super.getStackTraceStr();
		if (trace != null) {
			retour.addAll(Arrays.asList(trace));
		}
		return retour;
	}

}
