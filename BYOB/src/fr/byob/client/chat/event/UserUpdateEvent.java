package fr.byob.client.chat.event;

import fr.byob.client.chat.model.IChatUser;

public class UserUpdateEvent extends ChatEvent {
  private IChatUser user;

  public UserUpdateEvent() {
  }

  public UserUpdateEvent(IChatUser user) {
    this.user = user;
  }

  public void accept(ChatEventVisitor visitor) {
    visitor.visit(this);
  }

  public IChatUser getUser() {
    return user;
  }
}