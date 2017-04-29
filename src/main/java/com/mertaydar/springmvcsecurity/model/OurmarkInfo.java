package com.mertaydar.springmvcsecurity.model;

public class OurmarkInfo {
	private Integer id;
	private String mark;
	private Float value;
	
	public OurmarkInfo() {
		
	}
	
	public OurmarkInfo(Integer id, String mark, Float value) {
		super();
		this.id = id;
		this.mark = mark;
		this.value = value;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Float getValue() {
		return value;
	}
	public void setValue(Float value) {
		this.value = value;
	}
	
	
}

