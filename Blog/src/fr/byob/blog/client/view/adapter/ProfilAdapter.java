package fr.byob.blog.client.view.adapter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import fr.byob.blog.client.IProfil;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.model.ProfilGWT;
import fr.byob.blog.client.model.UserGWT;
import fr.byob.blog.client.view.util.BlogStrings;
import fr.byob.blog.client.view.widget.ProfilWidget;
import fr.byob.client.view.adapter.IConsoleAdapter;
import fr.byob.client.view.adapter.ITableAdapter;
import fr.byob.client.view.widget.TableWidget;

public class ProfilAdapter implements ITableAdapter<IProfil>,IConsoleAdapter{

    public final static int loginId = 0;
    public final static int pwdId = 1;
    public final static int mailId = 2;
    public final static int presentationId = 3;
    public final static int dateId = 4;
    public final static int cssId = 5;
    public final static int photoId = 6;
    public final static String USERPROFIL = "Profil";
    
    public IProfil getManagedObject(TableWidget<IProfil> table, int row) {
        IProfil profil = new ProfilGWT();
        IUser user = new UserGWT();
        List<Widget> list = table.getValues(row, 1);
        ProfilWidget widget = (ProfilWidget)list.get(0);
        user.setUserid(widget.getLogin());
        profil.setUserid(user);
        profil.setProfildate(widget.getDate());
        profil.setProfilmail(widget.getMail());
        profil.setProfilpresentation(widget.getPresentation());
        return profil;
    }

    public String getObjectName() {
        return USERPROFIL;
    }

    public List<Widget> getTableTitles() {
        List<Widget> list = new ArrayList<Widget>();
        list.add(new Label(BlogStrings.INSTANCE.userLogin()));
        return list;
    }

    public List<Widget> getTableWidgets(IProfil object) {
        List<Widget> list = new ArrayList<Widget>();
        ProfilWidget user = new ProfilWidget();
        user.setData(object.getUserid().getUserid(), object.getProfilmail(), object.getProfilinscription(), object.getProfilpresentation(),object.getProfilphoto());
        list.add(user);
        return list;
    }


}
