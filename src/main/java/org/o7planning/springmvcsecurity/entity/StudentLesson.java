package org.o7planning.springmvcsecurity.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "student_lesson")
public class StudentLesson implements Serializable {
	
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "report_id", nullable = false)
	private Integer reportId;
	
	@Column(name = "taking_lesson_id", nullable = false)
	private Integer takingLessonId;
	
	@Column(name = "org_mark", length = 5, nullable = false)
	private String orgMark;
	
	@Column(name = "conv_mark", length = 5, nullable = false)
	private String convMark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Integer getTakingLessonId() {
		return takingLessonId;
	}

	public void setTakingLessonId(Integer takingLessonId) {
		this.takingLessonId = takingLessonId;
	}

	public String getOrgMark() {
		return orgMark;
	}

	public void setOrgMark(String orgMark) {
		this.orgMark = orgMark;
	}

	public String getConvMark() {
		return convMark;
	}

	public void setConvMark(String convMark) {
		this.convMark = convMark;
	}
	
	
}
