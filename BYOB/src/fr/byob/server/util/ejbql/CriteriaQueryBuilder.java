package fr.byob.server.util.ejbql;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.logging.Logger;

/**
 * Classe de construction d'une requette select portant sur un ensemble de
 * critères passés en paramètres.
 * 
 * @author Kojiro
 * 
 * @param <T> Type des objets sur lesquels porte la recherche
 */
public class CriteriaQueryBuilder<T> {

	// Contient l'EJBQL de la query
	private final StringBuilder ejbql;

	// Liste des critères
	// La clé est le nom du champ sur lequel porte le critère
	// La valeur est le critère de type ICriteria
	private final HashMap<String, ICriteria> criterias;

	// Le logger
	private final Logger log = Logger.getLogger(this.getClass());

	private final boolean count;
	/**
	 * Constructeur
	 * 
	 * @param objectName
	 *            Obligatoire. Le nom de l'objet sur lequel porte la requete.
	 *            Par exemple pour une rechercher sur les utilisateurs (dont
	 *            l'entity est User) on mettra "User"
	 * @param orderField
	 *            Optionnel. Si présent, définit le champ sur lequel doit porter
	 *            le tri du résultat retourné. Le tri est forcément par ordre
	 *            décroissant (à voir pour rendre cela modifiable)
	 * @param criterias
	 *            Obligatoire, mais peut être vide (l'ensemble des objets est
	 *            alors retourné). Ensemble des critères de filtre.
	 *            @param count definit si la query est un select classique ou un count
	 */
	public CriteriaQueryBuilder(final String objectName,
			final String orderField, final HashMap<String, ICriteria> criterias,final boolean count) {
		this.count = count;
		this.criterias = criterias;
		ejbql = new StringBuilder();
		String select; 
		if (count){
			select = "count(*)";
		}else{
			select = "object(o)";
		}
		ejbql.append("select "+select+" from " + objectName + " as o");
		if (!criterias.isEmpty()) {
			ejbql.append(" where");
		}

		boolean and = false;

		for (Map.Entry<String, ICriteria> criteria : criterias.entrySet()) {
			and = criteria.getValue().addCriteriaToEJBQL(ejbql,
					criteria.getKey(), and);
		}
		if (orderField != null) {
			ejbql.append(" ORDER BY o." + orderField + " DESC");
		}
		log.debug("criteriaQueryBuilder : "+ejbql.toString());
	}
	public CriteriaQueryBuilder(final String objectName,
			final String orderField, final HashMap<String, ICriteria> criterias) {
		this(objectName,
				orderField,criterias,false);
	}

	/**
	 * Execute la requette 
	 * @param em EntityManager permettant de créer l'objet Query
	 * @param start offset. Ignoré si null.
	 * @param maxResult Nombre maximum de résultat retournés (si null aucune limite n'est fixée).
	 * @return La liste des objets trouvés ou leur nombre.
	 */
	public Object executeQuery(final EntityManager em,final Integer start, final Integer maxResult) {
		if (log.isDebugEnabled()) {
			log.debug(ejbql.toString());
		}

		final Query query = em.createQuery(ejbql.toString());
		for (Map.Entry<String, ICriteria> criteria : criterias.entrySet()) {
			criteria.getValue().setParameters(query, criteria.getKey());
		}
		if (start != null) {
			query.setFirstResult(start);
		}
		if (maxResult != null) {
			query.setMaxResults(maxResult);
		}
		if (count){
			return query.getSingleResult();
		}else{
			return query.getResultList();	
		}
	}
	
}
