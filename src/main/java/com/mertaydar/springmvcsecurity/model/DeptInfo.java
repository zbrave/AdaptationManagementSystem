package com.mertaydar.springmvcsecurity.model;

public class DeptInfo {
	private Integer id;
	private Integer uniId;
    private String name;
    
	public DeptInfo() {
		super();
	}

	public DeptInfo(Integer id, Integer uniId, String name) {
		super();
		this.id = id;
		this.uniId = uniId;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
    
}
