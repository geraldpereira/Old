package fr.byob.blog.client.chat.service;

import com.google.gwt.user.client.rpc.RemoteService;

import fr.byob.client.chat.event.ChatEvent;
import fr.byob.client.chat.model.IChatUser;
import fr.byob.client.chat.util.ChatServiceException;

import java.util.List;

/**
 * Chat service interface.
 */
public interface ChatService extends RemoteService {
  /**
   * Exchange events with the chat service.
   * 
   */
  List<ChatEvent> exchangeEvents(IChatUser user, List<ChatEvent> clientEvents) throws ChatServiceException;
  
  /**
   * Returns a list of the users that are available on the system.
   * 
   * @return list of users who are on the system
   * 
   */
  List<IChatUser> getCurrentUsers(IChatUser user);
}
