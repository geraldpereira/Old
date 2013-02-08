package fr.byob.blog.server.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.jboss.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.byob.blog.client.chat.service.ChatService;
import fr.byob.client.chat.event.ChatEvent;
import fr.byob.client.chat.event.ChatEventVisitor;
import fr.byob.client.chat.event.NewGeneralMessageEvent;
import fr.byob.client.chat.event.NewMessageEvent;
import fr.byob.client.chat.event.UserAddEvent;
import fr.byob.client.chat.event.UserRemoveEvent;
import fr.byob.client.chat.event.UserUpdateEvent;
import fr.byob.client.chat.model.ChatUser;
import fr.byob.client.chat.model.IChatUser;
import fr.byob.client.chat.util.ChatServiceException;

/**
 * Servlet which implements the {@link ChatService} service.
 */
public class ChatServiceImpl extends RemoteServiceServlet implements
		ChatService {

	private final Logger log = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = 1L;
	
	/**
   * 
   */
	private final class ChatEventHandler extends ChatEventVisitor {
		public void visit(NewMessageEvent event) {
			Set<IChatUser> recipients = event.getRecipients();

			if (recipients != null) {
				propagateEvent(recipients.iterator(), event);
			}
		}

		public void visit(UserAddEvent event) {
			// The server sends this message but it should never receive it
		}

		public void visit(UserRemoveEvent event) {
			// The server should send this message but it should never receive
			// it
		}

		public void visit(UserUpdateEvent event) {
			Set<IChatUser> keys = userToUserInfoMap.keySet();
			UserInfo userInfo = findUserInfoForUser(event.getUser());
			userInfo.user.setStatusMessage(event.getUser().getStatusMessage());

			propagateEvent(keys.iterator(), event);
		}

		public void visit(NewGeneralMessageEvent event) {
			Set<IChatUser> recipients = event.getRecipients();
			if (recipients != null) {
				propagateEvent(recipients.iterator(), event);
			}
			
		}
	}

	private final class SuperUserInfo extends UserInfo {
		public SuperUserInfo(IChatUser user) {
			super(user);
		}

		public void addEvent(ChatEvent event) {
			if (event instanceof NewMessageEvent) {
				NewMessageEvent newMsgEvt = (NewMessageEvent) event;

				UserInfo userInfo = findUserInfoForUser(newMsgEvt.getSender());
				if (userInfo != null) {
					userInfo.addEvent(new NewMessageEvent(getUser(),
							new HashSet<IChatUser>(Collections
									.singletonList(newMsgEvt.getSender())),
							getUser().getStatusMessage()));
				}
			}
		}
	}

	private class UserInfo {
		private static final long DEFAULT_INACTIVITY_TIMEOUT_MILLIS = 10000;
		private List<ChatEvent> eventQueue = new ArrayList<ChatEvent>();
		private TimerTask inactivityTimerTask;

		private final IChatUser user;

		public UserInfo(IChatUser user) {
			this.user = user;
		}

		public void addEvent(ChatEvent event) {
			eventQueue.add(event);
		}

		public void clearEventQueue() {
			eventQueue = new ArrayList<ChatEvent>();
		}

		public List<ChatEvent> getEventQueue() {
			return eventQueue;
		}

		public IChatUser getUser() {
			return user;
		}

		public void resetInactivityTimer() {
			if (inactivityTimerTask != null) {
				inactivityTimerTask.cancel();
			}

			inactivityTimerTask = new TimerTask() {
				public void run() {
					userToUserInfoMap.remove(user);
					fireUserRemovedEvent(user);
				}
			};

			try {

				inactivityTimer.schedule(inactivityTimerTask,
						DEFAULT_INACTIVITY_TIMEOUT_MILLIS);
			} catch (IllegalStateException ex) {
				ex.printStackTrace();
			}
		}
	}

	private ChatEventHandler eventHandler = new ChatEventHandler();

	private Timer inactivityTimer = new Timer();

	/**
	 * Map of user names to event queues
	 */
	private static final Map<IChatUser,UserInfo> userToUserInfoMap = new HashMap<IChatUser,UserInfo>();

	public ChatServiceImpl() {
		// Default to George P. Burdell
		/*IChatUser user = new ChatUser("George P. Burdell", "Go Jackets!");
		UserInfo userInfo = new SuperUserInfo(user);
		userToUserInfoMap.put(user, userInfo);*/
	}

	/**
	 * 
	 * <ul>
	 * <li>Get the user - if non-existant create the user and add an event to
	 * all of the other user's event queues</li>
	 * <li>Kick the inactivity timer</li>
	 * <li>Process all of the events sent by this user user</li>
	 * <li>Get all of the events queued for this user</li>
	 * <li>clear the user's event queue</li>
	 * </ul>
	 * 
	 * @throws ChatServiceException
	 */
	public List<ChatEvent> exchangeEvents(IChatUser user,
			List<ChatEvent> clientEvents) throws ChatServiceException {

		if (user == null) {
			// RuntimeExceptions are not serialized by default
			throw new ChatServiceException("User cannot be null");
		}

		UserInfo userInfo = findUserInfoForUser(user);
		if (userInfo == null) {
			userInfo = createUser(user);
		}

		userInfo.resetInactivityTimer();

		if (clientEvents != null) {
			processClientEvents(clientEvents);
		}

		List<ChatEvent> events = userInfo.getEventQueue();

		userInfo.clearEventQueue();

		return events;
	}

	public List<IChatUser> getCurrentUsers(IChatUser user) {
		UserInfo userInfo = findUserInfoForUser(user);
		if (userInfo == null) {
			userInfo = createUser(user);
		}

		List<IChatUser> currentUsers = new ArrayList<IChatUser>(userToUserInfoMap.keySet());
		currentUsers.remove(user);

		return currentUsers;
	}

	private UserInfo createUser(IChatUser user) {
		UserInfo userInfo = new UserInfo(user);

		fireNewUserEvent(user);

		notifyNewUserOfExistingUsers(userInfo);

		userToUserInfoMap.put(user, userInfo);
		return userInfo;
	}

	/**
	 * Returns the {@link UserInfo} for a given {@link ChatUser}.
	 * 
	 * @param user
	 *            the user whose UserInfo we want to lookup
	 * @return UserInfo for the given user or null, if no such user exists
	 */
	private UserInfo findUserInfoForUser(IChatUser user) {
		return (UserInfo) userToUserInfoMap.get(user);
	}

	/**
	 * 
	 * @param user
	 */
	private void fireNewUserEvent(IChatUser user) {
		UserAddEvent userAddEvent = new UserAddEvent(user);

		propagateEvent(userToUserInfoMap.keySet().iterator(), userAddEvent);
	}

	/**
	 * 
	 * @param user
	 */
	private void fireUserRemovedEvent(IChatUser user) {
		UserRemoveEvent userRemovedEvent = new UserRemoveEvent(user);

		propagateEvent(userToUserInfoMap.keySet().iterator(), userRemovedEvent);
	}

	private void notifyNewUserOfExistingUsers(UserInfo newUserInfo) {
		Iterator<IChatUser> iter = userToUserInfoMap.keySet().iterator();

		while (iter.hasNext()) {
			IChatUser user = iter.next();

			newUserInfo.addEvent(new UserAddEvent(user));
		}
	}

	/**
	 * Processes the list of events that a client sent.
	 * 
	 * @param clientEvents
	 *            list of events that a client sent
	 */
	private void processClientEvents(List<ChatEvent> clientEvents) {
		assert (clientEvents != null);
		Iterator<ChatEvent> iter = clientEvents.iterator();
		while (iter.hasNext()) {
			ChatEvent chatEvent = (ChatEvent) iter.next();
			chatEvent.accept(eventHandler);
		}
	}

	/**
	 * Propagates an event to a set of users.
	 * 
	 * @param iterRecipients
	 *            iterator over set of recipients
	 * @param event
	 *            the event that we want to propagate
	 */
	private void propagateEvent(Iterator<IChatUser> iterRecipients,
			ChatEvent event) {
		while (iterRecipients.hasNext()) {
			IChatUser recipient = iterRecipients.next();
			UserInfo userInfo = findUserInfoForUser(recipient);
			if (userInfo != null) {
				userInfo.addEvent(event);
			}
		}
	}

}
