package com.mertaydar.springmvcsecurity.dao;

import java.util.List;

import com.mertaydar.springmvcsecurity.entity.Student;
import com.mertaydar.springmvcsecurity.model.RulesInfo;
import com.mertaydar.springmvcsecurity.model.StudentInfo;

public interface StudentDAO {
	public Student findStudent(Integer id);
	 
    public List<StudentInfo> listStudentInfos();
    
    public List<StudentInfo> listStudentInfos(Integer pageid, Integer total);
    
    public List<StudentInfo> listStudentInfosByNo(Integer pageid, Integer total, String no);
 
    public void saveStudent(StudentInfo studentInfo);
 
    public StudentInfo findStudentInfo(Integer id);
 
    public void deleteStudent(Integer id);
    
    public boolean isDuplicate(StudentInfo studentInfo);
}
