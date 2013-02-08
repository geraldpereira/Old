package fr.byob.blog.client.view.adapter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.IUser;
import fr.byob.blog.client.model.UserGWT;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.client.view.adapter.IConsoleAdapter;
import fr.byob.client.view.adapter.IFormAdapter;
import fr.byob.client.view.adapter.ITableAdapter;
import fr.byob.client.view.widget.IFormWidget;
import fr.byob.client.view.widget.TableWidget;

public class UserAdapter implements IFormAdapter<IUser>,ITableAdapter<IUser>,IConsoleAdapter{

    public final static int loginId = 0;
    public final static int pwdId = 1;
    public final static String USER = "Utilisateur";
    
    public IUser getManagedObject(TableWidget<IUser> table, int row) {
        IUser user = new UserGWT();
        List<Widget> list = table.getValues(row, 1);
        user.setUserid(((Label) list.get(0)).getText());
        return user;
    }

    public String getObjectName(){
        return USER;
    }

    public List<Widget> getTableTitles() {
        List<Widget> list = new ArrayList<Widget>();
        list.add(new Label(BlogStrings.INSTANCE.userLogin()));
        return list;
    }

    public List<Widget> getTableWidgets(IUser object) {
        List<Widget> list = new ArrayList<Widget>();
        list.add(new Label(object.getUserid()));
        return list;
    }

    public void fillForm(IFormWidget<IUser> form, IUser object) {
        if (form != null) {
            form.setTextElement(loginId, object.getUserid());
            form.setEnabled(loginId,false);
            form.setTextElement(pwdId, object.getPassword());
        }
    }

    public IUser getManagedObject(IFormWidget<IUser> form) {
        if (form != null) {
            IUser user = new UserGWT();
            user.setUserid(form.getTextElement(loginId));
            user.setPassword(form.getTextElement(pwdId));
            return user;
        } else {
            return null;
        }
    }

    public void reinitForm(IFormWidget<IUser> form) {
        if (form != null) {
            form.setTextElement(loginId, "");
            form.setEnabled(loginId,true);
            form.setTextElement(pwdId, "");
        }
    }

    public String getFormTitle() {
        return BlogStrings.INSTANCE.userInfo();
    }


    public IUser getManagedObject(IFormWidget<IUser> form, IUser t) {
        if (form != null) {
            IUser user = getManagedObject(form);
            t.setPassword(user.getPassword());
            return t;
        } else {
            return null;
        }
    }

}
