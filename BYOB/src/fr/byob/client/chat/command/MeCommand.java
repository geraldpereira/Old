package fr.byob.client.chat.command;

import fr.byob.client.chat.view.ICommandedConversationPanel;

public class MeCommand implements IConversationCommand {

	public void execute(ICommandedConversationPanel panel, String[] params) {
	    String msg = "";
        for (int i = 1; i <params.length ; i++){
            msg += params[i]+" ";
        }
		panel.setMeMessage(msg);
	}

	public String getStringCommand() {
		return CommandStrings.INSTANCE.me();
	}

    public String getStringHelp() {
        return HelpCommandStrings.INSTANCE.me();
    }
}
