package fr.byob.blog.client.controler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import fr.byob.blog.client.Constants;
import fr.byob.blog.client.IProfil;
import fr.byob.blog.client.IUser;
import fr.byob.blog.client.model.ConnectedUserModel;
import fr.byob.blog.client.service.ProfilService;
import fr.byob.blog.client.service.ProfilServiceAsync;
import fr.byob.blog.client.service.UserService;
import fr.byob.blog.client.service.UserServiceAsync;
import fr.byob.client.util.Utils;

/**
 * Controleur pour l'utilisateur connecté.
 * Permet de se connecter / déconnecter ainsi que diverses autres fonctions liés à l'utilisateur.
 * 
 * @author gpereira
 *
 */
public class ConnectedUserControler{

    private final UserServiceAsync svc;
    private final ProfilServiceAsync svcProfil;
    private final StringsControler strings;
    private final ConnectedUserModel model;

    /**
     * Constructeur
     * Initialise le service pour communiquer avec le serveur.
     */
    public ConnectedUserControler(final ConnectedUserModel model){
        svc = (UserServiceAsync) GWT.create(UserService.class);
        ServiceDefTarget endpoint = (ServiceDefTarget) svc;
        String moduleRelativeURL = GWT.getModuleBaseURL() + "userService";
        endpoint.setServiceEntryPoint(moduleRelativeURL);

        svcProfil = (ProfilServiceAsync) GWT.create(ProfilService.class);
        ServiceDefTarget endpointProfil = (ServiceDefTarget) svcProfil;
        String moduleRelativeURLProfil = GWT.getModuleBaseURL() + "profilService";
        endpointProfil.setServiceEntryPoint(moduleRelativeURLProfil);

        strings = (StringsControler) GWT.create(StringsControler.class);
        this.model = model;
    }

    /**
     * Retourne le ClickListener utilisable lors du click sur un lien de connection.
     * Lors du click l'utilisateur est redirigé sur la page sécurisée mettant ainsi en marche le système d'authentification du serveur d'application
     * @return le ClickListener utilisable lors du click sur un lien de connection.
     */
    public ClickHandler getClickOnConnectionLinkListener(){
        return new ClickHandler (){
            public void onClick(ClickEvent event) {
                Utils.redirect(Constants.SECURED_PAGE);
            }
        };
    }

    /**
     * Retourne le click listener utilisable lors d'une déconnection
     * @return le click listener utilisable lors d'une déconnection
     */
    public ClickHandler getClickOnLogoutLinkListener(){
        return new ClickHandler (){

            public void onClick(ClickEvent event) {
                logout();
                //				Window.addCloseHandler(windowCloseListener);
            }
        };
    }

    public CloseHandler<Window> getWindowOnLogoutCloseListener(){
        return windowCloseListener;
    }

    private CloseHandler<Window> windowCloseListener = windowOnLogoutCloseListener();
    private CloseHandler<Window> windowOnLogoutCloseListener(){
        return new CloseHandler<Window>(){
            public void onClose(CloseEvent<Window> event) {
                logout();
            }
        };
    }

    /**
     * Récupère l'utilisateur connecté (sur le serveur via le service instancié à la construction de cette classe)
     * Modifie le modèle en conséquence.
     */
    public void getConnectedUser(){

        AsyncCallback<IUser> callback = new AsyncCallback<IUser>() {
            public void onSuccess (IUser result)
            {
                if (result == null){
                    model.logginUser(null);
                    model.logginUserProfil(null);
                }else{
                    model.logginUser(result);  
                    if(model.getConnectedUserRole() == ConnectedUserModel.ROLE_USER){
                        AsyncCallback<IProfil> callbackProfil = new AsyncCallback<IProfil>() {

                            public void onFailure(Throwable caught) {
                                model.logginUserProfil(null);
                            }

                            public void onSuccess(IProfil result) {
                                model.logginUserProfil(result);
                            }

                        };
                        svcProfil.findProfil(result.getUserid(), callbackProfil);
                    }else{
                        model.logginUserProfil(null);
                    }
                }

            }

            public void onFailure (Throwable ex)
            {
                Window.alert(strings.pbServer());
            }
        };
        svc.getConnectedUser(callback);
    }

    /**
     * Déconnecte l'utilisateur courant.
     */
    private void logout(){
        if(model.getConnectedUserRole() == ConnectedUserModel.ROLE_USER){
            AsyncCallback<IUser> callback = new AsyncCallback<IUser>() {
                public void onSuccess (IUser result)
                {
                    model.logginUser(null);
                    model.logginUserProfil(null);
                    Utils.redirect(Constants.HOME_PAGE);
                }

                public void onFailure (Throwable ex)
                {
//                    Window.alert(strings.pbServer());
                }
            };
            svc.logout(model.getConnectedUserProfil().getProfilstatut(),callback);
        }else{
//            AsyncCallback<IUser> callback = new AsyncCallback<IUser>() {
//
//                public void onSuccess (IUser result)
//                {
                    model.logginUser(null);
                    model.logginUserProfil(null);
                    Utils.redirect(Constants.HOME_PAGE);
//                }
//
//                public void onFailure (Throwable ex)
//                {
//                    Window.alert(strings.pbServer());
//                }
//            };
//            svc.logout(null,callback);
        }
    }
}
