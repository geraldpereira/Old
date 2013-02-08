package fr.byob.client.view.adapter;

import fr.byob.client.view.widget.IFormWidget;

/**
 * Adapte pour le forumlaire
 * Permet de passer d'un OM au remplissage du form et inverssement.
 * 
 * @author pereirag
 *
 * @param <T>
 */
public interface IFormAdapter<T> {
	
	public String getFormTitle();
	/**
	 * Remplie le formulaire avec les données de l'objet
	 * 
	 * @param form
	 *            le formulaire à remplir
	 * @param object
	 *            l'objet source de données
	 */
	public void fillForm(final IFormWidget<T> form,final T object);

	/**
	 * Réinitialise le formulaire passé en paramètre (par exemple vidage des
	 * champs)
	 * 
	 * @param form
	 *            le formulaire à réinitialiser
	 */
	public void reinitForm(final IFormWidget<T> form);

	/**
	 * Retourne l'objet métier correspondant aux données du formulaire
	 * 
	 * @param form
	 *            formulaire source de données
	 * @return object correspondant
	 */
	public T getManagedObject(final IFormWidget<T> form);

	/**
	 * Retourne l'objet métier correspondant aux données du formulaire et de
	 * lobjet T passé en paramètre (généralement récupéré du model, pour avoir
	 * l'id de l'objet) A utiliser pour la modification d'un objet via le
	 * formulaire donc.
	 * 
	 * @param form
	 *            formulaire source de données
	 * @param T
	 *            l'objet récupéré du model (contient ID etc)
	 * @return object correspondant
	 */
	public T getManagedObject(final IFormWidget<T> form, T t);

}
