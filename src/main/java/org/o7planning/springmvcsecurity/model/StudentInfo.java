package org.o7planning.springmvcsecurity.model;

public class StudentInfo {
	private Integer id;
	private Integer deptId;
	private String name;
	private String surname;
	private String no;
	private Integer adpScore;
	private Integer recordYear;
	
	public StudentInfo(){
		
	}

	public StudentInfo(Integer id, Integer deptId, String name, String surname, String no, Integer adpScore, Integer recordYear) {
		super();
		this.id = id;
		this.deptId = deptId;
		this.name = name;
		this.surname = surname;
		this.no = no;
		this.adpScore = adpScore;
		this.recordYear = recordYear;
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

	public Integer getAdpScore() {
		return adpScore;
	}

	public void setAdpScore(Integer adpScore) {
		this.adpScore = adpScore;
	}

	public Integer getRecordYear() {
		return recordYear;
	}

	public void setRecordYear(Integer recordYear) {
		this.recordYear = recordYear;
	}
	
	
}
