package fr.byob.blog.client.view.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.ILink;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.model.LinkGWT;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.blog.client.view.widget.LinkWidget;
import fr.byob.client.view.adapter.IConsoleAdapter;
import fr.byob.client.view.adapter.IFormAdapter;
import fr.byob.client.view.adapter.ITableAdapter;
import fr.byob.client.view.widget.IFormWidget;
import fr.byob.client.view.widget.ObjectListBoxWidget;
import fr.byob.client.view.widget.TableWidget;

public class LinkAdapter implements IFormAdapter<ILink>,ITableAdapter<ILink>,IConsoleAdapter{

    public final static int titleId = 0;
    public final static int authorId = 1;
    public final static int urlId = 2;
    public final static int categoryId = 3;
    public final static int descriptionId = 4;
    
    private final static int nbCharDisplay = 600;

    public ILink getManagedObject(TableWidget<ILink> table, int row) {
        ILink link = new LinkGWT();
        List<Widget> list = table.getValues(row, 1);
        LinkWidget linkW = (LinkWidget)list.get(0);
        link.setLinktitle(linkW.getTitleLink());
        link.setLinktext(linkW.getTextLink());
        link.setLinkurl(linkW.getUrlLink());
        link.setCategoryCollection(linkW.getCategoriesLink());
        return link;
    }

    public String getObjectName() {
        return BlogStrings.INSTANCE.linkUpperLabel();
    }

    public List<Widget> getTableTitles() {
        List<Widget> list = new ArrayList<Widget>();
        list.add(new Label(BlogStrings.INSTANCE.linkUpperLabel()));
        return list;
    }

    public List<Widget> getTableWidgets(ILink object) {
        List<Widget> list = new ArrayList<Widget>();
        LinkWidget link = new LinkWidget();
        
        if(object.getCategoryCollection() != null  && object.getUserid()!= null && object.getCategoryCollection().size() != 0){
            String text = null;
            if(object.getLinktext().length()>nbCharDisplay){
                text = object.getLinktext().substring(0,nbCharDisplay)+" ...";
            }else{
                text = object.getLinktext();
            }
            link.setData(object.getCategoryCollection(),object.getLinktitle(),object.getUserid(),object.getLinkurl(),text);
        }else if(object.getUserid() != null){
            String text = null;
            if(object.getLinktext().length()>nbCharDisplay){
                text = object.getLinktext().substring(0,nbCharDisplay)+" ...";
            }else{
                text = object.getLinktext();
            }
            link.setData(null,object.getLinktitle(),object.getUserid(),object.getLinkurl(),text); 
        }else{
            link.setData(null,object.getLinktitle(),null,object.getLinkurl(),object.getLinktext());
        }
        list.add(link);
        return list;
        
    }

    public void fillForm(IFormWidget<ILink> form, ILink object) {
        if (form != null) {
            form.setTextElement(titleId, object.getLinktitle());
            form.setTextElement(authorId, object.getUserid().getUserid());
            form.setEnabled(authorId,false);
            form.setTextElement(urlId, object.getLinkurl());
            form.setTextElement(descriptionId, object.getLinktext());
            ObjectListBoxWidget<ICategory> list = (ObjectListBoxWidget<ICategory>) form.getElement(categoryId);
            list.setSelectedObjects(object.getCategoryCollection());
        }
    }

    public ILink getManagedObject(IFormWidget<ILink> form) {
        if (form != null) {
            ILink link = new LinkGWT();
            link.setLinktitle(form.getTextElement(titleId));
            link.setLinkurl(form.getTextElement(urlId));
            link.setLinktext(form.getTextElement(descriptionId));
            ObjectListBoxWidget<ICategory> list = (ObjectListBoxWidget<ICategory>) form.getElement(categoryId);
            Set<ICategory> categories = list.getSelectedObjects();
            link.setCategoryCollection(categories);
            return link;
        } else {
            return null;
        }
    }

    public void reinitForm(IFormWidget<ILink> form) {
        if (form != null) {
            form.setTextElement(titleId, "");
            form.setTextElement(authorId, ConnectedUserModel.getInstance().getConnectedUser().getUserid());
            form.setEnabled(authorId,false);
            form.setTextElement(urlId, "http://");
            form.setTextElement(descriptionId, "");
            form.setTextElement(categoryId, "");
            ObjectListBoxWidget<ICategory> list = (ObjectListBoxWidget<ICategory>) form.getElement(categoryId);
            list.unselectedObjects();
        }
    }

    public int getTableColNumber() {
        return 4;
    }

    public ILink getManagedObject(IFormWidget<ILink> form, ILink t) {
        if (form != null) {
            ILink link = this.getManagedObject(form);
            t.setLinktitle(link.getLinktitle());
            t.setLinkurl(link.getLinkurl());
            t.setLinktext(link.getLinktext());
            t.setCategoryCollection(link.getCategoryCollection());
            return t;
        } else {
            return null;
        }
    }

    public String getFormTitle() {
        return BlogStrings.INSTANCE.linkInfo();
    }

}
