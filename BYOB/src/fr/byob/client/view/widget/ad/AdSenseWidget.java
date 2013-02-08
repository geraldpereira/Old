package fr.byob.client.view.widget.ad;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.util.Utils;

public class AdSenseWidget extends Widget {

	/**
	 * Identifiant du div contenant le script adsense (voir le fichier html)
	 * @param id
	 */
	public AdSenseWidget(String id) {
		super();
		RootPanel rootPanel = RootPanel.get(id);
		if (rootPanel != null) {
			Element element = rootPanel.getElement();
			if (Utils.isIEBrowser() || Utils.isIE7Browser()){
				// Cette solution plante sous FF (page reste blanche sans jamais finir de charger)
				Element div = DOM.createDiv();
				div.setInnerHTML(element.getInnerHTML());
				this.setElement(div);
			}else{
				// Fonctionne bien sous FF
				// Sous IE les pubs ne sont pas affichées
				this.setElement(element);
			}
		}
	}
}
