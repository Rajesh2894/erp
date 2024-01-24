package com.abm.mainet.materialmgmt.dao;

import java.util.Date;
import java.util.List;


public interface PurchaseOrderDao {
	
	List<Object[]> purchaseOrderSearchSummaryData(Long storeId, Long vendorId ,Date fromDate, Date toDate, Long orgId);
	
}
