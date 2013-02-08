package fr.byob.blog.client.chat.service;

import fr.byob.client.chat.event.ChatEvent;
import fr.byob.client.chat.model.IChatUser;
import fr.byob.client.chat.service.IChatServiceAsync;

import java.util.List;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Chat service interface.
 */
public interface ChatServiceAsync extends IChatServiceAsync{
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
