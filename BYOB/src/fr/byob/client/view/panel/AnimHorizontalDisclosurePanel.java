package fr.byob.client.view.panel;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.util.Css;
import fr.byob.client.view.widget.ImageButton;

public class AnimHorizontalDisclosurePanel extends Composite {

	private int disclosurePosition;
	public final static int RIGHT = 1;
	public final static int LEFT = 2;

	private final SimplePanel disclosed;
	private boolean visible;
	private int disclosureSize;
	private int animTimer;
	private final ImageButton discloseBar;

    private final OpenLeft openLeft = new OpenLeft();
    private final OpenRight openright = new OpenRight();
    private final CloseLeft closeLeft = new CloseLeft();
    private final CloseRight closeRight = new CloseRight();
	
	private final Image left = HorizontalDisclosureImages.INSTANCE.left().createImage();
	private final Image right = HorizontalDisclosureImages.INSTANCE.right().createImage();
	
	/**
	 * Constructeur
	 * @param center Widget au centre de la page (celui qui ne sera pas disclosed en somme)
	 * @param wdisclosed Widget a afficher ou masquer
	 * @param disclosurePosition LEFT ou RIGHT selon où l'on veut le disclosure par rapport au center
	 * @param disclosureSize Largeur du panel a afficher / masquer
	 * @param minHeight Hauteur minimale de tout le panel 
	 * @param disclosedVisible wdisclosed affiché ou non par défaut ?
	 * @param animTimer Durée de l'animation d'ouverture / fermeture en ms (0 pour pas d'animation)
	 */
	public AnimHorizontalDisclosurePanel(final Widget center, final Widget wdisclosed,
			final int disclosurePosition, final int disclosureSize, final int minHeight,
			final boolean disclosedVisible, final int animTimer) {
		this.visible = disclosedVisible;
		this.disclosureSize = disclosureSize;
		this.animTimer = animTimer;
		this.disclosurePosition = disclosurePosition;
		HorizontalPanel disclosurePanel = new HorizontalPanel();
		disclosurePanel.setSize(disclosureSize+"px", "99%");
		disclosurePanel.setBorderWidth(0);
		disclosurePanel.setSpacing(0);

		disclosed = new SimplePanel();
		disclosed.addStyleName(Css.INSTANCE.hideOverflow());
        disclosed.add(wdisclosed);
        if (disclosedVisible){
            this.disclosed.setWidth(disclosureSize+"px");
        }else{
            this.disclosed.setWidth("0px");    
        }
		
		discloseBar = new ImageButton();
		discloseBar.setSize("8px",minHeight+"px");
		discloseBar.setStyleName(Css.INSTANCE.HorizontalDisclosurePanel());
		discloseBar.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                manageClick();
            }
		});
		discloseBar.setOnMouseOverStyleName(Css.INSTANCE.HorizontalDisclosurePanelMouseOver());
		
//		Label filler = new Label();
//		filler.setSize("8px", minHeight+"px");
		
		if (disclosurePosition == LEFT) {
			disclosurePanel.add(this.disclosed);
//			disclosurePanel.add(filler);
			disclosurePanel.add(discloseBar);
			disclosurePanel.add(center);
		} else {
			disclosurePanel.add(center);
			disclosurePanel.add(discloseBar);
//			disclosurePanel.add(filler);
			disclosurePanel.add(this.disclosed);
		}
		
		disclosurePanel.setCellWidth(center, "98%");
		disclosurePanel.setCellWidth(this.disclosed, disclosureSize+"px");
        disclosurePanel.setCellVerticalAlignment( this.discloseBar, HasVerticalAlignment.ALIGN_TOP);
		initWidget(disclosurePanel);

		if (disclosurePosition == LEFT && disclosedVisible == true) {
            discloseBar.setWidget(left);
        } else if (disclosurePosition == RIGHT && disclosedVisible == true) {
            discloseBar.setWidget(right);
        } else if (disclosurePosition == LEFT && disclosedVisible == false) {
            discloseBar.setWidget(right);
        } else if (disclosurePosition == RIGHT && disclosedVisible == false) {
            discloseBar.setWidget(left);
        }

	}

	private final void manageClick(){
		if (disclosurePosition == LEFT && visible == true) {
		    closeLeft.run(animTimer);
		} else if (disclosurePosition == RIGHT && visible == true) {
		    closeRight.run(animTimer);
		} else if (disclosurePosition == LEFT && visible == false) {
		    openLeft.run(animTimer);
		} else if (disclosurePosition == RIGHT && visible == false) {
		    openright.run(animTimer);
		}
	}

	public class Open extends Animation {
        @Override
        protected void onComplete() {
            super.onComplete();
            disclosed.setWidth(disclosureSize+"px");
            visible = true;
        }

        @Override
        protected void onStart() {
            super.onStart();
            disclosed.setWidth("0px");
           
        }

        @Override
        protected void onUpdate(double progress) {
            disclosed.setWidth(((int) (disclosureSize * (progress))) + "px");
        }
    }
	
	public class OpenRight extends Open{
	    @Override
        protected void onStart() {
            super.onStart();
            discloseBar.setWidget(right);
        }
	}
	
	public class OpenLeft extends Open{
        @Override
        protected void onStart() {
            super.onStart();
            discloseBar.setWidget(left);
        }
    }
	
	public class Close extends Animation {
        @Override
        protected void onComplete() {
            super.onComplete();
            disclosed.setWidth("0px");
            visible = false;
        }

        @Override
        protected void onStart() {
            super.onStart();
            disclosed.setWidth(disclosureSize+"px");       
        }

        @Override
        protected void onUpdate(double progress) {
            disclosed.setWidth(((int) (disclosureSize * ( 1 - progress))) + "px");
        }
    }
	
	public class CloseRight extends Close{
        @Override
        protected void onStart() {
            super.onStart();
            discloseBar.setWidget(left);
        }
    }
    
    public class CloseLeft extends Close{
        @Override
        protected void onStart() {
            super.onStart();
            discloseBar.setWidget(right);
        }
    }
}
