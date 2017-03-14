package org.o7planning.springmvcsecurity.model;

public class TakingLessonInfo {
	
	private Integer id;
	private Integer deptId;
	private String code;
	private Integer credit;
	private Integer akts;
	
	public TakingLessonInfo() {
		super();
	}

	public TakingLessonInfo(Integer id, Integer deptId, String code, Integer credit, Integer akts) {
		super();
		this.id = id;
		this.deptId = deptId;
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
	
	
	
	
}
