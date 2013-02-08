package fr.byob.server.util.ejbql;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 * Vérifie qu'un date est comprise dans un interval de dates
 * 
 * @author Kojiro
 *
 */
public class BetweenDatesCriteria implements ICriteria{
	
	private static final long serialVersionUID = 1L;

	private final Date start;
	private final Date end;
	
	/**
	 * Constructeur
	 * @param start date de début
	 * @param end date de fin
	 */
	public BetweenDatesCriteria(final Date start,final Date end){
		this.start = start;
		// Pour palier un bug chelou des ejbQL
        Calendar date = Calendar.getInstance();
        date.setTime(end);
        date.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH)+1);
        this.end = date.getTime();
		
	}
	public boolean addCriteriaToEJBQL (final StringBuilder ejbql, final String field, final boolean and){
		if (and){
			ejbql.append(" and");
		}
		ejbql.append(" (o."+field+" >= :start"+field+" AND o."+field+" <= :end"+field+" )");			
		return true;
	}
	
	public void setParameters (final Query query, final String field){
		query.setParameter("start"+field, start,TemporalType.DATE);
		query.setParameter("end"+field, end,TemporalType.DATE);
	}
	
	
	
}

