package fr.byob.client.chat.command;

import fr.byob.client.chat.view.ICommandedConversationPanel;

public class ScrollCommand implements IConversationCommand {

	public void execute(ICommandedConversationPanel panel, String[] params) {
		if (params.length > 1){
			if (params[1].trim().equals(CommandStrings.INSTANCE.on())){
				panel.setAutoScrolling(true);
			}else if (params[1].trim().equals(CommandStrings.INSTANCE.off())){
				panel.setAutoScrolling(false);
			}
		}else{
			panel.setAutoScrolling(!panel.isAutoScrolling());
		}
		panel.onConversationSelection();
	}

	public String getStringCommand() {
		return CommandStrings.INSTANCE.scroll();
	}

    public String getStringHelp() {
        return HelpCommandStrings.INSTANCE.scroll();
    }
}
