package fr.byob.client.chat.view;

import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.chat.model.IChatUser;
import fr.byob.client.chat.model.ChatModel.UserEventHandler;
import fr.byob.client.chat.util.ConversationManager;
import fr.byob.client.util.Css;
import fr.byob.client.view.panel.RoundedPanel;
import fr.byob.client.view.widget.ImageButton;
import fr.byob.client.view.widget.smiley.SmileyUtils;

public class ContactList extends Composite implements UserEventHandler {

	private class UserEntry extends Composite {
		private final Label nameLabel;
		private final HTML statusLabel;
		private IChatUser user;

		UserEntry(final IChatUser user, boolean isSelfUser) {
			this.user = user;
			VerticalPanel flow = new VerticalPanel();
			nameLabel = new Label();
			statusLabel = new HTML();
			updateUser(user);
			flow.add(nameLabel);
			flow.add(statusLabel);
			flow.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
			
			ImageButton button = new ImageButton(HasVerticalAlignment.ALIGN_MIDDLE,HasHorizontalAlignment.ALIGN_LEFT);
			button.setWidget(flow);
			button.setWidth("100%");
			initWidget(button);

			nameLabel.setStyleName(Css.INSTANCE.entryName());
			statusLabel.setStyleName(Css.INSTANCE.entryStatus());
			statusLabel.addStyleName(Css.INSTANCE.hideOverflow());
			statusLabel.setWidth("120px");

			flow.setStyleName(Css.INSTANCE.padr7());
			flow.addStyleName(Css.INSTANCE.padl7());
			
			if (!isSelfUser) {
				button.addClickHandler(new ClickHandler() {
                    public void onClick(ClickEvent event) {
                        manager.setFocusedConversation(manager
                                .maybeInitiateConversationPanel(user));
                    }
				});
				button.addStyleName(Css.INSTANCE.cursor());
				button.setOnMouseOverStyleName(Css.INSTANCE.contactMouseOver());
			}else{
				addStyleName(Css.INSTANCE.contactSelf());
			}
			
		}

		private void updateUser(IChatUser newUser) {
			user = newUser;
			update();
		}

		private void update() {
			nameLabel.setText(user.getName());
			statusLabel.setHTML(SmileyUtils.getTextWithSmiley(user.getStatusMessage()));
		}
	}

	final FlowPanel userPanel = new FlowPanel();
	final Label label;
	final ConversationManager manager;

	public ContactList(String title, ConversationManager manager) {
		label = new Label(title);
		VerticalPanel topPanel = new VerticalPanel();
		
		initWidget(topPanel);
		this.manager = manager;

		RoundedPanel roundedLabel = new RoundedPanel(label, RoundedPanel.TOP,
				4, Css.INSTANCE.contactRoundHeader());
		RoundedPanel roundedUser = new RoundedPanel(userPanel,
				RoundedPanel.BOTTOM, 4, Css.INSTANCE.contactRound());
		topPanel.add(roundedLabel);
		topPanel.add(roundedUser);

		label.setStyleName(Css.INSTANCE.headerContact());
		userPanel.setStyleName(Css.INSTANCE.contact());

		manager.getModel().addEventHandler(this);

		addSelf();
	}

	private UserEntry findEntry(IChatUser user) {
		for (Iterator<Widget> it = userPanel.iterator(); it.hasNext();) {
			UserEntry entry = (UserEntry) it.next();
			if (entry.user.getName().equals(user.getName())) {
				return entry;
			}
		}
		return null;
	}

	private void addSelf() {
		UserEntry entry = new UserEntry(manager.getUser(), true);
		userPanel.add(entry);
	}

	public void onUserAdded(IChatUser user) {
		if (findEntry(user) == null) {
			userPanel.add(new UserEntry(user, false));
		}
	}

	public void onUserRemoved(IChatUser user) {
		UserEntry entry = findEntry(user);
		if (entry != null) {
			userPanel.remove(entry);
		}
	}

	public void onUserUpdated(IChatUser user) {
		UserEntry entry = findEntry(user);
		if (entry != null) {
			entry.updateUser(user);
		}
	}
}
