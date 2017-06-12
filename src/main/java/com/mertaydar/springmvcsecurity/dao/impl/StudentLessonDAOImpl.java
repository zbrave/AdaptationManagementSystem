package com.mertaydar.springmvcsecurity.dao.impl;

import java.util.ArrayList;
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
import com.mertaydar.springmvcsecurity.model.OurmarkInfo;
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentLessonInfo> listStudentLessonInfosForStudentNoDuplicate(Integer id) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + StudentLessonInfo.class.getName()//
                + "(a.id, a.studentId, a.takingLessonId, a.substituteLessonId, a.orgMark, a.convMark) "//
                + " from " + StudentLesson.class.getName() + " a Where a.studentId = :code";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        query.setParameter("code", id);
        List<StudentLessonInfo> list = query.list();
        List<StudentLessonInfo> list2 = new ArrayList<StudentLessonInfo>();
        List<Integer> processed = new ArrayList<Integer>();
        for (StudentLessonInfo l : list) {
        	boolean dup = false;
        	List<StudentLessonInfo> dupList = new ArrayList<StudentLessonInfo>();
        	for (StudentLessonInfo k : list) {
            	if (!processed.contains(l.getId()) && !processed.contains(k.getId()) && l.getId() != k.getId() && l.getSubstituteLessonId() == k.getSubstituteLessonId()){
            		if (!dup && !processed.contains(l.getId())) {
            			System.out.println("add duplist "+l.getId());
            			dupList.add(l);
            			processed.add(l.getId());
            		}
            		dup = true;
            		boolean exist = false;
            		for (StudentLessonInfo d : dupList){
            			if (d.getId() == k.getId()) {
            				exist = true;
            			}
            		}
            		if (!exist && processed.add(k.getId())) {
            			System.out.println("add dupblist "+k.getId());
            			dupList.add(k);
            			processed.add(k.getId());
            		}
            	}
            }
        	if (!dupList.isEmpty())
        		list2.add(takeAverage(dupList));
        }
        for (StudentLessonInfo l : list) {
        	boolean exist = false;
        	for (StudentLessonInfo k : list2) {
        		if (l.getSubstituteLessonId() == k.getSubstituteLessonId()) {
        			exist = true;
        		}
        	}
        	if (!exist)
        		list2.add(l);
        }
        return list2;
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
        	return ((Mark) crit.uniqueResult()).getValue().toString();
        return ((Ourmark) crit2.uniqueResult()).getMark();
	}
	
	public StudentLessonInfo takeAverage(List<StudentLessonInfo> list) {
		List<Ourmark> marks = new ArrayList<Ourmark>();
		System.out.println("takeavarage list size"+list.size());
		for (StudentLessonInfo l : list) {
			System.out.println("tules id: "+l.getId());
		}
		for (StudentLessonInfo l : list) {
			Session session = sessionFactory.getCurrentSession();
	        Criteria crit = session.createCriteria(Ourmark.class);
	        crit.add(Restrictions.eq("mark", l.getConvMark()));
	        if (!crit.list().isEmpty()) {
	        	Ourmark our1 = ((Ourmark) crit.uniqueResult());
		        marks.add(our1);
	        }
		}
		float total = 0;
		for (Ourmark m : marks) {
			total += m.getValue();
		}
		total = total / marks.size();
		System.out.println(total+" "+marks.size());
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Ourmark.class);
        crit.add(Restrictions.eq("value", total));
        if (crit.list().isEmpty()) {
        	list.get(0).setConvMark(total+"");
        	return list.get(0);
        }
        else {
        	Ourmark our1 = ((Ourmark) crit.uniqueResult());
	        list.get(0).setConvMark(our1.getMark());
	        return list.get(0);
        }
	}

}
