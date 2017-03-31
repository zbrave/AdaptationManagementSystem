package com.mertaydar.springmvcsecurity.dao;

import java.util.List;

import com.mertaydar.springmvcsecurity.entity.Report;
import com.mertaydar.springmvcsecurity.model.ReportInfo;

public interface ReportDAO {
	
	public Report findReport(Integer id);
	 
    public List<ReportInfo> listReportInfos();
 
    public void saveReport(ReportInfo reportInfo);
 
    public ReportInfo findReportInfo(Integer id);
 
    public void deleteReport(Integer id);
}
