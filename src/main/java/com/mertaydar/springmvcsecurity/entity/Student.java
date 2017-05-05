package com.mertaydar.springmvcsecurity.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "student")
public class Student implements Serializable {
	
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "dept_id", nullable = false)
	private Integer deptId;
	
	@Column(name = "name", length = 45, nullable = false)
	private String name;
	
	@Column(name = "surname", length = 45, nullable = false)
	private String surname;
	
	@Column(name = "no", length = 10, nullable = false)
	private String no;
	
	@Column(name = "adp_score", nullable = true)
	private Integer adpScore;
	
	@Column(name = "record_year", nullable = false)
	private Integer recordYear;
	
	@Column(name = "advisor_id", nullable = true)
	private Integer advisorId;

	public Integer getAdvisorId() {
		return advisorId;
	}

	public void setAdvisorId(Integer advisorId) {
		this.advisorId = advisorId;
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
