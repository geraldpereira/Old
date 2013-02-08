package fr.byob.client.chat.command;

import fr.byob.client.chat.view.ICommandedConversationPanel;

/**
 * Definit une command sur panel de conversation
 * 
 * @author pereirag
 *
 */
public interface IConversationCommand {
    public String getStringHelp();
	/**
	 * Retourne la chaine de caractères que doit taper l'utilisateur pour exécuter cette commande (sans le /)
	 * @return
	 */
	public String getStringCommand();
	
	/**
	 * Execute la commande sur le panel
	 * @param panel la conversation sur laquelle executer la commande
	 * @param params les paramètres de la commande. Attention le premier paramètre est la commande en elle-meme !
	 */
	public void execute(ICommandedConversationPanel panel, String[] params);
	
}
