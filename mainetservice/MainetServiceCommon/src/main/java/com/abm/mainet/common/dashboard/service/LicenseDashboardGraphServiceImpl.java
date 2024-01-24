package com.abm.mainet.common.dashboard.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abm.mainet.common.dashboard.dao.LicenseDashboardGraphDAO;
import com.abm.mainet.common.dashboard.domain.skdcl.LicenseCntDayWiseEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LicenseCntEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LicenseDataEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LicenseDaysWiseDetEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LicenseIssuedCntAndRevenueEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LicenseIssuedDetEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.LocAndCategoryWiseLicenseCntEntity;

@Service
@Transactional(readOnly = true)
public class LicenseDashboardGraphServiceImpl {

	private static final Logger log = LoggerFactory.getLogger(LicenseDashboardGraphServiceImpl.class);

	@Autowired
	LicenseDashboardGraphDAO licenseDashboardGraphDAO;

	@Autowired
	CitizenDashboardGraphServiceImpl citizenDashboardGraphServiceImpl;

	public List<LocAndCategoryWiseLicenseCntEntity> getZoneAndCategoryWiseLicenseCount() {
		return licenseDashboardGraphDAO.getZoneAndCategoryWiseLicenseCount();
	}

	public List<LicenseCntEntity> getYearWiseLicenseCount() {
		return licenseDashboardGraphDAO.getYearWiseLicenseCount();
	}

	public List<LicenseCntDayWiseEntity> getLicenseCountByDays(int noOfDays) {
		noOfDays = citizenDashboardGraphServiceImpl.getNoOfDays(noOfDays);
		return licenseDashboardGraphDAO.getLicenseCountByDays(noOfDays);
	}

	public List<LicenseDataEntity> getLicenseDataByDaysAndType(int noOfDays, String type) {
		noOfDays = citizenDashboardGraphServiceImpl.getNoOfDays(noOfDays);
		if (type == null || type.trim().equalsIgnoreCase(""))
			type = null;
		return licenseDashboardGraphDAO.getLicenseDataByDaysAndType(noOfDays, type);
	}

	public List<LicenseIssuedDetEntity> getIssuedLicenseDetByOrgId(Long orgId) {
		// TODO Auto-generated method stub
		return licenseDashboardGraphDAO.getIssuedLicenseDetByOrgId(orgId);
		
	}

	public List<LicenseDaysWiseDetEntity> getYearWiseLicenseData(long orgId) {
		// TODO Auto-generated method stub
		return licenseDashboardGraphDAO.getYearWiseLicenseData(orgId);
	}

	public List<LicenseDaysWiseDetEntity> getHalfYearWiseLicenseData(long orgId) {
		// TODO Auto-generated method stub
		return licenseDashboardGraphDAO.getHalfYearWiseLicenseData(orgId);
	}

	public List<LicenseDaysWiseDetEntity> getQuarterWiseLicenseData(long orgId) {
		// TODO Auto-generated method stub
		return licenseDashboardGraphDAO.getQuarterWiseLicenseData(orgId);
	}

	public List<LicenseDaysWiseDetEntity> getsevenDayrWiseLicenseData(long orgId) {
		// TODO Auto-generated method stub
		return licenseDashboardGraphDAO.getsevenDayrWiseLicenseData(orgId);
	}

	public List<LicenseIssuedDetEntity> getLicenceULBReport(long orgId) {
	
		return licenseDashboardGraphDAO.getLicenceULBReport(orgId);
	}

	public List<LicenseIssuedCntAndRevenueEntity> getTotalIssuedlicenseTotalrevenue(long orgId) {
		// TODO Auto-generated method stub
		return licenseDashboardGraphDAO.getTotalIssuedlicenseTotalrevenue(orgId);
	}
}
