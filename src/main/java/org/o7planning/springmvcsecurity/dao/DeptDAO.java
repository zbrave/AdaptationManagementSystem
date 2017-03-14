package org.o7planning.springmvcsecurity.dao;

import java.util.List;

import org.o7planning.springmvcsecurity.entity.Dept;
import org.o7planning.springmvcsecurity.model.DeptInfo;

public interface DeptDAO {
	
	public Dept findDept(Integer id);
	 
    public List<DeptInfo> listDeptInfos();
 
    public void saveDept(DeptInfo deptInfo);
 
    public DeptInfo findDeptInfo(Integer id);
 
    public void deleteDept(Integer id);
}
