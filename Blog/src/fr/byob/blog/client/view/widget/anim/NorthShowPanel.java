package fr.byob.blog.client.view.widget.anim;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.util.Css;

public class NorthShowPanel extends Composite implements IShowPanel{
    
    private SimplePanel topPanel;
    private Widget botomWidget;
    private final int size;
    private final VerticalPanel container;
    
    public NorthShowPanel(final Widget toShow, final Widget toHide, int size) {
        this.size = size;
        this.botomWidget = toHide;
        this.topPanel = new SimplePanel();
        topPanel.addStyleName(Css.INSTANCE.hideOverflow());
        topPanel.setHeight("0px");
        topPanel.add(toShow);
                
        container = new VerticalPanel();
        container.add(topPanel);
        container.add(botomWidget);
        
        SimplePanel overflowPanel = new SimplePanel();
        overflowPanel.setHeight(size+"px");
        overflowPanel.setStyleName(Css.INSTANCE.hideOverflow());
        overflowPanel.add(container);
        
        initWidget(overflowPanel);
        
    }

    public void show (int timer){
        anim.run(timer);
    }
    
    public void setToShow (final Widget w){
        topPanel.clear();
        topPanel.add(w);
        topPanel.setHeight("0px");
    }
    
    public void setToHide (final Widget w){
        container.remove(botomWidget);
        botomWidget = w;
        container.add(botomWidget);
    }
    
    private final NorthAnimation anim = new NorthAnimation();

    public class NorthAnimation extends Animation {
        @Override
        protected void onComplete() {
            super.onComplete();
            topPanel.setHeight(size + "px");            
        }

        @Override
        protected void onStart() {
            super.onStart();
            topPanel.setHeight("0px");
        }

        @Override
        protected void onUpdate(double progress) {
            topPanel.setHeight(((int) (size * (progress))) + "px");
        }
    }

}
