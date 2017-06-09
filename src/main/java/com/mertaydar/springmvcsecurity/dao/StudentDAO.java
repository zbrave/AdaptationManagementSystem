package com.mertaydar.springmvcsecurity.dao;

import java.util.List;

import com.mertaydar.springmvcsecurity.entity.Student;
import com.mertaydar.springmvcsecurity.model.RulesInfo;
import com.mertaydar.springmvcsecurity.model.StudentInfo;

public interface StudentDAO {
	public Student findStudent(Integer id);
	 
    public List<StudentInfo> listStudentInfos();
    
    public List<StudentInfo> listStudentInfos(Integer pageid, Integer total);
    
    public List<StudentInfo> listStudentInfosBySearch(Integer pageid, Integer total, String val, String cate);
 
    public void saveStudent(StudentInfo studentInfo);
 
    public StudentInfo findStudentInfo(Integer id);
 
    public void deleteStudent(Integer id);
    
    public boolean isDuplicate(StudentInfo studentInfo);
    
    public void calcAdpAkts(Integer id, Integer coef);
    
    public void calcAdpCredit(Integer id, Integer coef);


	List<StudentInfo> listStudentInfosForAdv(Integer pageid, Integer total, Integer userid);

	List<StudentInfo> listStudentInfosBySearchForAdv(Integer pageid, Integer total, String val, String cate,
			Integer userid);

	List<StudentInfo> listStudentInfosBySearchForAdvSize(String val, String cate, Integer userid);

	List<StudentInfo> listStudentInfosBySearchSize(String val, String cate);

	List<StudentInfo> listStudentInfosForAdvSize(Integer userid);
}
