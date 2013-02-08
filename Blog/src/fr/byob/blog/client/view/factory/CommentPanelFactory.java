package fr.byob.blog.client.view.factory;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.IComment;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.proxy.CommentServiceProxy;
import fr.byob.blog.client.view.adapter.CommentAdapter;
import fr.byob.blog.client.view.util.BlogStrings;
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
import fr.byob.client.view.widget.SimpleFormWidget;
import fr.byob.client.view.widget.TableWidget;
import fr.byob.client.view.widget.richtext.BYOBRichText;
import fr.byob.client.view.widget.toolbar.ToolbarWidget;

public class CommentPanelFactory {

    private static CommentPanelFactory panel = new CommentPanelFactory();
    private CommentPanelFactory() {
    }

    public static CommentPanelFactory getInstance(){
        return panel;
    }
    public ObjectManagementToolbarPanel<IComment> getCommentPanel() {

        int role = ConnectedUserModel.getInstance().getConnectedUserRole();
        
        final CommentAdapter adapter = new CommentAdapter();

        final ITableModel<IComment> tableModel = new TableModel<IComment>(0);
        final IToolbarModel toolbarModel = new ToolbarModel(5);
        final IFormModel<IComment> formModel = new FormModel<IComment>();
        final IConsoleModel consoleModel = new ConsoleModel();

        IObjectToolbarManagementServiceProxy<IComment> service = CommentServiceProxy.getInstance();
        ObjectManagementControler<IComment> controler = new ObjectManagementControler<IComment>(service);
        controler.setTableModel(tableModel);
        controler.setFormModel(formModel);
        controler.setConsoleModel(consoleModel);
        controler.setToolbarModel(toolbarModel);
        

        final TableWidget<IComment> table; 
        if (role == ConnectedUserModel.ROLE_USER) {
            table = new TableWidget<IComment>(tableModel, adapter,  true,true);
            table.addListener(controler.getClickOnTableListener());
        }else{
            table = new TableWidget<IComment>(tableModel, adapter,  false,true);
        }
        table.setSize("100%", "100%");
        final SimpleFormWidget<IComment> form = new SimpleFormWidget<IComment>(formModel, adapter);
        final BYOBTextBox mail = new BYOBTextBox();
        final BYOBTextBox author = new BYOBTextBox();
        final BYOBTextBox date = new BYOBTextBox();
        final BYOBRichText text = new BYOBRichText();
        form.add(CommentAdapter.authorId, BlogStrings.INSTANCE.divAuthor(), author);
        form.add(CommentAdapter.mailId, BlogStrings.INSTANCE.divMail(), mail);
        form.add(CommentAdapter.dateId, BlogStrings.INSTANCE.divDate(), date);
        form.add(CommentAdapter.textId, text);
        

        final Button delBtn = new Button(BlogStrings.INSTANCE.buttonDelete(), controler.getClickOnDelListener());
        final Button addBtn = new Button(BlogStrings.INSTANCE.buttonAdd(), controler.getClickOnAddListener());
        form.addButton(SimpleFormWidget.ADD_BUTTON_ID,addBtn);
        if (role == ConnectedUserModel.ROLE_USER) {
            form.addButton(SimpleFormWidget.DEL_BUTTON_ID,delBtn);
        }
        author.setTabIndex(1);
        mail.setTabIndex(2);
        text.setTabIndex(3);
        addBtn.setTabIndex(4);
        delBtn.setTabIndex(4);
        ToolbarWidget toolbar = new ToolbarWidget(controler, toolbarModel);
        toolbar.setSize("100%", "100%");
        ConsoleWidget console = new ConsoleWidget(consoleModel, adapter);
        
        final ObjectManagementToolbarPanel<IComment> view = new ObjectManagementToolbarPanel<IComment>(controler, table, form, console, toolbar,
                ObjectManagementToolbarPanel.BOTTOM,true);
        
        formModel.addListener(form);
        consoleModel.addListener(console);
        tableModel.addListener(table);
        toolbarModel.addListener(toolbar);
        view.getToolbar().setWidth("100%");
        view.getTable().setWidth("100%");
        ((Widget)view.getForm()).setWidth("100%");
        view.initView();
        return view;
    }

}
