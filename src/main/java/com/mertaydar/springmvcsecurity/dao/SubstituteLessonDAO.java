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

	List<SubstituteLessonInfo> listSubstituteLessonInfos(Integer curriculumId);

	List<String> listPoolLessons();

	List<String> listPoolLessonsByCurriculum(Integer id);

	List<SubstituteLessonInfo> listSubstituteLessonInfosPagination(Integer curriculumId, Integer offset,
			Integer maxResults);


	Long count(Integer currId);
}
