package com.mertaydar.springmvcsecurity.dao;

import java.util.List;

import com.mertaydar.springmvcsecurity.entity.Mark;
import com.mertaydar.springmvcsecurity.model.MarkInfo;

public interface MarkDAO {
	public Mark findMark(Integer id);
	 
    public List<MarkInfo> listMarkInfos();
    
    public List<MarkInfo> listMarkFromUni(Integer id);
 
    public void saveMark(MarkInfo markInfo);
 
    public MarkInfo findMarkInfo(Integer id);
 
    public void deleteMark(Integer id);
}
