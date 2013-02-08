package fr.byob.client.view.widget.calendar;

import com.google.gwt.user.client.ui.Composite;


public class CellBox extends Composite implements ICellCalendar{
        CellHTML cellHTML ;
        public CellBox(int displayNum) {
            cellHTML = new CellHTML(String.valueOf(displayNum) ,
                    displayNum);
            cellHTML.addStyleName("cellbox");
            initWidget(cellHTML);
        }
        public CellHTML getCellHTML() {
            return cellHTML;
        }
    }