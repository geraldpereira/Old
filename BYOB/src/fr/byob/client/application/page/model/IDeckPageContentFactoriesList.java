package fr.byob.client.application.page.model;

import java.util.List;

import fr.byob.client.application.model.IIndexSelectionModel;

public interface IDeckPageContentFactoriesList {
    
    public List<IPageContentFactory> getPageContentFactoriesList();
    
    public IIndexSelectionModel getSelectionModel();
}
