package org.o7planning.springmvcsecurity.model;

public class StudentInfo {
	private Integer id;
	private Integer deptId;
	private String name;
	private String surname;
	private String no;
	
	public StudentInfo(){
		
	}

	public StudentInfo(Integer id, Integer deptId, String name, String surname, String no) {
		super();
		this.id = id;
		this.deptId = deptId;
		this.name = name;
		this.surname = surname;
		this.no = no;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	
}
