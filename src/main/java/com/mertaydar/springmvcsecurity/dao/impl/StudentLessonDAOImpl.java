package com.mertaydar.springmvcsecurity.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.mertaydar.springmvcsecurity.dao.DeptDAO;
import com.mertaydar.springmvcsecurity.dao.MarkDAO;
import com.mertaydar.springmvcsecurity.dao.StudentDAO;
import com.mertaydar.springmvcsecurity.dao.StudentLessonDAO;
import com.mertaydar.springmvcsecurity.entity.Mark;
import com.mertaydar.springmvcsecurity.entity.Ourmark;
import com.mertaydar.springmvcsecurity.entity.StudentLesson;
import com.mertaydar.springmvcsecurity.model.StudentLessonInfo;

public class StudentLessonDAOImpl implements StudentLessonDAO {
	
	@Autowired
	private DeptDAO deptDAO;
	
	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private MarkDAO markDAO;
	
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
        studentLesson.setConvMark(convertMark(studentLessonInfo.getStudentId(), studentLessonInfo.getOrgMark()));
        studentLesson.setOrgMark(studentLessonInfo.getOrgMark());
        studentLesson.setTakingLessonId(studentLessonInfo.getTakingLessonId());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(studentLesson);
        }
		
	}
	
	@Override
	public void saveStudentLessonExempt(StudentLessonInfo studentLessonInfo) {
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
        studentLesson.setConvMark("MUAF");
        studentLesson.setOrgMark("MUAF");
        studentLesson.setTakingLessonId(studentLessonInfo.getTakingLessonId());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(studentLesson);
        }
		
	}
	
	@Override
	public void saveStudentLessonNoConvert(StudentLessonInfo studentLessonInfo) {
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
	
	@Override
	public boolean isDuplicate(StudentLessonInfo stuLes) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + StudentLessonInfo.class.getName()//
                + "(a.id, a.studentId, a.takingLessonId, a.substituteLessonId, a.orgMark, a.convMark) "//
                + " from " + StudentLesson.class.getName() + " a Where a.takingLessonId = :code AND a.substituteLessonId = :code2 AND a.studentId = :code3";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        query.setParameter("code", stuLes.getTakingLessonId());
        query.setParameter("code2", stuLes.getSubstituteLessonId());
        query.setParameter("code3", stuLes.getStudentId());
        
        if (query.list().isEmpty()){
        	return false;
        }
        else {
        	return true;
        }
		
	}
	
	public String convertMark(Integer id, String orgMark) {
		Integer uniId = deptDAO.findDept(studentDAO.findStudent(id).getDeptId()).getUniId();
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Mark.class);
        crit.add(Restrictions.eq("uniId", uniId));
        crit.add(Restrictions.eq("mark", orgMark));
        if (crit.list().isEmpty())
        	return "?";
        Criteria crit2 = session.createCriteria(Ourmark.class);
        crit2.add(Restrictions.eq("value", ((Mark) crit.uniqueResult()).getValue()));
        if (crit2.list().isEmpty())
        	return "??";
        return ((Ourmark) crit2.uniqueResult()).getMark();
	}

}
