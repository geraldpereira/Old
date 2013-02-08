package fr.byob.client.chat.view;

import java.util.Set;

import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.chat.model.IChatUser;
import fr.byob.client.chat.model.ChatModel.MessageEventHandler;

public interface IConversationPanel extends MessageEventHandler{

	public Widget getTitleWidget();

	public Widget getWidget();

	public void onConversationSelection();

	public void onConversationUnselection();

	public Set<IChatUser> getRecipients();

}
