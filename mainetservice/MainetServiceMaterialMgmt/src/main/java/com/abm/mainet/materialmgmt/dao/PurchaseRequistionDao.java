package com.abm.mainet.materialmgmt.dao;

import java.util.Date;
import java.util.List;

public interface PurchaseRequistionDao {

	List<Object[]> searchPurchaseRequisitionDataByAll(Long storeid, String prno,
			Date fromDate, Date toDate, Long orgId);
}
