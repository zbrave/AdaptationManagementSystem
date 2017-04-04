package com.mertaydar.springmvcsecurity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mark")
public class Mark {
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "uni_id", nullable = false)
	private Integer uniId;
	
	@Column(name = "`from`", length = 3, nullable = false)
	private String from;
	
	@Column(name = "`to`", length = 3, nullable = false)
	private String to;

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
