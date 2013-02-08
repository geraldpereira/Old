package fr.byob.blog.client.view.factory;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.proxy.PostServiceProxy;
import fr.byob.blog.client.view.adapter.PostAdapter;
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

public class PostPanelFactory implements IPageContentFactory{
    private static PostPanelFactory panel = new PostPanelFactory();

    private PostPanelFactory() {
    }

    public static PostPanelFactory getInstance() {
        return panel;
    }

    public Widget createPageContent() {
        PostAdapter adapter = new PostAdapter();

        final ITableModel<IPost> tableModel = new TableModel<IPost>(1);
        final IToolbarModel toolbarModel = new ToolbarModel(10);
        final IFormModel<IPost> formModel = new FormModel<IPost>();
        final IConsoleModel consoleModel = new ConsoleModel();
        
        IObjectToolbarManagementServiceProxy<IPost> service = PostServiceProxy.getInstance();
        ObjectManagementControler<IPost> controler = new ObjectManagementControler<IPost>(service);
        controler.setTableModel(tableModel);
        controler.setFormModel(formModel);
        controler.setConsoleModel(consoleModel);
        controler.setToolbarModel(toolbarModel);

        final TableWidget<IPost> table = new TableWidget<IPost>(tableModel, adapter,  true,true);
        
        if (ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_USER) {
            table.addListener(controler.getClickOnTableListener());
        }
        FormWidget<IPost> form = null;
        Button addBtn = null;
        Button modBtn = null;
        Button delBtn = null;

        final ConsoleWidget console = new ConsoleWidget(consoleModel, adapter);

        if (ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_USER) {

            form = new FormWidget<IPost>(formModel, adapter);
            final BYOBTextBox title = new BYOBTextBox();
            final BYOBTextBox author = new BYOBTextBox();
            final BYOBTextBox date = new BYOBTextBox();
            final CheckBox isprivate = new CheckBox();
            final ObjectListBoxWidget<ICategory> category = CategoryListBoxFactory.getInstance().getCategoryListBoxWidget(consoleModel);           
            final BYOBRichText text = new BYOBRichText(400);
            
            form.add(PostAdapter.titleId, BlogStrings.INSTANCE.divTitleDot(), title,0,0,1,1);
            form.add(PostAdapter.authorId, BlogStrings.INSTANCE.divAuthor(), author,1,0,1,1);
            form.add(PostAdapter.dateId, BlogStrings.INSTANCE.divDate(), date,2,0,1,1);
            form.add(PostAdapter.isprivate, BlogStrings.INSTANCE.isPrivate(), isprivate, 3, 0, 1, 1);
            form.add(PostAdapter.categoryId, BlogStrings.INSTANCE.divCategoriesDot(), category,0,1,4,1);
            form.add(PostAdapter.textId, text,4,0,1,2);
            addBtn = new Button(BlogStrings.INSTANCE.buttonAdd(), controler.getClickOnAddListener());
            modBtn = new Button(BlogStrings.INSTANCE.buttonModify(), controler.getClickOnModListener());
            delBtn = new Button(BlogStrings.INSTANCE.buttonDelete(), controler.getClickOnDelListener());
            title.setTabIndex(1);
            category.setTabIndex(2);
            text.setTabIndex(3);
            addBtn.setTabIndex(4);
            modBtn.setTabIndex(4);
            delBtn.setTabIndex(5);
            form.addButton(IFormWidget.ADD_BUTTON_ID, addBtn);
            form.addButton(IFormWidget.MOD_BUTTON_ID, modBtn);
            form.addButton(IFormWidget.DEL_BUTTON_ID, delBtn);
        }
        ToolbarWidget toolbar = new ToolbarWidget(controler, toolbarModel);
        final ObjectManagementToolbarPanel<IPost> view = new ObjectManagementToolbarPanel<IPost>(controler, table, form, console, toolbar,
                ObjectManagementToolbarPanel.BOTTOM,true);

        if (ConnectedUserModel.getInstance().getConnectedUserRole() == ConnectedUserModel.ROLE_USER) {
            formModel.addListener(form);
            consoleModel.addListener(console);
        }
        tableModel.addListener(table);
        toolbarModel.addListener(toolbar);
        ((Widget)view.getForm()).setWidth("100%");
       
        view.initView();
        return view;
    }
}
