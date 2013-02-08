package fr.byob.blog.client.view.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.IComment;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.model.CommentGWT;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.model.UserGWT;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.blog.client.view.widget.CommentWidget;
import fr.byob.client.util.Utils;
import fr.byob.client.view.adapter.IConsoleAdapter;
import fr.byob.client.view.adapter.IFormAdapter;
import fr.byob.client.view.adapter.ITableAdapter;
import fr.byob.client.view.widget.IFormWidget;
import fr.byob.client.view.widget.TableWidget;

public class CommentAdapter implements IFormAdapter<IComment>,ITableAdapter<IComment>,IConsoleAdapter{

    public final static int mailId = 0;
    public final static int authorId = 1;
    public final static int dateId = 2;
    public final static int textId = 3;
    
    public IComment getManagedObject(TableWidget<IComment> table, int row) {
    	IComment comment = new CommentGWT();
        List<Widget> list = table.getValues(row, 1);
        CommentWidget widget = (CommentWidget)list.get(0);
        comment.setCommentautor(widget.getAuthorPost().getUserid());
        comment.setCommenttext(widget.getTextPost());
        return comment;
    }

    public String getObjectName() {
        return BlogStrings.INSTANCE.commentUpper();
    }

    public List<Widget> getTableTitles() {
        List<Widget> list = new ArrayList<Widget>();
        list.add(new Label(BlogStrings.INSTANCE.commentUpper()));
        return list;
    }

    public List<Widget> getTableWidgets(IComment object) {
    	ArrayList<Widget> list = new ArrayList<Widget>();
    	CommentWidget comment = new CommentWidget();
        IUser user = new UserGWT();
        user.setUserid(object.getCommentautor());
        comment.setData(user,object.getCommentdate(),object.getCommenttext());
        comment.setWidth("100%");
        list.add(comment);
        return list;
    }
    
    public void fillForm(IFormWidget<IComment> form, IComment object) {
        form.setTextElement(authorId,object.getCommentautor());
        form.setEnabled(authorId,false);
        form.setTextElement(mailId,object.getCommentmail());
        form.setEnabled(mailId,false);
        form.setTextElement(dateId,Utils.formatDate(object.getCommentdate()));
        form.setEnabled(dateId,false);
        form.setTextElement(textId,object.getCommenttext());
        form.setEnabled(textId,false);
    }

    public IComment getManagedObject(IFormWidget<IComment> form) {
    	IComment comment = new CommentGWT();
    	comment.setCommentautor(form.getTextElement(authorId));
    	comment.setCommentmail(form.getTextElement(mailId));
    	comment.setCommenttext(form.getTextElement(textId));
    	comment.setCommentdate(new Date());
        return comment;
    }

    public void reinitForm(IFormWidget<IComment> form) {
    	form.setTextElement(mailId,"");
    	if(ConnectedUserModel.getInstance().getConnectedUser() != null){
    	    form.setTextElement(authorId,ConnectedUserModel.getInstance().getConnectedUser().getUserid());
    	    form.setEnabled(authorId,false);
    	}else{
    	    form.setTextElement(authorId,"");
    	    form.setEnabled(authorId,true);
    	}
    	if(ConnectedUserModel.getInstance().getConnectedUserProfil() != null && ConnectedUserModel.getInstance().getConnectedUserProfil().getProfilmail() != null && !ConnectedUserModel.getInstance().getConnectedUserProfil().getProfilmail().equals("")){
    	    form.setTextElement(mailId,ConnectedUserModel.getInstance().getConnectedUserProfil().getProfilmail());
            form.setEnabled(mailId,false);
    	}else{
    	    form.setTextElement(mailId,"");
    	    form.setEnabled(mailId,true);    
    	}
    	
        form.setTextElement(textId,"");
        form.setEnabled(textId,true);
        form.setTextElement(dateId,Utils.formatDate(new Date()));
        form.setEnabled(dateId,false);
    }

    public int getTableColNumber() {
        return 1;
    }

    public IComment getManagedObject(IFormWidget<IComment> form, IComment t) {
        throw new UnsupportedOperationException();
    }

    public String getFormTitle() {
        return BlogStrings.INSTANCE.commentCreate();
    }
    

}
