package fr.byob.client.chat.command;

import fr.byob.client.chat.view.ICommandedConversationPanel;

public class ClearCommand implements IConversationCommand {

	public void execute(ICommandedConversationPanel panel, String[] params) {
		panel.clearMessages();
		panel.onConversationSelection();
	}

	public String getStringCommand() {
		return CommandStrings.INSTANCE.clear();
	}

    public String getStringHelp() {
        return HelpCommandStrings.INSTANCE.clear();
    }
}
