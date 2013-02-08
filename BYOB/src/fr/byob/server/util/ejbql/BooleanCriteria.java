package fr.byob.server.util.ejbql;

import javax.persistence.Query;

/**
 * Test si une liste de chaines de caractères est présente dans un champ de l'objet sur lequel porte la requete
 * 
 * @author Kojiro
 *
 */
public class BooleanCriteria implements ICriteria{
	
	private static final long serialVersionUID = 1L;

	public final static int AND = 1;
	public final static int OR = 2;
	
	public final static int LIKE = 11;
	public final static int EQUAL = 12;
	
	private final boolean bool;
	private final String op;
	private final int comparisonOperator;

	/**
	 * Constructeur
	 * @param strings La liste de strings a tester
	 * @param logicalOperator savoir si on veut que toute les chaines soient présentes (AND) ou si une seule suffit (OR)
	 * @param comparisonOperator savoir si le test doit être de type like ou =
	 */
	public BooleanCriteria (boolean bool,final int logicalOperator, final int comparisonOperator){
		this.bool = bool;
		this.comparisonOperator = comparisonOperator;
		
		if (logicalOperator == AND){
			this.op = " and";
		}else if (logicalOperator == OR){
			this.op = " or";
		}else{
			this.op = " and";
		}
	}
	
	public boolean addCriteriaToEJBQL (final StringBuilder ejbql, final String field, final boolean and){
		boolean privateAnd = and;
		boolean privateOp = false;
		
		if (and) {
			ejbql.append(" and");
		}
		
//		for (String str : strings){
			if (privateOp) {
				ejbql.append(op);
			} else {
				ejbql.append(" (");
			}
			if (comparisonOperator == LIKE){
				ejbql.append(" o."+field+" like \'%"+bool+"%\'");
			}else if (comparisonOperator == EQUAL){
				ejbql.append(" o."+field+" = \'"+bool+"\'");
			}
			privateOp = true;
			privateAnd = true;
//		}
		ejbql.append(" )");
		return privateAnd;
	}

	public void setParameters(Query query, String field) {
	}
}
