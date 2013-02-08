package fr.byob.client.view.controler;

import com.google.gwt.event.dom.client.ClickHandler;

import fr.byob.client.view.model.IFormModel;


public interface IFormControler<T> {

public ClickHandler getClickOnAddListener();
	
	public ClickHandler getClickOnModListener();

	public ClickHandler getClickOnDelListener() ;
	
	public void setFormModel (IFormModel<T> formModel);
}
