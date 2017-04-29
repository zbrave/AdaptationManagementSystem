package com.mertaydar.springmvcsecurity.dao;

import java.util.List;

import com.mertaydar.springmvcsecurity.entity.Ourmark;
import com.mertaydar.springmvcsecurity.model.OurmarkInfo;

public interface OurmarkDAO {
	public Ourmark findOurmark(Integer id);
	 
    public List<OurmarkInfo> listOurmarkInfos();
 
    public void saveOurmark(OurmarkInfo ourmarkInfo);
 
    public OurmarkInfo findOurmarkInfo(Integer id);
 
    public void deleteOurmark(Integer id);
}
