package com.abm.mainet.workManagement.dao;


import java.util.List;


public interface WorkProjectRegisterReportDao {

	public List<Object[]> findProjectRegisterSheetReport(Long schId,Long projId,Long workType,Long orgId);
}
