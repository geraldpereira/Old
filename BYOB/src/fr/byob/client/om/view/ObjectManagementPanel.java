package fr.byob.client.om.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.application.IDeckListener;
import fr.byob.client.om.controler.IObjectManagementControler;
import fr.byob.client.util.Css;
import fr.byob.client.view.panel.RoundedPanel;
import fr.byob.client.view.widget.ConsoleWidget;
import fr.byob.client.view.widget.IFormWidget;
import fr.byob.client.view.widget.TableWidget;

/**
 * Implémentation par défaut de la vue de gestion des objets.
 * 
 * Pour en modifier l'aspect de mannière simple il suffit de la surcharger en
 * modifiant la classe InitView Pour une modification p^lus lourde on pourra
 * créer une autre implémentation de {@link IObjectManagementPanel}
 * 
 * @author Kojiro
 * 
 * @param <T>
 *            le type des objets gérés
 */
public class ObjectManagementPanel<T> extends Composite implements
IObjectManagementPanel<T>, IDeckListener {

    protected final IObjectManagementControler<T> controler;

    protected final TableWidget<T> table;

    protected final IFormWidget<T> form;

    protected final ConsoleWidget console;

    private VerticalPanel vertical = new VerticalPanel();

    private RoundedPanel rounded;

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
     *            optionnelle : la console de rteour après opération
     */
    public ObjectManagementPanel(
            IObjectManagementControler<T> controler,
            TableWidget<T> table,
            IFormWidget<T> form, 
            ConsoleWidget console,boolean isFullSize) {
        this.controler = controler;
        controler.setView(this);

        this.table = table;

        this.form = form;

        this.console = console;
        this.isFullSize = isFullSize;
    }

    public void initView() {
        // Seuls les afficheurs non nulls sont ajoutés
        if (table != null) {
            if(isFullSize){
                table.setWidth("100%");
            }
            rounded = new RoundedPanel(table, RoundedPanel.ALL, 5,
            Css.INSTANCE.cbgRPForm());
            if(isFullSize){
                rounded.setWidth("100%");
            }
            VerticalPanel simple = new VerticalPanel();
            simple.add(rounded);
            if(isFullSize){
                simple.setWidth("100%");
            }
            vertical.add(simple);
            vertical.setCellHorizontalAlignment(simple,HasHorizontalAlignment.ALIGN_CENTER);
        }
        if (console != null) {
            vertical.add(console);
            vertical.setCellHorizontalAlignment(console,HasHorizontalAlignment.ALIGN_CENTER);
        }
        if (form != null) {
            vertical.add((Widget) form);
            vertical.setCellHorizontalAlignment((Widget) form,HasHorizontalAlignment.ALIGN_CENTER);
        }
        if(isFullSize){
            vertical.setWidth("100%");
//            vertical.setHeight("100%");
        }
        initWidget(vertical);
    }

    public void deckCharged() {
        controler.refreshModel();
    }

    public TableWidget<T> getTable() {
        return table;
    }

    public IFormWidget<T> getForm() {
        return form;
    }

    public ConsoleWidget getConsole() {
        return console;
    }

    public void setStyleName(String style){
        vertical.setStyleName(style);
    }
    public void removeStyleName(String style){
        vertical.removeStyleName(style);
    }
    public void setVisibleTable(boolean isVisible){
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
