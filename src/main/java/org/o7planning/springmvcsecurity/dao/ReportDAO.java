package org.o7planning.springmvcsecurity.dao;

import java.util.List;

import org.o7planning.springmvcsecurity.entity.Report;
import org.o7planning.springmvcsecurity.model.ReportInfo;

public interface ReportDAO {
	
	public Report findReport(Integer id);
	 
    public List<ReportInfo> listReportInfos();
 
    public void saveReport(ReportInfo reportInfo);
 
    public ReportInfo findReportInfo(Integer id);
 
    public void deleteReport(Integer id);
}
