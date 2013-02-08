package fr.byob.blog.client.view.widget.anim;

import com.google.gwt.user.client.ui.Widget;

public interface IShowPanel {
    public void show (int timer);    
    public void setToShow (final Widget w);
    public void setToHide (final Widget w);
    
}
