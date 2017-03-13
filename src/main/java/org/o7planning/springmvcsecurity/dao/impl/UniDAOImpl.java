package org.o7planning.springmvcsecurity.dao.impl;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.o7planning.springmvcsecurity.dao.UniDAO;
import org.o7planning.springmvcsecurity.entity.Uni;
import org.o7planning.springmvcsecurity.model.UniInfo;
import org.springframework.beans.factory.annotation.Autowired;

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

	@Override
	public List<UniInfo> listUniInfos() {
		String sql = "Select new " + UniInfo.class.getName()//
                + "(a.id, a.name) "//
                + " from " + Uni.class.getName() + " a ";
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
            System.out.println(query.list().get(0).toString());
            uni.setId(Integer.parseInt(query.list().get(0).toString())+1);
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
		System.out.println("deleteUni worked. bla bla....");
		
	}

}
