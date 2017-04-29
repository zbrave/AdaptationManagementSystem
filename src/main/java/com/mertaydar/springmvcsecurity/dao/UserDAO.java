package com.mertaydar.springmvcsecurity.dao;

import java.util.List;

import com.mertaydar.springmvcsecurity.entity.User;
import com.mertaydar.springmvcsecurity.model.UserInfo;
 
public interface UserDAO {
	
	public User findUser (Integer id); 
	public void saveUser (UserInfo userInfo);
    public UserInfo findUserInfo (Integer id);  
    public void deleteUser (Integer id);
    
    public User findLoginUser(String username);
    public UserInfo findLoginUserInfo(String username);
    public List<UserInfo> listUserInfos();
    public List<UserInfo> listUserInfosRoleAdmin();
}