package fr.byob.client.view.panel;

import com.google.gwt.user.client.ui.Widget;

public class HorizontalDisclosurePanel extends AnimHorizontalDisclosurePanel {

	public HorizontalDisclosurePanel(final Widget center, final Widget wdisclosed,
			final int disclosurePosition, final int disclosureSize, final int minHeight,
			final boolean disclosedVisible) {
		super(center, wdisclosed, disclosurePosition, disclosureSize, minHeight, disclosedVisible, 0);
	}

}
