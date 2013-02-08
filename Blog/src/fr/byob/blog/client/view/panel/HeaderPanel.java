package fr.byob.blog.client.view.panel;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

import fr.byob.blog.client.BYOBBlog;
import fr.byob.blog.client.Constants;
import fr.byob.blog.client.model.BriefGWT;
import fr.byob.blog.client.view.image.BlogImages;
import fr.byob.blog.client.view.widget.BriefWidget;
import fr.byob.client.application.controler.IPageSelectionControler;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;
import fr.byob.client.view.panel.RoundedPanel;

/**
 * D�crit le Panel nord 
 * 
 * @author Kojir0
 */
public class HeaderPanel extends Composite /*implements IConnectedUserListener*/{
	
    private final HorizontalPanel horizontal ;
    private BriefWidget briefWidget;
    
	/**
	 * Constructor
	 * @param centerLabelText le texte de titre
	 * @param isUserConnected utilisateur connecté ou non
	 */	
	public HeaderPanel (){
		horizontal = new HorizontalPanel();
		RoundedPanel rp = new RoundedPanel(horizontal,RoundedPanel.TOPBOTTOMRIGHT,5,Css.INSTANCE.cbgRPBorderSite());
		rp.setHeight("100%");
		initWidget(rp);
	}
	/**
	 * Initialise la vue
	 */
	protected void initView (final String centerLabelText,IPageSelectionControler pageControler){
	    
	    if( ! ColorSelectionPanel.getInstance().getCurrentStyle().equals(BYOBBlog.BLANC_THEME_STYLE)){
	        Image BYOBLogo = BlogImages.INSTANCE.BYOBLogo().createImage();
	        BYOBLogo.addStyleName(Css.INSTANCE.borderTitle());
	        BYOBLogo.addStyleName(Css.INSTANCE.cursor());
	        BYOBLogo.setHeight("81px");
	        BYOBLogo.setWidth("180px");
	        BYOBLogo.addClickHandler(new ClickHandler(){
	            public void onClick(ClickEvent event) {
	                Utils.redirect(Constants.HOME_PAGE);
	            }
	        });
	        horizontal.add(BYOBLogo);
	        horizontal.setCellWidth(BYOBLogo, "180px");
	        horizontal.setCellHeight(BYOBLogo, "81px");
	    }
		HTML title = new HTML(Utils.getLinkHTML(Constants.HOME_PAGE, centerLabelText));
		title.addStyleName(Css.INSTANCE.aTitle());
		title.addStyleName(Css.INSTANCE.ghgrn());
		title.addStyleName(Css.INSTANCE.borderSite());
		title.setHeight("81px");
		
		briefWidget = new BriefWidget(pageControler);
		
		// Add them		
		
		horizontal.add(title);
		
		horizontal.add(briefWidget);
		horizontal.setCellHorizontalAlignment(briefWidget, HasHorizontalAlignment.ALIGN_RIGHT);
				
		// Init the panel properties
		horizontal.setBorderWidth(0);
		horizontal.setSpacing(0);
		horizontal.setWidth("100%");
		horizontal.addStyleName(Css.INSTANCE.borderTitle());
		
		horizontal.setCellHorizontalAlignment(title, HasHorizontalAlignment.ALIGN_LEFT);
	}
	
	public void setBriefs(List<BriefGWT> briefs){
	    if(briefs.size() != 0){
    	    briefWidget.clearWidget();
    	    for(BriefGWT brief : briefs){
    	        briefWidget.setDatas(brief);
    	    }
	    }
	}
}
