package fr.byob.client.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.byob.client.util.Utils;

/**
 * Exception de base BYOB. Attention : La sérialisation des Exception n'est pas
 * fonctionnelle avec la version actuelle de GWT (1.5) Ainsi il faut tricher et
 * mémoriser ici le message et la stackTrace (Pas sous forme StackTraceElement
 * qui ne sont pas non plus seiralisables) De même on mémorise l'exception cause
 * (si il y en a une)
 * 
 * Enfin le champ redirectRequested permet de savoir si la levée de cette
 * exception necessite une redirection vers la page d'accueil (cas typique d'un
 * accès non autorisé) ou si un simple affichage en console d'erreur suffit.
 * 
 * @author pereirag
 * 
 */
public abstract class BYOBException extends Exception implements Serializable {

	private static final long serialVersionUID = -5213098703118265852L;

	// Le message
	private String message = null;

	// La stackTrace
	private String[] stackTrace = null;

	// L'exception cause
	private Throwable cause = null;

	// Definit s'il faut rediriger l'utilisateur vers une autre page lors du catch
	// Si oui contient l'url vers laquelle rediriger l'utilisateur
	private String redirectRequested = null;

	/**
	 * Constructeur
	 */
	protected BYOBException() {
		super();
		this.message = super.getMessage();
		this.cause = super.getCause();
	}

	/**
	 * Constructeur
	 * @param cause exception cause
	 * @param message message d'erreur
	 */
	public BYOBException(Throwable cause, String message) {
		super(message, cause);
		this.message = message;
		this.cause = cause;
		if (cause != null && cause.getStackTrace() != null) {
			StackTraceElement[] trace = cause.getStackTrace();
			this.stackTrace = new String[trace.length];
			for (int i = 0; i < trace.length; i++) {
				this.stackTrace[i] = trace[i].toString();
			}
		}
	}

	/**
	 * Constructeur
	 * @param message message
	 * @param stackTrace stacktrace
	 */
	public BYOBException(String message, String[] stackTrace) {
		super(message);
		this.message = message;
		this.stackTrace = stackTrace;
	}

	/**
	 * Message
	 * @param message
	 */
	public BYOBException(String message) {
		super(message);
		this.message = message;
	}

	/**
	 * Constructeur
	 * @param cause
	 * @param message
	 * @param redirectRequested
	 */
	public BYOBException(Throwable cause, String message,
			String redirectRequested) {
		this(cause, message);
		this.redirectRequested = redirectRequested;
	}

	public Throwable getCause() {
		return cause;
	}

	public String getMessage() {
		return message;
	}

	protected String[] getStackTraceStr() {
		return stackTrace;
	}

	public boolean isRedirectRequested() {
		return redirectRequested != null;
	}

	/**
	 * A redéfinir par toutes les classes filles pour l'affichage en console
	 * @return une liste de string pour affichage en console.
	 */
	public abstract List<String> toStringList();

	/**
	 * Redirige l'utilisateur vers la page spécifiée par redirectRequested si necessaire 
	 * @param cause l'exception catchée
	 */
	public static void redirectIfRequested(Throwable cause) {
		if (cause instanceof BYOBException
				&& ((BYOBException) cause).isRedirectRequested()) {
			Utils.redirect(((BYOBException) cause).redirectRequested);
		}
	}

	/**
	 * Convertit une exception en liste de string pour affichage en console
	 * @param cause l'exception à afficher
	 * @return la lsite de String
	 */
	public static List<String> getStringList(Throwable cause) {
		if (cause instanceof BYOBException) {
			return ((BYOBException) cause).toStringList();
		}

		ArrayList<String> retour = new ArrayList<String>();
		retour.add(cause.getClass().toString() + " : " + cause.getMessage());
		StackTraceElement[] trace = cause.getStackTrace();
		if (trace != null) {
			for (int i = 0; i < trace.length; i++) {
				retour.add("	" + trace[i].toString());
			}
		}
		return retour;
	}
}
