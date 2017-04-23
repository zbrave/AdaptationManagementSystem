package com.mertaydar.springmvcsecurity.dao;

import java.util.List;

import com.mertaydar.springmvcsecurity.entity.UserRole;
import com.mertaydar.springmvcsecurity.model.UserRoleInfo;

public interface UserRoleDAO {
	
	public UserRole findUserRole (Integer id);
	public void saveUserRole (UserRoleInfo userRoleInfo );
	public UserRoleInfo findUserRoleInfo (Integer id);
	public void deleteUserRole(Integer id);
	
	public List<String> getUserRoles(Integer userId);
	public List<UserRoleInfo> listUserRoleInfos();
}
