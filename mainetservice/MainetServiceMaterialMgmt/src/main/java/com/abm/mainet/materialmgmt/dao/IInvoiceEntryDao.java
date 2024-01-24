package com.abm.mainet.materialmgmt.dao;

import java.util.Date;
import java.util.List;

public interface IInvoiceEntryDao {

	List<Object[]> searchInvoiceEntrySummaryData(Long invoiceId, Long poId, Date fromDate, Date toDate, Long storeId,
			Long vendorId, Long orgId);
	
}
