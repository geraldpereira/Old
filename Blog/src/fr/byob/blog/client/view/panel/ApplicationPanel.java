package fr.byob.blog.client.view.panel;

import java.util.List;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.BYOBBlog;
import fr.byob.blog.client.Constants;
import fr.byob.blog.client.IConnectedUserListener;
import fr.byob.blog.client.controler.BriefControler;
import fr.byob.blog.client.controler.ConnectedUserControler;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.model.UserGWT;
import fr.byob.blog.client.view.application.AdminApplication;
import fr.byob.blog.client.view.application.GuestApplication;
import fr.byob.blog.client.view.application.UserApplication;
import fr.byob.blog.client.view.util.BlogUtils;
import fr.byob.client.application.IIndexSelectionListener;
import fr.byob.client.application.controler.IPageSelectionControler;
import fr.byob.client.application.controler.PageSelectionControler;
import fr.byob.client.application.model.IApplicationModel;
import fr.byob.client.application.model.IIndexSelectionModel;
import fr.byob.client.application.model.IndexSelectionModel;
import fr.byob.client.application.page.view.IPage;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;

/**
 * Panel principal de l'application
 * 
 * @author akemi
 * 
 */
public class ApplicationPanel extends Composite implements IConnectedUserListener, IIndexSelectionListener {
   
//    private StringsPanel strings;

    private IApplicationModel applicationModel;
    
    private ConnectedUserModel userModel;
    private ConnectedUserControler userControler;
    
    private IPageSelectionControler pageControler;
    private IIndexSelectionModel pageModel;

    /**
     * Panel principal dans lequelle tout est affiché
     */
    private SimplePanel rootPanel;
    /**
     * Panel qui permet de stocker les différentes pages centrales
     */
    private DeckPanel deckPanel;

    /**
     * Constructor
     */
    public ApplicationPanel() {
        this.userModel = ConnectedUserModel.getInstance();
        this.userModel.addListener(this);
        this.userControler = new ConnectedUserControler(userModel);
        
        this.pageModel = new IndexSelectionModel(0);
        this.pageModel.addListener(this);
        this.pageControler = new PageSelectionControler(pageModel);
        
        rootPanel = new SimplePanel();
        initWidget(rootPanel);
        
        this.userControler.getConnectedUser();
    }

    /**
     * Initialise la vue une fois connect�
     * 
     * @param isUserConnected
     *            si l'utilisateur est connect�
     */
    private void initViewConnected(boolean isUserConnected) {
        
        if(isUserConnected){
            if(userModel.getConnectedUserProfil() != null && userModel.getConnectedUserProfil().getProfilcss() != null){
                ColorSelectionPanel.getInstance().changeColor(userModel.getConnectedUserProfil().getProfilcss());
            }
        }
        Css css = Css.INSTANCE;
        DockPanel dockPanel = new DockPanel();
        deckPanel = new DeckPanel();
        for (int i = 0; i < applicationModel.getPages().size(); i++) {
            deckPanel.add(((IPage) applicationModel.getPages().get(i)).getCenterWidget());
        }
        applicationModel.getPages().get(Constants.DEFAULT_PAGE_INDEX).chargedPage();
        deckPanel.showWidget(Constants.DEFAULT_PAGE_INDEX);
        
        final EastPanel eastPanel = (EastPanel) applicationModel.getEastWidget();
        
        final HorizontalPanel center = new HorizontalPanel();
        center.add(deckPanel);
        center.add(eastPanel);
        center.setCellWidth(deckPanel, "100%");
        center.setCellWidth(eastPanel, "150px");
        
        final ScrollPanel scroll = new ScrollPanel(center);
        scroll.setHeight(Window.getClientHeight()-200+"px");
        scroll.setWidth("98%");
        scroll.addStyleName(Css.INSTANCE.mrg10());
        dockPanel.add(scroll, DockPanel.CENTER);
        
        VerticalPanel northPanel = new VerticalPanel();
        LoginPanel login = (LoginPanel)applicationModel.getOverHeaderWidget();
        login.setWidth("100%");
        login.initView(userModel,userControler,isUserConnected,pageControler);
        northPanel.add(login);
        northPanel.setCellHorizontalAlignment(login,HasHorizontalAlignment.ALIGN_RIGHT);
        
        HeaderPanel header = ((HeaderPanel)applicationModel.getHeaderWidget());
        header.initView(applicationModel.getTitle(),pageControler);
        BriefControler.getInstance().setHeader(header);
        BriefControler.getInstance().getUpdateBriefs();
        northPanel.add(applicationModel.getHeaderWidget());
        northPanel.addStyleName(Css.INSTANCE.mrgt5());
        northPanel.addStyleName(Css.INSTANCE.mrgl4()); 
        northPanel.addStyleName(Css.INSTANCE.mrgr5());
        northPanel.setWidth("99%");
        dockPanel.add(northPanel, DockPanel.NORTH);
        
        
        Widget south = applicationModel.getSouthWidget();
        south.setStyleName(css.msgdate());
        dockPanel.add(south, DockPanel.SOUTH);
        
        final VerticalMenusPanel menus = (VerticalMenusPanel) applicationModel.getWestWidget();
        menus.initView(applicationModel, pageControler,144);
        pageModel.addListener(menus);
        
        // Pour rafraichir les pubs à chaque changement de page
//        AdRefreshControler.getInstance().addAdRefreshListener(eastPanel);
//        AdRefreshControler.getInstance().addAdRefreshListener(login);
//        pageModel.addListener(new IIndexSelectionListener(){
//            public void indexSelected(int oldIndex, int newIndex) {
//                AdRefreshControler.getInstance().refreshAds();                
//            }
//        });
        
        menus.setStyleName(Css.INSTANCE.mrgl4());
        menus.setSize("144px", Window.getClientHeight()-200+"px");
        dockPanel.add(menus, DockPanel.WEST);
        
        Window.addResizeHandler(new ResizeHandler(){

            public void onResize(ResizeEvent event) {
                menus.setHeight( Window.getClientHeight()-200+"px");
                menus.getWestPanel().setHeight( Window.getClientHeight()-200+"px");
                scroll.setHeight(Window.getClientHeight()-200+"px");
            }
            
        });
        
        // Init the panel properties
        dockPanel.setWidth("100%");
        dockPanel.setHeight("100%");

        dockPanel.setCellWidth(scroll, "100%");
        dockPanel.setCellHeight(northPanel, "90px");
        dockPanel.setCellHeight(south, "20px");
        
        rootPanel.clear();
        rootPanel.add(dockPanel);
        
        List<Object> params = BYOBBlog.getIdObject();
        if(params.size() != 0 && params.get(0) != null){
            pageModel.selectIndex(Utils.redirectUrl(applicationModel, deckPanel, params));
            BlogUtils.isNotBasicUrl(applicationModel,deckPanel,params,pageModel);
        }
        
    }

    /**
     * Update la vue
     * 
     * @param isConnected
     *            indique si l'utilisateur est connect�
     */
    public void connectedUserUpdate(boolean isConnected) {
        boolean isAlreadyConnected = false;
        UserGWT user = (UserGWT) userModel.getConnectedUser();
        if (user != null && user.isAdmin()) {
            this.applicationModel = new AdminApplication();
        } else if (user != null) {
            if(!(applicationModel instanceof UserApplication)){
                this.applicationModel = new UserApplication();
            }else{
                isAlreadyConnected = true;
            }
        } else {
            this.applicationModel = new GuestApplication();
        }
        if(!isAlreadyConnected){
            initViewConnected(isConnected);
        }
    }

    /**
     * Change la page centrale
     * 
     * @param oldPageIndex
     *            ancien index de page
     * @param newPageIndex
     *            nouvelle page
     */
    public void indexSelected(int oldPageIndex, int newPageIndex) {
        deckPanel.showWidget(newPageIndex);
        applicationModel.getPages().get(newPageIndex).chargedPage();
    }
}
