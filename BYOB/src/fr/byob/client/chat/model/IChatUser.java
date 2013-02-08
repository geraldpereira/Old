package fr.byob.client.chat.model;

import com.google.gwt.user.client.rpc.IsSerializable;


public interface IChatUser extends IsSerializable{
	/**
	 * Returns the user's name.
	 * 
	 * @return user's name
	 */
	public String getName();

	/**
	 * Returns the user's status message.
	 * 
	 * @return user's status message.
	 */
	public String getStatusMessage();

	/**
	 * Sets the user's status message.
	 * 
	 * @param statusMessage
	 *            new value for the user's status message
	 */
	public void setStatusMessage(String statusMessage);
}
