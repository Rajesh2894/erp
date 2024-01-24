package com.abm.mainet.materialmgmt.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.materialmgmt.domain.PurchaseReturnEntity;

public interface IPurchaseReturnDao {

	List<PurchaseReturnEntity> searchPurchaseReturnAllData(Long returnId, Long grnId, Date fromDate, Date toDate,
			Long storeId, Long vendorId, Long orgId);

	List<Object[]> searchPurchaseReturnSummaryData(Long returnId, Long grnId, Date fromDate, Date toDate, Long storeId,
			Long vendorId, Long orgId);
	
}
