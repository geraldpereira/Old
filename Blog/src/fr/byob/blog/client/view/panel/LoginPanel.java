package fr.byob.blog.client.view.panel;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;

import fr.byob.blog.client.Constants;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.controler.ConnectedUserControler;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.application.controler.IPageSelectionControler;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;
import fr.byob.client.view.widget.ad.IAdRefreshListener;

/**
 * D�crit le Panel pour l'ajout le login
 * 
 * @author Akemi
 */
public class LoginPanel extends Composite implements IAdRefreshListener {

    private final HorizontalPanel mainPanel;
    private final HorizontalPanel adPanel;
//    private NorthAdWidget ad;
//    private AdSenseWidget ad;

    /**
     * Constructor
     */
    public LoginPanel() {
        mainPanel = new HorizontalPanel();
        adPanel = new HorizontalPanel();
        initWidget(mainPanel);
    }

    /**
     * Initialise la vue
     */
    protected void initView(ConnectedUserModel model, ConnectedUserControler controler, boolean isUserConnected, IPageSelectionControler pageControler) {
        Label userLabel;
        Hyperlink login;
//        Hyperlink logout;
        HTML logout;
        Hyperlink paramUser = new Hyperlink(BlogStrings.INSTANCE.userParam(), BlogStrings.INSTANCE.userParam());
        paramUser.addStyleName(Css.INSTANCE.deco());

        login = new Hyperlink(BlogStrings.INSTANCE.userLogIn(), Constants.LOGIN_PAGE);
        login.addClickHandler(controler.getClickOnConnectionLinkListener());
        login.addStyleName(Css.INSTANCE.deco());

        logout = new HTML(Utils.getLinkHTML(Constants.HOME_PAGE, BlogStrings.INSTANCE.userLogOut()));
//        logout = new Hyperlink(BlogStrings.INSTANCE.userLogOut(), Constants.LOGOUT_PAGE);
        logout.addClickHandler(controler.getClickOnLogoutLinkListener());
        logout.addStyleName(Css.INSTANCE.deco());

        userLabel = new Label();

        if (isUserConnected) {
            Window.addCloseHandler(controler.getWindowOnLogoutCloseListener());
            IUser user = model.getConnectedUser();
            userLabel.setText(user.getUserid());
            login.setVisible(false);
            userLabel.setVisible(true);
            logout.setVisible(true);
            if (model.getConnectedUserRole() == ConnectedUserModel.ROLE_USER) {
                paramUser.setVisible(true);
                paramUser.addClickHandler(pageControler.getMenuSelectionControler(8));
            } else {
                paramUser.setVisible(false);
            }
        } else {
            userLabel.setText(BlogStrings.INSTANCE.userGuest());
            login.setVisible(true);
            userLabel.setVisible(false);
            logout.setVisible(false);
            paramUser.setVisible(false);
        }

        userLabel.addStyleName(Css.INSTANCE.userConnected());
        HorizontalPanel loginPanel = new HorizontalPanel();

        loginPanel.add(login);
        loginPanel.add(userLabel);
        loginPanel.add(paramUser);
        loginPanel.add(logout);
        loginPanel.setSpacing(5);
        loginPanel.setWidth("100px");
        loginPanel.setHeight("15px");

//        ad = new NorthAdWidget("myadsense2");
//        ad = new AdSenseWidget("myadsense2");
        adPanel.setBorderWidth(0);
        adPanel.setSpacing(0);
//        adPanel.add(ad);

        mainPanel.add(adPanel);
        mainPanel.add(loginPanel);
        mainPanel.setCellHorizontalAlignment(adPanel, HasHorizontalAlignment.ALIGN_LEFT);
        mainPanel.setCellHorizontalAlignment(loginPanel, HasHorizontalAlignment.ALIGN_RIGHT);
        mainPanel.setWidth("100%");

    }

    public void adRefreshed() {
//        ad.refresh();
//        adPanel.remove(ad);
//        ad = new AdSenseWidget("myadsense2");
//        adPanel.add(ad);
    }

}
