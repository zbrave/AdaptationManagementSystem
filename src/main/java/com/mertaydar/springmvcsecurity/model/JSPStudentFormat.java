package com.mertaydar.springmvcsecurity.model;

public class JSPStudentFormat {
	private Integer id;
	private Integer uniId;
	private String uniName;
	private Integer deptId;
	private String deptName;
	private String name;
	private String surname;
	private String no;
	private Integer adpScore;
	private Integer recordYear;
	
	public JSPStudentFormat() {
		
	}
	
	public JSPStudentFormat(Integer id, Integer uniId, String uniName, Integer deptId, String deptName, String name,
			String surname, String no, Integer adpScore, Integer recordYear) {
		super();
		this.id = id;
		this.uniId = uniId;
		this.uniName = uniName;
		this.deptId = deptId;
		this.deptName = deptName;
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

	public Integer getUniId() {
		return uniId;
	}

	public void setUniId(Integer uniId) {
		this.uniId = uniId;
	}

	public String getUniName() {
		return uniName;
	}

	public void setUniName(String uniName) {
		this.uniName = uniName;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
