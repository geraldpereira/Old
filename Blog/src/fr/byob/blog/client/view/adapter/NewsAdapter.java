package fr.byob.blog.client.view.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.IPost;
import fr.byob.blog.client.model.PostGWT;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.blog.client.view.util.BlogUtils;
import fr.byob.blog.client.view.widget.PostWidget;
import fr.byob.client.view.adapter.IConsoleAdapter;
import fr.byob.client.view.adapter.ITableAdapter;
import fr.byob.client.view.widget.TableWidget;

public class NewsAdapter implements ITableAdapter<IPost>,IConsoleAdapter{

    public final static int titleId = 0;
    public final static int categoryId = 1;
    public final static int textId = 2;
    
    public IPost getManagedObject(TableWidget<IPost> table, int row) {
        IPost post = new PostGWT();
        List<Widget> list = table.getValues(row, 1);
        PostWidget widget = (PostWidget)list.get(0);
        post.setPosttitle(widget.getTitlePost());
        post.setCategoryCollection(widget.getCategoriesPost());
        post.setCommentCollection(widget.getCommentsPost());
        post.setPostdate(widget.getDatePost());
        post.setPosttext(widget.getTextPost());
        post.setUserid(widget.getAuthorPost());
        return post;
    }

    public List<Widget> getTableTitles() {
        List<Widget> list = new ArrayList<Widget>();
        list.add(new Label(BlogStrings.INSTANCE.post()));
        return list;
    }

    public List<Widget> getTableWidgets(IPost object) {
        List<Widget> list = new ArrayList<Widget>();
        PostWidget post = new PostWidget();
        Date datePost = object.getPostdate();
        Date updatePost = object.getPostlastupdate();
        if(object.getCategoryCollection() != null &&object.getCommentCollection() != null && object.getUserid()!= null && object.getCategoryCollection().size() != 0){
            post.setData(object.getCategoryCollection(),object.getPosttitle(),object.getUserid(),datePost,updatePost,object.getCommentCollection(),BlogUtils.getBreakenText(object.getPosttext()),object.getPostid());
        }else if(object.getUserid() != null){
            post.setData(null,object.getPosttitle(),object.getUserid(),datePost,updatePost,null,BlogUtils.getBreakenText(object.getPosttext()),object.getPostid()); 
        }else{
            post.setData(null,object.getPosttitle(),null,datePost,updatePost,null,BlogUtils.getBreakenText(object.getPosttext()),object.getPostid());
        }
        list.add(post);
        return list;
    }

    public String getObjectName() {
        return BlogStrings.INSTANCE.post();
    }
    
}
