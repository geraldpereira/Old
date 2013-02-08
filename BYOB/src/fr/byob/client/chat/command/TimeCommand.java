package fr.byob.client.chat.command;

import fr.byob.client.chat.view.ICommandedConversationPanel;

public class TimeCommand implements IConversationCommand {
	
	public void execute(ICommandedConversationPanel panel, String[] params) {
		if (params.length > 1){
			if (params[1].trim().equals(CommandStrings.INSTANCE.on())){
				panel.setTimedDisplay(true);
			}else if (params[1].trim().equals(CommandStrings.INSTANCE.off())){
				panel.setTimedDisplay(false);
			}
		}else{
			panel.setTimedDisplay(!panel.isTimedDisplay());
		}
		panel.onConversationSelection();
	}

	public String getStringCommand() {
		return CommandStrings.INSTANCE.time();
	}

    public String getStringHelp() {
        return HelpCommandStrings.INSTANCE.time();
    }
}
