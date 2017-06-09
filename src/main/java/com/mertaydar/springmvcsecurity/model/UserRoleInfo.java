package com.mertaydar.springmvcsecurity.model;



public class UserRoleInfo {
	private Integer id;
	private Integer userId;
	private UserInfo userInfo;
	private String role;
	
	public UserRoleInfo(){
		
	}
	public UserRoleInfo(Integer id,Integer userId,String role){
		this.id=id;
		this.userId=userId;
		this.role=role;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
