package org.o7planning.springmvcsecurity.dao;

import java.util.List;

import org.o7planning.springmvcsecurity.entity.StudentLesson;
import org.o7planning.springmvcsecurity.model.StudentLessonInfo;

public interface StudentLessonDAO {
	public StudentLesson findStudentLesson(Integer id);
	 
    public List<StudentLessonInfo> listStudentLessonInfos();
    
    public List<StudentLessonInfo> listStudentLessonInfosForStudent(Integer id);
    
    public void saveStudentLesson(StudentLessonInfo studentLessonInfo);
 
    public StudentLessonInfo findStudentLessonInfo(Integer id);
 
    public void deleteStudentLesson(Integer id);
}
