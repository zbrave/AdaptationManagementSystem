package org.o7planning.springmvcsecurity.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.o7planning.springmvcsecurity.dao.StudentLessonDAO;
import org.o7planning.springmvcsecurity.entity.StudentLesson;
import org.o7planning.springmvcsecurity.model.StudentLessonInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentLessonDAOImpl implements StudentLessonDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public StudentLesson findStudentLesson(Integer id) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(StudentLesson.class);
        crit.add(Restrictions.eq("id", id));
        return (StudentLesson) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentLessonInfo> listStudentLessonInfos() {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + StudentLessonInfo.class.getName()//
                + "(a.id, a.studentId, a.takingLessonId, a.substituteLessonId, a.orgMark, a.convMark) "//
                + " from " + StudentLesson.class.getName() + " a ";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentLessonInfo> listStudentLessonInfosForStudent(Integer id) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + StudentLessonInfo.class.getName()//
                + "(a.id, a.studentId, a.takingLessonId, a.substituteLessonId, a.orgMark, a.convMark) "//
                + " from " + StudentLesson.class.getName() + " a Where a.studentId = :code";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        query.setParameter("code", id);
        return query.list();
	}

	@Override
	public void saveStudentLesson(StudentLessonInfo studentLessonInfo) {
		Integer id = studentLessonInfo.getId();
        StudentLesson studentLesson = null;
        if (id != null) {
            studentLesson = this.findStudentLesson(id);
        }
        boolean isNew = false;
        if (studentLesson == null) {
            isNew = true;
            studentLesson = new StudentLesson();
            //
            String sql = "SELECT MAX(id) FROM " + StudentLesson.class.getName();
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(sql);
            if (query.list().get(0) == null){
            	studentLesson.setId(1);
            }
            else {
            	studentLesson.setId(Integer.parseInt(query.list().get(0).toString())+1);
            }
        }
        studentLesson.setSubstituteLessonId(studentLessonInfo.getSubstituteLessonId());
        studentLesson.setStudentId(studentLessonInfo.getStudentId());
        studentLesson.setConvMark(studentLessonInfo.getConvMark());
        studentLesson.setOrgMark(studentLessonInfo.getOrgMark());
        studentLesson.setTakingLessonId(studentLessonInfo.getTakingLessonId());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(studentLesson);
        }
		
	}

	@Override
	public StudentLessonInfo findStudentLessonInfo(Integer id) {
		StudentLesson studentLesson = this.findStudentLesson(id);
        if (studentLesson == null) {
            return null;
        }
        return new StudentLessonInfo(studentLesson.getId(), studentLesson.getStudentId(), studentLesson.getTakingLessonId(), studentLesson.getSubstituteLessonId(), studentLesson.getOrgMark(), studentLesson.getConvMark());
	}

	@Override
	public void deleteStudentLesson(Integer id) {
		StudentLesson studentLesson = this.findStudentLesson(id);
        if (studentLesson != null) {
            this.sessionFactory.getCurrentSession().delete(studentLesson);
        }
		
	}

}
