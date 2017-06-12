package com.mertaydar.springmvcsecurity.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "substitute_lesson")
public class SubstituteLesson implements Serializable {
	
	@Id
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "name", length = 45, nullable = false)
	private String name;
	
	@Column(name = "code", length = 10, nullable = false)
	private String code;
	
	@Column(name = "language", length = 10, nullable = false)
	private String lang;
	
	@Column(name = "credit", nullable = false)
	private Integer credit;
	
	@Column(name = "lab", nullable = false)
	private Integer lab;

	@Column(name = "akts", nullable = false)
	private Integer akts;
	
	@Column(name = "term", nullable = false)
	private Integer term;
	
	@Column(name = "condition_id", nullable = true)
	private Integer conditionId;
	
	@Column(name = "curriculum_id", nullable = false)
	private Integer curriculumId;
	
	@Column(name = "base", nullable = true, length = 5)
	private String base;
	
	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Integer getCurriculumId() {
		return curriculumId;
	}

	public void setCurriculumId(Integer curriculumId) {
		this.curriculumId = curriculumId;
	}

	public Integer getLab() {
		return lab;
	}

	public void setLab(Integer lab) {
		this.lab = lab;
	}

	public Integer getConditionId() {
		return conditionId;
	}

	public void setConditionId(Integer conditionId) {
		this.conditionId = conditionId;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
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

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	
	
	
}
