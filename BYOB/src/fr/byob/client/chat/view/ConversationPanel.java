package fr.byob.client.chat.view;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.chat.command.CommandManager;
import fr.byob.client.chat.command.IConversationCommand;
import fr.byob.client.chat.event.ChatEvent;
import fr.byob.client.chat.link.LinkAnalyser;
import fr.byob.client.chat.model.IChatUser;
import fr.byob.client.chat.util.ConversationManager;
import fr.byob.client.images.Images;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;
import fr.byob.client.view.panel.RoundedPanel;
import fr.byob.client.view.widget.BYOBTextBox;
import fr.byob.client.view.widget.ImageButton;
import fr.byob.client.view.widget.smiley.SmileyUtils;

public class ConversationPanel extends Composite implements ICommandedConversationPanel {

    protected class MessagePanel extends Composite {

        private final VerticalPanel panelMessage;

        MessagePanel() {
            panelMessage = new VerticalPanel();
            initWidget(panelMessage);
        }

        void addNormalMessage(IChatUser sender, String message, String styleName) {
            String fullMessage = "";
            if (timedDisplay) {
                fullMessage = "[" + Utils.formatTime(new Date()) + "] " + sender.getName() + " : " + SmileyUtils.getTextWithSmiley(message);
            } else {
                fullMessage = sender.getName() + " : " + SmileyUtils.getTextWithSmiley(message);
            }
            HTML line = new HTML(fullMessage);
            line.setStyleName(styleName);
            panelMessage.add(line);
        }

        void addMeMessage(IChatUser sender, String message, String styleName) {
            String fullMessage = SmileyUtils.getTextWithSmiley(message);
            HTML line = new HTML(fullMessage);
            line.setStyleName(styleName);
            panelMessage.add(line);
        }
        
        void addInfoMessage(IChatUser sender, String message, String styleName) {
            String fullMessage = SmileyUtils.getTextWithSmiley(message);
            HTML line = new HTML(fullMessage);
            line.setStyleName(styleName);
            panelMessage.add(line);
        }

        public void clearMessages() {
            panelMessage.clear();
        }
    }

    protected final static String STYLENAME_SENT_MESSAGE = Css.INSTANCE.sent();
    protected final static String STYLENAME_RECEIVED_MESSAGE = Css.INSTANCE.received();
    protected final static String STYLENAME_ME_MESSAGE = Css.INSTANCE.italic();
    protected final static String STYLENAME_INFO_MESSAGE = Css.INSTANCE.entryStatus();

    private boolean autoScrolling = true;
    private boolean timedDisplay = true;

    protected final MessagePanel messagePanel = new MessagePanel();
    protected ScrollPanel messageScroll = new ScrollPanel(messagePanel);
    protected final BYOBTextBox entryBox = new BYOBTextBox();
    protected final Set<IChatUser> recipients;
    protected final ConversationManager manager;
    protected HorizontalPanel tabBarTitle;

    public ConversationPanel(String title, int widthOffset, int heightOffset, ConversationManager manager, final IChatUser recipient) {
        this(title, widthOffset, heightOffset, manager, ConversationManager.wrapInSet(recipient));
    }

    public ConversationPanel(String title, final int widthOffset, final int heightOffset, final ConversationManager manager, final Set<IChatUser> recipients) {
        this.recipients = recipients;
        this.manager = manager;

        initTitleWidget(title);
        VerticalPanel panel = new VerticalPanel();
        panel.add(messageScroll);

        messageScroll.setHeight(Window.getClientHeight() - heightOffset + "px");
        messageScroll.setWidth(Window.getClientWidth() - widthOffset + "px");
        Window.addResizeHandler(new ResizeHandler(){
            public void onResize(ResizeEvent event) {
                messageScroll.setHeight(event.getHeight() - heightOffset + "px");
                messageScroll.setWidth(event.getWidth() - widthOffset + "px");
            }
        });
        final Image help = ImagesChat.INSTANCE.help().createImage();
        help.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                HashMap<String, IConversationCommand> msg = new HashMap<String, IConversationCommand>();
                HashMap<String, IConversationCommand> commands = CommandManager.getInstance().getCommands();
                for(IConversationCommand command : commands.values()){
                    msg.put(command.getStringHelp(),command);
                }
                final ToolTipChatHelper toolTip = new ToolTipChatHelper(msg,entryBox);
                toolTip.show(help);
            }
        });
        HorizontalPanel entry = new HorizontalPanel();
        entry.add(entryBox);
        entry.add(help);
        entry.setCellHorizontalAlignment(help, HasHorizontalAlignment.ALIGN_RIGHT);
        entry.setCellWidth(entryBox, "100%");
        panel.add(entry);
        panel.addStyleName(Css.INSTANCE.tabContent());
        panel.setSpacing(6);
        RoundedPanel roundedLabel = new RoundedPanel(panel, RoundedPanel.ALL, 4, Css.INSTANCE.tabRound());

        initWidget(roundedLabel);
        entry.setWidth("100%");
        entryBox.setWidth("98%");
        entryBox.addKeyUpHandler(getEntryBoxListener(this));
        this.setStyleName(Css.INSTANCE.conversationPanel());
    }

    protected void initTitleWidget(String title) {

        final Label label;
        if (title == null) {
            label = new Label(ConversationManager.getTitle(recipients));
        } else {
            label = new Label(title);
        }
        ImageButton button = new ImageButton();
        Image exit = Images.INSTANCE.exit().createImage();
        button.setWidget(exit);
        final ConversationPanel current = this;
        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                manager.removeConversation(current);
            }
        });
        button.setStyleName(Css.INSTANCE.cursor());
        tabBarTitle = new HorizontalPanel();
        tabBarTitle.add(label);
        tabBarTitle.setCellWidth(label, "100%");
        tabBarTitle.setCellHeight(label, "18px");
        tabBarTitle.add(button);
        tabBarTitle.addStyleName(Css.INSTANCE.tabBarTitle());
    }

    public Set<IChatUser> getRecipients() {
        return recipients;
    }

    protected void adjustScroll() {
        DeferredCommand.addCommand(new Command() {
            public void execute() {
                if (autoScrolling) {
                    messageScroll.setScrollPosition(messagePanel.getOffsetHeight() - messageScroll.getOffsetHeight());
                }
            }
        });
    }

    public void onNewMessage(IChatUser sender, Set<IChatUser> recipients, String message, int type) {
        onUnknownTypeMessage(sender, recipients, message, type);
    }

    public void onNewGeneralMessage(IChatUser sender, Set<IChatUser> recipients, String message, int type) {
    }

    protected void onUnknownTypeMessage(IChatUser sender, Set<IChatUser> recipients, String message, int type) {
        switch (type) {
        case ChatEvent.ME:
            messagePanel.addMeMessage(sender, message, STYLENAME_ME_MESSAGE);
            adjustScroll();
            break;
        case ChatEvent.INFO:
            messagePanel.addInfoMessage(sender, message, STYLENAME_INFO_MESSAGE);
            adjustScroll();
            break;
        default:
        case ChatEvent.NORMAL:
            messagePanel.addNormalMessage(sender, message, STYLENAME_RECEIVED_MESSAGE);
            adjustScroll();
            break;

        }
    }

    public Widget getTitleWidget() {
        return tabBarTitle;
    }

    public void onConversationUnselection() {
        tabBarTitle.removeStyleName(Css.INSTANCE.tabBarTitleSelected());
    }

    public void onConversationSelection() {
        getTitleWidget().removeStyleName(Css.INSTANCE.tabBarTitleReceived());
        adjustScroll();
        tabBarTitle.addStyleName(Css.INSTANCE.tabBarTitleSelected());
        Command command = new Command() {
            public void execute() {
                entryBox.setFocus(true);
            }
        };
        DeferredCommand.addCommand(command);
    }

    protected KeyUpHandler getEntryBoxListener(final ICommandedConversationPanel panel) {
        return new KeyUpHandler(){
            public void onKeyUp(KeyUpEvent event) {
                if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER ) {
                    final String message = entryBox.getText();
                    entryBox.setText("");
                    if (!CommandManager.getInstance().processCommand(panel, message)) {
                        if (message.trim().length() > 0) {
                            String linkAnalysedMessage = LinkAnalyser.getInstance().trimLinks(message);
                            sendMessage(linkAnalysedMessage, ChatEvent.NORMAL);
                            messagePanel.addNormalMessage(manager.getUser(), linkAnalysedMessage, STYLENAME_SENT_MESSAGE);
                            adjustScroll();
                        }
                    }
                } 
            }
        };
    }

    protected void sendMessage(String message, int type) {
        manager.sendMessage(recipients, message, type);
    }

    public void clearMessages() {
        messagePanel.clearMessages();
    }

    public boolean isAutoScrolling() {
        return autoScrolling;
    }

    public void setAutoScrolling(boolean autoScrolling) {
        this.autoScrolling = autoScrolling;
        if (autoScrolling) {
            adjustScroll();
            messagePanel.addMeMessage(manager.getUser(), ChatStrings.INSTANCE.scrollOn(), STYLENAME_INFO_MESSAGE);
        }else{
            messagePanel.addMeMessage(manager.getUser(), ChatStrings.INSTANCE.scrollOff(), STYLENAME_INFO_MESSAGE);
        }
        
    }

    public boolean isTimedDisplay() {
        return timedDisplay;
    }

    public void setTimedDisplay(boolean timedDisplay) {
        this.timedDisplay = timedDisplay;
        if(timedDisplay){
            messagePanel.addMeMessage(manager.getUser(), ChatStrings.INSTANCE.timeDisplay(), STYLENAME_INFO_MESSAGE);
        }else{
            messagePanel.addMeMessage(manager.getUser(), ChatStrings.INSTANCE.timeNoDisplay(), STYLENAME_INFO_MESSAGE);
        }
    }

    public Widget getWidget() {
        return this;
    }

    public void setUserStatus(String status) {
        manager.setUserStatus(status);
        String message = "("+manager.getUser().getName() + " "+ChatStrings.INSTANCE.changestatus()+" " + status.trim()+")";
        sendMessage(message, ChatEvent.INFO);
        messagePanel.addMeMessage(manager.getUser(), message, STYLENAME_INFO_MESSAGE);
    }

    public void setMeMessage(String msg) {
        String message = "*"+manager.getUser().getName() + " " + msg;
        sendMessage(message, ChatEvent.ME);
        messagePanel.addMeMessage(manager.getUser(), message, STYLENAME_ME_MESSAGE);
    }
    
    public void setHelpMessage(String msg) {
        String message = "* " + msg;
        messagePanel.addMeMessage(manager.getUser(), message, STYLENAME_ME_MESSAGE);
    }

    public void addBrief(String brief) {
       manager.addBrief(brief,recipients);
    }
}
