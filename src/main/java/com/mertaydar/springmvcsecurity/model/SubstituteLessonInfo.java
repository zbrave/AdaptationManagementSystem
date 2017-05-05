package com.mertaydar.springmvcsecurity.model;

public class SubstituteLessonInfo {
	
	private Integer id;
	private String name;
	private String code;
	private String lang;
	private Integer credit;
	private Integer lab;
	private Integer akts;
	private Integer term;
	private Integer conditionId;
	
	public SubstituteLessonInfo() {
		super();
	}

	public SubstituteLessonInfo(Integer id, String name, String code, String lang, Integer credit, Integer lab, Integer akts,
			Integer term, Integer conditionId) {
		super();
		this.id = id;
		this.name = name;
		this.code = code;
		this.lang = lang;
		this.credit = credit;
		this.lab = lab;
		this.akts = akts;
		this.term = term;
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
	
}
