package fr.byob.client.view.widget;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FormPanel;

public class BYOBFormPanel extends FormPanel {

    public void reset() {
        //form, iframe
//        reset(getElement(), DOM.getFirstChild(getElement()));
        resetForm(getElement()); 
    }
    private native void resetForm(Element form)/*-{
    form.reset();
    }-*/;

    /**
     *      Reset a form, this code is copy from FormPanelImpl of google
     *
     * @param form the form to be submitted
     * @param iframe the iframe that is targetted, or <code>null</code>
     */
    private native void reset(Element form, Element iframe) /*-{
        // Hang on to the form's action url, needed in the
        // onload/onreadystatechange handler.
        if (iframe)
          iframe.__formAction = form.action;
        form.reset();
    }-*/; 
}
