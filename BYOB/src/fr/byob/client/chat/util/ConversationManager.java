package fr.byob.client.chat.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import fr.byob.client.chat.model.ChatModel;
import fr.byob.client.chat.model.IChatUser;
import fr.byob.client.chat.model.ChatModel.MessageEventHandler;
import fr.byob.client.chat.view.ChatPanel;
import fr.byob.client.chat.view.IConversationPanel;

public class ConversationManager implements MessageEventHandler {

	private final ChatModel model;
	private final HashMap<String, IConversationPanel> conversations = new HashMap<String, IConversationPanel>();

	private final ChatPanel chatPanel;

	public ConversationManager(final ChatModel model, final ChatPanel chatPanel) {
		this.model = model;
		this.chatPanel = chatPanel;
		model.addEventHandler(this);
	}

	public ChatModel getModel() {
		return model;
	}

	public IChatUser getUser() {
		return model.getUser();
	}

	public void setFocusedConversation(IConversationPanel conversation) {
		if (conversation != null) {
			chatPanel.selectConversation(conversation);
		}
	}

	public void setHilightedConversation(IConversationPanel conversation) {
		if (!chatPanel.isSelectedConversation(conversation)) {
			chatPanel.hilightConversation(conversation);
		}
	}

	public IConversationPanel maybeInitiateConversationPanel(IChatUser toUser) {
		if (!model.getUser().equals(toUser)) {
			final String key = getTitle(wrapInSet(toUser));
			IConversationPanel conversation = (IConversationPanel) conversations
					.get(key);
			if (conversation == null) {
				conversation = chatPanel.conversationPanelFactory(toUser);
				conversations.put(key, conversation);
				chatPanel.addConversation(conversation);
			}
			return conversation;
		}
		return null;
	}

	public void onNewMessage(IChatUser sender, Set<IChatUser> recipient,
			String message,int type) {
		IConversationPanel handler = maybeInitiateConversationPanel(sender);
		setHilightedConversation(handler);
		handler.onNewMessage(sender, recipient, message,type);
	}

	public void onNewGeneralMessage(IChatUser sender, Set<IChatUser> recipient,
			String message, int type) {
		IConversationPanel handler = chatPanel.getGeneralConversation();
		if (handler != null) {
			setHilightedConversation(handler);
			handler.onNewGeneralMessage(sender, recipient, message,type);
		}
	}

	public void sendMessage(Set<IChatUser> recipients, String message, int type) {
		model.sendMessage(recipients, message, type);
	}
	
	public void sendGeneralMessage(Set<IChatUser> recipients, String message,int type) {
		model.sendGeneralMessage(recipients, message, type);
	}

	public void removeConversation(IConversationPanel converstation) {
		conversations.remove(getTitle(converstation.getRecipients()));
		chatPanel.removeConversation(converstation);

	}

	public static Set<IChatUser> wrapInSet(IChatUser user) {
		final HashSet<IChatUser> set = new HashSet<IChatUser>();
		set.add(user);
		return set;
	}

	public static String getTitle(Set<IChatUser> recipients) {
		final StringBuffer buffer = new StringBuffer();
		final Iterator<IChatUser> it = recipients.iterator();
		if (it.hasNext()) {
			buffer.append(it.next().getName().split(" ")[0]);
		}

		while (it.hasNext()) {
			buffer.append(", ");
			buffer.append(it.next().getName());
		}

		return buffer.toString();
	}

	public void setUserStatus(String status) {
		model.getUser().setStatusMessage(status);
		model.sendUserUpdate();
	}
	public void addBrief(String text,Set<IChatUser> recipients) {
        model.addBrief(text,recipients);
    }
}
