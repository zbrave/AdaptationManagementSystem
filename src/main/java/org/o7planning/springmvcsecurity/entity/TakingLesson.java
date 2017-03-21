package org.o7planning.springmvcsecurity.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "taking_lesson")
public class TakingLesson implements Serializable {
	
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "dept_id", nullable = false)
	private Integer deptId;
	
	@Column(name = "name", length = 45, nullable = false)
	private String name;
	
	@Column(name = "code", length = 10, nullable = false)
	private String code;
	
	@Column(name = "language", length = 10, nullable = false)
	private String lang;
	
	@Column(name = "credit", nullable = false)
	private Integer credit;

	@Column(name = "akts", nullable = false)
	private Integer akts;
	
	@Column(name = "term", nullable = false)
	private Integer term;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public Integer getAkts() {
		return akts;
	}

	public void setAkts(Integer akts) {
		this.akts = akts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}
	
	
	
}
