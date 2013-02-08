package fr.byob.blog.client.view.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.IPost;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.model.PostGWT;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.blog.client.view.util.BlogUtils;
import fr.byob.client.util.Css;
import fr.byob.client.util.Utils;
import fr.byob.client.view.adapter.IConsoleAdapter;
import fr.byob.client.view.adapter.IFormAdapter;
import fr.byob.client.view.adapter.ITableAdapter;
import fr.byob.client.view.widget.IFormWidget;
import fr.byob.client.view.widget.ObjectListBoxWidget;
import fr.byob.client.view.widget.ObjectListWidget;
import fr.byob.client.view.widget.TableWidget;
import fr.byob.client.view.widget.smiley.SmileyUtils;

public class PostAdapter implements IFormAdapter<IPost>,ITableAdapter<IPost>,IConsoleAdapter{

    public final static int titleId = 0;
    public final static int authorId = 1;
    public final static int dateId = 2;
    public final static int categoryId = 3;
    public final static int textId = 4;
    public final static int isprivate = 5;
    
    private final static int nbCharDisplay = 600;


    public IPost getManagedObject(TableWidget<IPost> table, int row) {
        IPost post = new PostGWT();
        List<Widget> list = table.getValues(row, 4);
        post.setPosttitle(((Label) list.get(0)).getText());
        post.setPosttext(((HTML) list.get(1)).getHTML());
        post.setCategoryCollection(((ObjectListWidget<ICategory>) list.get(3)).getObjects());
        return post;
    }

    public String getObjectName() {
        return BlogStrings.INSTANCE.post();
    }

    public List<Widget> getTableTitles() {
        List<Widget> list = new ArrayList<Widget>();
        list.add(new Label(BlogStrings.INSTANCE.addPostTitle()));
        list.add(new Label(BlogStrings.INSTANCE.addPostText()));
        list.add(new Label(BlogStrings.INSTANCE.addPostDate()));
        list.add(new Label(BlogStrings.INSTANCE.addPostCategories()));
        return list;
    }

    public List<Widget> getTableWidgets(IPost object) {
        List<Widget> list = new ArrayList<Widget>();
        list.add(new Label(object.getPosttitle()));
        HTML content = new HTML(SmileyUtils.getTextWithSmiley(BlogUtils.getBreakenText(object.getPosttext())));
        content.addStyleName(Css.INSTANCE.cursorFleche());
        list.add(content);
        list.add(new Label(Utils.formatDate(object.getPostdate())));
        ObjectListWidget<ICategory> listCategories = new ObjectListWidget<ICategory>();
        for (ICategory cat : object.getCategoryCollection()) {
            listCategories.addElement(cat, cat.getCategorylabel());
        }
        list.add(listCategories);
        return list;
    }

    public void fillForm(IFormWidget<IPost> form, IPost object) {
        if (form != null) {
            form.setTextElement(titleId, object.getPosttitle());
            form.setTextElement(authorId, object.getUserid().getUserid());
            form.setEnabled(authorId,false);
            form.setTextElement(dateId, Utils.formatDate(object.getPostdate()));
            form.setEnabled(dateId,false);
            form.setTextElement(isprivate, ""+object.getPostisprivate());
            form.setTextElement(textId, object.getPosttext());
            ObjectListBoxWidget<ICategory> list = (ObjectListBoxWidget<ICategory>) form.getElement(categoryId);
            list.setSelectedObjects(object.getCategoryCollection());
        }
    }

    public IPost getManagedObject(IFormWidget<IPost> form) {
        if (form != null) {
            IPost post = new PostGWT();
            post.setPosttitle(form.getTextElement(titleId));
            post.setPosttext(form.getTextElement(textId));
            if(form.getTextElement(isprivate).equals("true")){
                post.setPostisprivate(true);
            }else{
                post.setPostisprivate(false);
            }
            ObjectListBoxWidget<ICategory> list = (ObjectListBoxWidget<ICategory>) form.getElement(categoryId);
            Set<ICategory> categories = list.getSelectedObjects();
            post.setCategoryCollection(categories);
            post.setPostdate(new Date());
            return post;
        } else {
            return null;
        }
    }

    public void reinitForm(IFormWidget<IPost> form) {
        if (form != null) {
            form.setTextElement(titleId, "");
            form.setTextElement(authorId, ConnectedUserModel.getInstance().getConnectedUser().getUserid());
            form.setEnabled(authorId,false);
            form.setTextElement(dateId, Utils.formatDate(new Date()));
            form.setEnabled(dateId,false);
            form.setTextElement(textId, "");
            form.setTextElement(categoryId, "");
            form.setTextElement(isprivate, "false");
            ObjectListBoxWidget<ICategory> list = (ObjectListBoxWidget<ICategory>) form.getElement(categoryId);
            list.unselectedObjects();
        }
    }

    public int getTableColNumber() {
        return 4;
    }

    public IPost getManagedObject(IFormWidget<IPost> form, IPost t) {
        if (form != null) {
            IPost post = this.getManagedObject(form);
            t.setPosttitle(post.getPosttitle());
            t.setPosttext(post.getPosttext());
            t.setPostisprivate(post.getPostisprivate());
            t.setCategoryCollection(post.getCategoryCollection());
            return t;
        } else {
            return null;
        }
    }

    public String getFormTitle() {
        return BlogStrings.INSTANCE.postInfo();
    }

}
