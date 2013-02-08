package fr.byob.blog.client.view.factory;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.IPost;
import fr.byob.blog.client.view.panel.ArchivesPanel;
import fr.byob.blog.client.view.panel.PostPanel;
import fr.byob.client.application.model.IIndexSelectionModel;
import fr.byob.client.application.model.IndexSelectionModel;
import fr.byob.client.application.page.model.IDeckPageContentFactoriesList;
import fr.byob.client.application.page.model.IPageContentFactory;
import fr.byob.client.view.model.ITableModel;
import fr.byob.client.view.model.TableModel;

public class ArchivePanelFactory implements IDeckPageContentFactoriesList{
    private static final ArchivePanelFactory panel = new ArchivePanelFactory();
    
    private final IIndexSelectionModel deckPageModel = new IndexSelectionModel(0);
    
    private ArchivePanelFactory() {
    }

    public static ArchivePanelFactory getInstance(){
        return panel;
    }
    
    public List<IPageContentFactory> getPageContentFactoriesList() {
        ArrayList<IPageContentFactory> factories = new ArrayList<IPageContentFactory>();
        
        final ITableModel<IPost> tableModel = new TableModel<IPost>(0);
        
        
        factories.add(new IPageContentFactory(){
            public Widget createPageContent() {
                return new ArchivesPanel(deckPageModel,tableModel);
            }
            
        });    
        factories.add(new IPageContentFactory(){
            public Widget createPageContent() {
                return new PostPanel(tableModel);
            }
        });  
        
        return factories;
    }

    public IIndexSelectionModel getSelectionModel() {
        return deckPageModel;
    }
}
