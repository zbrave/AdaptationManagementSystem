package com.mertaydar.springmvcsecurity.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.mertaydar.springmvcsecurity.dao.DeptDAO;
import com.mertaydar.springmvcsecurity.entity.Dept;
import com.mertaydar.springmvcsecurity.model.DeptInfo;

public class DeptDAOImpl implements DeptDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public Dept findDept(Integer id) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Dept.class);
        crit.add(Restrictions.eq("id", id));
        return (Dept) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeptInfo> listDeptInfos() {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + DeptInfo.class.getName()//
                + "(a.id, a.uniId, a.name) "//
                + " from " + Dept.class.getName() + " a ";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DeptInfo> listDeptFromUni(Integer id) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + DeptInfo.class.getName()//
                + "(a.id, a.uniId, a.name) "//
                + " from " + Dept.class.getName() + " a where a.uniId = :code";
//		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        query.setParameter("code", id);
        return query.list();
	}

	@Override
	public void saveDept(DeptInfo deptInfo) {
		Integer id = deptInfo.getId();
        Dept dept = null;
        if (id != null) {
            dept = this.findDept(id);
        }
        boolean isNew = false;
        if (dept == null) {
            isNew = true;
            dept = new Dept();
            //
            String sql = "SELECT MAX(id) FROM " + Dept.class.getName();
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(sql);
            if (query.list().get(0) == null){
            	dept.setId(1);
            }
            else {
            	dept.setId(Integer.parseInt(query.list().get(0).toString())+1);
            }
        }
        dept.setUniId(deptInfo.getUniId());
        dept.setName(deptInfo.getName());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(dept);
        }
		
	}

	@Override
	public DeptInfo findDeptInfo(Integer id) {
		Dept dept = this.findDept(id);
        if (dept == null) {
            return null;
        }
        return new DeptInfo(dept.getId(), dept.getUniId(), dept.getName());
	}
	
	@Override
	public DeptInfo findDeptInfoWithName(String name) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Dept.class);
        crit.add(Restrictions.eq("name", name));
        Dept dept = (Dept) crit.uniqueResult();
        if (dept == null) {
            return null;
        }
        return new DeptInfo(dept.getId(), dept.getUniId(), dept.getName());
	}
	
	@Override
	public List<DeptInfo> listDeptInfoWithName(String name) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Dept.class);
        crit.add(Restrictions.ilike("name", name, MatchMode.ANYWHERE));
        List<Dept> depts = crit.list();
        if (depts == null || depts.isEmpty()) {
            return null;
        }
        List<DeptInfo> list = new ArrayList<DeptInfo>();
        for (Dept d : depts) {
        	list.add(new DeptInfo(d.getId(), d.getUniId(), d.getName()));
        }
        return list;
	}

	@Override
	public void deleteDept(Integer id) {
		Dept dept = this.findDept(id);
        if (dept != null) {
            this.sessionFactory.getCurrentSession().delete(dept);
        }
		
	}

}
