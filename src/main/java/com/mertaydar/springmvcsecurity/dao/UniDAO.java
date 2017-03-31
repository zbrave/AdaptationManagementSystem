package com.mertaydar.springmvcsecurity.dao;

import java.util.List;

import com.mertaydar.springmvcsecurity.entity.Uni;
import com.mertaydar.springmvcsecurity.model.UniInfo;

public interface UniDAO {

    public Uni findUni(Integer id);
 
    public List<UniInfo> listUniInfos();
 
    public void saveUni(UniInfo uniInfo);
 
    public UniInfo findUniInfo(Integer id);
 
    public void deleteUni(Integer id);
    
}
