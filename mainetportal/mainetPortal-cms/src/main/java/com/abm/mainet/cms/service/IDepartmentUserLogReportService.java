package com.abm.mainet.cms.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.cms.dto.DepartmentUserLogReportDTO;

public interface IDepartmentUserLogReportService {

	List<DepartmentUserLogReportDTO> getUserLogReport(String section, Date fromDate, Date toDate,
			Long orgId);


}
