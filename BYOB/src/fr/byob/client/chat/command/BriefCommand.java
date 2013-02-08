package fr.byob.client.chat.command;

import fr.byob.client.chat.view.ICommandedConversationPanel;

public class BriefCommand implements IConversationCommand {

	public void execute(ICommandedConversationPanel panel, String[] params) {
	    String msg = "";
        for (int i = 1; i <params.length ; i++){
            msg += params[i]+" ";
        }
		panel.addBrief(msg);
	}

	public String getStringCommand() {
		return CommandStrings.INSTANCE.brief();
	}

    public String getStringHelp() {
        return HelpCommandStrings.INSTANCE.brief();
    }

}
