package com.mertaydar.springmvcsecurity.dao;

import java.util.List;

import com.mertaydar.springmvcsecurity.entity.Rules;
import com.mertaydar.springmvcsecurity.model.RulesInfo;

public interface RulesDAO {
	
	public Rules findRules(Integer id);
	 
    public List<RulesInfo> listRulesInfos();
    
    public List<RulesInfo> listRulesForLesson(Integer id);
 
    public void saveRules(RulesInfo rulesInfo);
 
    public RulesInfo findRulesInfo(Integer id);
 
    public void deleteRules(Integer id);
    
    public boolean isDuplicate(RulesInfo rulesInfo);
    
    public boolean isLabCondition(RulesInfo rulesInfo);
}
