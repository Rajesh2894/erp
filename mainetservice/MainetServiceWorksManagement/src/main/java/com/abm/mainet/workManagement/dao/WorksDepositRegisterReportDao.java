/**
 * 
 */
package com.abm.mainet.workManagement.dao;

import java.util.Date;
import java.util.List;



public interface WorksDepositRegisterReportDao {

	public List<Object[]> getWorksDepositRegisterReport(Long orgId, Date  fromDate, Date  toDate);
}
