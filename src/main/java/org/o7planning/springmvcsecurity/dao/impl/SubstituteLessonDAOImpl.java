package org.o7planning.springmvcsecurity.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.o7planning.springmvcsecurity.dao.SubstituteLessonDAO;
import org.o7planning.springmvcsecurity.entity.SubstituteLesson;
import org.o7planning.springmvcsecurity.model.SubstituteLessonInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class SubstituteLessonDAOImpl implements SubstituteLessonDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public SubstituteLesson findSubstituteLesson(Integer id) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(SubstituteLesson.class);
        crit.add(Restrictions.eq("id", id));
        return (SubstituteLesson) crit.uniqueResult();
	}

	@Override
	public List<SubstituteLessonInfo> listSubstituteLessonInfos() {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + SubstituteLessonInfo.class.getName()//
                + "(a.id, a.takingLessonId, a.code, a.credit, a.akts) "//
                + " from " + SubstituteLesson.class.getName() + " a ";
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
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
            System.out.println(query.list().get(0).toString());
            substituteLesson.setId(Integer.parseInt(query.list().get(0).toString())+1);
        }
        substituteLesson.setTakingLessonId(substituteLessonInfo.getTakingLessonId());
        substituteLesson.setCode(substituteLessonInfo.getCode());
        substituteLesson.setCredit(substituteLessonInfo.getCredit());
        substituteLesson.setAkts(substituteLessonInfo.getAkts());
 
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
        return new SubstituteLessonInfo(substituteLesson.getId(), substituteLesson.getTakingLessonId(), substituteLesson.getCode(), substituteLesson.getCredit(), substituteLesson.getAkts());
	}

	@Override
	public void deleteSubstituteLesson(Integer id) {
		SubstituteLesson substituteLesson = this.findSubstituteLesson(id);
        if (substituteLesson != null) {
            this.sessionFactory.getCurrentSession().delete(substituteLesson);
        }

	}

}
