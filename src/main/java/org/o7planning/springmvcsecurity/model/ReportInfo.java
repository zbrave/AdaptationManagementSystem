package org.o7planning.springmvcsecurity.model;

public class ReportInfo {
	private Integer id;
	private Integer studentId;
    private Integer adpScore;
    private Integer recordYear;
    
    public ReportInfo(){
    	
    }
	public ReportInfo(Integer id, Integer studentId, Integer adpScore, Integer recordYear) {
		super();
		this.id = id;
		this.studentId = studentId;
		this.adpScore = adpScore;
		this.recordYear = recordYear;
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
	public Integer getAdpScore() {
		return adpScore;
	}
	public void setAdpScore(Integer adpScore) {
		this.adpScore = adpScore;
	}
	public Integer getRecordYear() {
		return recordYear;
	}
	public void setRecordYear(Integer recordYear) {
		this.recordYear = recordYear;
	}
    
    
}
