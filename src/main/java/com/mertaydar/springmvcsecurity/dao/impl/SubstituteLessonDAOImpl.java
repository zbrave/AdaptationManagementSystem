package com.mertaydar.springmvcsecurity.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.mertaydar.springmvcsecurity.dao.SubstituteLessonDAO;
import com.mertaydar.springmvcsecurity.entity.SubstituteLesson;
import com.mertaydar.springmvcsecurity.model.SubstituteLessonInfo;


public class SubstituteLessonDAOImpl implements SubstituteLessonDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public Long count(Integer currId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(SubstituteLesson.class);
        crit.add(Restrictions.eq("curriculumId", currId));
        crit.setProjection(Projections.rowCount());
        return (Long) crit.uniqueResult();
	}
	
	@Override
	public SubstituteLesson findSubstituteLesson(Integer id) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(SubstituteLesson.class);
        crit.add(Restrictions.eq("id", id));
        return (SubstituteLesson) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubstituteLessonInfo> listSubstituteLessonInfos() {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + SubstituteLessonInfo.class.getName()//
                + "(a.id, a.name, a.code, a.lang, a.credit, a.lab, a.akts, a.term, a.conditionId, a.curriculumId, a.base) "//
                + " from " + SubstituteLesson.class.getName() + " a ";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> listPoolLessons() {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + SubstituteLessonInfo.class.getName()//
                + "(a.id, a.name, a.code, a.lang, a.credit, a.lab, a.akts, a.term, a.conditionId, a.curriculumId, a.base) "//
                + " from " + SubstituteLesson.class.getName() + " a Where a.base is NOT NULL";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<SubstituteLessonInfo> list = query.list();
        List<String> list2 = new ArrayList<String>();
        for (SubstituteLessonInfo l : list) {
        	if (l.getBase() != null && !l.getBase().contains("H") && !list2.contains(l.getBase())) {
        		list2.add(l.getBase());
        	}
        }
        return list2;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> listPoolLessonsByCurriculum(Integer id) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + SubstituteLessonInfo.class.getName()//
                + "(a.id, a.name, a.code, a.lang, a.credit, a.lab, a.akts, a.term, a.conditionId, a.curriculumId, a.base) "//
                + " from " + SubstituteLesson.class.getName() + " a Where a.base is NOT NULL AND a.curriculumId="+id;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        List<SubstituteLessonInfo> list = query.list();
        List<String> list2 = new ArrayList<String>();
        for (SubstituteLessonInfo l : list) {
        	if (l.getBase() != null && !l.getBase().contains("H") && !list2.contains(l.getBase())) {
        		list2.add(l.getBase());
        	}
        }
        return list2;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubstituteLessonInfo> listSubstituteLessonInfos(Integer curriculumId) {
		// sql query has to have exact names from own class variable 
		// get curri id from yead
		String sql = "Select new " + SubstituteLessonInfo.class.getName()//
                + "(a.id, a.name, a.code, a.lang, a.credit, a.lab, a.akts, a.term, a.conditionId, a.curriculumId, a.base) "//
                + " from " + SubstituteLesson.class.getName() + " a where a.curriculumId="+curriculumId;
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubstituteLessonInfo> listSubstituteLessonInfosPagination(Integer curriculumId, Integer offset, Integer maxResults) {
		List<SubstituteLessonInfo> fullList= listSubstituteLessonInfos(curriculumId);
        List<SubstituteLessonInfo> offsetList=new ArrayList<SubstituteLessonInfo>();
        maxResults = maxResults!=null?maxResults:10;
        offset = (offset!=null?offset:0);
        for(int i=0; i<maxResults && offset+i<fullList.size(); i++){
        	offsetList.add(fullList.get(offset+i));
        }
        return offsetList;
	}
	
	@Override
	public void saveSubstituteLesson(SubstituteLessonInfo substituteLessonInfo) {
		Integer id = substituteLessonInfo.getId();
        SubstituteLesson substituteLesson = null;
        if (id != null) {
            substituteLesson = this.findSubstituteLesson(id);
        }
        boolean isNew = false;
        if (substituteLesson == null) {
            isNew = true;
            substituteLesson = new SubstituteLesson();
            //
            String sql = "SELECT MAX(id) FROM " + SubstituteLesson.class.getName();
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(sql);
            if (query.list().get(0) == null){
            	substituteLesson.setId(1);
            }
            else {
            	substituteLesson.setId(Integer.parseInt(query.list().get(0).toString())+1);
            }
        }
        substituteLesson.setName(substituteLessonInfo.getName());
        substituteLesson.setLang(substituteLessonInfo.getLang());
        substituteLesson.setTerm(substituteLessonInfo.getTerm());
        substituteLesson.setCode(substituteLessonInfo.getCode());
        substituteLesson.setCredit(substituteLessonInfo.getCredit());
        substituteLesson.setAkts(substituteLessonInfo.getAkts());
        substituteLesson.setLab(substituteLessonInfo.getLab());
        substituteLesson.setConditionId(substituteLessonInfo.getConditionId());
        substituteLesson.setCurriculumId(substituteLessonInfo.getCurriculumId());
        substituteLesson.setBase(substituteLessonInfo.getBase());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(substituteLesson);
        }

	}

	@Override
	public SubstituteLessonInfo findSubstituteLessonInfo(Integer id) {
		SubstituteLesson substituteLesson = this.findSubstituteLesson(id);
        if (substituteLesson == null) {
            return null;
        }
        return new SubstituteLessonInfo(substituteLesson.getId(), substituteLesson.getName(), substituteLesson.getCode(), substituteLesson.getLang(), substituteLesson.getCredit(), substituteLesson.getLab(), substituteLesson.getAkts(), substituteLesson.getTerm(), substituteLesson.getConditionId(), substituteLesson.getCurriculumId(), substituteLesson.getBase());
	}

	@Override
	public void deleteSubstituteLesson(Integer id) {
		SubstituteLesson substituteLesson = this.findSubstituteLesson(id);
        if (substituteLesson != null) {
            this.sessionFactory.getCurrentSession().delete(substituteLesson);
        }

	}

}
