package com.mertaydar.springmvcsecurity.dao;

import java.util.List;

import com.mertaydar.springmvcsecurity.entity.SubstituteLesson;
import com.mertaydar.springmvcsecurity.model.SubstituteLessonInfo;

public interface SubstituteLessonDAO {
	
	public SubstituteLesson findSubstituteLesson(Integer id);
	 
    public List<SubstituteLessonInfo> listSubstituteLessonInfos();
 
    public void saveSubstituteLesson(SubstituteLessonInfo substituteLessonInfo);
 
    public SubstituteLessonInfo findSubstituteLessonInfo(Integer id);
 
    public void deleteSubstituteLesson(Integer id);
}
