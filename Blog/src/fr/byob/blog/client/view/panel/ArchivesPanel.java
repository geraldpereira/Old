package fr.byob.blog.client.view.panel;

import java.util.Date;
import java.util.Set;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.proxy.ArchiveServiceProxy;
import fr.byob.blog.client.view.adapter.PostsAdapter;
import fr.byob.blog.client.view.factory.CategoryListBoxFactory;
import fr.byob.blog.client.view.factory.UserListBoxFactory;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.IListener;
import fr.byob.client.application.IDeckListener;
import fr.byob.client.application.model.IIndexSelectionModel;
import fr.byob.client.application.page.controler.SubPageSelectionControler;
import fr.byob.client.om.controler.ObjectManagementControler;
import fr.byob.client.om.view.ObjectManagementToolbarPanel;
import fr.byob.client.util.Css;
import fr.byob.client.view.model.ConsoleModel;
import fr.byob.client.view.model.IConsoleModel;
import fr.byob.client.view.model.ITableModel;
import fr.byob.client.view.model.IToolbarModel;
import fr.byob.client.view.model.ToolbarModel;
import fr.byob.client.view.panel.AnimHorizontalDisclosurePanel;
import fr.byob.client.view.panel.HorizontalDisclosurePanel;
import fr.byob.client.view.widget.BYOBTextBox;
import fr.byob.client.view.widget.ConsoleWidget;
import fr.byob.client.view.widget.ObjectListBoxWidget;
import fr.byob.client.view.widget.TableWidget;
import fr.byob.client.view.widget.calendar.CalendarWidget;
import fr.byob.client.view.widget.toolbar.ToolbarWidget;

/**
 * Décrit le Panel pour l'affichage des articles
 * 
 * @author Akemi
 */
public class ArchivesPanel extends Composite implements IDeckListener, IListener {

    private ObjectManagementControler<IPost> controler;
    private final ITableModel<IPost> tableModel;
    private final IConsoleModel consoleModel = new ConsoleModel();

    private ObjectManagementToolbarPanel<IPost> view;
    private AnimHorizontalDisclosurePanel posts;
    private final ObjectListBoxWidget<IUser> authorField = UserListBoxFactory.getInstance().getUserListBoxWidget(consoleModel);
    private final ObjectListBoxWidget<ICategory> categoryField = CategoryListBoxFactory.getInstance().getCategoryListBoxWidget(consoleModel);
    private final BYOBTextBox title = new BYOBTextBox();
    private final BYOBTextBox text = new BYOBTextBox();
    private final CalendarWidget date = new CalendarWidget(false,false,CalendarWidget.CELL_HTML);

    /**
     * Constructor
     * 
     * @param model
     */
    public ArchivesPanel(final IIndexSelectionModel deckPageModel, final ITableModel<IPost> tableModel) {
        this.tableModel = tableModel;
        initView(deckPageModel);
        initWidget(posts);
    }

    /**
     * Initialise la vue
     */
    public void initView(IIndexSelectionModel deckPageModel) {
        createPostsPanel(deckPageModel);
        Widget search = createSearchPanel();
        posts = new AnimHorizontalDisclosurePanel(view, search, HorizontalDisclosurePanel.RIGHT, 260,260,true,1000);
        posts.setWidth("99%");
        search.addStyleName(Css.INSTANCE.pad10());
    }

    private void createPostsPanel(IIndexSelectionModel deckPageModel) {
        PostsAdapter adapter = new PostsAdapter();

        final IToolbarModel toolbarModel = new ToolbarModel(5);

        ArchiveServiceProxy service = ArchiveServiceProxy.getInstance();
        service.setArchivePanel(this);
        this.controler = new ObjectManagementControler<IPost>(service);
        controler.setTableModel(tableModel);
        controler.setConsoleModel(consoleModel);
        controler.setToolbarModel(toolbarModel);

        final TableWidget<IPost> table = new TableWidget<IPost>(tableModel, adapter, false,true);
        table.addListener(controler.getClickOnTableListener());
        table.addListener(new SubPageSelectionControler(deckPageModel).getSubPageSelectionControler(1));

        ToolbarWidget toolbar = new ToolbarWidget(controler, toolbarModel);

        ConsoleWidget console = new ConsoleWidget(consoleModel, adapter);

        view = new ObjectManagementToolbarPanel<IPost>(controler, table, null, console, toolbar, ObjectManagementToolbarPanel.BOTTOM, true);
        consoleModel.addListener(console);
        tableModel.addListener(table);
        toolbarModel.addListener(toolbar);
        view.initView();
    }

    private Widget createSearchPanel() {
        VerticalPanel global = new VerticalPanel();
        global.add(disclosurePanelFactory(BlogStrings.INSTANCE.archiveByAuthor(), authorField, true));
        global.add(disclosurePanelFactory(BlogStrings.INSTANCE.archiveByCategory(), categoryField, true));
        global.add(disclosurePanelFactory(BlogStrings.INSTANCE.archiveByTitle(), title, true));
        global.add(disclosurePanelFactory(BlogStrings.INSTANCE.archiveByText(), text, true));
        global.add(disclosurePanelFactory(BlogStrings.INSTANCE.archiveByDate(), date, false));
        HorizontalPanel buttons = new HorizontalPanel();
        buttons.setWidth("100%");
        buttons.setSpacing(5);
        buttons.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
        Button search = new Button(BlogStrings.INSTANCE.buttonSearch());
        search.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                controler.refreshModel();
            }
        });

        Button reinit = new Button(BlogStrings.INSTANCE.buttonReinit());
        reinit.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                authorField.unselectedObjects();
                categoryField.unselectedObjects();
                text.setText("");
                title.setText("");
                date.reinitCalendar();
                controler.refreshModel();
            }
        });

        buttons.add(search);
        buttons.add(reinit);
        global.add(buttons);
        return global;
    }

    private DisclosurePanel disclosurePanelFactory(String title, Widget disclosed, boolean offset) {
        DisclosurePanel panel = new DisclosurePanel(title);
        HorizontalPanel horizon = new HorizontalPanel();
        if (offset) {
            Label filler = new Label();
            filler.setWidth("25px");
            horizon.add(filler);
        }
        horizon.add(disclosed);
        panel.add(horizon);
        panel.setAnimationEnabled(true);
        return panel;
    }

    public Set<IUser> getSelectedAuthors() {
        return authorField.getSelectedObjects();
    }

    public Set<ICategory> getSelectedCategories() {
        return categoryField.getSelectedObjects();
    }

    public String getText() {
        return text.getText();
    }

    public String getTitle() {
        return title.getText();
    }

    public Date getStartDate() {
        return date.getStartDate().getDate();
    }

    public Date getEndDate() {
        return date.getEndDate().getDate();
    }

    public void manageModelModification() {
    }

    public void deckCharged() {
        view.deckCharged();
    }
}
