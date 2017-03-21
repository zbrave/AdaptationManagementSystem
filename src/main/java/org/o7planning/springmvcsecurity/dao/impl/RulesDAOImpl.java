package org.o7planning.springmvcsecurity.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.o7planning.springmvcsecurity.dao.RulesDAO;
import org.o7planning.springmvcsecurity.entity.Rules;
import org.o7planning.springmvcsecurity.model.RulesInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class RulesDAOImpl implements RulesDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public Rules findRules(Integer id) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Rules.class);
        crit.add(Restrictions.eq("id", id));
        return (Rules) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RulesInfo> listRulesInfos() {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + RulesInfo.class.getName()//
                + "(a.id, a.takingLessonId, a.substituteLessonId) "//
                + " from " + Rules.class.getName() + " a ";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RulesInfo> listRulesForLesson(Integer id) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + RulesInfo.class.getName()//
                + "(a.id, a.takingLessonId, a.substituteLessonId) "//
                + " from " + Rules.class.getName() + " a where a.takingLessonId = :code";
//		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        query.setParameter("code", id);
        return query.list();
	}

	@Override
	public void saveRules(RulesInfo rulesInfo) {
		Integer id = rulesInfo.getId();
        Rules rules = null;
        if (id != null) {
            rules = this.findRules(id);
        }
        boolean isNew = false;
        if (rules == null) {
            isNew = true;
            rules = new Rules();
            //
            String sql = "SELECT MAX(id) FROM " + Rules.class.getName();
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(sql);
            if (query.list().get(0) == null){
            	rules.setId(1);
            }
            else {
            	rules.setId(Integer.parseInt(query.list().get(0).toString())+1);
            }  
        }
        rules.setTakingLessonId(rulesInfo.getTakingLessonId());
        rules.setSubstituteLessonId(rulesInfo.getSubstituteLessonId());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(rules);
        }
		
	}

	@Override
	public RulesInfo findRulesInfo(Integer id) {
		Rules rules = this.findRules(id);
        if (rules == null) {
            return null;
        }
        return new RulesInfo(rules.getId(), rules.getTakingLessonId(), rules.getSubstituteLessonId());
	}

	@Override
	public void deleteRules(Integer id) {
		Rules rules = this.findRules(id);
        if (rules != null) {
            this.sessionFactory.getCurrentSession().delete(rules);
        }
		
	}

	@Override
	public boolean isDuplicate(RulesInfo rulesInfo) {
//		String sql = "SELECT new "+RulesInfo.class.getName()+"(a.takingLessonId, a.substituteLessonId) FROM " + Rules.class.getName() + " a WHERE takingLessonID="+rulesInfo.getTakingLessonId()+" AND substituteLessonId="+rulesInfo.getSubstituteLessonId();
        Session session = sessionFactory.getCurrentSession();
//        Query query = session.createQuery(sql);
		Query query = session.createQuery("from Rules where takingLessonId = "+rulesInfo.getTakingLessonId()+" AND substituteLessonId="+rulesInfo.getSubstituteLessonId());
		if (query.list().isEmpty()){
        	return false;
        }
        else {
        	return true;
        }
	}

}
