package fr.byob.server.util.ejbql;

import java.util.List;

import javax.persistence.Query;

/**
 * Test si une liste d'objet fait partie d'une liste d'autres objets  
 * 
 * @author Kojiro
 *
 */
public class MemberOfCriteria implements ICriteria {

	private static final long serialVersionUID = 1L;
	
	private final List<String> ids;

	/**
	 * Constructeur
	 * @param La liste des identifiants d'objets dont on va tester l'appartenance a un champ (de type Collection bien evidement !)
	 */
	public MemberOfCriteria(final List<String> ids) {
		this.ids = ids;
	}

	public boolean addCriteriaToEJBQL(final StringBuilder ejbql,
			final String field, final boolean and) {
		boolean privateAnd = and;
		if (and) {
			ejbql.append(" and");
		}
		boolean privateOr = false;
		for (String id : ids) {
			if (privateOr) {
				ejbql.append(" or");
			} else {
				ejbql.append(" (");
			}
			ejbql.append(":" + field + id + " member of o." + field);
			privateOr = true;
			privateAnd = true;
		}
		ejbql.append(" )");
		return privateAnd;
	}

	public void setParameters(Query query, String field) {
		for (String id : ids) {
			query.setParameter(field+ id, id);
		}
	}
}
