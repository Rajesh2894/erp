package com.abm.mainet.workManagement.dao;


import java.util.Date;
import java.util.List;


public interface WorksDeductionReportDao {

	public List<Object[]> getWorksDeductionReport(Long orgId, Date  fromDate, Date  toDate);
}
