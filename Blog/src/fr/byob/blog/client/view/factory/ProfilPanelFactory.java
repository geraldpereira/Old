package fr.byob.blog.client.view.factory;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.IProfil;
import fr.byob.blog.client.proxy.ProfilServiceProxy;
import fr.byob.blog.client.view.adapter.ProfilAdapter;
import fr.byob.blog.client.view.panel.ProfilPanel;
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

public class ProfilPanelFactory implements IDeckPageContentFactoriesList{

    private static ProfilPanelFactory panel = new ProfilPanelFactory();

    private final IIndexSelectionModel deckPageModel = new IndexSelectionModel(0);
    
    private ProfilPanelFactory() {
    }

    public static ProfilPanelFactory getInstance() {
        return panel;
    }

    public List<IPageContentFactory> getPageContentFactoriesList() {
        ArrayList<IPageContentFactory> factories = new ArrayList<IPageContentFactory>();
        
        final ITableModel<IProfil> tableModel = new TableModel<IProfil>(0);
        
        factories.add(new IPageContentFactory(){
            public Widget createPageContent() {
                final ProfilAdapter adapter = new ProfilAdapter();

                
                final IToolbarModel toolbarModel = new ToolbarModel(10);
                final IConsoleModel consoleModel = new ConsoleModel();

                IObjectToolbarManagementServiceProxy<IProfil> service = ProfilServiceProxy.getInstance();
                ObjectManagementControler<IProfil> controler = new ObjectManagementControler<IProfil>(service);
                controler.setTableModel(tableModel);
                controler.setConsoleModel(consoleModel);
                controler.setToolbarModel(toolbarModel);

                TableWidget<IProfil> table = null;
                table = new TableWidget<IProfil>(tableModel, adapter, false,true);
                table.addListener(controler.getClickOnTableListener());
                table.addListener(new SubPageSelectionControler(deckPageModel).getSubPageSelectionControler(1));

                ConsoleWidget console = new ConsoleWidget(consoleModel, adapter);
                ToolbarWidget toolbar = new ToolbarWidget(controler, toolbarModel);

                tableModel.addListener(table);
                consoleModel.addListener(console);
                toolbarModel.addListener(toolbar);
                final ObjectManagementToolbarPanel<IProfil> view = new ObjectManagementToolbarPanel<IProfil>(controler, table, null, console, toolbar,
                        ObjectManagementToolbarPanel.BOTTOM, true);

                view.initView();
                
                return view;
            }
            
        });    
        factories.add(new IPageContentFactory(){
            public Widget createPageContent() {
                return new ProfilPanel(tableModel);
            }
        });  
        
        return factories;
    }

    public IIndexSelectionModel getSelectionModel() {
        return deckPageModel;
    }

}
