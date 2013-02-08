package fr.byob.blog.client.view.adapter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.model.CategoryGWT;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.view.adapter.IConsoleAdapter;
import fr.byob.client.view.adapter.IFormAdapter;
import fr.byob.client.view.adapter.ITableAdapter;
import fr.byob.client.view.widget.IFormWidget;
import fr.byob.client.view.widget.TableWidget;

public class CategoryAdapter implements IFormAdapter<ICategory>, ITableAdapter<ICategory>, IConsoleAdapter {

    public final static int labelId = 0;


    public ICategory getManagedObject(TableWidget<ICategory> table, int row) {
        ICategory category = new CategoryGWT();
        List<Widget> list = table.getValues(row, 2);
        category.setCategorylabel(((Label) list.get(0)).getText());
        return category;
    }

    public String getObjectName() {
        return BlogStrings.INSTANCE.categoryLabel();
    }

    public List<Widget> getTableTitles() {
        List<Widget> list = new ArrayList<Widget>();
        list.add(new Label(BlogStrings.INSTANCE.categoryLabel()));
        list.add(new Label(BlogStrings.INSTANCE.categoryNbPost()));
        list.add(new Label(BlogStrings.INSTANCE.categoryNbLink()));
        return list;
    }

    public List<Widget> getTableWidgets(ICategory object) {
        List<Widget> list = new ArrayList<Widget>();
        list.add(new Label(object.getCategorylabel()));
        if (object.getPostCollection() != null) {
            list.add(new Label(object.getPostCollection().size() + ""));
        } else {
            list.add(new Label("0"));
        }
        if (object.getLinkCollection() != null) {
            list.add(new Label(object.getLinkCollection().size() + ""));
        } else {
            list.add(new Label("0"));
        }
        return list;
    }

    public void fillForm(IFormWidget<ICategory> form, ICategory object) {
        if (form != null) {
            form.setTextElement(labelId, object.getCategorylabel());
            form.setEnabled(labelId,true);
        }
    }

    public ICategory getManagedObject(IFormWidget<ICategory> form) {
        if (form != null) {
            ICategory category = new CategoryGWT();
            category.setCategorylabel(form.getTextElement(labelId));
            return category;
        } else {
            return null;
        }
    }

    public void reinitForm(IFormWidget<ICategory> form) {
        if (form != null) {
            form.setTextElement(labelId, "");
            form.setEnabled(labelId,true);
        }
    }

    public int getTableColNumber() {
        return 2;
    }

    public ICategory getManagedObject(IFormWidget<ICategory> form, ICategory t) {
        if (form != null) {
            ICategory category = getManagedObject(form);
            t.setCategorylabel(category.getCategorylabel());
            return t;
        } else {
            return null;
        }
    }

    public String getFormTitle() {
        return BlogStrings.INSTANCE.categoryInfo();
    }

}
