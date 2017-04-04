package com.mertaydar.springmvcsecurity.model;

public class MarkInfo {
	private Integer id;
	private Integer uniId;
	private String from;
	private String to;
	
	public MarkInfo() {
		
	}
	
	public MarkInfo(Integer id, Integer uniId, String from, String to) {
		super();
		this.id = id;
		this.uniId = uniId;
		this.from = from;
		this.to = to;
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
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	
}
