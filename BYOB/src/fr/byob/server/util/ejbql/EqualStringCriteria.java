package fr.byob.server.util.ejbql;

import javax.persistence.Query;

/**
 * Vérifie qu'un chaine de caractère est égale a une autre
 * 
 * @author Kojiro
 *
 */
public class EqualStringCriteria implements ICriteria {

	private static final long serialVersionUID = 1L;

	private String value;
	
	/**
	 * Valeur à tester
	 * @param value
	 */
	public EqualStringCriteria(final String value){
		this.value = value;
	}
	
	public boolean addCriteriaToEJBQL (final StringBuilder ejbql, final String field, final boolean and){
		if (and){
			ejbql.append(" and");
		}
		
		ejbql.append(" o."+field+"=\'"+value+"\'");			
		return true;
	}

	public void setParameters(Query query, String field) {
	}
	
}
