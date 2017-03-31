package com.mertaydar.springmvcsecurity.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.mertaydar.springmvcsecurity.dao.UniDAO;
import com.mertaydar.springmvcsecurity.entity.Uni;
import com.mertaydar.springmvcsecurity.model.UniInfo;

public class UniDAOImpl implements UniDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public Uni findUni(Integer id) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Uni.class);
        crit.add(Restrictions.eq("id", id));
        return (Uni) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UniInfo> listUniInfos() {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + UniInfo.class.getName()//
                + "(a.id, a.name) "//
                + " from " + Uni.class.getName() + " a ";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
	}

	@Override
	public void saveUni(UniInfo uniInfo) {
		Integer id = uniInfo.getId();
        Uni uni = null;
        if (id != null) {
            uni = this.findUni(id);
        }
        boolean isNew = false;
        if (uni == null) {
            isNew = true;
            uni = new Uni();
            //
            String sql = "SELECT MAX(id) FROM " + Uni.class.getName();
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(sql);
            if (query.list().get(0) == null){
            	uni.setId(1);
            }
            else {
            	uni.setId(Integer.parseInt(query.list().get(0).toString())+1);
            }
            
        }
        uni.setName(uniInfo.getName());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(uni);
        }
		
	}

	@Override
	public UniInfo findUniInfo(Integer id) {
		Uni uni = this.findUni(id);
        if (uni == null) {
            return null;
        }
        return new UniInfo(uni.getId(), uni.getName());
	}

	@Override
	public void deleteUni(Integer id) {
		Uni uni = this.findUni(id);
        if (uni != null) {
            this.sessionFactory.getCurrentSession().delete(uni);
        }
		
	}

}
