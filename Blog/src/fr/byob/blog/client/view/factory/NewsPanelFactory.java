package fr.byob.blog.client.view.factory;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.IPost;
import fr.byob.blog.client.proxy.NewsServiceProxy;
import fr.byob.blog.client.view.adapter.NewsAdapter;
import fr.byob.blog.client.view.panel.PostPanel;
import fr.byob.client.application.model.IIndexSelectionModel;
import fr.byob.client.application.model.IndexSelectionModel;
import fr.byob.client.application.page.controler.SubPageSelectionControler;
import fr.byob.client.application.page.model.IDeckPageContentFactoriesList;
import fr.byob.client.application.page.model.IPageContentFactory;
import fr.byob.client.om.controler.ObjectManagementControler;
import fr.byob.client.om.service.IObjectToolbarManagementServiceProxy;
import fr.byob.client.om.view.ObjectManagementToolbarPanel;
import fr.byob.client.view.model.ConsoleModel;
import fr.byob.client.view.model.IConsoleModel;
import fr.byob.client.view.model.ITableModel;
import fr.byob.client.view.model.IToolbarModel;
import fr.byob.client.view.model.TableModel;
import fr.byob.client.view.model.ToolbarModel;
import fr.byob.client.view.widget.ConsoleWidget;
import fr.byob.client.view.widget.TableWidget;
import fr.byob.client.view.widget.toolbar.ToolbarWidget;

public class NewsPanelFactory implements IDeckPageContentFactoriesList{
    private static NewsPanelFactory panel = new NewsPanelFactory();

    private final IndexSelectionModel lastNewsModel = new IndexSelectionModel(0);
    
    private NewsPanelFactory() {
       
    }

    public static NewsPanelFactory getInstance() {
        return panel;
    }


    public List<IPageContentFactory> getPageContentFactoriesList() {
        ArrayList<IPageContentFactory> factories = new ArrayList<IPageContentFactory>();
        final ITableModel<IPost> tableModel = new TableModel<IPost>(0);
        
        factories.add(new IPageContentFactory(){
            public Widget createPageContent() {
                NewsAdapter adapter = new NewsAdapter();
                
                final IToolbarModel toolbarModel = new ToolbarModel(5, 10);
                final IConsoleModel consoleModel = new ConsoleModel();
                IObjectToolbarManagementServiceProxy<IPost> service = NewsServiceProxy.getInstance();
                ObjectManagementControler<IPost> controler = new ObjectManagementControler<IPost>(service);
                controler.setTableModel(tableModel);
                controler.setConsoleModel(consoleModel);
                controler.setToolbarModel(toolbarModel);

                final TableWidget<IPost> table = new TableWidget<IPost>(tableModel, adapter, false, true);
                table.addListener(controler.getClickOnTableListener());
                table.addListener(new SubPageSelectionControler(lastNewsModel).getSubPageSelectionControler(1));

                ToolbarWidget toolbar = new ToolbarWidget(controler, toolbarModel);

                ConsoleWidget console = new ConsoleWidget(consoleModel, adapter);

                final ObjectManagementToolbarPanel<IPost> view = new ObjectManagementToolbarPanel<IPost>(controler, table, null, console, toolbar,
                        ObjectManagementToolbarPanel.BOTTOM, true);
                consoleModel.addListener(console);

                tableModel.addListener(table);
                toolbarModel.addListener(toolbar);
                view.initView();
                return view;
            }
            
        });
        
        factories.add(new IPageContentFactory(){

            public Widget createPageContent() {
                PostPanel postPanel = new PostPanel(tableModel);
                return postPanel;
            }
        });
        
        return factories;
        
    }

    public IIndexSelectionModel getSelectionModel() {
        return lastNewsModel;
    }


}
