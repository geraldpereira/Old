package fr.byob.client.view.widget.calendar;

import com.google.gwt.user.client.ui.HTML;


public class CellHTML extends HTML implements ICellCalendar{
    private int day;

    public CellHTML(String text, int day) {
        super(text);
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public CellHTML getCellHTML() {
        return this;
    }

}
