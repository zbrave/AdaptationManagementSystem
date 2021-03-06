package com.mertaydar.springmvcsecurity.model;

public class MarkInfo {
	private Integer id;
	private Integer uniId;
	private String mark;
	private Float value;
	
	public MarkInfo() {
		
	}
	
	
	public MarkInfo(Integer id, Integer uniId, String mark, Float value) {
		super();
		this.id = id;
		this.uniId = uniId;
		this.mark = mark;
		this.value = value;
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
