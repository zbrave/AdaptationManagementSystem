package com.mertaydar.springmvcsecurity.dao;

import java.util.List;

import com.mertaydar.springmvcsecurity.entity.Curriculum;
import com.mertaydar.springmvcsecurity.model.CurriculumInfo;

public interface CurriculumDAO {
	public Curriculum findCurriculum(Integer id);
	 
    public List<CurriculumInfo> listCurriculumInfos();
    
    public void saveCurriculum(CurriculumInfo curriculumInfo);
 
    public CurriculumInfo findCurriculumInfo(Integer id);
    
    public CurriculumInfo findCurriculumInfoActive();
    
    public void deleteCurriculum(Integer id);
}
