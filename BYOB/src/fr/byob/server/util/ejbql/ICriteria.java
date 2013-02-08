package fr.byob.server.util.ejbql;

import java.io.Serializable;

import javax.persistence.Query;

/**
 * 
 * Définit le foncitonnement des critères de recherche
 * 
 * @author Kojiro
 * 
 */
public interface ICriteria extends Serializable {

	/**
	 * Ajout le critère à l'ejbql passé en paramètre. On suppose l'objet sur
	 * lequel porte le critere a pour alias o. On poura voir plus tard pour
	 * passer cet alias en paramètre (si on a un jour des requetes plus poussées
	 * a faire)
	 * 
	 * @param ejbql
	 *            l'ejbql a critériser
	 * @param field
	 *            le champ de l'objet sur lequel porte le critere
	 * @param and
	 *            boolean permettant de savoir s'il faut ajouter un "AND" à
	 *            l'ejbql (si d'autres critères ont été ajoutés)
	 * @return la valeur du and : il se peut qu'elle ne soit pas modifiée si
	 *         l'ajout si critère à l'ejbql n'a rien donné (cas d'une liste vide
	 *         par exemple)
	 */
	public boolean addCriteriaToEJBQL(final StringBuilder ejbql,
			final String field, final boolean and);

	/**
	 * Affecte les paramètres adéquats (et si necessaire) a la requette. 
	 * @param query Requette construite a partir de l'ejbql passé en paramètres aux critères via la méthode addCriteriaToEJBQL.
	 * @param field Champ si lequel porte le critere
	 */
	public void setParameters(final Query query, final String field);
}
