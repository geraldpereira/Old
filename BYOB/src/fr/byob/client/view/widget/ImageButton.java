package fr.byob.client.view.widget;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;


public class ImageButton extends Composite /*, MouseListener*/{

	protected FocusMouseListener focusMouseListener;
	protected FocusPanel focusPanel;
	protected HorizontalPanel alignmentPanel;
	protected String mouseOverStyle;
	protected Widget displayed;
	private HandlerRegistration clickHandler;
	private HandlerRegistration wheelHandler;
	private HandlerRegistration mouseUpHandler;
	private HandlerRegistration mouseOutHandler;
	private HandlerRegistration mouseOverHandler;
	private HandlerRegistration mouseDownHandler;
	private HandlerRegistration mouseMoveHandler;
	
	
	public ImageButton() {		
		initView();
		// Centré par défaut
		alignmentPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		alignmentPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
	}
	
	public ImageButton(VerticalAlignmentConstant valign,HorizontalAlignmentConstant halign) {		
		initView();
		
		alignmentPanel.setVerticalAlignment(valign);
		alignmentPanel.setHorizontalAlignment(halign);
	}
	
	protected void initView(){
		focusMouseListener = new FocusMouseListener();
		alignmentPanel = new HorizontalPanel();
		alignmentPanel.setBorderWidth(0);
		alignmentPanel.setSpacing(0);
		alignmentPanel.setSize("100%","100%");
		
		focusPanel = new FocusPanel();
		focusPanel.add(alignmentPanel);
		initWidget(focusPanel);
	}

	public void setOnMouseOverStyleName (String style){
		this.mouseOverStyle = style;
		focusPanel.addMouseOverHandler(focusMouseListener);
		focusPanel.addMouseUpHandler(focusMouseListener);
		focusPanel.addMouseOutHandler(focusMouseListener);
	}

	public void addMouseOutListener(MouseOutHandler listener) {
	    if(mouseOutHandler == null){
	        mouseOutHandler = focusPanel.addMouseOutHandler(listener);
	    }
	    focusPanel.addMouseOutHandler(listener);
	}
	public void addMouseOverListener(MouseOverHandler listener) {
	    if(mouseOverHandler == null){
            mouseOverHandler = focusPanel.addMouseOverHandler(listener);
	    }
	    focusPanel.addMouseOverHandler(listener);
    }
	public void addMouseUpListener(MouseUpHandler listener) {
	    if(mouseUpHandler == null){
            mouseUpHandler = focusPanel.addMouseUpHandler(listener);
	    }
	    focusPanel.addMouseUpHandler(listener);
    }
	public void addMouseDownListener(MouseDownHandler listener) {
	    if(mouseDownHandler == null){
            mouseDownHandler = focusPanel.addMouseDownHandler(listener);
	    }
	    focusPanel.addMouseDownHandler(listener);
    }
	public void addMouseMoveListener(MouseMoveHandler listener) {
	    if(mouseMoveHandler == null){
            mouseMoveHandler = focusPanel.addMouseMoveHandler(listener);
	    }
	    focusPanel.addMouseMoveHandler(listener);
    }
	public void removeMouseOverListener() {
		mouseOverHandler.removeHandler();
	}
	public void removeMouseOutListener() {
        mouseOutHandler.removeHandler();
    }
	public void removeMouseDownListener() {
        mouseDownHandler.removeHandler();
    }
	public void removeMouseUpListener() {
        mouseUpHandler.removeHandler();
    }
	public void removeMouseMoveListener() {
        mouseMoveHandler.removeHandler();
    }

	public void addClickHandler(ClickHandler listener) {
	    if(clickHandler == null){
	        clickHandler = focusPanel.addClickHandler(listener);
	    }
	    focusPanel.addClickHandler(listener);
	}

	public void removeClickHandler() {
	    clickHandler.removeHandler();
	}

	public void addMouseWheelListener(MouseWheelHandler listener) {
	    if(wheelHandler == null){
	        wheelHandler = focusPanel.addMouseWheelHandler(listener);
	    }
	    focusPanel.addMouseWheelHandler(listener);
	}

	public void removeMouseWheelListener() {
	    wheelHandler.removeHandler();
	}

	
	public void setWidget(Widget widget){
		if (displayed != null){
			alignmentPanel.remove(displayed);
		}
		alignmentPanel.add(widget);
		displayed = widget;
		focusPanel.setFocus(false);
	}
	
	/**
	 * A utiliser lors des catch d'evenement : le sender n'est pas le ImageButton mais notre focus
	 * @return
	 */
	public Widget getSender() {
		return focusPanel;
	}
	
	protected class FocusMouseListener implements MouseOverHandler,MouseOutHandler,MouseUpHandler{

        public void onMouseOver(MouseOverEvent event) {
            alignmentPanel.addStyleName(mouseOverStyle);
        }

        public void onMouseOut(MouseOutEvent event) {
            alignmentPanel.removeStyleName(mouseOverStyle);
        }

        public void onMouseUp(MouseUpEvent event) {
            alignmentPanel.removeStyleName(mouseOverStyle);
        }
	}
}
