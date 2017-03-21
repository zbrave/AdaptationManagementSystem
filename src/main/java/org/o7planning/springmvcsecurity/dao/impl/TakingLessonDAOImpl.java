package org.o7planning.springmvcsecurity.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.o7planning.springmvcsecurity.dao.TakingLessonDAO;
import org.o7planning.springmvcsecurity.entity.TakingLesson;
import org.o7planning.springmvcsecurity.model.TakingLessonInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class TakingLessonDAOImpl implements TakingLessonDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public TakingLesson findTakingLesson(Integer id) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(TakingLesson.class);
        crit.add(Restrictions.eq("id", id));
        return (TakingLesson) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TakingLessonInfo> listTakingLessonInfos() {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + TakingLessonInfo.class.getName()//
                + "(a.id, a.deptId, a.name, a.code, a.lang, a.credit, a.akts, a.term) "//
                + " from " + TakingLesson.class.getName() + " a ";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TakingLessonInfo> listTakingLessonFromDept(Integer id) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + TakingLessonInfo.class.getName()//
                + "(a.id, a.deptId, a.name, a.code, a.lang, a.credit, a.akts, a.term) "//
                + " from " + TakingLesson.class.getName() + " a where a.deptId = :code";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        query.setParameter("code", id);
        return query.list();
	}

	@Override
	public void saveTakingLesson(TakingLessonInfo takingLessonInfo) {
		Integer id = takingLessonInfo.getId();
        TakingLesson takingLesson = null;
        if (id != null) {
            takingLesson = this.findTakingLesson(id);
        }
        boolean isNew = false;
        if (takingLesson == null) {
            isNew = true;
            takingLesson = new TakingLesson();
            //
            String sql = "SELECT MAX(id) FROM " + TakingLesson.class.getName();
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(sql);
            if (query.list().get(0) == null){
            	takingLesson.setId(1);
            }
            else {
            	takingLesson.setId(Integer.parseInt(query.list().get(0).toString())+1);
            }
        }
        takingLesson.setName(takingLessonInfo.getName());
        takingLesson.setLang(takingLessonInfo.getLang());
        takingLesson.setTerm(takingLessonInfo.getTerm());
        takingLesson.setDeptId(takingLessonInfo.getDeptId());
        takingLesson.setCode(takingLessonInfo.getCode());
        takingLesson.setCredit(takingLessonInfo.getCredit());
        takingLesson.setAkts(takingLessonInfo.getAkts());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(takingLesson);
        }

	}

	@Override
	public TakingLessonInfo findTakingLessonInfo(Integer id) {
		TakingLesson takingLesson = this.findTakingLesson(id);
        if (takingLesson == null) {
            return null;
        }
        return new TakingLessonInfo(takingLesson.getId(), takingLesson.getDeptId(), takingLesson.getName(), takingLesson.getCode(), takingLesson.getLang(), takingLesson.getCredit(), takingLesson.getAkts(), takingLesson.getTerm());
	}

	@Override
	public void deleteTakingLesson(Integer id) {
		TakingLesson takingLesson = this.findTakingLesson(id);
        if (takingLesson != null) {
            this.sessionFactory.getCurrentSession().delete(takingLesson);
        }

	}

}
