package com.mertaydar.springmvcsecurity.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.mertaydar.springmvcsecurity.dao.StudentDAO;
import com.mertaydar.springmvcsecurity.entity.Student;
import com.mertaydar.springmvcsecurity.model.StudentInfo;

public class StudentDAOImpl implements StudentDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public Student findStudent(Integer id) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Student.class);
        crit.add(Restrictions.eq("id", id));
        return (Student) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudentInfo> listStudentInfos() {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + StudentInfo.class.getName()//
                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear) "//
                + " from " + Student.class.getName() + " a ";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
	}

	@Override
	public void saveStudent(StudentInfo studentInfo) {
		Integer id = studentInfo.getId();
        Student student = null;
        if (id != null) {
            student = this.findStudent(id);
        }
        boolean isNew = false;
        if (student == null) {
            isNew = true;
            student = new Student();
            //
            String sql = "SELECT MAX(id) FROM " + Student.class.getName();
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(sql);
            if (query.list().get(0) == null){
            	student.setId(1);
            }
            else {
            	student.setId(Integer.parseInt(query.list().get(0).toString())+1);
            }
        }
        student.setDeptId(studentInfo.getDeptId());
        student.setName(studentInfo.getName());
        student.setNo(studentInfo.getNo());
        student.setSurname(studentInfo.getSurname());
        student.setAdpScore(studentInfo.getAdpScore());
        student.setRecordYear(studentInfo.getRecordYear());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(student);
        }
		
	}

	@Override
	public StudentInfo findStudentInfo(Integer id) {
		Student student = this.findStudent(id);
        if (student == null) {
            return null;
        }
        return new StudentInfo(student.getId(), student.getDeptId(), student.getName(), student.getSurname(), student.getNo(), student.getAdpScore(), student.getRecordYear());
	}

	@Override
	public void deleteStudent(Integer id) {
		Student student = this.findStudent(id);
        if (student != null) {
            this.sessionFactory.getCurrentSession().delete(student);
        }
		
	}
	
	@Override
	public boolean isDuplicate(StudentInfo studentInfo) {
//		String sql = "SELECT new "+RulesInfo.class.getName()+"(a.takingLessonId, a.substituteLessonId) FROM " + Rules.class.getName() + " a WHERE takingLessonID="+rulesInfo.getTakingLessonId()+" AND substituteLessonId="+rulesInfo.getSubstituteLessonId();
        Session session = sessionFactory.getCurrentSession();
//        Query query = session.createQuery(sql);
		Query query = session.createQuery("from Student where no = "+studentInfo.getNo());
		if (query.list().isEmpty()){
        	return false;
        }
        else {
        	return true;
        }
	}

}
