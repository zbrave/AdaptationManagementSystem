package com.mertaydar.springmvcsecurity.model;

public class StudentLessonInfo {
	private Integer id;
	private Integer studentId;
	private Integer takingLessonId;
	private Integer substituteLessonId;
    private String orgMark;
    private String convMark;
    
    public StudentLessonInfo(){
    	
    }

	public StudentLessonInfo(Integer id, Integer studentId, Integer takingLessonId, Integer substituteLessonId,
			String orgMark, String convMark) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.takingLessonId = takingLessonId;
		this.substituteLessonId = substituteLessonId;
		this.orgMark = orgMark;
		this.convMark = convMark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getTakingLessonId() {
		return takingLessonId;
	}

	public void setTakingLessonId(Integer takingLessonId) {
		this.takingLessonId = takingLessonId;
	}

	public Integer getSubstituteLessonId() {
		return substituteLessonId;
	}

	public void setSubstituteLessonId(Integer substituteLessonId) {
		this.substituteLessonId = substituteLessonId;
	}

	public String getOrgMark() {
		return orgMark;
	}

	public void setOrgMark(String orgMark) {
		this.orgMark = orgMark;
	}

	public String getConvMark() {
		return convMark;
	}

	public void setConvMark(String convMark) {
		this.convMark = convMark;
	}

	
}
