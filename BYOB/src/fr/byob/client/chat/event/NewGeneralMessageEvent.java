package fr.byob.client.chat.event;

import java.util.Set;

import fr.byob.client.chat.model.IChatUser;

public class NewGeneralMessageEvent extends NewMessageEvent {

    public NewGeneralMessageEvent() {

    }

    /**
     * @param sender
     * @param recipients
     * @param message
     */
    public NewGeneralMessageEvent(IChatUser sender, Set<IChatUser> recipients, String message) {
        super(sender, recipients, message);
    }

    /**
     * @param sender
     * @param recipients
     * @param message
     */
    public NewGeneralMessageEvent(IChatUser sender, Set<IChatUser> recipients, String message, int type) {
        super(sender, recipients, message, type);
    }

    /**
   * 
   */
    public void accept(ChatEventVisitor visitor) {
        visitor.visit(this);
    }

}
