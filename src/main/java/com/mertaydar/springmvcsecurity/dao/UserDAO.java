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
    public List<UserInfo> listUserInfos(Integer pageid, Integer total);
    public List<UserInfo> listUserInfosBySearch(Integer pageid, Integer total, String val, String cate);
	List<UserInfo> listUserInfosRoleManager();
	List<UserInfo> listUserInfosBySearchSize(String val, String cate);
	List<UserInfo> listUserInfosRoleUser();
	List<UserInfo> listUserInfosBySearchRoleUser(Integer pageid, Integer total, String val, String cate);
	List<UserInfo> listUserInfosBySearchSizeRoleUser(String val, String cate);
	List<UserInfo> listUserInfosRoleUser(Integer pageid, Integer total);
	List<UserInfo> listUserInfosBySearchSizeRoleManager(String val, String cate);
	List<UserInfo> listUserInfosBySearchRoleManager(Integer pageid, Integer total, String val, String cate);
	List<UserInfo> listUserInfosRoleManager(Integer pageid, Integer total);
	List<UserInfo> listUserInfosRoleAdmin();
	List<UserInfo> listUserInfosRoleAdmin(Integer pageid, Integer total);
	List<UserInfo> listUserInfosBySearchRoleAdmin(Integer pageid, Integer total, String val, String cate);
	List<UserInfo> listUserInfosBySearchSizeRoleAdmin(String val, String cate);
	List<UserInfo> listUserInfosBySearchSizeRoleNewUser(String val, String cate);
	List<UserInfo> listUserInfosBySearchRoleNewUser(Integer pageid, Integer total, String val, String cate);
	List<UserInfo> listUserInfosRoleNewUser(Integer pageid, Integer total);
	List<UserInfo> listUserInfosRoleNewUser();
	List<UserInfo> listUserInfosBySearchSizeRoleUserStu(String val, String cate);
	List<UserInfo> listUserInfosBySearchRoleUserStu(Integer pageid, Integer total, String val, String cate);
	List<UserInfo> listUserInfosRoleUserStu(Integer pageid, Integer total);
	List<UserInfo> listUserInfosRoleUserStu();
}