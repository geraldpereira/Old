package fr.byob.client.chat.command;

import fr.byob.client.chat.view.ICommandedConversationPanel;

public class StatusCommand implements IConversationCommand {

	public void execute(ICommandedConversationPanel panel, String[] params) {
		String statut = "";
		for (int i = 1; i <params.length ; i++){
			statut += params[i]+" ";
		}
//		if (statut.length() > 15){
//			statut = statut.substring(0, 15)+" ...";
//		}
		panel.setUserStatus(statut);
	}

	public String getStringCommand() {
		return CommandStrings.INSTANCE.status();
	}

    public String getStringHelp() {
        return HelpCommandStrings.INSTANCE.status();
    }
}
