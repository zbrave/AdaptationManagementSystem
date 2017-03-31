package com.mertaydar.springmvcsecurity.dao;

import java.util.List;

import com.mertaydar.springmvcsecurity.entity.TakingLesson;
import com.mertaydar.springmvcsecurity.model.TakingLessonInfo;

public interface TakingLessonDAO {
	
	public TakingLesson findTakingLesson(Integer id);
	 
    public List<TakingLessonInfo> listTakingLessonInfos();
    
    public List<TakingLessonInfo> listTakingLessonFromDept(Integer id);
 
    public void saveTakingLesson(TakingLessonInfo takingLessonInfo);
 
    public TakingLessonInfo findTakingLessonInfo(Integer id);
 
    public void deleteTakingLesson(Integer id);
}
