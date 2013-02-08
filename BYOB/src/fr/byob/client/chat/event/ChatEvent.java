package fr.byob.client.chat.event;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * Base class for chat events
 */
public abstract class ChatEvent implements IsSerializable {

    public final static int NORMAL = 0;
    
    public final static int ME = 1;
    
    public final static int INFO = 2;
    
  /**
   * Accepts a ChatEventVisitor instance.  This method allows the different
   * event object to participate in a Visitor patter.
   * 
   * @param visitor
   */
  public abstract void accept(ChatEventVisitor visitor);
}