package fr.byob.client.view.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MouseListener;
import com.google.gwt.user.client.ui.MouseListenerAdapter;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.IListener;
import fr.byob.client.util.Css;
import fr.byob.client.view.adapter.ITableAdapter;
import fr.byob.client.view.model.ITableModel;

public class TableWidget<T> extends Composite implements IListener{

	private FlexTable round;
	private int nbRows = 0;
	private final boolean newEnabled;
	private final boolean headerVisible;
	private final boolean isOver;
	private final ITableModel<T> model;
	private final ITableAdapter<T> adapter;
	private Css css = Css.INSTANCE;

	/**
	 * Headers null si pas de visible
	 * 
	 * @param headers
	 * @param visible
	 * @param addnew
	 */
	public TableWidget(ITableModel<T> model, ITableAdapter<T> adapter, boolean newEnabled,boolean isOver) {
		this.model = model;
		this.adapter = adapter;
		this.isOver = isOver;
		round = new FlexTable();
		round.setCellSpacing(0);
		round.addStyleName(css.gridborder());
		this.headerVisible = model.getOffset() > 0;
		if (headerVisible){
			addHeaders();
		}

		this.newEnabled = newEnabled;
		if (newEnabled) {
			addNew();
		}
		initWidget(round);
	}

	private void addHeaders() {
		if (adapter.getTableTitles() != null) {
			List<Widget> headers = adapter.getTableTitles();
			for (int i = 0; i < headers.size(); i++) {
				Widget cell = headers.get(i);
				cell.addStyleName(css.gridheader());
				round.setWidget(0, i, cell);
			}
		}
		nbRows++;
	}

	private void addNew() {
		round.getFlexCellFormatter().setHorizontalAlignment(nbRows, 0,
				HasHorizontalAlignment.ALIGN_CENTER);
		round.getFlexCellFormatter().setColSpan(nbRows, 0, adapter.getTableTitles().size());
		round.getRowFormatter().addStyleName(nbRows, css.lastrow());
		round.getRowFormatter().addStyleName(nbRows, css.gridcell());
		round.setWidget(nbRows, 0, new Label("Nouveau"));

	}

	public void addValue(List<Widget> values) {
            round.insertRow(nbRows);
            modifyValue(values, nbRows);
		nbRows++;
	}

	public void modifyValue(List<Widget> values, int row) {
		for (int i = 0; i < values.size(); i++) {
			Widget cell = values.get(i);
			cell.addStyleName(css.gridcell());
			if(isOver){
			    round.setWidget(row, i, cellFocusFactory(cell,row));
			}else{
			    round.setWidget(row, i, cell);
			}
		}
	}

	public void removeValue(int row) {
		round.removeRow(row);
		nbRows--;
	}

	public void removeAll() {
		int start = 0;
		if (headerVisible) {
			start++;
		}

		int end = nbRows;

		for (int i = end - 1; i >= start; i--) {
			removeValue(i);
		}
	}

	public void addListener(ClickHandler listener) {
		round.addClickHandler(listener);
	}

	public List<Widget> getValues(int row, int size) {
		List<Widget> list = new ArrayList<Widget>();
		for (int i = 0; i < size; i++) {
			list.add(round.getWidget(row, i));
		}
		return list;
	}

	public void selectRow(int row) {
		int start = 0;
		int end = nbRows;
		if (headerVisible) {
			start++;
		}
		for (int i = start; i < end; i++) {
			round.getRowFormatter().removeStyleName(i, css.rownoselected());
			round.getRowFormatter().removeStyleName(i, css.rowselected());
			round.getRowFormatter().addStyleName(i, css.rownoselected());
		}
		if (row >= 0) {
			round.getRowFormatter().removeStyleName(row, css.rownoselected());
			round.getRowFormatter().addStyleName(row, css.rowselected());
		}
	}

	public void unselect() {
		int start = 0;
		int end = nbRows;
		for (int i = start; i < end; i++) {
			round.getRowFormatter().removeStyleName(i, css.rownoselected());
			round.getRowFormatter().removeStyleName(i, css.rowselected());
			round.getRowFormatter().addStyleName(i, css.rownoselected());
		}
	}

	public boolean isNewRowIndex(int row) {
		return newEnabled && (row == nbRows);
	}

	public boolean isNewEnabled() {
		return newEnabled;
	}

	public boolean isHeaderRowIndex(int row) {
		return headerVisible && row == 0;
	}

	public void manageModelModification() {
		if (model.getLastOperation() != ITableModel.NO_OPERATION) {
			manageOperation();
		}
		manageSelectionChanged();
	}

	private void manageSelectionChanged() {
		int index = model.getSelectedObjectIndex();
		if (index == ITableModel.UNSELECTED_INDEX) {
			unselect();
		} else if (index == ITableModel.NEW_SELECTED_INDEX) {
			selectRow(model.getObjects().size() + 1);
		} else {
			selectRow(index);
		}
	}

	private void manageOperation() {
		int index = model.getSelectedObjectIndex();
		switch (model.getLastOperation()) {
		case ITableModel.ADD_OPERATION:
		    addValue(adapter.getTableWidgets(model.getAddedObject()));
			break;
		case ITableModel.MODIFY_OPERATION:
			modifyValue(adapter.getTableWidgets(model.getSelectedObject()),
					index);
			break;
		case ITableModel.DELETE_OPERATION:
			removeValue(index);
			break;
		case ITableModel.REFRESH_OPERATION:
			removeAll();
			List<T> datas = model.getObjects();
		    for (int nbdata = 0;nbdata<datas.size();nbdata++) {
				addValue(adapter.getTableWidgets(datas.get(nbdata)));
			}
			break;
		}
	}

	private final HashMap<Integer,MouseListener> rows = new HashMap<Integer, MouseListener>();
	
	private FocusPanel cellFocusFactory(Widget cell,final int row){
		FocusPanel focus = new FocusPanel();
		focus.add(cell);
		if (!rows.containsKey(row)){
			rows.put(row,new MouseListenerAdapter(){

				public void onMouseEnter(Widget sender) {
					round.getRowFormatter().addStyleName(row, css.overMouseTable());
				}

				public void onMouseLeave(Widget sender) {
					round.getRowFormatter().removeStyleName(row, css.overMouseTable());
				}
			});
		}
		focus.addMouseListener(rows.get(row));
		return focus;
	}
	public FlexTable getFlexTable(){
	    return round;
	}
}
