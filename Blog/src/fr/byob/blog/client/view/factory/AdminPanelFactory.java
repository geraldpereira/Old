package fr.byob.blog.client.view.factory;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.IUser;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.proxy.UserServiceProxy;
import fr.byob.blog.client.view.adapter.UserAdapter;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.application.page.model.IPageContentFactory;
import fr.byob.client.om.controler.ObjectManagementControler;
import fr.byob.client.om.service.IObjectManagementServiceProxy;
import fr.byob.client.om.view.ObjectManagementPanel;
import fr.byob.client.om.view.ObjectManagementToolbarPanel;
import fr.byob.client.view.model.ConsoleModel;
import fr.byob.client.view.model.FormModel;
import fr.byob.client.view.model.IConsoleModel;
import fr.byob.client.view.model.IFormModel;
import fr.byob.client.view.model.ITableModel;
import fr.byob.client.view.model.IToolbarModel;
import fr.byob.client.view.model.TableModel;
import fr.byob.client.view.model.ToolbarModel;
import fr.byob.client.view.widget.BYOBPasswordTextBox;
import fr.byob.client.view.widget.BYOBTextBox;
import fr.byob.client.view.widget.ConsoleWidget;
import fr.byob.client.view.widget.SimpleFormWidget;
import fr.byob.client.view.widget.TableWidget;
import fr.byob.client.view.widget.toolbar.ToolbarWidget;

public class AdminPanelFactory implements IPageContentFactory{

    private static AdminPanelFactory panel = new AdminPanelFactory();
    private AdminPanelFactory() {
    }

    public static AdminPanelFactory getInstance(){
        return panel;
    }

    public Widget createPageContent() {
        final UserAdapter adapter = new UserAdapter();

        final ITableModel<IUser> tableModel = new TableModel<IUser>(1);
        final IToolbarModel toolbarModel = new ToolbarModel(10);
        final IFormModel<IUser> formModel = new FormModel<IUser>();
        final IConsoleModel consoleModel = new ConsoleModel();

        IObjectManagementServiceProxy<IUser> service = UserServiceProxy.getInstance();
        ObjectManagementControler<IUser> controler = new ObjectManagementControler<IUser>(service);
        controler.setTableModel(tableModel);
        if (ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_ADMIN) {
        controler.setFormModel(formModel);
        }
        controler.setConsoleModel(consoleModel);
        if (ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_USER) {
            controler.setToolbarModel(toolbarModel);
        }

        TableWidget<IUser> table = null;
        if (ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_ADMIN) {
            table = new TableWidget<IUser>(tableModel,adapter,  true,true);
            table.addListener(controler.getClickOnTableListener());
        } else {
            table = new TableWidget<IUser>(tableModel,adapter, false,true);
        }
        Button addBtn = null;
        Button modBtn = null;
        Button delBtn = null;
        SimpleFormWidget<IUser> form = null;
        if (ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_ADMIN) {
            form = new SimpleFormWidget<IUser>(formModel,adapter);
            final BYOBTextBox login = new BYOBTextBox();
            final BYOBPasswordTextBox pwd = new BYOBPasswordTextBox();
            form.add(UserAdapter.loginId, BlogStrings.INSTANCE.userLogin(), login);
            form.add(UserAdapter.pwdId, BlogStrings.INSTANCE.userPassword(), pwd);
            addBtn = new Button(BlogStrings.INSTANCE.buttonAdd(), controler.getClickOnAddListener());
            modBtn = new Button(BlogStrings.INSTANCE.buttonModify(), controler.getClickOnModListener());
            delBtn = new Button(BlogStrings.INSTANCE.buttonDelete(), controler.getClickOnDelListener());
            form.addButton(SimpleFormWidget.ADD_BUTTON_ID,addBtn);
            form.addButton(SimpleFormWidget.MOD_BUTTON_ID,modBtn);
            form.addButton(SimpleFormWidget.DEL_BUTTON_ID,delBtn);
        }

        ConsoleWidget console = new ConsoleWidget(consoleModel, adapter);
        ToolbarWidget toolbar = new ToolbarWidget(controler, toolbarModel);


        if (ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_ADMIN) {
            formModel.addListener(form);
        }

        tableModel.addListener(table);
        consoleModel.addListener(console);
        toolbarModel.addListener(toolbar);
        ObjectManagementPanel<IUser> view = null;
        if (ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_USER) {
            view = new ObjectManagementToolbarPanel<IUser>(controler,table, form, console,toolbar,ObjectManagementToolbarPanel.BOTTOM,false);
        }else{
            view = new ObjectManagementPanel<IUser>(controler,table, form, console,false);
        }
        view.initView();
        return view;
    }

}
