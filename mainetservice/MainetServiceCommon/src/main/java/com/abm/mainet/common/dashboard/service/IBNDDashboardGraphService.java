package com.abm.mainet.common.dashboard.service;

import java.util.List;

import com.abm.mainet.common.dashboard.domain.YearWiseGrievanceGraphEntity;
import com.abm.mainet.common.dashboard.domain.skdcl.BndApplicationDtlEntity;

public interface IBNDDashboardGraphService {
	
	public List<YearWiseGrievanceGraphEntity> getYearWiseGraphEntityList();
	
	public List<YearWiseGrievanceGraphEntity> getBNDDataStatusByYearAndType(String type,Integer noOfDays);
	
	public List<BndApplicationDtlEntity> getBNDDataListByStatusAndType(String type,Integer noOfDays,String status);
}
