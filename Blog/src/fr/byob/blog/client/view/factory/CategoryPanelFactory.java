package fr.byob.blog.client.view.factory;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.proxy.CategoryServiceProxy;
import fr.byob.blog.client.view.adapter.CategoryAdapter;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.application.page.model.IPageContentFactory;
import fr.byob.client.om.controler.ObjectManagementControler;
import fr.byob.client.om.service.IObjectToolbarManagementServiceProxy;
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
import fr.byob.client.view.widget.BYOBTextBox;
import fr.byob.client.view.widget.ConsoleWidget;
import fr.byob.client.view.widget.SimpleFormWidget;
import fr.byob.client.view.widget.TableWidget;
import fr.byob.client.view.widget.toolbar.ToolbarWidget;

public class CategoryPanelFactory implements IPageContentFactory{

    private static CategoryPanelFactory panel = new CategoryPanelFactory();

    private CategoryPanelFactory() {
    }

    public static CategoryPanelFactory getInstance() {
        return panel;
    }

    public Widget createPageContent() {
        CategoryAdapter adapter = new CategoryAdapter();

        final ITableModel<ICategory> tableModel = new TableModel<ICategory>(1);
        final IToolbarModel toolbarModel = new ToolbarModel(10);
        final IFormModel<ICategory> formModel = new FormModel<ICategory>();
        final IConsoleModel consoleModel = new ConsoleModel();

        IObjectToolbarManagementServiceProxy<ICategory> service = CategoryServiceProxy.getInstance();
        ObjectManagementControler<ICategory> controler = new ObjectManagementControler<ICategory>(service);
        controler.setTableModel(tableModel);
        if (ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_ADMIN) {
            controler.setFormModel(formModel);
        }
        controler.setConsoleModel(consoleModel);
        if (ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_USER) {
            controler.setToolbarModel(toolbarModel);
        }
        TableWidget<ICategory> table = null;
        Button addBtn = null;
        Button modBtn = null;
        Button delBtn = null;
        SimpleFormWidget<ICategory> form = null;
        if (ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_ADMIN) {
            table = new TableWidget<ICategory>(tableModel, adapter, true,true);
            table.addListener(controler.getClickOnTableListener());
            form = new SimpleFormWidget<ICategory>(formModel, adapter);
            final BYOBTextBox label = new BYOBTextBox();
            form.add(CategoryAdapter.labelId, BlogStrings.INSTANCE.divCategoryDot(), label);
            addBtn = new Button(BlogStrings.INSTANCE.buttonAdd(), controler.getClickOnAddListener());
            modBtn = new Button(BlogStrings.INSTANCE.buttonModify(), controler.getClickOnModListener());
            delBtn = new Button(BlogStrings.INSTANCE.buttonDelete(), controler.getClickOnDelListener());
            form.addButton(SimpleFormWidget.ADD_BUTTON_ID, addBtn);
            form.addButton(SimpleFormWidget.MOD_BUTTON_ID, modBtn);
            form.addButton(SimpleFormWidget.DEL_BUTTON_ID, delBtn);
        } else {
            table = new TableWidget<ICategory>(tableModel, adapter, false,false);
        }
        ToolbarWidget toolbar = new ToolbarWidget(controler, toolbarModel);

        ConsoleWidget console = new ConsoleWidget(consoleModel, adapter);

        if (ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_ADMIN) {
            formModel.addListener(form);
        }
        consoleModel.addListener(console);
        tableModel.addListener(table);
        toolbarModel.addListener(toolbar);
        if(ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_USER) {
            final ObjectManagementToolbarPanel<ICategory> view = new ObjectManagementToolbarPanel<ICategory>(controler, table, form, console, toolbar,
                    ObjectManagementToolbarPanel.BOTTOM,false);
            view.initView();
            return view;
        }else{
            final ObjectManagementPanel<ICategory> view = new ObjectManagementPanel<ICategory>(controler, table, form, console,false);
            view.initView();
            return view;
        }
    }

}
