package fr.byob.blog.client.view.factory;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.ILink;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.proxy.LinkServiceProxy;
import fr.byob.blog.client.view.adapter.LinkAdapter;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.application.page.model.IPageContentFactory;
import fr.byob.client.om.controler.ObjectManagementControler;
import fr.byob.client.om.service.IObjectToolbarManagementServiceProxy;
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
import fr.byob.client.view.widget.FormWidget;
import fr.byob.client.view.widget.IFormWidget;
import fr.byob.client.view.widget.ObjectListBoxWidget;
import fr.byob.client.view.widget.TableWidget;
import fr.byob.client.view.widget.richtext.BYOBRichText;
import fr.byob.client.view.widget.toolbar.ToolbarWidget;

public class LinkPanelFactory implements IPageContentFactory{
    private static LinkPanelFactory panel = new LinkPanelFactory();

    private LinkPanelFactory() {
    }

    public static LinkPanelFactory getInstance() {
        return panel;
    }

    public Widget createPageContent() {
        LinkAdapter adapter = new LinkAdapter();

        final ITableModel<ILink> tableModel = new TableModel<ILink>(0);
        final IToolbarModel toolbarModel = new ToolbarModel(10);
        final IFormModel<ILink> formModel = new FormModel<ILink>();
        final IConsoleModel consoleModel = new ConsoleModel();
        
        IObjectToolbarManagementServiceProxy<ILink> service = LinkServiceProxy.getInstance();
        ObjectManagementControler<ILink> controler = new ObjectManagementControler<ILink>(service);
        controler.setTableModel(tableModel);
        controler.setFormModel(formModel);
        controler.setConsoleModel(consoleModel);
        controler.setToolbarModel(toolbarModel);

        final TableWidget<ILink> table ;
        
        if (ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_USER) {
            table = new TableWidget<ILink>(tableModel, adapter,  true,true);
            table.addListener(controler.getClickOnTableListener());
        }else{
            table = new TableWidget<ILink>(tableModel, adapter,  false,false);
        }
        FormWidget<ILink> form = null;
        Button addBtn = null;
        Button modBtn = null;
        Button delBtn = null;

        final ConsoleWidget console = new ConsoleWidget(consoleModel, adapter);

        if (ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_USER) {
            form = new FormWidget<ILink>(formModel, adapter);
            final BYOBTextBox title = new BYOBTextBox();
            final BYOBTextBox author = new BYOBTextBox();
            final BYOBTextBox url = new BYOBTextBox();
            final ObjectListBoxWidget<ICategory> category = CategoryListBoxFactory.getInstance().getCategoryListBoxWidget(consoleModel);            
            final BYOBRichText text = new BYOBRichText();
            title.setTabIndex(1);
            category.setTabIndex(2);
            url.setTabIndex(3);
            text.setTabIndex(4);
            form.add(LinkAdapter.titleId, BlogStrings.INSTANCE.divTitleDot(), title,0,0,1,1);
            form.add(LinkAdapter.authorId, BlogStrings.INSTANCE.divAuthor(), author,1,0,1,1);
            form.add(LinkAdapter.urlId, BlogStrings.INSTANCE.linkUrlUpper(), url,2,0,1,1);
            form.add(LinkAdapter.categoryId, BlogStrings.INSTANCE.divCategoriesDot(), category,0,1,3,1);
            form.add(LinkAdapter.descriptionId, text,3,0,1,2);
            addBtn = new Button(BlogStrings.INSTANCE.buttonAdd(), controler.getClickOnAddListener());
            modBtn = new Button(BlogStrings.INSTANCE.buttonModify(), controler.getClickOnModListener());
            delBtn = new Button(BlogStrings.INSTANCE.buttonDelete(), controler.getClickOnDelListener());
            addBtn.setTabIndex(5);
            modBtn.setTabIndex(5);
            delBtn.setTabIndex(6);
            form.addButton(IFormWidget.ADD_BUTTON_ID, addBtn);
            form.addButton(IFormWidget.MOD_BUTTON_ID, modBtn);
            form.addButton(IFormWidget.DEL_BUTTON_ID, delBtn);
        }
        ToolbarWidget toolbar = new ToolbarWidget(controler, toolbarModel);
        final ObjectManagementToolbarPanel<ILink> view = new ObjectManagementToolbarPanel<ILink>(controler, table, form, console, toolbar,
                ObjectManagementToolbarPanel.BOTTOM,true);

        if (ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_USER) {
            formModel.addListener(form);
            ((Widget)view.getForm()).setWidth("100%");
        }
        consoleModel.addListener(console);
        tableModel.addListener(table);
        toolbarModel.addListener(toolbar);
        
        view.initView();
        return view;
    }
}
