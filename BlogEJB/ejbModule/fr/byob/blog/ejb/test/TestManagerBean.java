package fr.byob.blog.ejb.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import fr.byob.blog.ejb.entity.Post;
import fr.byob.server.util.ejbql.CriteriaQueryBuilder;
import fr.byob.server.util.ejbql.ICriteria;

@Stateless
public class TestManagerBean implements TestManagerRemote {

	Logger log = Logger.getLogger(this.getClass());
	
	private final static String IN_PARAM = "inParam";
	
	@PersistenceContext
	private EntityManager em;

	public List<Post> findPostsByCriteria (final HashMap<String, ICriteria> criterias, final Integer maxResult) {
		CriteriaQueryBuilder<Post> builder = new CriteriaQueryBuilder<Post>("Post","postdate",criterias);
		return (List<Post>) builder.executeQuery(em,0, maxResult);
	}

	public List<Post> findPosts(final HashMap<String, Object> criterias, Integer maxResult) {
		boolean and = false; 
		
		final StringBuilder sb = new StringBuilder();
		sb.append("select distinct object(p) from Post as p");
		if (! criterias.isEmpty()){
			sb.append(" where");
		}
		
		if (criterias.containsKey(USER_CRITERIA)){
			and = equalCriteria(sb,USER_CRITERIA,(String)criterias.get(USER_CRITERIA),and);
		}
		if (criterias.containsKey(TITLE_CRITERIA)){
			and = likeCriteria(sb,TITLE_CRITERIA,(List<String>)criterias.get(TITLE_CRITERIA),and);
		}
		if (criterias.containsKey(TEXT_CRITERIA)){
			and = likeCriteria(sb,TEXT_CRITERIA,(List<String>)criterias.get(TEXT_CRITERIA),and);
		}
		if (criterias.containsKey(DATE_CRITERIA)){
			List<Date> dates = (List<Date>)criterias.get(DATE_CRITERIA);
			Date start = dates.get(0);
			Date end = dates.get(1);
			and = betweenCriteria(sb,DATE_CRITERIA,start,end,and);
		}
		
		if (criterias.containsKey(CATEGORY_CRITERIA)){
			and = inCriteria(sb,CATEGORY_CRITERIA,(List<Integer>)criterias.get(CATEGORY_CRITERIA),and);
		}
		
		
		log.info("=>"+sb.toString()+"<=");
		
		sb.append(" ORDER BY p.postdate DESC");
		final Query query = em.createQuery(sb.toString());
		
		if (criterias.containsKey(CATEGORY_CRITERIA)){
			 for (Integer id : (List<Integer>)criterias.get(CATEGORY_CRITERIA)){
				 query.setParameter(IN_PARAM+id, id);
			 }
		}
		if (criterias.containsKey(DATE_CRITERIA)){
			List<Date> dates = (List<Date>)criterias.get(DATE_CRITERIA);
			Date start = dates.get(0);
			Date end = dates.get(1);
			query.setParameter("startDate", start);
			query.setParameter("endDate", end);
		}
		
		if (maxResult != null){
			query.setMaxResults(maxResult);
		}
		return (List<Post>) query.getResultList();
	}
	
	private boolean betweenCriteria (final StringBuilder sb, final String field, final Date start, final Date end, final boolean and){
		if (and){
			sb.append(" and");
		}
		sb.append(" (p."+field+" >= :startDate AND p."+field+" <= :endDate )");			
		return true;
	}
	
	private boolean equalCriteria (final StringBuilder sb, final String field, final String value, final boolean and){
		if (and){
			sb.append(" and");
		}
		sb.append(" p."+field+"=\'"+value+"\'");			
		return true;
	}
	
	private boolean likeCriteria (final StringBuilder sb, final String field,final List<String> strings, final boolean and){
		boolean privateAnd = and;
		for (String str : strings){
			if (and){
				sb.append(" and");
			}
			sb.append(" p."+field+" like \'%"+str+"%\'");
			privateAnd = true;
		}
		return privateAnd;
	}
	
	private boolean inCriteria (final StringBuilder sb, final String field,final List<Integer> ids, final boolean and){
		boolean privateAnd = and;
		if (and){
			sb.append(" and");
		}
		boolean privateOr = false;
		for (Integer id : ids){
			if (privateOr){
				sb.append(" or");
			}else{
				sb.append(" (");
			}
			sb.append(":"+IN_PARAM+id+" member of p."+field);
			privateOr = true;
			privateAnd = true;
		}
		sb.append(" )");
		return privateAnd;
	}
}
