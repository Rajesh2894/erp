package com.abm.mainet.materialmgmt.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.materialmgmt.domain.ExpiryItemsEntity;

public interface ExpiredItemDao {
	List<ExpiryItemsEntity> searchExpiredDataByAll(Long storeid, String movementNo,
			String fromDate, String toDate, Long orgId);
	
	List<ExpiryItemsEntity> searchExpiredSummaryData(Long storeid, String movementNo,
			Date fromDate, Date toDate, Long orgId);
	
}
