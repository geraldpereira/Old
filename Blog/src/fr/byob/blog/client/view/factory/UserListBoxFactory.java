package fr.byob.blog.client.view.factory;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.byob.blog.client.IUser;
import fr.byob.blog.client.proxy.UserServiceProxy;
import fr.byob.client.exception.BYOBException;
import fr.byob.client.view.model.IConsoleModel;
import fr.byob.client.view.widget.ObjectListBoxWidget;

/**
 * Crée une liste sélectionnable de users.
 * 
 * Attention !!! Les données ne sont remplies que lors du lancement de l'application. Toute modification des users n'est ensuite pas prise en
 * compte. Les users n'étant modifiés (ajout / suppression) que très rarement cela n'est pas génant.
 * 
 * @author Kojiro
 * 
 */
public class UserListBoxFactory {

    private static UserListBoxFactory instance = new UserListBoxFactory();

    private UserListBoxFactory() {
    }

    public static UserListBoxFactory getInstance() {
        return instance;
    }

    /**
     * Créer une liste sélectionnable de users
     * @param consoleModel un consoleModel pour afficher les erreurs au cas où. Peut être null.
     * @return Le widget qui va bien
     */
    public ObjectListBoxWidget<IUser> getUserListBoxWidget(final IConsoleModel consoleModel) {
        final ObjectListBoxWidget<IUser> users = new ObjectListBoxWidget<IUser>();
        UserServiceProxy userProxy = UserServiceProxy.getInstance();
        userProxy.findAllObjects(new AsyncCallback<List<IUser>>() {
            public void onFailure(Throwable caught) {
                if (consoleModel != null){
                    consoleModel.setConsoleText(BYOBException.getStringList(caught));
                }
            }

            public void onSuccess(List<IUser> result) {
                for (IUser curUser : result) {
                    users.addElement(curUser);
                }
            }
        });
        return users;
    }
}
