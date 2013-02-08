package fr.byob.client.chat.event;

import java.util.Set;

import fr.byob.client.chat.model.IChatUser;

public class NewMessageEvent extends ChatEvent {
    private String message;

    private Set<IChatUser> recipients;

    private IChatUser sender;

    private int type;

    public NewMessageEvent() {

    }

    /**
     * @param sender
     * @param recipients
     * @param message
     */
    public NewMessageEvent(IChatUser sender, Set<IChatUser> recipients, String message) {
        this.sender = sender;
        this.recipients = recipients;
        this.message = message;
        this.type = NORMAL;
    }

    /**
     * @param sender
     * @param recipients
     * @param message
     */
    public NewMessageEvent(IChatUser sender, Set<IChatUser> recipients, String message, int type) {
        this.sender = sender;
        this.recipients = recipients;
        this.message = message;
        this.type = type;
    }

    /**
   * 
   */
    public void accept(ChatEventVisitor visitor) {
        visitor.visit(this);
    }

    /**
     * 
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @return
     */
    public Set<IChatUser> getRecipients() {
        return recipients;
    }

    /**
     * 
     * @return
     */
    public IChatUser getSender() {
        return sender;
    }

    public int getType() {
        return type;
    }

}
