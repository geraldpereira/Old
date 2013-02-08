package fr.byob.client.view.widget;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.view.panel.RoundedPanel;

@Deprecated
public class RoundedImageButton extends ImageButton {

	private RoundedPanel rounded;
	private SimplePanel styledPanel;

	public RoundedImageButton(int corners, int cornerHeight) {
		focusMouseListener = new RoundedFocusMouseListener();
		rounded = new RoundedPanel(corners, cornerHeight, "");
		focusPanel.remove(alignmentPanel);
		focusPanel.add(rounded);
		styledPanel = new SimplePanel();
		styledPanel.add(alignmentPanel);
		rounded.setWidget(styledPanel);
	}

	public void setStyleName(String style, String roundedStyle) {
		rounded.setCornerStyleName(style);
		if (displayed != null) {
			styledPanel.setStyleName(style);
		}
	}

	public void removeStyleName(String style) {
		rounded.emptyCornerStyleName();
		if (displayed != null) {
			styledPanel.removeStyleName(style);
		}
	}

	private class RoundedFocusMouseListener extends FocusMouseListener {

		public void onMouseEnter(Widget sender) {
			styledPanel.addStyleName(mouseOverStyle);
			rounded.addStyleName(mouseOverStyle);
		}

		public void onMouseLeave(Widget sender) {
			styledPanel.removeStyleName(mouseOverStyle);
			rounded.removeStyleName(mouseOverStyle);
		}

		public void onMouseUp(Widget sender, int x, int y) {
			styledPanel.removeStyleName(mouseOverStyle);
			rounded.removeStyleName(mouseOverStyle);
		}
	}
}
