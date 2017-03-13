package org.o7planning.springmvcsecurity.dao;

import java.util.List;

import org.o7planning.springmvcsecurity.entity.Uni;
import org.o7planning.springmvcsecurity.model.UniInfo;

public interface UniDAO {

    public Uni findUni(Integer id);
 
    public List<UniInfo> listUniInfos();
 
    public void saveUni(UniInfo uniInfo);
 
    public UniInfo findUniInfo(Integer id);
 
    public void deleteUni(Integer id);
    
}
