package com.mertaydar.springmvcsecurity.model;

public class UniInfo {
	
	private Integer id;
    private String name;
	
    public UniInfo() {
    	
    }
    
    public UniInfo(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    
    
}
