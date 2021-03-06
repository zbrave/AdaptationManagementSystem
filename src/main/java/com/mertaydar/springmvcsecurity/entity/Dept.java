package com.mertaydar.springmvcsecurity.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "dept")
public class Dept implements Serializable {
	
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "uni_id", nullable = false)
	private Integer uniId;
	
	@Column(name = "name", length = 45, nullable = false)
	private String name;
	
	public Integer getId() {
	    return id;
	}
	 
	public void setId(Integer string) {
	    this.id = string;
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
