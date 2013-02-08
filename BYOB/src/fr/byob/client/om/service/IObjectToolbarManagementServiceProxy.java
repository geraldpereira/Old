package fr.byob.client.om.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Proxy vers le service utilisé pour la gestion des objets
 * 
 * @author Kojiro
 *
 * @param <T> le type des objets
 */
public interface IObjectToolbarManagementServiceProxy<T> extends IObjectManagementServiceProxy<T>{
    /**
     * Récupère la liste des objets
     * @param callback
     */
    public void findObjects(final int nbStart,final int nbEnd,final AsyncCallback<List<T>> callback);

    /**
     * Compte les objets 
     * @param callback
     */
    public void countObjects(final AsyncCallback<Integer> callback);
}
