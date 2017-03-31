package com.mertaydar.springmvcsecurity.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "report")
public class Report implements Serializable {
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "studentid", nullable = false)
	private Integer studentId;
	
	@Column(name = "adpScore", nullable = false)
	private Integer adpScore;
	
	@Column(name = "recordYear", nullable = false)
	private Integer recordYear;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
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
