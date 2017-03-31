package com.mertaydar.springmvcsecurity.model;

public class JSPLessonFormat {
	
	private Integer takId;
	private Integer subId;
	private String takName;
	private String subName;
	private String takCode;
	private String subCode;
	private String subLang;
	private String takLang;
	private Integer takCredit;
	private Integer subCredit;
	private Integer subAkts;
	private Integer takAkts;
	private Integer subTerm;
	private Integer takTerm;
	private String subMark;
	private String takMark;
	
	public JSPLessonFormat() {
		
	}
	
	public JSPLessonFormat(SubstituteLessonInfo sub, TakingLessonInfo tak, String orgMark, String convMark){
		this.subId = sub.getId();
		this.subAkts = sub.getAkts();
		this.subCode = sub.getCode();
		this.subCredit = sub.getCredit();
		this.subLang = sub.getLang();
		this.subName = sub.getName();
		this.subTerm = sub.getTerm();
		this.takId = tak.getId();
		this.takAkts = tak.getAkts();
		this.takCode = tak.getCode();
		this.takCredit = tak.getCredit();
		this.takLang = tak.getLang();
		this.takName = tak.getName();
		this.takTerm = tak.getTerm();
		this.subMark = convMark;
		this.takMark = orgMark;
	}
	
	public Integer getTakId() {
		return takId;
	}
	public void setTakId(Integer takId) {
		this.takId = takId;
	}
	public Integer getSubId() {
		return subId;
	}
	public void setSubId(Integer subId) {
		this.subId = subId;
	}
	public String getTakName() {
		return takName;
	}
	public void setTakName(String takName) {
		this.takName = takName;
	}
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public String getTakCode() {
		return takCode;
	}
	public void setTakCode(String takCode) {
		this.takCode = takCode;
	}
	public String getSubCode() {
		return subCode;
	}
	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	public String getSubLang() {
		return subLang;
	}
	public void setSubLang(String subLang) {
		this.subLang = subLang;
	}
	public String getTakLang() {
		return takLang;
	}
	public void setTakLang(String takLang) {
		this.takLang = takLang;
	}
	public Integer getTakCredit() {
		return takCredit;
	}
	public void setTakCredit(Integer takCredit) {
		this.takCredit = takCredit;
	}
	public Integer getSubCredit() {
		return subCredit;
	}
	public void setSubCredit(Integer subCredit) {
		this.subCredit = subCredit;
	}
	public Integer getSubAkts() {
		return subAkts;
	}
	public void setSubAkts(Integer subAkts) {
		this.subAkts = subAkts;
	}
	public Integer getTakAkts() {
		return takAkts;
	}
	public void setTakAkts(Integer takAkts) {
		this.takAkts = takAkts;
	}
	public Integer getSubTerm() {
		return subTerm;
	}
	public void setSubTerm(Integer subTerm) {
		this.subTerm = subTerm;
	}
	public Integer getTakTerm() {
		return takTerm;
	}
	public void setTakTerm(Integer takTerm) {
		this.takTerm = takTerm;
	}
	public String getSubMark() {
		return subMark;
	}
	public void setSubMark(String subMark) {
		this.subMark = subMark;
	}
	public String getTakMark() {
		return takMark;
	}
	public void setTakMark(String takMark) {
		this.takMark = takMark;
	}
	
	
}
