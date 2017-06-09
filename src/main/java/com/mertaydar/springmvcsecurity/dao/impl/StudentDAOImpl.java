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
import com.mertaydar.springmvcsecurity.dao.StudentDAO;
import com.mertaydar.springmvcsecurity.dao.StudentLessonDAO;
import com.mertaydar.springmvcsecurity.dao.SubstituteLessonDAO;
import com.mertaydar.springmvcsecurity.dao.TakingLessonDAO;
import com.mertaydar.springmvcsecurity.dao.UniDAO;
import com.mertaydar.springmvcsecurity.entity.Student;
import com.mertaydar.springmvcsecurity.entity.StudentLesson;
import com.mertaydar.springmvcsecurity.model.DeptInfo;
import com.mertaydar.springmvcsecurity.model.StudentInfo;
import com.mertaydar.springmvcsecurity.model.StudentLessonInfo;
import com.mertaydar.springmvcsecurity.model.SubstituteLessonInfo;
import com.mertaydar.springmvcsecurity.model.TakingLessonInfo;
import com.mertaydar.springmvcsecurity.model.UniInfo;

public class StudentDAOImpl implements StudentDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	@Autowired
	private StudentLessonDAO studentLessonDAO;
	
	@Autowired
	private SubstituteLessonDAO substituteLessonDAO;
	
	@Autowired
	private DeptDAO deptDAO;
	
	@Autowired
	private UniDAO uniDAO;
	
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
                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear, a.advisorId) "//
                + " from " + Student.class.getName() + " a ";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentInfo> listStudentInfos(Integer pageid, Integer total) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + StudentInfo.class.getName()//
                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear, a.advisorId) "//
                + " from " + Student.class.getName() + " a ";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql).setFirstResult(pageid-1).setMaxResults(total);
        return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentInfo> listStudentInfosForAdv(Integer pageid, Integer total, Integer userid) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + StudentInfo.class.getName()//
                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear, a.advisorId) "//
                + " from " + Student.class.getName() + " a where advisorId="+userid;
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql).setFirstResult(pageid-1).setMaxResults(total);
        return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentInfo> listStudentInfosForAdvSize(Integer userid) {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + StudentInfo.class.getName()//
                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear, a.advisorId) "//
                + " from " + Student.class.getName() + " a where advisorId="+userid;
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentInfo> listStudentInfosBySearch(Integer pageid, Integer total, String val, String cate) {
		// sql query has to have exact names from own class variable 
		int to = pageid - 1;
		int from = to + total;
		if (cate.equals("deptId")) {
			List<StudentInfo> list = new ArrayList<StudentInfo>();
			List<DeptInfo> depts = this.deptDAO.listDeptInfoWithName(val);
			if (depts == null || depts.isEmpty()) {
				return list;
			}
			for (DeptInfo dept : depts) {
				String sql = "Select new " + StudentInfo.class.getName()//
		                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear, a.advisorId) "//
		                + " from " + Student.class.getName() + " a where "+cate+" like :val";
		        Session session = sessionFactory.getCurrentSession();
		        Query query = session.createQuery(sql).setParameter("val", dept.getId());
		        System.out.println(sql.toString());
		        list.addAll(query.list());
			}
			if (list.size() < from) {
				from = list.size();
			}
        	return list.subList(to, from);
		}
		else if (cate.equals("uni")) {
			List<StudentInfo> list = new ArrayList<StudentInfo>();
			List<UniInfo> unis = this.uniDAO.listUniInfoWithName(val);
			List<DeptInfo> depts = new ArrayList<DeptInfo>();
			if (unis == null || unis.isEmpty()) {
				return list;
			}
			for (UniInfo uni : unis) {
				 depts.addAll(this.deptDAO.listDeptFromUni(uni.getId()));
			}
			for (DeptInfo dept : depts) {
				String sql = "Select new " + StudentInfo.class.getName()//
		                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear, a.advisorId) "//
		                + " from " + Student.class.getName() + " a where a.deptId like :val";
		        Session session = sessionFactory.getCurrentSession();
		        Query query = session.createQuery(sql).setParameter("val", dept.getId());
		        System.out.println(sql.toString());
		        list.addAll(query.list());
			}
			if (list.size() < from) {
				from = list.size();
			}
        	return list.subList(to, from);
		}
		else {
			String sql = "Select new " + StudentInfo.class.getName()//
	                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear, a.advisorId) "//
	                + " from " + Student.class.getName() + " a where "+cate+" like '%"+val+"%'";
			System.out.println(sql.toString());
	        Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery(sql).setFirstResult(pageid-1).setMaxResults(total);
	        return query.list();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentInfo> listStudentInfosBySearchSize(String val, String cate) {
		// sql query has to have exact names from own class variable 
		if (cate.equals("deptId")) {
			List<StudentInfo> list = new ArrayList<StudentInfo>();
			List<DeptInfo> depts = this.deptDAO.listDeptInfoWithName(val);
			if (depts == null || depts.isEmpty()) {
				return list;
			}
			for (DeptInfo dept : depts) {
				String sql = "Select new " + StudentInfo.class.getName()//
		                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear, a.advisorId) "//
		                + " from " + Student.class.getName() + " a where "+cate+" like :val";
		        Session session = sessionFactory.getCurrentSession();
		        Query query = session.createQuery(sql).setParameter("val", dept.getId());
		        System.out.println(sql.toString());
		        list.addAll(query.list());
			}
        	return list;
		}
		else if (cate.equals("uni")) {
			List<StudentInfo> list = new ArrayList<StudentInfo>();
			List<UniInfo> unis = this.uniDAO.listUniInfoWithName(val);
			List<DeptInfo> depts = new ArrayList<DeptInfo>();
			if (unis == null || unis.isEmpty()) {
				return list;
			}
			for (UniInfo uni : unis) {
				 depts.addAll(this.deptDAO.listDeptFromUni(uni.getId()));
			}
			for (DeptInfo dept : depts) {
				String sql = "Select new " + StudentInfo.class.getName()//
		                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear, a.advisorId) "//
		                + " from " + Student.class.getName() + " a where a.deptId like :val";
		        Session session = sessionFactory.getCurrentSession();
		        Query query = session.createQuery(sql).setParameter("val", dept.getId());
		        System.out.println(sql.toString());
		        list.addAll(query.list());
			}
        	return list;
		}
		else {
			String sql = "Select new " + StudentInfo.class.getName()//
	                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear, a.advisorId) "//
	                + " from " + Student.class.getName() + " a where "+cate+" like '%"+val+"%'";
			System.out.println(sql.toString());
	        Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery(sql);
	        return query.list();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentInfo> listStudentInfosBySearchForAdv(Integer pageid, Integer total, String val, String cate, Integer userid) {
		// sql query has to have exact names from own class variable 
		if (cate.equals("deptId")) {
			List<StudentInfo> list = new ArrayList<StudentInfo>();
			List<DeptInfo> depts = this.deptDAO.listDeptInfoWithName(val);
			if (depts == null || depts.isEmpty()) {
				return list;
			}
			for (DeptInfo dept : depts) {
				String sql = "Select new " + StudentInfo.class.getName()//
		                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear, a.advisorId) "//
		                + " from " + Student.class.getName() + " a where advisorId="+userid+" AND "+cate+" like :val";
		        Session session = sessionFactory.getCurrentSession();
		        Query query = session.createQuery(sql).setParameter("val", dept.getId()).setFirstResult(pageid-1).setMaxResults(total);
		        System.out.println(sql.toString());
		        list.addAll(query.list());
			}
        	return list;
		}
		else if (cate.equals("uni")) {
			List<StudentInfo> list = new ArrayList<StudentInfo>();
			List<UniInfo> unis = this.uniDAO.listUniInfoWithName(val);
			List<DeptInfo> depts = new ArrayList<DeptInfo>();
			if (unis == null || unis.isEmpty()) {
				return list;
			}
			for (UniInfo uni : unis) {
				 depts.addAll(this.deptDAO.listDeptFromUni(uni.getId()));
			}
			for (DeptInfo dept : depts) {
				String sql = "Select new " + StudentInfo.class.getName()//
		                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear, a.advisorId) "//
		                + " from " + Student.class.getName() + " a where advisorId="+userid+" AND a.deptId like :val";
		        Session session = sessionFactory.getCurrentSession();
		        Query query = session.createQuery(sql).setParameter("val", dept.getId()).setFirstResult(pageid-1).setMaxResults(total);
		        System.out.println(sql.toString());
		        list.addAll(query.list());
			}
        	return list;
		}
		else {
			String sql = "Select new " + StudentInfo.class.getName()//
	                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear, a.advisorId) "//
	                + " from " + Student.class.getName() + " a where advisorId="+userid+" AND "+cate+" like '%"+val+"%'";
			System.out.println(sql.toString());
	        Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery(sql).setFirstResult(pageid-1).setMaxResults(total);
	        return query.list();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StudentInfo> listStudentInfosBySearchForAdvSize(String val, String cate, Integer userid) {
		// sql query has to have exact names from own class variable 
		if (cate.equals("deptId")) {
			List<StudentInfo> list = new ArrayList<StudentInfo>();
			List<DeptInfo> depts = this.deptDAO.listDeptInfoWithName(val);
			if (depts == null || depts.isEmpty()) {
				return list;
			}
			for (DeptInfo dept : depts) {
				String sql = "Select new " + StudentInfo.class.getName()//
		                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear, a.advisorId) "//
		                + " from " + Student.class.getName() + " a where advisorId="+userid+" AND "+cate+" like :val";
		        Session session = sessionFactory.getCurrentSession();
		        Query query = session.createQuery(sql).setParameter("val", dept.getId());
		        System.out.println(sql.toString());
		        list.addAll(query.list());
			}
        	return list;
		}
		else if (cate.equals("uni")) {
			List<StudentInfo> list = new ArrayList<StudentInfo>();
			List<UniInfo> unis = this.uniDAO.listUniInfoWithName(val);
			List<DeptInfo> depts = new ArrayList<DeptInfo>();
			if (unis == null || unis.isEmpty()) {
				return list;
			}
			for (UniInfo uni : unis) {
				 depts.addAll(this.deptDAO.listDeptFromUni(uni.getId()));
			}
			for (DeptInfo dept : depts) {
				String sql = "Select new " + StudentInfo.class.getName()//
		                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear, a.advisorId) "//
		                + " from " + Student.class.getName() + " a where advisorId="+userid+" AND a.deptId like :val";
		        Session session = sessionFactory.getCurrentSession();
		        Query query = session.createQuery(sql).setParameter("val", dept.getId());
		        System.out.println(sql.toString());
		        list.addAll(query.list());
			}
        	return list;
		}
		else {
			String sql = "Select new " + StudentInfo.class.getName()//
	                + "(a.id, a.deptId, a.name, a.surname, a.no, a.adpScore, a.recordYear, a.advisorId) "//
	                + " from " + Student.class.getName() + " a where advisorId="+userid+" AND "+cate+" like '%"+val+"%'";
			System.out.println(sql.toString());
	        Session session = sessionFactory.getCurrentSession();
	        Query query = session.createQuery(sql);
	        return query.list();
		}
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
        student.setAdvisorId(studentInfo.getAdvisorId());
 
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
        return new StudentInfo(student.getId(), student.getDeptId(), student.getName(), student.getSurname(), student.getNo(), student.getAdpScore(), student.getRecordYear(), student.getAdvisorId());
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

	@Override
	public void calcAdpAkts(Integer id, Integer coef) {
		List<StudentLessonInfo> list = studentLessonDAO.listStudentLessonInfosForStudent(id);
		int total = 0;
		int val = -1;
		for (StudentLessonInfo l : list) {
			SubstituteLessonInfo les = substituteLessonDAO.findSubstituteLessonInfo(l.getSubstituteLessonId());
			total += les.getAkts();
		}
		if (total < coef){
			val = 1;
		}
		else if (total < 2*coef){
			val = 2;
		}
		else if (total < 3*coef){
			val = 3;
		}
		else if (total < 4*coef){
			val = 4;
		}
		StudentInfo stu = findStudentInfo(id);
		stu.setAdpScore(val);
		saveStudent(stu);
		System.out.println(stu.getAdpScore()+val);
		
	}
	
	@Override
	public void calcAdpCredit(Integer id, Integer coef) {
		List<StudentLessonInfo> list = studentLessonDAO.listStudentLessonInfosForStudent(id);
		int total = 0;
		int val = -1;
		System.out.println("id"+id+" coef"+coef);
		for (StudentLessonInfo l : list) {
			System.out.println("x"+l.getId()+" "+l.getSubstituteLessonId());
			SubstituteLessonInfo les = substituteLessonDAO.findSubstituteLessonInfo(l.getSubstituteLessonId());
			total += les.getCredit();
		}
		System.out.println("tot"+total+" coef "+coef);
		if (total < coef){
			val = 1;
		}
		else if (total < 2*coef){
			val = 2;
		}
		else if (total < 3*coef){
			val = 3;
		}
		else if (total < 4*coef){
			val = 4;
		}
		StudentInfo stu = findStudentInfo(id);
		stu.setAdpScore(val);
		saveStudent(stu);
		System.out.println(stu.getAdpScore()+" "+val);
		
	}

}
