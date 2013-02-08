package fr.byob.client.chat.event;

import fr.byob.client.chat.model.IChatUser;

public class UserRemoveEvent extends ChatEvent {
  private IChatUser user;
  
  public UserRemoveEvent() {
  }
  
  public UserRemoveEvent(IChatUser user) {
    this.user = user;
  }

  public IChatUser getUser() {
    return user;
  }

  public void accept(ChatEventVisitor visitor) {
    visitor.visit(this);
  }
}