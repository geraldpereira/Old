package fr.byob.client.view.widget;

import com.google.gwt.user.client.ui.Widget;

public interface IFormWidget<T> {
    public final static int ADD_BUTTON_ID = 0;
    public final static int MOD_BUTTON_ID = 1;
    public final static int DEL_BUTTON_ID = 2;
    
    void setTextElement(Integer labelId, String string);

    void setEnabled(Integer labelId, boolean b);

    String getTextElement(Integer labelId);
    
    public Widget getElement(Integer key);

    boolean isButtonPresent(Integer modButtonId);

    T getNewObject();
    
    T getModifiedObject(T t);
    
    public void setVisible(boolean isVisible);

}
