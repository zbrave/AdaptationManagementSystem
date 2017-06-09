package com.mertaydar.springmvcsecurity.model;

public class CurriculumInfo {
	
	private Integer id;
	private Integer year;
	private boolean enabled;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public CurriculumInfo(Integer id, Integer year, boolean enabled) {
		super();
		this.id = id;
		this.year = year;
		this.enabled = enabled;
	}
	public CurriculumInfo() {
		
	}
}
