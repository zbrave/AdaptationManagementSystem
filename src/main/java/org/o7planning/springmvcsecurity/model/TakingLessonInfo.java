package org.o7planning.springmvcsecurity.model;

public class TakingLessonInfo {
	
	private Integer id;
	private Integer deptId;
	private String name;
	private String code;
	private String lang;
	private Integer credit;
	private Integer akts;
	private Integer term;
	
	public TakingLessonInfo() {
		super();
	}
	
	

	public TakingLessonInfo(Integer id, Integer deptId, String name, String code, String lang, Integer credit,
			Integer akts, Integer term) {
		super();
		this.id = id;
		this.deptId = deptId;
		this.name = name;
		this.code = code;
		this.lang = lang;
		this.credit = credit;
		this.akts = akts;
		this.term = term;
	}



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
