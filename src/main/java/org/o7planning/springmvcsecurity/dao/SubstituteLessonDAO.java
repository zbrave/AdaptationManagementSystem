package org.o7planning.springmvcsecurity.dao;

import java.util.List;

import org.o7planning.springmvcsecurity.entity.SubstituteLesson;
import org.o7planning.springmvcsecurity.model.SubstituteLessonInfo;

public interface SubstituteLessonDAO {
	
	public SubstituteLesson findSubstituteLesson(Integer id);
	 
    public List<SubstituteLessonInfo> listSubstituteLessonInfos();
 
    public void saveSubstituteLesson(SubstituteLessonInfo substituteLessonInfo);
 
    public SubstituteLessonInfo findSubstituteLessonInfo(Integer id);
 
    public void deleteSubstituteLesson(Integer id);
}
