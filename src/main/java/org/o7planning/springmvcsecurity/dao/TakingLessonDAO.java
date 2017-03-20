package org.o7planning.springmvcsecurity.dao;

import java.util.List;

import org.o7planning.springmvcsecurity.entity.TakingLesson;
import org.o7planning.springmvcsecurity.model.TakingLessonInfo;

public interface TakingLessonDAO {
	
	public TakingLesson findTakingLesson(Integer id);
	 
    public List<TakingLessonInfo> listTakingLessonInfos();
    
    public List<TakingLessonInfo> listTakingLessonFromDept(Integer id);
 
    public void saveTakingLesson(TakingLessonInfo takingLessonInfo);
 
    public TakingLessonInfo findTakingLessonInfo(Integer id);
 
    public void deleteTakingLesson(Integer id);
}
