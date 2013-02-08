package fr.byob.client.view.controler;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.util.Css;

public class CellControler implements MouseOutHandler,MouseOverHandler{

    public void onMouseOut(MouseOutEvent event) {
       ((Widget) (event.getSource())).removeStyleName(Css.INSTANCE.overMouseTable());
    }

    public void onMouseOver(MouseOverEvent event) {
        ((Widget) (event.getSource())).addStyleName(Css.INSTANCE.overMouseTable());
    }

}
