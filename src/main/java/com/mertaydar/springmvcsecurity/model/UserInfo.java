package com.mertaydar.springmvcsecurity.model;
 
public class UserInfo {
	
	private Integer id;
	private String email;
    private String username;
    private String password;
    private String passwordConf;
    private Integer studentId;
    private String tel;
    private boolean enabled;
    private boolean manager;
    private Integer totalWeight;
    public UserInfo() {
    	
    }
    
    

	public UserInfo(Integer id, String email, String username, String password, Integer studentId,
			boolean enabled, String tel) {
		super();
		this.id = id;
		this.email = email;
		this.username = username;
		this.password = password;
		this.studentId = studentId;
		this.enabled = enabled;
		this.tel = tel;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getPasswordConf() {
		return passwordConf;
	}

	public void setPasswordConf(String passwordConf) {
		this.passwordConf = passwordConf;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}



	public String getTel() {
		return tel;
	}



	public void setTel(String tel) {
		this.tel = tel;
	}



	public boolean isManager() {
		return manager;
	}



	public void setManager(boolean manager) {
		this.manager = manager;
	}



	public Integer getTotalWeight() {
		return totalWeight;
	}



	public void setTotalWeight(Integer totalWeight) {
		this.totalWeight = totalWeight;
	}
     
    
 
}