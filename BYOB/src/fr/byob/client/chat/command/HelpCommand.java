package fr.byob.client.chat.command;

import java.util.HashMap;

import fr.byob.client.chat.view.ICommandedConversationPanel;

public class HelpCommand implements IConversationCommand {

	public void execute(ICommandedConversationPanel panel, String[] params) {
	    HashMap<String, IConversationCommand> commands = CommandManager.getInstance().getCommands();
	    for(IConversationCommand command : commands.values()){
	        panel.setHelpMessage(command.getStringHelp());
	    }
	}

	public String getStringCommand() {
		return CommandStrings.INSTANCE.help();
	}

    public String getStringHelp() {
        return HelpCommandStrings.INSTANCE.help();
    }
}
