package fr.byob.client.chat.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.byob.client.chat.event.ChatEvent;
import fr.byob.client.chat.event.ChatEventVisitor;
import fr.byob.client.chat.event.NewGeneralMessageEvent;
import fr.byob.client.chat.event.NewMessageEvent;
import fr.byob.client.chat.event.UserAddEvent;
import fr.byob.client.chat.event.UserRemoveEvent;
import fr.byob.client.chat.event.UserUpdateEvent;
import fr.byob.client.chat.service.IBriefControler;
import fr.byob.client.chat.service.IChatServiceAsync;
import fr.byob.client.chat.util.HandlerCollection;

final public class ChatModel {

	public interface EventHandler {

	}

	public interface MessageEventHandler extends EventHandler {
		public void onNewMessage(IChatUser sender, Set<IChatUser> recipients,
				String message, int type);

		public void onNewGeneralMessage(IChatUser sender,
				Set<IChatUser> recipients, String message, int type);
	}

	public interface GeneralMessageEventHandler extends EventHandler {
		public void onNewGeneralMessage(IChatUser sender,
				Set<IChatUser> recipients, String message);
	}

	public interface UserEventHandler extends EventHandler {
		public void onUserAdded(IChatUser user);

		public void onUserRemoved(IChatUser user);

		public void onUserUpdated(IChatUser user);
	}

	/**
	 * Visitor for processing {@link ChatEvent}s.
	 */
	private final class ModelUpdateVisitor extends ChatEventVisitor {

		public void visit(NewGeneralMessageEvent event) {
			fireNewGeneralMessageEvent(event.getSender(),
					event.getRecipients(), event.getMessage(),event.getType());
		}

		public void visit(NewMessageEvent event) {
			fireNewMessageEvent(event.getSender(), event.getRecipients(), event
					.getMessage(),event.getType());
		}

		public void visit(UserAddEvent event) {
			fireUserAddEvent(event.getUser());
		}

		public void visit(UserRemoveEvent event) {
			fireUserRemoveEvent(event.getUser());
		}

		public void visit(UserUpdateEvent event) {
			fireUserUpdateEvent(event.getUser());
		}
	}

	private final class PollTimer extends Timer {
		public void run() {
			service.exchangeEvents(user, outgoingEvents,
					new AsyncCallback<List<ChatEvent>>() {
						public void onFailure(Throwable caught) {
							pollTimer.schedule(DEFAULT_POLLING_MILLIS);
						}

						public void onSuccess(List<ChatEvent> result) {
							update(result);
							pollTimer.schedule(DEFAULT_POLLING_MILLIS);
						}
					});
			outgoingEvents.clear();
		}
	}

	private static final int DEFAULT_POLLING_MILLIS = 1000;

	private final Timer pollTimer = new PollTimer();

	private final IChatServiceAsync service;
	private final IBriefControler briefService;

	private final IChatUser user;
	private final HandlerCollection<UserEventHandler> userEventHandlers = new HandlerCollection<UserEventHandler>();
	private final HandlerCollection<MessageEventHandler> messageEventHandlers = new HandlerCollection<MessageEventHandler>();
	private final ArrayList<ChatEvent> outgoingEvents = new ArrayList<ChatEvent>();

	public ChatModel(IChatUser user, IChatServiceAsync service,IBriefControler briefService) {
		this.user = user;
		this.service = service;
		this.briefService = briefService;

		service.getCurrentUsers(user, new AsyncCallback<List<IChatUser>>() {
			public void onFailure(Throwable caught) {
				pollTimer.schedule(DEFAULT_POLLING_MILLIS);
			}

			public void onSuccess(List<IChatUser> result) {
				Iterator<IChatUser> iter = result.iterator();
				// TODO clean this up after the event refactoring
				// HACK
				while (iter.hasNext()) {
					IChatUser user = (IChatUser) iter.next();
					fireUserAddEvent(user);
				}

				pollTimer.schedule(DEFAULT_POLLING_MILLIS);
			}
		});

		// pollTimer.schedule(DEFAULT_POLLING_MILLIS);
	}

	public void addBrief(String text,final Set<IChatUser> recipients){
	    briefService.addBriefFromChat(user.getName(), text);
	}
	
	public void addEventHandler(UserEventHandler handler) {
		userEventHandlers.add(handler);
	}

	public void addEventHandler(MessageEventHandler handler) {
		messageEventHandlers.add(handler);
	}

	public IChatUser getUser() {
		return user;
	}

	public void sendMessage(Set<IChatUser> recipients, String message,int type) {
		outgoingEvents.add(new NewMessageEvent(user, recipients, message,type));
	}

	public void sendGeneralMessage(Set<IChatUser> recipients, String message,int type) {
		outgoingEvents
				.add(new NewGeneralMessageEvent(user, recipients, message,type));
	}
	
	public void sendUserUpdate() {
		outgoingEvents
				.add(new UserUpdateEvent(user));
	}
//	Caused by: java.lang.NullPointerException: null
//	at fr.byob.server.chat.ChatServiceImpl$UserInfo.access$0(ChatServiceImpl.java:106)
//	at fr.byob.server.chat.ChatServiceImpl$ChatEventHandler.visit(ChatServiceImpl.java:67)
//	at fr.byob.client.chat.event.UserUpdateEvent.accept(UserUpdateEvent.java:18)
//	at fr.byob.server.chat.ChatServiceImpl.processClientEvents(ChatServiceImpl.java:280)
//	at fr.byob.server.chat.ChatServiceImpl.exchangeEvents(ChatServiceImpl.java:195)

	public void removeEventHandler(UserEventHandler handler) {
		userEventHandlers.remove(handler);
	}

	public void removeEventHandler(MessageEventHandler handler) {
		messageEventHandlers.remove(handler);
	}

	public void update(List<ChatEvent> serverEvents) {
		ChatEventVisitor visitor = new ModelUpdateVisitor();
		Iterator<ChatEvent> iter = serverEvents.iterator();
		while (iter.hasNext()) {
			ChatEvent chatEvent = (ChatEvent) iter.next();
			chatEvent.accept(visitor);
		}
	}

	private void fireNewGeneralMessageEvent(IChatUser sender,
			Set<IChatUser> recipients, String message,int type) {
		for (Iterator<MessageEventHandler> it = messageEventHandlers.iterator(); it
				.hasNext();) {
			it.next().onNewGeneralMessage(sender, recipients, message, type);
		}
	}

	private void fireNewMessageEvent(IChatUser sender,
			Set<IChatUser> recipients, String message, int type) {
		for (Iterator<MessageEventHandler> it = messageEventHandlers.iterator(); it
				.hasNext();) {
			it.next().onNewMessage(sender, recipients, message, type);
		}
	}

	private void fireUserAddEvent(IChatUser user) {
		for (Iterator<UserEventHandler> it = userEventHandlers.iterator(); it
				.hasNext();) {
			it.next().onUserAdded(user);
		}
	}

	private void fireUserRemoveEvent(IChatUser user) {
		for (Iterator<UserEventHandler> it = userEventHandlers.iterator(); it
				.hasNext();) {
			it.next().onUserRemoved(user);
		}
	}

	private void fireUserUpdateEvent(IChatUser user) {
		for (Iterator<UserEventHandler> it = userEventHandlers.iterator(); it
				.hasNext();) {
			it.next().onUserUpdated(user);
		}
	}
}
