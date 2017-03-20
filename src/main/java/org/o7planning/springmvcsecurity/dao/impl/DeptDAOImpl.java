package org.o7planning.springmvcsecurity.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.o7planning.springmvcsecurity.dao.DeptDAO;
import org.o7planning.springmvcsecurity.entity.Dept;
import org.o7planning.springmvcsecurity.model.DeptInfo;
import org.springframework.beans.factory.annotation.Autowired;

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
            System.out.println(query.list().get(0).toString());
            dept.setId(Integer.parseInt(query.list().get(0).toString())+1);
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
	public void deleteDept(Integer id) {
		Dept dept = this.findDept(id);
        if (dept != null) {
            this.sessionFactory.getCurrentSession().delete(dept);
        }
		
	}

}
