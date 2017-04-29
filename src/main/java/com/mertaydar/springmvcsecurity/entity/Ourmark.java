package com.mertaydar.springmvcsecurity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ourmark")
public class Ourmark {
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "`mark`", length = 3, nullable = false)
	private String mark;
	
	@Column(name = "`value`", nullable = false)
	private Float value;

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

	