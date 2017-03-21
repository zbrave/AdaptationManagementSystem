package org.o7planning.springmvcsecurity.dao;

import java.util.List;

import org.o7planning.springmvcsecurity.entity.Rules;
import org.o7planning.springmvcsecurity.model.RulesInfo;

public interface RulesDAO {
	
	public Rules findRules(Integer id);
	 
    public List<RulesInfo> listRulesInfos();
    
    public List<RulesInfo> listRulesForLesson(Integer id);
 
    public void saveRules(RulesInfo rulesInfo);
 
    public RulesInfo findRulesInfo(Integer id);
 
    public void deleteRules(Integer id);
    
    public boolean isDuplicate(RulesInfo rulesInfo);
}
