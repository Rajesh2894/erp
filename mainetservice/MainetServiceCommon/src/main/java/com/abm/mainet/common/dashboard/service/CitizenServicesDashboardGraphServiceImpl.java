package com.abm.mainet.common.dashboard.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dashboard.dao.CitizenServicesDashboardGraphDAO;
import com.abm.mainet.common.dashboard.domain.skdcl.DeptOrSLAWiseDataEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.DeptOrSLAWiseServiceStatusEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.FinancialDurationWiseServiceStatusEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.ServiceStatusWiseCountEntity;

@Service
@Transactional(readOnly = true)
public class CitizenServicesDashboardGraphServiceImpl {

	private static final Logger log = LoggerFactory.getLogger(CitizenServicesDashboardGraphServiceImpl.class);

	@Autowired
	CitizenServicesDashboardGraphDAO citizenServicesDashboardGraphDAO;

	public List<ServiceStatusWiseCountEntity> getServiceStatusWiseCounts() {
		return citizenServicesDashboardGraphDAO.getServiceStatusWiseCounts();
	}

	public List<DeptOrSLAWiseServiceStatusEntity> getSLAWiseServiceStatus(int noOfDays) {
		if (noOfDays != 0) {
			noOfDays = getNoOfDays(noOfDays);
		}
		return citizenServicesDashboardGraphDAO.getSLAWiseServiceStatus(noOfDays);
	}

	public List<DeptOrSLAWiseServiceStatusEntity> getDeptWiseServiceStatus(int noOfDays) {
		if (noOfDays != 0) {
			noOfDays = getNoOfDays(noOfDays);
		}
		return citizenServicesDashboardGraphDAO.getDeptWiseServiceStatus(noOfDays);
	}

	public List<DeptOrSLAWiseDataEntity> getGridDataByDaysAndSLA(int noOfDays, String sla) {
		noOfDays = getNoOfDays(noOfDays);
		if (sla == null || sla.trim().equalsIgnoreCase(""))
			sla = null;
		return citizenServicesDashboardGraphDAO.getGridDataByDaysAndSLA(noOfDays, sla);
	}

	public List<DeptOrSLAWiseDataEntity> getGridDataByDaysAndDept(int noOfDays, String dept) {
		noOfDays = getNoOfDays(noOfDays);
		if (dept == null || dept.trim().equalsIgnoreCase(""))
			dept = null;
		return citizenServicesDashboardGraphDAO.getGridDataByDaysAndDept(noOfDays, dept);
	}

	public List<FinancialDurationWiseServiceStatusEntity> getYearWiseServiceStatus() {
		return citizenServicesDashboardGraphDAO.getYearWiseServiceStatus();
	}

	private int getNoOfDays(int noOfDays) {
		if (noOfDays == 5) {
			noOfDays = 365;
		} else if (noOfDays == 4) {
			noOfDays = 180;
		} else if (noOfDays == 3) {
			noOfDays = 90;
		} else if (noOfDays == 2) {
			noOfDays = 30;
		} else {
			noOfDays = 1;
		}
		return noOfDays;
	}
	
	public Long getPriviousDayGrievances() {

		return citizenServicesDashboardGraphDAO.getPriviousDayGrievances();
	}

	public Long getPriviousDayReslovedGrievances() {

		return citizenServicesDashboardGraphDAO.getPriviousDayReslovedGrievances();
	}
	public Long getOpenRtiComplaints() {

		return citizenServicesDashboardGraphDAO.getOpenRtiComplaints();
	}

}
