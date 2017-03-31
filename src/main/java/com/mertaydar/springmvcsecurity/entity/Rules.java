package com.mertaydar.springmvcsecurity.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "rules")
public class Rules implements Serializable {
	
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "taking_lesson_id", nullable = false)
	private Integer takingLessonId;
	
	@Column(name = "substitute_lesson_id", nullable = false)
	private Integer substituteLessonId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTakingLessonId() {
		return takingLessonId;
	}

	public void setTakingLessonId(Integer takingLessonId) {
		this.takingLessonId = takingLessonId;
	}

	public Integer getSubstituteLessonId() {
		return substituteLessonId;
	}

	public void setSubstituteLessonId(Integer substituteLessonId) {
		this.substituteLessonId = substituteLessonId;
	}
	
	
}
