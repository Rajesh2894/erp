package com.abm.mainet.common.dashboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abm.mainet.common.dashboard.dao.IBNDDashboardGraphDAO;
import com.abm.mainet.common.dashboard.domain.YearWiseGrievanceGraphEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.BndApplicationDtlEntity;

@Service
public class BNDDashboardGraphServiceImpl implements IBNDDashboardGraphService {
	
	@Autowired
	private IBNDDashboardGraphDAO bndDashboardGraphDAO; 
	
	@Override
	public List<YearWiseGrievanceGraphEntity> getYearWiseGraphEntityList() {
		return bndDashboardGraphDAO.getYearWiseGraphEntityList();
	}
	
	@Override
	public List<YearWiseGrievanceGraphEntity> getBNDDataStatusByYearAndType(String type,Integer noOfDays) {
		return bndDashboardGraphDAO.getBNDDataStatusByYearAndType(type,noOfDays);
	}
	
	public List<BndApplicationDtlEntity> getBNDDataListByStatusAndType(String type,Integer noOfDays,String status){
		return bndDashboardGraphDAO.getBNDDataListByStatusAndType(type,noOfDays,status);
	}

}
