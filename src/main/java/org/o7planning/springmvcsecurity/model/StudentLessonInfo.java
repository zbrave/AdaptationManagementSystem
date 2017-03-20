package org.o7planning.springmvcsecurity.model;

public class StudentLessonInfo {
	private Integer id;
	private Integer reportId;
	private Integer takingLessonId;
    private String orgMark;
    private String convMark;
    
    public StudentLessonInfo(){
    	
    }

	public StudentLessonInfo(Integer id, Integer reportId, Integer takingLessonId, String orgMark, String convMark) {
		super();
		this.id = id;
		this.reportId = reportId;
		this.takingLessonId = takingLessonId;
		this.orgMark = orgMark;
		this.convMark = convMark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getReportId() {
		return reportId;
	}

	public void setReportId(Integer reportId) {
		this.reportId = reportId;
	}

	public Integer getTakingLessonId() {
		return takingLessonId;
	}

	public void setTakingLessonId(Integer takingLessonId) {
		this.takingLessonId = takingLessonId;
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
