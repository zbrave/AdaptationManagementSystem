package org.o7planning.springmvcsecurity.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.o7planning.springmvcsecurity.dao.ReportDAO;
import org.o7planning.springmvcsecurity.entity.Report;
import org.o7planning.springmvcsecurity.model.ReportInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class ReportDAOImpl implements ReportDAO {

	@Autowired
    private SessionFactory sessionFactory;
	
	@Override
	public Report findReport(Integer id) {
		Session session = sessionFactory.getCurrentSession();
        Criteria crit = session.createCriteria(Report.class);
        crit.add(Restrictions.eq("id", id));
        return (Report) crit.uniqueResult();
	}

	@Override
	public List<ReportInfo> listReportInfos() {
		// sql query has to have exact names from own class variable 
		String sql = "Select new " + ReportInfo.class.getName()//
                + "(a.id, a.studentId, a.adpScore, a.recordYear) "//
                + " from " + Report.class.getName() + " a ";
		System.out.println(sql.toString());
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(sql);
        return query.list();
	}

	@Override
	public void saveReport(ReportInfo reportInfo) {
		Integer id = reportInfo.getId();
        Report report = null;
        if (id != null) {
            report = this.findReport(id);
        }
        boolean isNew = false;
        if (report == null) {
            isNew = true;
            report = new Report();
            //
            String sql = "SELECT MAX(id) FROM " + Report.class.getName();
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(sql);
            if (query.list().get(0) == null){
            	report.setId(1);
            }
            else {
            	report.setId(Integer.parseInt(query.list().get(0).toString())+1);
            }       
        }
        report.setAdpScore(reportInfo.getAdpScore());
        report.setRecordYear(reportInfo.getRecordYear());
        report.setStudentId(reportInfo.getStudentId());
 
        if (isNew) {
            Session session = this.sessionFactory.getCurrentSession();
            session.persist(report);
        }
		
	}

	@Override
	public ReportInfo findReportInfo(Integer id) {
		Report report = this.findReport(id);
        if (report == null) {
            return null;
        }
        return new ReportInfo(report.getId(), report.getStudentId(), report.getAdpScore(), report.getRecordYear());
	}

	@Override
	public void deleteReport(Integer id) {
		Report report = this.findReport(id);
        if (report != null) {
            this.sessionFactory.getCurrentSession().delete(report);
        }
		
	}

}
