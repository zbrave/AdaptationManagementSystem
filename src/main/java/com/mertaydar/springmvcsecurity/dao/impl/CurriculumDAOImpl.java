package com.mertaydar.springmvcsecurity.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.mertaydar.springmvcsecurity.dao.CurriculumDAO;
import com.mertaydar.springmvcsecurity.entity.Curriculum;
import com.mertaydar.springmvcsecurity.entity.Dept;
import com.mertaydar.springmvcsecurity.model.CurriculumInfo;
import com.mertaydar.springmvcsecurity.model.DeptInfo;

public class CurriculumDAOImpl implements CurriculumDAO {
	
	@Autowired
    private SessionFactory sessionFactory;

	@Override
	public Curriculum findCurriculum(Integer id) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Curriculum.class);
        crit.add(Restrictions.eq("id", id));
        return (Curriculum) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurriculumInfo> listCurriculumInfos() {
		String sql = "Select new " + CurriculumInfo.class.getName()//
                + "(a.id, a.year, a.enabled) "//
                + " from " + Curriculum.class.getName() + " a ";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
	}

	@Override
	public void saveCurriculum(CurriculumInfo curriculumInfo) {
		Integer id = curriculumInfo.getId();
		Curriculum cur = null;
        if (id != null) {
            cur = this.findCurriculum(id);
        }
        boolean isNew = false;
        if (cur == null) {
            isNew = true;
            cur = new Curriculum();
        }
        cur.setId(curriculumInfo.getId());
        cur.setYear(curriculumInfo.getYear());
        cur.setEnabled(curriculumInfo.getEnabled());
        if (curriculumInfo.getEnabled() == true) {
        	List<CurriculumInfo> list = listCurriculumInfos();
        	for (CurriculumInfo l : list) {
        		if (l.getId() != curriculumInfo.getId()) {
        			l.setEnabled(false);
        			saveCurriculum(l);
        		}
        	}
        }
        System.out.println("cur:"+cur.getId()+" "+cur.getYear()+" "+cur.getEnabled());
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(cur);
        }
	}

	@Override
	public CurriculumInfo findCurriculumInfo(Integer id) {
		Curriculum cur = this.findCurriculum(id);
        if (cur == null) {
            return null;
        }
        return new CurriculumInfo(cur.getId(), cur.getYear(), cur.getEnabled());
	}

	@Override
	public CurriculumInfo findCurriculumInfoActive() {
		List<CurriculumInfo> list = this.listCurriculumInfos();
		CurriculumInfo act = new CurriculumInfo();
        if (list == null) {
            return null;
        }
        for (CurriculumInfo l : list) {
        	if (l.getEnabled()) {
        		act.setId(l.getId());
        		act.setYear(l.getYear());
        		act.setEnabled(l.getEnabled());
        	}
        }
        return act;
	}

	@Override
	public void deleteCurriculum(Integer id) {
		Curriculum cur = this.findCurriculum(id);
        if (cur != null) {
            this.sessionFactory.getCurrentSession().delete(cur);
        }
	}

}
