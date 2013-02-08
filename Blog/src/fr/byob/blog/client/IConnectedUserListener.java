package fr.byob.blog.client;
/**
 * Interface permettant d'�couter le Model
 * @author Akemi
 *
 */
public interface IConnectedUserListener {
	
	/**
	 * Indique si un utilisateur s'est connect�
	 * @param isConnected est connect� ou pas
	 */
	public void connectedUserUpdate (boolean isConnected);
}
