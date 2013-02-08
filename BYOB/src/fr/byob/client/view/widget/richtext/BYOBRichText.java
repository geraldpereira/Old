package fr.byob.client.view.widget.richtext;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;

import fr.byob.client.util.Css;
import fr.byob.client.view.panel.RoundedPanel;

public class BYOBRichText extends Composite{

    BYOBRichTextArea area;
	BYOBRichTextToolbar toolbar;
	
	/**
	 * @param height hauteur en nombre de lignes
	 */
	public BYOBRichText (int height){
        
        initView(height);
    }
	
	public BYOBRichText (){
		
		initView(200);
	}
	
	private void initView(int height){
	    Css css = Css.INSTANCE;
		// Create the text area and toolbar
	    area = new BYOBRichTextArea();
	    area.setSize("100%", height+"px");
	    toolbar = new BYOBRichTextToolbar(area);
	    toolbar.setWidth("100%");
	    RoundedPanel rpanel = new RoundedPanel(toolbar,css.BYOBRichTextToolbarBorder());
	    area.setHTML("");

	    // Add the components to a panel
	    Grid grid = new Grid(2, 1);
	    grid.setStyleName(css.BYOBRichText());
	    grid.setWidget(0, 0, rpanel);
	    grid.setWidget(1, 0, area);
	    grid.setWidth("100%");
	    initWidget(grid);	    
	}
	public String getText(){
	    if(area.getFullHTML()){
	        return area.getText();
	    }
		return area.getHTML();
	}
	public void setText(String text){
		area.setHTML(text);
	}

	public void setEnabled(boolean isEnabled) {
		area.setEnabled(isEnabled);
		toolbar.setEnabled(isEnabled);
	}
	public void setTabIndex(int index){
	    area.setTabIndex(index);
	}
}
