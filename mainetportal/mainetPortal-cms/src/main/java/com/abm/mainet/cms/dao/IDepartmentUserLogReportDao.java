package com.abm.mainet.cms.dao;

import java.util.Date;
import java.util.List;

public interface IDepartmentUserLogReportDao {

	List<Object[]> getUserLogReport(String section, Date fromDate, Date toDate, Long orgid);

}
