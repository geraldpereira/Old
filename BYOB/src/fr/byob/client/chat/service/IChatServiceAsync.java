package fr.byob.client.chat.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.byob.client.chat.event.ChatEvent;
import fr.byob.client.chat.model.IChatUser;

public interface IChatServiceAsync {
	  /**
	   * Exchange events with the chat service.
	   * 
	   */
	  void exchangeEvents(IChatUser user, List<ChatEvent> clientEvents, AsyncCallback<List<ChatEvent>> callback);
	  
	  /**
	   * Returns a list of the users that are available on the system.
	   * 
	   * @return list of users who are on the system
	   * 
	   */
	  void getCurrentUsers(IChatUser user, AsyncCallback<List<IChatUser>> callback);
}
