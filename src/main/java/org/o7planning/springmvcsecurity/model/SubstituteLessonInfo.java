package org.o7planning.springmvcsecurity.model;

public class SubstituteLessonInfo {
	
	private Integer id;
	private Integer takingLessonId;
	private String code;
	private Integer credit;
	private Integer akts;
	
	public SubstituteLessonInfo() {
		super();
	}

	public SubstituteLessonInfo(Integer id, Integer takingLessonId, String code, Integer credit, Integer akts) {
		super();
		this.id = id;
		this.takingLessonId = takingLessonId;
		this.code = code;
		this.credit = credit;
		this.akts = akts;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTakingLessonId() {
		return takingLessonId;
	}

	public void setTakingLessonId(Integer takingLessonId) {
		this.takingLessonId = takingLessonId;
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
	
	
	
}
