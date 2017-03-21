package org.o7planning.springmvcsecurity.dao;

import java.util.List;

import org.o7planning.springmvcsecurity.entity.Student;
import org.o7planning.springmvcsecurity.model.RulesInfo;
import org.o7planning.springmvcsecurity.model.StudentInfo;

public interface StudentDAO {
	public Student findStudent(Integer id);
	 
    public List<StudentInfo> listStudentInfos();
 
    public void saveStudent(StudentInfo studentInfo);
 
    public StudentInfo findStudentInfo(Integer id);
 
    public void deleteStudent(Integer id);
    
    public boolean isDuplicate(StudentInfo studentInfo);
}
