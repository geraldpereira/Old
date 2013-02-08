package fr.byob.client.chat.command;

import java.util.HashMap;

import com.google.gwt.user.client.Command;

import fr.byob.client.chat.view.ICommandedConversationPanel;

public class CommandManager {

	private static CommandManager instance = new CommandManager();

	private final HashMap<String, IConversationCommand> commands;

	private CommandManager() {
		ClearCommand clear = new ClearCommand();
		TimeCommand time = new TimeCommand();
		ScrollCommand scroll = new ScrollCommand();
		StatusCommand status = new StatusCommand();
		MeCommand me = new MeCommand();
		BriefCommand brief = new BriefCommand();
		HelpCommand help = new HelpCommand();
		
		commands = new HashMap<String, IConversationCommand>();
		commands.put(clear.getStringCommand(), clear);
		commands.put(time.getStringCommand(), time);
		commands.put(scroll.getStringCommand(), scroll);
		commands.put(status.getStringCommand(), status);
		commands.put(me.getStringCommand(), me);
		commands.put(brief.getStringCommand(), brief);
		commands.put(help.getStringCommand(), help);
	}

	public static CommandManager getInstance() {
		return instance;
	}

	public IConversationCommand getConversationCommand(String stringCommand) {
		return commands.get(stringCommand);
	}

	/**
	 * Execute l'eventuelle commande contenue dans le message
	 * 
	 * @param panel
	 *            la conversation sur laquelle executer la commande
	 * @param message
	 *            le message contenant peut être une commande
	 * @return true si une commande est dans le message
	 */
	public boolean processCommand(ICommandedConversationPanel panel,
			String message) {
		String trimed = message.trim();
		System.out.println("processCommand commandStr : "+trimed);
		if (!trimed.startsWith("/")) {
			return false;
		}

		String[] commandTab = trimed.split(" ");
		if (commandTab.length < 1) {
			return true;
		}

		String commandStr = commandTab[0];
		commandStr = commandStr.substring(1);
		System.out.println("commandStr : "+commandStr);
		IConversationCommand command = commands.get(commandStr);
		if (command != null){
			command.execute(panel, commandTab);
		}
		return true;
	}

	public Command getCommand(final ICommandedConversationPanel panel,
			final String[] commandStr) {
		if (commandStr.length == 0) {
			return null;
		}
		final IConversationCommand command = commands.get(commandStr[0]);
		if (command != null) {
			return new Command() {
				public void execute() {
					command.execute(panel, commandStr);
				}
			};
		}
		return null;
	}
	public HashMap<String, IConversationCommand> getCommands(){
	    return commands;
	}
}
