package fr.byob.client.view.adapter;

/**
 * Adapte pour la console (juste besoin du nom de l'OM)
 * 
 * @author pereirag
 *
 */
public interface IConsoleAdapter {

	/**
	 * Retourne le nom de l'objet métier (pour affiche) Par exemple pour un User
	 * on retournera la chaine "Utilisateur"
	 * 
	 * @return le nom de l'objet métier
	 */
	public String getObjectName();

}
