package fr.byob.client.om.view;

import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.om.controler.IObjectManagementControler;
import fr.byob.client.util.Css;
import fr.byob.client.view.panel.RoundedPanel;
import fr.byob.client.view.widget.ConsoleWidget;
import fr.byob.client.view.widget.IFormWidget;
import fr.byob.client.view.widget.TableWidget;
import fr.byob.client.view.widget.toolbar.ToolbarWidget;

/**
 * Implémentation par défaut de la vue de gestion des objets.
 * 
 * Pour en modifier l'aspect de mannière simple il suffit de la surcharger en
 * modifiant la classe InitView Pour une modification plus lourde on pourra
 * créer une autre implémentation de {@link IObjectManagementPanel}
 * 
 * @author Kojiro
 * 
 * @param <T>
 *            le type des objets gérés
 */
public class ObjectManagementToolbarPanel<T> extends ObjectManagementPanel<T> {

	public static int TOP = 1;

	public static int BOTTOM = 2;

	private final ToolbarWidget toolbar;

	private RoundedPanel rounded = null;

	private final int position;

	private VerticalPanel vertical = new VerticalPanel();

	private boolean isFullSize;

	/**
	 * 
	 * @param adapter
	 *            obligatoire car utilisé pour la gestion des opérations
	 *            (probablement plus lorsque l'on améliorrara les opérations)
	 * @param controler
	 *            obligatoire pour rafraichir le modèle lors du chargement de la
	 *            vue
	 * @param model
	 *            obligatoire, contient les données
	 * @param table
	 *            optionnel la table d'affichage des objets
	 * @param form
	 *            optionnel le form d'affichage de l'objet sélectionné
	 * @param console
	 *            optionnelle : la console de retour après opération
	 */
	public ObjectManagementToolbarPanel(
			IObjectManagementControler<T> controler, TableWidget<T> table,
			IFormWidget<T> form, ConsoleWidget console, ToolbarWidget toolbar,
			int position, boolean isFullSize) {
		super(controler, table, form, console, isFullSize);
		this.position = position;
		this.toolbar = toolbar;
		this.isFullSize = isFullSize;
		// initView(position);
	}

	public void initView() {
		Css css = Css.INSTANCE;
		// Seuls les afficheurs non nulls sont ajoutés
		if (table != null) {
			VerticalPanel roundGlob = new VerticalPanel();
			if (isFullSize) {
				roundGlob.setWidth("100%");
			}
			if (position == TOP && toolbar != null) {
				toolbar.addStyleName(css.toolbarBottom());
				RoundedPanel round = new RoundedPanel(toolbar,
						RoundedPanel.TOP, 5, css.cbgRPToolbarTop());
				round.setWidth("100%");
				roundGlob.add(round);
				roundGlob.setCellHorizontalAlignment(round,
						HasHorizontalAlignment.ALIGN_CENTER);
			}
			table.setWidth("100%");
			roundGlob.add(table);
			if (position == BOTTOM && toolbar != null) {
				toolbar.addStyleName(css.toolbarTop());
				RoundedPanel round = new RoundedPanel(toolbar,
						RoundedPanel.BOTTOM, 5, css.cbgRPToolbarBottom());
				roundGlob.add(round);
				roundGlob.setCellHorizontalAlignment(round,
						HasHorizontalAlignment.ALIGN_CENTER);
				rounded = new RoundedPanel(roundGlob, RoundedPanel.TOP, 5, css
						.cbgRPFormTop());
			}
			if (position == TOP && toolbar != null) {
				rounded = new RoundedPanel(roundGlob, RoundedPanel.BOTTOM, 5,
						css.cbgRPFormBottom());
			}
			if (isFullSize) {
				rounded.setWidth("100%");
			}
			HorizontalPanel simple = new HorizontalPanel();
			simple.add(rounded);
			simple.addStyleName(css.gridPadding());
			if (isFullSize) {
				simple.setWidth("100%");
			}
			vertical.add(simple);
			vertical.setCellHorizontalAlignment(simple,
					HasHorizontalAlignment.ALIGN_CENTER);
		}

		if (console != null) {
			vertical.add(console);
			vertical.setCellHorizontalAlignment(console,
					HasHorizontalAlignment.ALIGN_CENTER);
		}
		if (form != null) {
			vertical.add((Widget) form);
			vertical.setCellHorizontalAlignment((Widget) form,
					HasHorizontalAlignment.ALIGN_CENTER);
		}
		vertical.setWidth("100%");
		// vertical.setHeight("100%");

		initWidget(vertical);
	}

	public ToolbarWidget getToolbar() {
		return toolbar;
	}

	public void setStyleName(String style) {
		vertical.setStyleName(style);
	}

	public void removeStyleName(String style) {
		vertical.removeStyleName(style);
	}

	public void setVisibleTable(boolean isVisible) {
		rounded.setVisible(isVisible);
	}
	public void addWidget(Widget w) {
        vertical.add(w);
    }
    public void removeWidget(Widget w) {
        vertical.remove(w);
    }
    public void setVisibleAll(boolean isVisible) {
        if(rounded != null){
        rounded.setVisible(isVisible);
        }
        if(console != null){
        console.setVisible(isVisible);
        }
        if(form != null){
        form.setVisible(isVisible);
        }
    }
}
