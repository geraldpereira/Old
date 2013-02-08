package fr.byob.client.view.widget;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.PasswordTextBox;

import fr.byob.client.util.Css;

public class BYOBPasswordTextBox extends PasswordTextBox{

    public BYOBPasswordTextBox() {
        super();
        addFocusHandler(new FocusHandler(){
            public void onFocus(FocusEvent event) {
                addStyleName(Css.INSTANCE.textboxfocus());
            }
        });
        addBlurHandler(new BlurHandler(){
            public void onBlur(BlurEvent event) {
                removeStyleName(Css.INSTANCE.textboxfocus());
            }
        });
    }
    
}
