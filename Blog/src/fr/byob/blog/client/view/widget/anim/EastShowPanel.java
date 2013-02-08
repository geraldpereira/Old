package fr.byob.blog.client.view.widget.anim;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.util.Css;

public class EastShowPanel extends Composite implements IShowPanel{
    
    private SimplePanel leftPanel;
    private Widget rightWidget;
    private final int size;
    private final HorizontalPanel container;

    public EastShowPanel(final Widget toShow, final Widget toHide, int size) {
        this.size = size;
        this.rightWidget = toShow;
        leftPanel = new SimplePanel();
        leftPanel.setWidth(size+"px");
        leftPanel.setStyleName(Css.INSTANCE.hideOverflow());
        leftPanel.add(toHide);
        
        container = new HorizontalPanel();
        container.add(leftPanel);
        container.add(rightWidget);
        
        SimplePanel overflowPanel = new SimplePanel();
        overflowPanel.setWidth(size+"px");
        overflowPanel.setStyleName(Css.INSTANCE.hideOverflow());
        overflowPanel.add(container);
        
        initWidget(overflowPanel);
    }

    public void show (int timer){
        anim.run(timer);
    }
    
    public void setToShow (final Widget w){
        container.remove(rightWidget);
        rightWidget = w;
        container.add(rightWidget);
    }
    
    public void setToHide (final Widget w){
        leftPanel.clear();
        leftPanel.add(w);
        leftPanel.setWidth(size + "px");
    }
    
    private final EastAnimation anim = new EastAnimation();

    public class EastAnimation extends Animation {
        @Override
        protected void onComplete() {
            super.onComplete();
            leftPanel.setWidth("0px");
        }

        @Override
        protected void onStart() {
            super.onStart();
            leftPanel.setWidth(size + "px");
        }

        @Override
        protected void onUpdate(double progress) {
            leftPanel.setWidth(((int) (size * (1 - progress))) + "px");
        }

    }

}
