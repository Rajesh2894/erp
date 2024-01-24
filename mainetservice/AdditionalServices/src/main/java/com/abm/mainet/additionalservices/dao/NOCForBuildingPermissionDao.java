package com.abm.mainet.additionalservices.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.additionalservices.domain.NOCForBuildingPermissionEntity;

public interface NOCForBuildingPermissionDao {
	
	List<NOCForBuildingPermissionEntity> getAppliDetail(Long apmApplicationId, Date fromDate, Date toDate,Long orgId,String refNo);

}
