package com.mertaydar.springmvcsecurity.dao;

import java.util.List;

import com.mertaydar.springmvcsecurity.entity.StudentLesson;
import com.mertaydar.springmvcsecurity.model.StudentLessonInfo;

public interface StudentLessonDAO {
	public StudentLesson findStudentLesson(Integer id);
	 
    public List<StudentLessonInfo> listStudentLessonInfos();
    
    public List<StudentLessonInfo> listStudentLessonInfosForStudent(Integer id);
    
    public void saveStudentLesson(StudentLessonInfo studentLessonInfo);
    
    public void saveStudentLessonExempt(StudentLessonInfo studentLessonInfo);
    
    public void saveStudentLessonNoConvert(StudentLessonInfo studentLessonInfo);
 
    public StudentLessonInfo findStudentLessonInfo(Integer id);
 
    public void deleteStudentLesson(Integer id);
    
    public boolean isDuplicate(StudentLessonInfo studentLessonInfo);
}
