package fr.byob.client.chat.view;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import fr.byob.client.chat.model.IChatUser;
import fr.byob.client.chat.model.ChatModel.UserEventHandler;
import fr.byob.client.chat.util.ConversationManager;
import fr.byob.client.util.Css;

public class GeneralConversationPanel extends ConversationPanel implements UserEventHandler {
    public GeneralConversationPanel(int widthOffset, int heightOffset, String title, final ConversationManager manager) {
        super(title, widthOffset, heightOffset, manager, new HashSet<IChatUser>());
    }

    protected void initTitleWidget(String title) {
        Label label = new Label(title);
        tabBarTitle = new HorizontalPanel();
        tabBarTitle.add(label);
        tabBarTitle.setCellWidth(label, "100%");
        tabBarTitle.setCellHeight(label, "18px");
        tabBarTitle.addStyleName(Css.INSTANCE.tabBarTitle());

    }

    /**
     * Less messages envoyes sont ici des messages generaux
     */
    protected void sendMessage(String message, int type) {
        manager.sendGeneralMessage(recipients, message, type);
    }

    public void onNewMessage(IChatUser sender, Set<IChatUser> recipients, String message) {
    }

    public void onNewGeneralMessage(IChatUser sender, Set<IChatUser> recipients, String message, int type) {
        onUnknownTypeMessage(sender,recipients,message,type);
    }

    public void onUserAdded(IChatUser user) {
        recipients.add(user);
    }

    public void onUserRemoved(IChatUser user) {
        recipients.remove(user);
    }

    public void onUserUpdated(IChatUser user) {

    }
}
