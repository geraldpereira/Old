package fr.byob.client.view.controler;

import com.google.gwt.event.dom.client.ClickHandler;

import fr.byob.client.view.model.ITableModel;


public interface ITableControler<T> {


	public ClickHandler getClickOnTableListener() ;
	public void setTableModel (ITableModel<T> tableModel);
}
