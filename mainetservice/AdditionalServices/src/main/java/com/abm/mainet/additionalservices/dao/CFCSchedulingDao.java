package com.abm.mainet.additionalservices.dao;

import java.util.List;

import com.abm.mainet.additionalservices.domain.CFCCollectionMasterEntity;
import com.abm.mainet.additionalservices.domain.CFCCounterScheduleEntity;

public interface CFCSchedulingDao {


	CFCCounterScheduleEntity searchCounterScheduleBuId(Long orgId, Long counterScheduleId);

	List<CFCCounterScheduleEntity> searchCollectionData(String collectionNo, String counterNo, Long userId,
			String status, Long orgId);

}
