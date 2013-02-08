package fr.byob.client.view.widget.calendar;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;


public class CompletCellHTML extends Composite implements ICellCalendar{
    CellHTML cellHTML ;
    public CompletCellHTML(int displayNum) {
        VerticalPanel cell = new VerticalPanel();
        cellHTML = new CellHTML("<span>"
                + String.valueOf(displayNum) + "</span>",
                displayNum);
        cell.add(cellHTML);
        Label lieu = new Label("Sao Vicente");
        lieu.addStyleName("lieu");
        cell.add(lieu);
        int nbP = (displayNum%5);
        Label perso = new Label(nbP+"personne(s)");
        perso.addStyleName("nbPersonne");
        if(nbP==0){
            perso.addStyleName("free");
        }else if(nbP == 4){
            perso.addStyleName("full");
        }else{
            perso.addStyleName("mi-full");
        }
        cell.add(perso);
        initWidget(cell);
    }
    public CellHTML getCellHTML() {
        return cellHTML;
    }
}
