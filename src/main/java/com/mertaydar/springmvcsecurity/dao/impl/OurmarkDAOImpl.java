package com.mertaydar.springmvcsecurity.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.mertaydar.springmvcsecurity.dao.OurmarkDAO;
import com.mertaydar.springmvcsecurity.entity.Mark;
import com.mertaydar.springmvcsecurity.entity.Ourmark;
import com.mertaydar.springmvcsecurity.model.MarkInfo;
import com.mertaydar.springmvcsecurity.model.OurmarkInfo;

public class OurmarkDAOImpl implements OurmarkDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public Ourmark findOurmark(Integer id) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Ourmark.class);
        crit.add(Restrictions.eq("id", id));
        return (Ourmark) crit.uniqueResult();
	}

	@Override
	public List<OurmarkInfo> listOurmarkInfos() {
		// sql query has to have exact names from own class variable 
				String sql = "Select new " + OurmarkInfo.class.getName()//
		                + "(a.id, a.mark, a.value) "//
		                + " from " + Ourmark.class.getName() + " a ";
				System.out.println(sql.toString());
		        Session session = sessionFactory.getCurrentSession();
		        Query query = session.createQuery(sql);
		        return query.list();
	}

	@Override
	public void saveOurmark(OurmarkInfo ourmarkInfo) {
		Integer id = ourmarkInfo.getId();
        Ourmark mark = null;
        if (id != null) {
            mark = this.findOurmark(id);
        }
        boolean isNew = false;
        if (mark == null) {
            isNew = true;
            mark = new Ourmark();
            //
            String sql = "SELECT MAX(id) FROM " + Ourmark.class.getName();
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(sql);
            if (query.list().get(0) == null){
            	mark.setId(1);
            }
            else {
            	mark.setId(Integer.parseInt(query.list().get(0).toString())+1);
            }
        }
        mark.setMark(ourmarkInfo.getMark());
        mark.setValue(ourmarkInfo.getValue());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(mark);
        }
		
	}

	@Override
	public OurmarkInfo findOurmarkInfo(Integer id) {
		Ourmark ourmark = this.findOurmark(id);
        if (ourmark == null) {
            return null;
        }
        return new OurmarkInfo(ourmark.getId(), ourmark.getMark(), ourmark.getValue());
	}

	@Override
	public void deleteOurmark(Integer id) {
		Ourmark ourmark = this.findOurmark(id);
        if (ourmark != null) {
            this.sessionFactory.getCurrentSession().delete(ourmark);
        }
		
	}

}
