package fr.byob.client.chat.event;

import fr.byob.client.chat.model.IChatUser;

public class UserAddEvent extends ChatEvent {
  private IChatUser user;

  public UserAddEvent() {
  }

  public UserAddEvent(IChatUser user) {
    this.user = user;
  }

  public void accept(ChatEventVisitor visitor) {
    visitor.visit(this);
  }

  public IChatUser getUser() {
    return user;
  }
  
  public String toString() {
    return "UserAdd: " + user.toString();
  }
}
