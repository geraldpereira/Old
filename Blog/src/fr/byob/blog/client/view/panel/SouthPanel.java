package fr.byob.blog.client.view.panel;

import java.util.Date;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;

import fr.byob.blog.client.Constants;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;

/**
 * Décrit le Panel sud
 * 
 * @author Kojir0
 */
public class SouthPanel extends Composite {
    private Date date;
	/**
	 * Constructor
	 */
	public SouthPanel (){
	    if(date == null){
	        date = new Date();
	    }
		initView();
	}
	/**
	 * Initialise la vue
	 */
	protected void initView(){

		HorizontalPanel southPanel = new HorizontalPanel();
		ColorSelectionPanel color = ColorSelectionPanel.getInstance();
		Label createBy = new Label(BlogStrings.INSTANCE.southCreateBy());
		HTML mailTo = new HTML(Utils.getLinkHTML("mailto:kojiro.sazaki@gmail.com", BlogStrings.INSTANCE.southContact()));
		createBy.addStyleName(Css.INSTANCE.msgdate());
		mailTo.addStyleName(Css.INSTANCE.msgdate());
		HTML help = new HTML(Utils.getLinkHTMLNewPage(Constants.AIDE_PAGE, BlogStrings.INSTANCE.southHelp()));
		help.addStyleName(Css.INSTANCE.msghelp());
		southPanel.add(color);
		southPanel.add(createBy);
		southPanel.add(help);
		southPanel.add(mailTo);
		southPanel.setCellHorizontalAlignment(color, HasHorizontalAlignment.ALIGN_LEFT);
		southPanel.setCellWidth(color, "25%");

		southPanel.setCellHorizontalAlignment(createBy, HasHorizontalAlignment.ALIGN_CENTER);
		southPanel.setCellWidth(createBy, "25%");
		
		southPanel.setCellHorizontalAlignment(help, HasHorizontalAlignment.ALIGN_CENTER);
		southPanel.setCellWidth(help, "25%");
		
		southPanel.setCellHorizontalAlignment(mailTo, HasHorizontalAlignment.ALIGN_CENTER);
		southPanel.setCellWidth(mailTo, "25%");

		
		// Init the panel properties
		southPanel.setBorderWidth(0);
		southPanel.setSpacing(0);
		southPanel.setWidth("99%");

		initWidget(southPanel);
	}
}
