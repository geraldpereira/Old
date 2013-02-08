package fr.byob.client.om.controler;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.byob.client.exception.BYOBException;
import fr.byob.client.om.service.IObjectManagementServiceProxy;
import fr.byob.client.om.service.IObjectToolbarManagementServiceProxy;
import fr.byob.client.om.view.IObjectManagementPanel;
import fr.byob.client.util.Css;
import fr.byob.client.view.controler.IConsoleControler;
import fr.byob.client.view.controler.IFormControler;
import fr.byob.client.view.controler.ITableControler;
import fr.byob.client.view.controler.IToolbarControler;
import fr.byob.client.view.model.IConsoleModel;
import fr.byob.client.view.model.IFormModel;
import fr.byob.client.view.model.ITableModel;
import fr.byob.client.view.model.IToolbarModel;
import fr.byob.client.view.widget.SimpleFormWidget;
import fr.byob.client.view.widget.anim.AnimView;

/**
 * 
 * Controler a tout faire question OM. Le formModel et le toolbarModel sont
 * optionnels. Gère l'ajout, la modification et la suppression d'objet, le
 * rafraichissement de table (chargement de tous les OM), et la sélection d'une
 * page dans la toolbar.
 * 
 * @author pereirag
 * 
 * @param <T>
 */
public class ObjectManagementControler<T> implements
		IObjectManagementControler<T>, IConsoleControler, IFormControler<T>,
		ITableControler<T>, IToolbarControler {

    private Css css = Css.INSTANCE;
	// Model de console
	protected IConsoleModel consoleModel;
	// De formulaire
	private IFormModel<T> formModel;
	// De table
	protected ITableModel<T> tableModel;
	// De toolbar
	protected IToolbarModel toolbarModel;

	// Vue
	protected IObjectManagementPanel<T> view;

	// Service utilisé pour l'accès aux objets
	protected final IObjectManagementServiceProxy<T> service;
	
	private final AnimView anim = AnimView.getInstance();

	/**
	 * Constructeur Juste le service, les modele et la vue sont a passer via les
	 * méthodes adéquates.
	 * 
	 * @param service
	 *            le service d'acces aux objets. Attention ! Doit être de type
	 *            IObjectToolbarManagementServiceProxy si une toolbar est
	 *            présente
	 */
	public ObjectManagementControler(IObjectManagementServiceProxy<T> service) {
		this.service = service;
	}

	public void setView(IObjectManagementPanel<T> view) {
		this.view = view;
	}

	public void setConsoleModel(IConsoleModel consoleModel) {
		this.consoleModel = consoleModel;
	}

	public void setFormModel(IFormModel<T> formModel) {
		this.formModel = formModel;
	}

	public void setTableModel(ITableModel<T> tableModel) {
		this.tableModel = tableModel;
	}

	public void setToolbarModel(IToolbarModel toolbarModel) {
		this.toolbarModel = toolbarModel;
	}

	public ClickHandler getClickOnAddListener() {
		return new ClickHandler() {
            public void onClick(ClickEvent event) {
                addObject();
            }
		};
	}

	public ClickHandler getClickOnModListener() {
		return new ClickHandler() {
		    public void onClick(ClickEvent event) {
				modifyObject();
			}
		};
	}

	public ClickHandler getClickOnDelListener() {
		return new ClickHandler() {
		    public void onClick(ClickEvent event) {
				deleteObject();
			}
		};
	}
	public ClickHandler getClickOnTableListener() {
		return new ClickHandler(){

            public void onClick(ClickEvent event) {
                int row =  view.getTable().getFlexTable().getCellForEvent(event).getRowIndex(); 
                if (view.getTable().isNewRowIndex(row)) { // Si on clic sur
                    // nouveau
                    // selection en table
                    tableModel.selectObject(ITableModel.NEW_SELECTED_INDEX);
                    if (formModel != null) {
                        // Passage en mode ajout
                        formModel.setAddOnlyMode();
                    }
                } else if (!view.getTable().isHeaderRowIndex(row)) { // Si on
                    // clic
                    // sur
                    // un
                    // objet
                    // Selection de l objet dans le model
                    tableModel.selectObject(row);
                    // Set le mode en fonction des boutons presents
                    if (formModel != null) {
                        if (view.getForm().isButtonPresent(
                                SimpleFormWidget.MOD_BUTTON_ID)) {
                            formModel.setModDelMode(tableModel
                                    .getSelectedObject());
                        } else {
                            formModel.setDelOnlyMode(tableModel
                                    .getSelectedObject());
                        }
                    }
                } else { // Sinon (cas peu probable : il n'est actuellement pas
                    // possible de déselectionner la table ... mais ca
                    // poura toujours servir)
                    // On repasse en mode inital
                    if (formModel != null) {
                        formModel.setInitMode();
                    }
                }
            }
		    
		};
	}

	private void addObject() {
		AsyncCallback<T> callback = new AsyncCallback<T>() {
			public void onSuccess(T result) {
				// Ajout de l'objet
				tableModel.addObject(result);
				// Déselection d'index
				tableModel.selectObject(ITableModel.UNSELECTED_INDEX);
				manageTableVisibility();
				// Passage en mode initial
				if (formModel != null) {
					formModel.setInitMode();
				}
				// Rafraichissement du model si une toolbar est présente
				// En effet pour plus de simplicité on rafraichi toute la table
				// lors de la modification (uniquement si une toolbar est
				// présente)
				if (toolbarModel != null) {
					refreshModel();
				}
				consoleModel.setConsoleText(IConsoleModel.ADDED);
			}

			public void onFailure(Throwable caught) {
				refreshToolbarModelFailure(caught);
			}
		};

		service.addObject(view.getForm().getNewObject(), callback);
	}

	@SuppressWarnings("unchecked")
	// Le callback ne retourne aucune résultat
	private void deleteObject() {
		// Attention : ne pas donner le wildcard comme type paramètré au
		// callback :
		// Cela procoque une erreur de compilation
		AsyncCallback callback = new AsyncCallback() {
			public void onSuccess(Object result) {
				tableModel.deleteObject();
				tableModel.selectObject(ITableModel.UNSELECTED_INDEX);
				manageTableVisibility();
				if (formModel != null) {
					formModel.setInitMode();
				}
				if (toolbarModel != null) {
					refreshModel();
				}
				consoleModel.setConsoleText(IConsoleModel.DELETED);
			}

			public void onFailure(Throwable caught) {
				refreshToolbarModelFailure(caught);
			}
		};
		// L'objet récupéré du model est complet (il à un id). Il ne faut pas le
		// récupérer via l'adapter !
		service.removeObject(tableModel.getSelectedObject(), callback);

	}

	private void modifyObject() {
		AsyncCallback<T> callback = new AsyncCallback<T>() {
			public void onSuccess(T result) {
				tableModel.modifyObject(result);
				tableModel.selectObject(ITableModel.UNSELECTED_INDEX);
				consoleModel.setConsoleText(IConsoleModel.MODIFIED);
				if (formModel != null) {
					formModel.setInitMode();
				}
			}

			public void onFailure(Throwable caught) {
				refreshToolbarModelFailure(caught);
			}
		};

		// L'objet récupéré du model est complet (il à un id). Il ne faut pas le
		// récupérer via l'adapter !
		service.modifyObject(view.getForm().getModifiedObject(
				tableModel.getSelectedObject()), callback);

	}

	public void refreshModel() {
		if (toolbarModel != null) {
			AsyncCallback<Integer> callback1 = new AsyncCallback<Integer>() {

				public void onSuccess(Integer result) {
					toolbarModel.setMaxData(result);
					toolbarModel.setOperationToolbar(
							IToolbarModel.PREV_OPERATION, false);
					nbStart = 0;
					nbEnd = nbStart + toolbarModel.getNbData();
					if (nbEnd >= toolbarModel.getMaxData()) {
						nbEnd = toolbarModel.getMaxData();
						toolbarModel.setOperationToolbar(
								IToolbarModel.NEXT_OPERATION, false);
					} else {
						toolbarModel.setOperationToolbar(
								IToolbarModel.NEXT_OPERATION, true);
					}
					((IObjectToolbarManagementServiceProxy<T>) service)
							.findObjects(nbStart, nbEnd, refreshCallback);
				}

				public void onFailure(Throwable caught) {
					refreshToolbarModelFailure(caught);
				}

			};
			view.setStyleName(css.opaque());
			
			view.setVisibleAll(false);
            view.addWidget(anim);
            anim.startTimer();
            
			
			consoleModel.setConsoleText("");
			((IObjectToolbarManagementServiceProxy<T>) service)
					.countObjects(callback1);
		} else {
		    
			AsyncCallback<List<T>> callback = new AsyncCallback<List<T>>() {

				public void onSuccess(List<T> result) {
					tableModel.setObjects(result);
					tableModel.selectObject(ITableModel.UNSELECTED_INDEX);
					view.removeStyleName(css.opaque());
					
					view.removeWidget(anim);
					view.setVisibleAll(true);
					anim.stopTimer();
					
					manageTableVisibility();
					initForm();
				}

				public void onFailure(Throwable caught) {
					refreshToolbarModelFailure(caught);
				}

			};
			view.setStyleName(css.opaque());
			
			
			view.setVisibleAll(false);
			view.addWidget(anim);
			anim.startTimer();
			
			consoleModel.setConsoleText("");
			service.findAllObjects(callback);
		}
	}

	// Gère l'affichage de la table on fonction de son contenu : une table vide
	// n'est pas affichée
	protected void manageTableVisibility() {
		view.setVisibleTable(tableModel.isNotEmpty());
		if (!tableModel.isNotEmpty()) {
			consoleModel.setConsoleText(IConsoleModel.NODATA);
		}
	}

	protected void initForm() {
		if (formModel != null && view.getForm() != null) {
			// Si seul le bouton add est présent il est directement affiche
			boolean isAddPresent = view.getForm().isButtonPresent(
					SimpleFormWidget.ADD_BUTTON_ID);
			boolean isModPresent = view.getForm().isButtonPresent(
					SimpleFormWidget.MOD_BUTTON_ID);
			boolean isDelPresent = view.getForm().isButtonPresent(
					SimpleFormWidget.DEL_BUTTON_ID);

			if (isAddPresent && !isModPresent && !isDelPresent) {
				formModel.setAddOnlyMode();
			} else {
				formModel.setInitMode();
			}
		}
	}

	protected void refreshToolbarModelFailure(Throwable caught) {
		BYOBException.redirectIfRequested(caught);
		consoleModel.setConsoleText(BYOBException.getStringList(caught));
	}

	private int nbStart = 0;
	private int nbEnd = 0;

	public ClickHandler createFirstPageListener() {
		return new ClickHandler() {
		    public void onClick(ClickEvent event) {
				refreshFirstModel();
			}
		};
	}

	public ClickHandler createPrevPageListener() {
		return new ClickHandler() {
		    public void onClick(ClickEvent event) {
				refreshPrevModel();
			}
		};
	}

	public ClickHandler createNextPageListener() {
		return new ClickHandler() {
            public void onClick(ClickEvent event) {
                refreshNextModel();
            }
		};
	}

	public ClickHandler createLastPageListener() {
		return new ClickHandler() {
		    public void onClick(ClickEvent event) {
				refreshLastModel();
			}
		};
	}

	public void refreshFirstModel() {
		view.setStyleName(css.opaque());
		
		view.setVisibleAll(false);
        view.addWidget(anim);
        anim.startTimer();
        
		
		consoleModel.setConsoleText("");
		toolbarModel.setOperationToolbar(IToolbarModel.PREV_OPERATION, false);
		nbStart = 0;
		nbEnd = nbStart + toolbarModel.getNbData();
		if (nbEnd >= toolbarModel.getMaxData()) {
			nbEnd = toolbarModel.getMaxData();
			toolbarModel.setOperationToolbar(IToolbarModel.NEXT_OPERATION,
					false);
		} else {
			toolbarModel
					.setOperationToolbar(IToolbarModel.NEXT_OPERATION, true);
		}
		((IObjectToolbarManagementServiceProxy<T>) service).findObjects(
				nbStart, nbEnd, refreshCallback);
	}

	public void refreshPrevModel() {
		view.setStyleName(css.opaque());
		consoleModel.setConsoleText("");
		nbStart = nbStart - toolbarModel.getNbData();
		if (nbStart <= 0) {
			nbStart = 0;
			toolbarModel.setOperationToolbar(IToolbarModel.PREV_OPERATION,
					false);
		} else {
			toolbarModel
					.setOperationToolbar(IToolbarModel.PREV_OPERATION, true);
		}
		nbEnd = nbStart + toolbarModel.getNbData();
		if (nbEnd > toolbarModel.getMaxData()) {
			nbEnd = toolbarModel.getMaxData();
			toolbarModel.setOperationToolbar(IToolbarModel.NEXT_OPERATION,
					false);
		} else {
			toolbarModel
					.setOperationToolbar(IToolbarModel.NEXT_OPERATION, true);
		}
		((IObjectToolbarManagementServiceProxy<T>) service).findObjects(
				nbStart, nbEnd, refreshCallback);
	}

	public void refreshNextModel() {
		view.setStyleName(css.opaque());
		
		view.setVisibleAll(false);
        view.addWidget(anim);
        anim.startTimer();
        
		
		consoleModel.setConsoleText("");
		if (nbStart + toolbarModel.getNbData() <= toolbarModel.getMaxData() - 1) {
			nbStart = nbStart + toolbarModel.getNbData();
			nbEnd = nbStart + toolbarModel.getNbData();
		}
		if (nbEnd >= toolbarModel.getMaxData()) {
			nbEnd = toolbarModel.getMaxData();
			toolbarModel.setOperationToolbar(IToolbarModel.NEXT_OPERATION,
					false);
		} else {
			toolbarModel
					.setOperationToolbar(IToolbarModel.NEXT_OPERATION, true);
		}
		if (nbStart == 0) {
			toolbarModel.setOperationToolbar(IToolbarModel.PREV_OPERATION,
					false);
		} else {
			toolbarModel
					.setOperationToolbar(IToolbarModel.PREV_OPERATION, true);
		}
		((IObjectToolbarManagementServiceProxy<T>) service).findObjects(
				nbStart, nbEnd, refreshCallback);
	}

	public void refreshLastModel() {
		view.setStyleName(css.opaque());
		
		view.setVisibleAll(false);
        view.addWidget(anim);
        anim.startTimer();
        
		
		consoleModel.setConsoleText(Arrays.asList(new String[] { "" }));
		nbEnd = toolbarModel.getMaxData();
		if (toolbarModel.getMaxData() > toolbarModel.getNbData()
				&& nbEnd + nbStart >= toolbarModel.getMaxData()) {
			if (nbEnd + (toolbarModel.getMaxData() % toolbarModel.getNbData()) == toolbarModel
					.getMaxData()) {
				nbStart = nbEnd - toolbarModel.getNbData();
			} else {
				nbStart = nbEnd
						- (toolbarModel.getMaxData() % toolbarModel.getNbData());
			}
		} else {
			nbStart = 0;
		}
		// if (nbEnd + nbStart > toolbarModel.getMaxData()) {
		// nbStart = nbEnd
		// - (toolbarModel.getNbData() % toolbarModel.getMaxData());
		// }
		if (nbEnd > toolbarModel.getMaxData()) {
			nbEnd = toolbarModel.getMaxData();

		}
		toolbarModel.setOperationToolbar(IToolbarModel.NEXT_OPERATION, false);
		if (nbStart <= 0) {
			nbStart = 0;
			toolbarModel.setOperationToolbar(IToolbarModel.PREV_OPERATION,
					false);
		} else {
			toolbarModel
					.setOperationToolbar(IToolbarModel.PREV_OPERATION, true);
		}
		((IObjectToolbarManagementServiceProxy<T>) service).findObjects(
				nbStart, nbEnd, refreshCallback);
	}

	private AsyncCallback<List<T>> refreshCallback = new AsyncCallback<List<T>>() {
		public void onSuccess(List<T> result) {
			refreshToolbarModelSuccess(result);

		}

		public void onFailure(Throwable caught) {
			refreshToolbarModelFailure(caught);
		}
	};

	private void refreshToolbarModelSuccess(List<T> result) {
		tableModel.setObjects(result);
		toolbarModel.setStartAndEndDate(nbStart, nbEnd);
		tableModel.selectObject(ITableModel.UNSELECTED_INDEX);
		view.setVisibleTable(tableModel.isNotEmpty());
		if (!tableModel.isNotEmpty()) {
			consoleModel.setConsoleText(IConsoleModel.NODATA);
		}
		view.removeStyleName(css.opaque());
		
        view.removeWidget(anim);
        view.setVisibleAll(true);
        anim.stopTimer();
		
		initForm();
	}

	/**
	 * Un main de test pardi !!!
	 * @param args
	 */
	public static void main(String[] args) {
		int nbEnd = 0;
		int nbStart = 0;
		int maxData = 7;
		int nbData = 5;
		nbEnd = maxData;
		// if (nbEnd + nbStart >maxData) {
		if (maxData > nbData && nbEnd + nbStart >= maxData) {
			if (nbEnd + (maxData % nbData) == maxData) {
				nbStart = nbEnd - nbData;
			} else {
				nbStart = nbEnd - (maxData % nbData);
			}
		} else {
			nbStart = 0;
		}
		// }
		if (nbEnd > maxData) {
			nbEnd = maxData;

		}
	}
}
