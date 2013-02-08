package fr.byob.client.view.widget.calendar;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventPreview;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.client.util.Css;
import fr.byob.client.view.panel.RoundedPanel;
import fr.byob.client.view.widget.ImageButton;

public class CalendarWidget extends Composite implements EventPreview{

    public static int CELL_HTML = 1;
    public static int COMPLET_CELL_HTML = 2;
    public static int CELLBOX_HTML = 3;
    
	private final NavigationBar navbar ;
	private final DockPanel outer = new DockPanel();
	private final FocusPanel focus = new FocusPanel();
	private final VerticalPanel legend = new VerticalPanel();

	private final FlexTable grid = new FlexTable()/*(7, 7)*/ {
		public boolean clearCell(int row, int column) {
			boolean retValue = super.clearCell(row, column);

			Element td = getCellFormatter().getElement(row, column);
			DOM.setInnerHTML(td, "");
			return retValue;
		}
	};

	private ChangeListenerCollection changeListeners;

	private String[] days = new String[] { CalendarStrings.INSTANCE.Sunday(), CalendarStrings.INSTANCE.Monday(),
			CalendarStrings.INSTANCE.Tuesday(), CalendarStrings.INSTANCE.Wednesday(), CalendarStrings.INSTANCE.Thursday(),
			CalendarStrings.INSTANCE.Friday(), CalendarStrings.INSTANCE.Saturday() };

	private String[] months = new String[] { CalendarStrings.INSTANCE.January(),
			CalendarStrings.INSTANCE.February(), CalendarStrings.INSTANCE.March(), CalendarStrings.INSTANCE.April(),
			CalendarStrings.INSTANCE.May(), CalendarStrings.INSTANCE.June(), CalendarStrings.INSTANCE.July(), CalendarStrings.INSTANCE.August(),
			CalendarStrings.INSTANCE.September(), CalendarStrings.INSTANCE.October(), CalendarStrings.INSTANCE.November(),
			CalendarStrings.INSTANCE.December() };

	private final CalendarDate startDate = new CalendarDate(0, 0, 0);
	private final CalendarDate endDate = new CalendarDate(23, 59, 59);

	private int displayedMonth;
	private int displayedYear;
	private int offset;

	private final static int STATE_UP = 1;
	private final static int STATE_DOWN = 2;

	private int state = STATE_UP;
	
	private int typeCell;
	
	private boolean isClickListner;

	public CalendarWidget(boolean isLegend,boolean isClick,int type) {
		DOM.addEventPreview(this);
		typeCell = type;
		isClickListner = isClick;
		navbar = new NavigationBar();
		grid.setStyleName(Css.INSTANCE.table());
		grid.setCellSpacing(0);
		focus.add(grid);
		if(isLegend){
		    drawLegend();
		}
		CalendarFocusPanelListener focusListener = new CalendarFocusPanelListener();
		focus.addFocusHandler(focusListener);
		focus.addBlurHandler(focusListener);
		focus.addMouseUpHandler(focusListener);
		focus.addMouseOutHandler(focusListener);
		outer.add(navbar, DockPanel.NORTH);
		outer.add(focus, DockPanel.CENTER);
		outer.add(legend, DockPanel.SOUTH);
		outer.setCellHorizontalAlignment(legend, HasHorizontalAlignment.ALIGN_CENTER);
		outer.setWidth("100%");
		RoundedPanel rounded = new RoundedPanel(outer, RoundedPanel.TOP, 5,Css.INSTANCE.RoundedCalendarWidget());
		initWidget(rounded);
		outer.setStyleName(Css.INSTANCE.CalendarWidget());
		reinitCalendar();
	}
	
	public boolean onEventPreview(Event event) {
	    if (DOM.eventGetType(event) == Event.ONMOUSEDOWN &&
	        DOM.isOrHasChild(getElement(), DOM.eventGetTarget(event))) {
	      DOM.eventPreventDefault(event);
	    }
	    // Always returning true as we don’t want to cancel
	    // the event, just to prevent the default behaviour.
	    return true;
	  }
	
	public void reinitCalendar (){
		startDate.reintDate();
		endDate.reintDate();
		displayedMonth = CalendarUtils.getCurrentMonth();
		displayedYear = CalendarUtils.getCurrentYear();
		drawCalendar();
	}

	private void drawCalendar() {
		setHeaderText(displayedYear, displayedMonth);
		grid.getRowFormatter().setStyleName(0, Css.INSTANCE.weekheader());
		for (int i = 0; i < days.length; i++) {
			grid.getCellFormatter().setStyleName(0, i, Css.INSTANCE.days());
			grid.setText(0, i, days[i].substring(0, 3));
		}
		drawCalendarCells();
	}
	
	private void drawLegend(){
	    HorizontalPanel free = new HorizontalPanel();
	    HorizontalPanel busy = new HorizontalPanel();
	    HorizontalPanel full = new HorizontalPanel();
	    ImageButton freeColor = new ImageButton();
	    freeColor.setSize("12px", "12px");
	    freeColor.addStyleName("free");
	    Label freeLabel = new Label("libre");
	    freeLabel.addStyleName("textLegend");
	    free.add(freeColor);
	    free.add(freeLabel);
	    ImageButton buzyColor = new ImageButton();
	    buzyColor.setSize("12px", "12px");
	    buzyColor.addStyleName("mi-full");
        Label buzyLabel = new Label("occupé");
        buzyLabel.addStyleName("textLegend");
        busy.add(buzyColor);
        busy.add(buzyLabel);
        ImageButton fullColor = new ImageButton();
        fullColor.setSize("12px", "12px");
        fullColor.addStyleName("full");
        Label fullLabel = new Label("plein");
        fullLabel.addStyleName("textLegend");
        full.add(fullColor);
        full.add(fullLabel);
        legend.add(free);
        legend.add(busy);
        legend.add(full);
        legend.addStyleName("legend");
	}
	
	private void drawCalendarCells() {
		boolean currentMonthDisplayed = displayedMonth == CalendarUtils
				.getCurrentMonth()
				&& displayedYear == CalendarUtils.getCurrentYear();

		boolean selectedMonthDisplayed = displayedMonth == startDate.getMonth()
				&& displayedYear == startDate.getYear();

		int firstDay = CalendarUtils.getFirstDay(displayedYear, displayedMonth);
		int numOfDays = CalendarUtils.getDaysInMonth(displayedYear,
				displayedMonth);

		int j = 0;
		for (int i = 1; i < 7; i++) {
			for (int k = 0; k < 7; k++, j++) {
				int displayNum = (j - firstDay + 1);
				if (j < firstDay || displayNum > numOfDays) {
					grid.getCellFormatter().setStyleName(i, k, "empty");
					grid.setHTML(i, k, "&nbsp;");
				} else {
				    ICellCalendar html;
				    if(typeCell == CELL_HTML){
				        html = new CellHTML("<span>"
	                            + String.valueOf(displayNum) + "</span>",
	                            displayNum);
				    }else  if(typeCell == CELLBOX_HTML){
				        html = new CellBox(displayNum);
				    }else{
				        html = new CompletCellHTML(displayNum);
				    }
				    if(isClickListner){
				        html.getCellHTML().addClickHandler(new CalendarClickListener(this));
				    }else{
				        CalendarMouseListener mouseListener = new CalendarMouseListener(this);
				        html.getCellHTML().addMouseOverHandler(mouseListener);
				        html.getCellHTML().addMouseUpHandler(mouseListener);
				        html.getCellHTML().addMouseDownHandler(mouseListener);
				    }
					grid.getCellFormatter().setStyleName(i, k, Css.INSTANCE.cell());
					if (currentMonthDisplayed
							&& displayNum == CalendarUtils.getCurrentDay()) {
						grid.getCellFormatter().addStyleName(i, k, Css.INSTANCE.today());
					}

					if (selectedMonthDisplayed
							&& displayNum >= startDate.getDay()
							&& displayNum <= endDate.getDay()) {
						grid.getCellFormatter().addStyleName(i, k, Css.INSTANCE.day());
					}
					grid.setWidget(i, k, (Widget)html);
				}
			}
		}
	}

	private void drawCalendarCellsOnMouseMove() {
		boolean currentMonthDisplayed = displayedMonth == CalendarUtils
				.getCurrentMonth()
				&& displayedYear == CalendarUtils.getCurrentYear();

		int firstDay = CalendarUtils.getFirstDay(displayedYear, displayedMonth);
		int numOfDays = CalendarUtils.getDaysInMonth(displayedYear,
				displayedMonth);

		int min, max;
		if (offset >= 0) {
			min = startDate.getDay();
			max = min + offset;
		} else {
			max = startDate.getDay();
			min = max + offset;
		}

		int j = 0;
		for (int i = 1; i < 6; i++) {
			for (int k = 0; k < 7; k++, j++) {
				int displayNum = (j - firstDay + 1);
				if (!(j < firstDay || displayNum > numOfDays)) {

					if (currentMonthDisplayed
							&& displayNum == CalendarUtils.getCurrentDay()) {
						grid.getCellFormatter().addStyleName(i, k, Css.INSTANCE.today());
					} else {
						grid.getCellFormatter().removeStyleName(i, k,
								Css.INSTANCE.today());
					}

					if (displayNum >= min && displayNum <= max) {
						grid.getCellFormatter().addStyleName(i, k, Css.INSTANCE.day());
					} else {
						grid.getCellFormatter()
								.removeStyleName(i, k, Css.INSTANCE.day());
					}
				}
			}
		}
	}

	protected void setHeaderText(int year, int month) {
		navbar.title.setWidget(new Label(months[month] + ", " + year));
	}

	public CalendarDate getStartDate() {
		return startDate;
	}

	public CalendarDate getEndDate() {
		return endDate;
	}

	public void addChangeListener(ChangeListener listener) {
		if (changeListeners == null){
			changeListeners = new ChangeListenerCollection();
		}
		changeListeners.add(listener);
	}

	public void removeChangeListener(ChangeListener listener) {
		if (changeListeners != null)
			changeListeners.remove(listener);
	}

	private void onStateUp(int day) {
		state = STATE_UP;
		if (day >= startDate.getDay()) {
			endDate.setDate(displayedYear, displayedMonth, day);
		} else {
			endDate.setDate(displayedYear, displayedMonth, startDate.getDay());
			startDate.setDate(displayedYear, displayedMonth, day);
		}

		drawCalendarCells();
		if (changeListeners != null) {
			changeListeners.fireChange(this);
		}
	}
	
	public class NavigationBar extends Composite implements ClickHandler {

		public final DockPanel bar = new DockPanel();
		public final ImageButton prevMonth = createButton(CalendarImages.INSTANCE.prevMonth(),
				this);
		public final ImageButton prevYear = createButton(CalendarImages.INSTANCE.prevYear(),
				this);
		public final ImageButton nextYear = createButton(CalendarImages.INSTANCE.nextYear(),
				this);
		public final ImageButton nextMonth = createButton(CalendarImages.INSTANCE.nextMonth(),
				this);
		public final ImageButton title = new ImageButton(HasVerticalAlignment.ALIGN_TOP,HasHorizontalAlignment.ALIGN_CENTER);

		public NavigationBar() {
		    if(!isClickListner){
		        title.addClickHandler(new ClickHandler() {
		            public void onClick(ClickEvent event) {
                        startDate.setDate(displayedYear, displayedMonth, 1);
                        endDate.setDate(displayedYear, displayedMonth,
                                CalendarUtils.getDaysInMonth(displayedYear,
                                        displayedMonth));
                        drawCalendar();
                    }
		        });
		    }
			bar.setStyleName(Css.INSTANCE.navbar());

			HorizontalPanel prevButtons = new HorizontalPanel();
			prevButtons.add(prevYear);
			prevButtons.add(prevMonth);

			HorizontalPanel nextButtons = new HorizontalPanel();
			nextButtons.add(nextMonth);
			nextButtons.add(nextYear);

			title.setHeight("24px");
			title.setStyleName(Css.INSTANCE.imageButton());

			bar.add(prevButtons, DockPanel.WEST);
			bar.setCellHorizontalAlignment(prevButtons, DockPanel.ALIGN_LEFT);
			bar.add(nextButtons, DockPanel.EAST);
			bar.setCellHorizontalAlignment(nextButtons, DockPanel.ALIGN_RIGHT);
			bar.add(title, DockPanel.CENTER);
			bar.setVerticalAlignment(DockPanel.ALIGN_MIDDLE);
			bar.setCellHorizontalAlignment(title, HasAlignment.ALIGN_CENTER);
			bar.setCellVerticalAlignment(title, HasAlignment.ALIGN_MIDDLE);
			bar.setCellWidth(title, "100%");
			initWidget(bar);
		}

		private ImageButton createButton(AbstractImagePrototype img,
				ClickHandler listener) {
			ImageButton button = new ImageButton(HasVerticalAlignment.ALIGN_TOP,HasHorizontalAlignment.ALIGN_CENTER);
			button.addClickHandler(listener);
			button.setWidget(img.createImage());
			button.setWidth("24px");
			button.setStyleName(Css.INSTANCE.imageButton());
			return button;
		}

		private void prevMonth() {
			displayedMonth--;
			if (displayedMonth < 0) {
				displayedMonth = 11;
			}
			drawCalendar();
		}

		private void nextMonth() {
			displayedMonth++;
			if (displayedMonth > 11) {
				displayedMonth = 0;
			}
			drawCalendar();
		}

		private void prevYear() {
			displayedYear--;
			drawCalendar();
		}

		private void nextYear() {
			displayedYear++;
			drawCalendar();
		}

        public void onClick(ClickEvent event) {
            Widget sender = (Widget) event.getSource();
            if (sender == prevMonth.getSender()) {
                prevMonth();
            } else if (sender == prevYear.getSender()) {
                prevYear();
            } else if (sender == nextYear.getSender()) {
                nextYear();
            } else if (sender == nextMonth.getSender()) {
                nextMonth();
            }
        }

	}

	private class CalendarMouseListener implements MouseOverHandler,MouseUpHandler,MouseDownHandler {

		private final CalendarWidget calendar;

		public CalendarMouseListener(CalendarWidget calendar) {
			this.calendar = calendar;
		}

        public void onMouseOver(MouseOverEvent event) {
            if (state == STATE_DOWN) {
                CellHTML cell = (CellHTML) event.getSource();
                offset = cell.getDay() - startDate.getDay();
                drawCalendarCellsOnMouseMove();
            }
        }

        public void onMouseUp(MouseUpEvent event) {
            CellHTML cell = (CellHTML) event.getSource();
            onStateUp(cell.getDay());
        }

        public void onMouseDown(MouseDownEvent event) {
            state = STATE_DOWN;
            CellHTML cell = (CellHTML) event.getSource();
            startDate.setDate(displayedYear, displayedMonth, cell.getDay());
            endDate.setDate(displayedYear, displayedMonth, cell.getDay());
            drawCalendarCells();
            if (changeListeners != null) {
                changeListeners.fireChange(calendar);
            }
        }
	}
	private class CalendarClickListener implements ClickHandler {

        private final CalendarWidget calendar;

        public CalendarClickListener(CalendarWidget calendar) {
            this.calendar = calendar;
        }

        public void onClick(ClickEvent event) {
            CellHTML cell = (CellHTML) event.getSource();
            startDate.setDate(displayedYear, displayedMonth, cell.getDay());
            endDate.setDate(displayedYear, displayedMonth, cell.getDay());
            drawCalendarCells();
            if (changeListeners != null) {
                changeListeners.fireChange(calendar);
            }
        }
    }
	private class CalendarFocusPanelListener implements FocusHandler,BlurHandler,MouseUpHandler,MouseOutHandler {

		private void onStateUpOffset() {
			if (state == STATE_DOWN){
				onStateUp(startDate.getDay() + offset);
			}
		}

        public void onFocus(FocusEvent event) {
            onStateUpOffset();
        }

        public void onBlur(BlurEvent event) {
            onStateUpOffset();
        }

        public void onMouseUp(MouseUpEvent event) {
            onStateUpOffset();
        }

        public void onMouseOut(MouseOutEvent event) {
            onStateUpOffset();
        }
	}

}
