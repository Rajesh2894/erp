package com.abm.mainet.additionalservices.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.additionalservices.dto.CFCCollectionMasterDto;
import com.abm.mainet.additionalservices.dto.CFCCounterScheduleDto;
import com.abm.mainet.additionalservices.dto.CFCSchedularSummaryDto;
import com.abm.mainet.common.dto.CFCSchedulingCounterDet;

public interface CFCSchedulingTrxService {

	boolean saveCollectionDetail(CFCCollectionMasterDto cfcCollectionMasterDto);
	

	List<String> getCounterNos(String collectionNo);

	CFCCollectionMasterDto getCFCCollectionInfoById(Long collectionId);

	boolean updateCounterScheduleDetail(CFCCounterScheduleDto cfcCounterScheduleDto);

	CFCCounterScheduleDto searchCounterScheduleBuId(Long orgId, Long counterScheduleId);

	CFCSchedulingCounterDet getScheduleDetByEmpiId(Long empId, Long orgId);

	List<CFCSchedularSummaryDto> searchCollectionInfo(String collectionnNo, String counterNo, Long userId,
			String status, Long orgId);


	Boolean getScheduleDetails(String cuCountcentreno, String cmCollncentreno, Long orgid,
			Date fromDate, Date toTime);
}

	
