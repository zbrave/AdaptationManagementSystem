package com.mertaydar.springmvcsecurity.dao;

import java.util.List;

import com.mertaydar.springmvcsecurity.entity.Dept;
import com.mertaydar.springmvcsecurity.model.DeptInfo;

public interface DeptDAO {
	
	public Dept findDept(Integer id);
	 
    public List<DeptInfo> listDeptInfos();
    
    public List<DeptInfo> listDeptFromUni(Integer id);
 
    public void saveDept(DeptInfo deptInfo);
 
    public DeptInfo findDeptInfo(Integer id);
    
    public DeptInfo findDeptInfoWithName(String name);
    
    public List<DeptInfo> listDeptInfoWithName(String name);
 
    public void deleteDept(Integer id);
}
