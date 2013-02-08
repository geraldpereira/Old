package fr.byob.client.chat.view;

public interface ICommandedConversationPanel extends IConversationPanel {

	public void clearMessages();

	public void setAutoScrolling(boolean autoScrolling);

	public boolean isAutoScrolling();
	
	public void setTimedDisplay(boolean timedDisplay);

	public boolean isTimedDisplay();

	public void setUserStatus(String status);
	
	public void setMeMessage(String msg);
	
	public void addBrief(String brief);
	
	public void setHelpMessage(String msg);

}
