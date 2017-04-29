package com.mertaydar.springmvcsecurity.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.mertaydar.springmvcsecurity.dao.MarkDAO;
import com.mertaydar.springmvcsecurity.entity.Mark;
import com.mertaydar.springmvcsecurity.model.MarkInfo;

public class MarkDAOImpl implements MarkDAO{

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public Mark findMark(Integer id) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Mark.class);
        crit.add(Restrictions.eq("id", id));
        return (Mark) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MarkInfo> listMarkInfos() {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + MarkInfo.class.getName()//
                + "(a.id, a.uniId, a.from, a.to) "//
                + " from " + Mark.class.getName() + " a ";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MarkInfo> listMarkFromUni(Integer id) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + MarkInfo.class.getName()//
                + "(a.id, a.uniId, a.mark, a.value) "//
                + " from " + Mark.class.getName() + " a where a.uniId = :code";
//		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        query.setParameter("code", id);
        return query.list();
	}

	@Override
	public void saveMark(MarkInfo markInfo) {
		Integer id = markInfo.getId();
        Mark mark = null;
        if (id != null) {
            mark = this.findMark(id);
        }
        boolean isNew = false;
        if (mark == null) {
            isNew = true;
            mark = new Mark();
            //
            String sql = "SELECT MAX(id) FROM " + Mark.class.getName();
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(sql);
            if (query.list().get(0) == null){
            	mark.setId(1);
            }
            else {
            	mark.setId(Integer.parseInt(query.list().get(0).toString())+1);
            }
        }
        mark.setUniId(markInfo.getUniId());
        mark.setMark(markInfo.getMark());
        mark.setValue(markInfo.getValue());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(mark);
        }
		
	}

	@Override
	public MarkInfo findMarkInfo(Integer id) {
		Mark mark = this.findMark(id);
        if (mark == null) {
            return null;
        }
        return new MarkInfo(mark.getId(), mark.getUniId(), mark.getMark(), mark.getValue());
	}

	@Override
	public void deleteMark(Integer id) {
		Mark mark = this.findMark(id);
        if (mark != null) {
            this.sessionFactory.getCurrentSession().delete(mark);
        }
		
	}
	
}
