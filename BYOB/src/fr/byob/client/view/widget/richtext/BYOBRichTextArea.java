package fr.byob.client.view.widget.richtext;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.RichTextArea;

public class BYOBRichTextArea extends RichTextArea{

    private boolean fullHTML = false;
    
    public BYOBRichTextArea() {
        super();
        addFocusHandler(new FocusHandler(){
            public void onFocus(FocusEvent event) {
                setStyleName("textboxfocus");
            }
        });
        addBlurHandler(new BlurHandler(){
            public void onBlur(BlurEvent event) {
                removeStyleName("textboxfocus");
            }
        });
    }   
    
    public void addBalise(String text){
        super.setHTML(super.getHTML()+text);
    }
    public void setFullHTML(boolean fullHTML){
        this.fullHTML =fullHTML;
    }
    public boolean getFullHTML(){
        return this.fullHTML ;
    }
    //    private static final String STYLE_ATTRIBUTE = "font:normal 12px verdana,tahoma,arial,helvetica,sans-serif;";

    //    @Override
    //    public void setHTML(String html) {
    //        super.setHTML("<SPAN STYLE=\"" + STYLE_ATTRIBUTE + 
    //                 "\">" + html + 
    //                "</SPAN>");
    //
    //    }
}
