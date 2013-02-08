package fr.byob.blog.client.view.panel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import fr.byob.blog.client.BYOBBlog;
import fr.byob.client.util.Css;
import fr.byob.client.view.widget.ImageButton;

public class ColorSelectionPanel extends Composite {

    private String currentStyleName = BYOBBlog.DEFAULT_THEME_STYLE;

    private static ColorSelectionPanel instance = new ColorSelectionPanel();
    
    public static ColorSelectionPanel getInstance(){
        return instance;
    }
    
    private ColorSelectionPanel() {
        HorizontalPanel mainPanel = new HorizontalPanel();
        ImageButton colorBlue = colorButtonFactory(Css.INSTANCE.backgroundColorBlue(),new ColorClickHandler(BYOBBlog.DEFAULT_THEME_STYLE));
        ImageButton colorGrey = colorButtonFactory(Css.INSTANCE.backgroundColorGrey(),new ColorClickHandler(BYOBBlog.GRIS_THEME_STYLE));
        ImageButton colorGris = colorButtonFactory(Css.INSTANCE.backgroundColorGris(),new ColorClickHandler(BYOBBlog.FONCE_THEME_STYLE));
        ImageButton colorWhite = colorButtonFactory(Css.INSTANCE.backgroundColorWhite(),new ColorClickHandler(BYOBBlog.BLANC_THEME_STYLE));
        Label filler = new Label();
        filler.setWidth("40px");
        Label filler2 = new Label();
        filler2.setWidth("8px");
        Label filler3 = new Label();
        filler3.setWidth("8px");
        Label filler4 = new Label();
        filler4.setWidth("8px");
        mainPanel.add(filler);
        mainPanel.add(colorBlue);
        mainPanel.add(filler2);
        mainPanel.add(colorGrey);
        mainPanel.add(filler3);
        mainPanel.add(colorGris);
        mainPanel.add(filler4);
        mainPanel.add(colorWhite);
        mainPanel.setSpacing(3);
        initWidget(mainPanel);
    }

    private ImageButton colorButtonFactory(String style, ClickHandler listener) {
        ImageButton button = new ImageButton();
        button.addClickHandler(listener);
        button.setStyleName(style);
        button.setSize("18px", "18px");
        return button;
    }

    private class ColorClickHandler implements ClickHandler {
        private final String styleName;
        
        public ColorClickHandler (String styleName){
            this.styleName = styleName;
        }

        public void onClick(ClickEvent event) {
            BYOBBlog.getRootPanel().removeStyleName(currentStyleName);
            currentStyleName = styleName;
            BYOBBlog.getRootPanel().addStyleName(currentStyleName);
        }
    }
    public void changeColor(String styleName){
        BYOBBlog.getRootPanel().removeStyleName(currentStyleName);
        currentStyleName = styleName;
        BYOBBlog.getRootPanel().addStyleName(currentStyleName);
    }
    public String getCurrentStyle(){
        return currentStyleName;
    }
}
