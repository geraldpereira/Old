package fr.byob.client.chat.view;

import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratedTabPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

import fr.byob.client.chat.model.ChatModel;
import fr.byob.client.chat.model.IChatUser;
import fr.byob.client.chat.model.ChatModel.UserEventHandler;
import fr.byob.client.chat.service.IBriefControler;
import fr.byob.client.chat.service.IChatServiceAsync;
import fr.byob.client.chat.util.ConversationManager;
import fr.byob.client.util.Css;

public class ChatPanel extends Composite {

	private final DecoratedTabPanel  tabbedPane;
	private final IChatUser user;
	private GeneralConversationPanel general;
	private ConversationManager manager;
	private int widthOffset;
	private int heightOffset; 
	
	public ChatPanel(int widthOffset, int heightOffset,String generalTitle, String contactListTitle, IChatUser user,IChatServiceAsync service,IBriefControler briefService) {
		this.user = user;
		this.widthOffset = widthOffset;
		this.heightOffset = heightOffset;
		tabbedPane = new DecoratedTabPanel ();
		initView(generalTitle, contactListTitle, service,briefService);
	}

	protected void initView(String generalTitle, String contactListTitle, IChatServiceAsync service,IBriefControler briefService) {
		HorizontalPanel chatPanel = new HorizontalPanel();
		ChatModel model = new ChatModel(user,service,briefService);
		manager = new ConversationManager(model,this);
		
		general = new GeneralConversationPanel(widthOffset, heightOffset,generalTitle,manager);
		model.addEventHandler((UserEventHandler)general);

		tabbedPane.add(general, general.getTitleWidget());
		tabbedPane.selectTab(0);
		general.onConversationSelection();
		
		ContactList contacts = new ContactList(contactListTitle,manager);
		contacts.setWidth("150px");
		contacts.addStyleName(Css.INSTANCE.mrgl10());
		
		chatPanel.add(tabbedPane);
		chatPanel.add(contacts);
		initWidget(chatPanel);
		tabbedPane.getTabBar().addSelectionHandler(new SelectionHandler<Integer>(){
            public void onSelection(SelectionEvent<Integer> event) {
                ((IConversationPanel)tabbedPane.getWidget(event.getSelectedItem())).onConversationSelection();
            }
		});
		tabbedPane.getTabBar().addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>(){
            public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
//                if (tabbedPane.getTabBar().getSelectedTab() == tabIndex){
//                } else 
                if (tabbedPane.getTabBar().getSelectedTab()>= 0 && tabbedPane.getTabBar().getSelectedTab() < tabbedPane.getTabBar().getTabCount()){
                    ((IConversationPanel)tabbedPane.getWidget(tabbedPane.getTabBar().getSelectedTab())).onConversationUnselection();                    
                }                               
            }
		});
//		tabbedPane.getTabBar().addTabListener(new TabListener(){
//
//			public boolean onBeforeTabSelected(SourcesTabEvents sender,
//					int tabIndex) {
//				if (tabbedPane.getTabBar().getSelectedTab() == tabIndex){
//					return false;
//				}else if (tabbedPane.getTabBar().getSelectedTab()>= 0 && tabbedPane.getTabBar().getSelectedTab() < tabbedPane.getTabBar().getTabCount()){
//					((IConversationPanel)tabbedPane.getWidget(tabbedPane.getTabBar().getSelectedTab())).onConversationUnselection();					
//				}								
//				return true;
//			}
//
//			public void onTabSelected(SourcesTabEvents sender, int tabIndex) {
//				((IConversationPanel)tabbedPane.getWidget(tabIndex)).onConversationSelection();
//			}
//			
//		});
	}

	public GeneralConversationPanel getGeneralConversation (){
		return general;
	}
	
	public void addConversation(IConversationPanel converstation) {
		tabbedPane.add(converstation.getWidget(),converstation.getTitleWidget());
	}

	public void removeConversation(IConversationPanel converstation) {
		int selectedTab = tabbedPane.getTabBar().getSelectedTab();
		// Si la conversation à fermer est la conversation courante.
		if (tabbedPane.getWidgetIndex(converstation.getWidget()) == selectedTab){
			tabbedPane.remove(converstation.getWidget());
			if (selectedTab >= 0){
				tabbedPane.selectTab(selectedTab -1);
			}
		}else{
			tabbedPane.remove(converstation.getWidget());
		}
		
	}
	
	public void selectConversation (IConversationPanel conversation){
		tabbedPane.selectTab(tabbedPane.getWidgetIndex(conversation.getWidget()));
	}
	
	public boolean isSelectedConversation (IConversationPanel conversation){
		return tabbedPane.getWidgetIndex(conversation.getWidget()) == tabbedPane.getTabBar().getSelectedTab();
	}
	
	public void hilightConversation(IConversationPanel conversation) {
		conversation.getTitleWidget().addStyleName(Css.INSTANCE.tabBarTitleReceived());
	}

	public IConversationPanel conversationPanelFactory(IChatUser toUser) {
		return new ConversationPanel(null,widthOffset,heightOffset,manager,toUser);
	}

	
}
