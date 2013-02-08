package fr.byob.blog.client.view.factory;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.byob.blog.client.ICategory;
import fr.byob.blog.client.proxy.CategoryServiceProxy;
import fr.byob.client.exception.BYOBException;
import fr.byob.client.view.model.IConsoleModel;
import fr.byob.client.view.widget.ObjectListBoxWidget;

/**
 * Crée une liste sélectionnable de catégories.
 * 
 * Attention !!! Les données ne sont remplies que lors du lancement de l'application. Toute modification des catégories n'est ensuite pas prise en
 * compte. Les catégories n'étant modifiées (ajout / suppression) que très rarement cela n'est pas gènant.
 * 
 * @author Kojiro
 * 
 */
public class CategoryListBoxFactory {

    private static CategoryListBoxFactory instance = new CategoryListBoxFactory();

    private CategoryListBoxFactory() {
    }

    public static CategoryListBoxFactory getInstance() {
        return instance;
    }

    /**
     * Créer une liste sélectionnable de catégories
     * @param consoleModel un consoleModel pour afficher les erreurs au cas où. Peut être null.
     * @return Le widget qui va bien
     */
    public ObjectListBoxWidget<ICategory> getCategoryListBoxWidget(final IConsoleModel consoleModel) {
        final ObjectListBoxWidget<ICategory> categories = new ObjectListBoxWidget<ICategory>();
        CategoryServiceProxy categoryProxy = CategoryServiceProxy.getInstance();
        categoryProxy.findAllObjects(new AsyncCallback<List<ICategory>>() {
            public void onFailure(Throwable caught) {
                if (consoleModel != null){
                    consoleModel.setConsoleText(BYOBException.getStringList(caught));
                }
            }

            public void onSuccess(List<ICategory> result) {
                for (ICategory curCat : result) {
                    categories.addElement(curCat);
                }
            }
        });
        return categories;
    }
}
