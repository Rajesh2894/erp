package com.abm.mainet.workManagement.dao;


import java.util.Date;
import java.util.List;


public interface WorksBudgetReportDao {

	public List<Object[]> getBudgetVsProjExpensesReport(Date fromDate,Date toDate,Long orgId);
}
