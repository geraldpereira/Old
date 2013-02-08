package fr.byob.client.chat.model;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * A user of the Chattr application.
 */
public class ChatUser implements IsSerializable, IChatUser {

	private String name;

	private String statusMessage;

	/**
	 * Only used by serialization
	 */
	public ChatUser() {
	}

	/**
	 * Constructs a {@link ChatUser} instance.
	 * 
	 * @param name
	 *            user's name
	 * @param statusMessage
	 *            user's status message
	 */
	public ChatUser(String name, String statusMessage) {
		this.name = name;
		this.statusMessage = statusMessage;
	}

	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}

		if (obj instanceof ChatUser) {
			ChatUser user = (ChatUser) obj;

			return name.equals(user.name);
		}

		return false;
	}

	/**
	 * Returns the user's name.
	 * 
	 * @return user's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the user's status message.
	 * 
	 * @return user's status message.
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	public int hashCode() {
		return name.hashCode();
	}

	/**
	 * Sets the user's status message.
	 * 
	 * @param statusMessage
	 *            new value for the user's status message
	 */
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String toString() {
		return getName() + " - " + getStatusMessage();
	}
}
