package fr.byob.blog.client.view.factory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.chat.service.ChatService;
import fr.byob.blog.client.chat.service.ChatServiceAsync;
import fr.byob.blog.client.controler.BriefControler;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.application.page.model.IPageContentFactory;
import fr.byob.client.chat.model.IChatUser;
import fr.byob.client.chat.view.ChatPanel;

public class ChatPanelFactory implements IPageContentFactory{
    private static ChatPanelFactory instance = new ChatPanelFactory();

    private ChatPanelFactory() {
    }

    public static ChatPanelFactory getInstance() {
        return instance;
    }

    private static ChatServiceAsync getService() {
        ChatServiceAsync service = (ChatServiceAsync) GWT
                .create(ChatService.class);

        ((ServiceDefTarget) service).setServiceEntryPoint(GWT
                .getModuleBaseURL()
                + "chatService");

        return service;
    }
    
    public Widget createPageContent() {
        ChatPanel chat = new ChatPanel(382/*532*/,295,BlogStrings.INSTANCE.chatGeneral(),BlogStrings.INSTANCE.chatContacts(),(IChatUser)ConnectedUserModel.getInstance().getConnectedUserProfil(),getService(),BriefControler.getInstance());
        return chat;
    }
}
