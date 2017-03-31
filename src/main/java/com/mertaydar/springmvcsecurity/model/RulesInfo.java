package com.mertaydar.springmvcsecurity.model;

public class RulesInfo {
	private Integer id;
	private Integer takingLessonId;
	private Integer substituteLessonId;
	
	public RulesInfo(){
		
	}
	
	public RulesInfo(Integer id, Integer takingLessonId, Integer substituteLessonId) {
		super();
		this.id = id;
		this.takingLessonId = takingLessonId;
		this.substituteLessonId = substituteLessonId;
	}
	
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
