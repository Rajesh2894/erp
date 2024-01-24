package com.abm.mainet.materialmgmt.service;


import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Expression;

import com.abm.mainet.materialmgmt.dto.GoodsReceivedNotesDto;
import com.abm.mainet.materialmgmt.dto.PurchaseReturnDto;



public interface IPurchaseReturnService {

	PurchaseReturnDto savePurchaseReturnData(PurchaseReturnDto purchaseReturnDto);

	List<GoodsReceivedNotesDto> getGrnNumbersForReturn(Long orgId);

	PurchaseReturnDto fetchRejectedItemDataByGRNId(Long grnId, Long orgId);

	PurchaseReturnDto fetchRejectedItemDataByReturnId(Long returnId, Long orgId);

	List<PurchaseReturnDto> fetchRejectedItemSummaryData(Long returnId, Long grnId, Date fromDate, Date toDate,
			Long storeId, Long vendorId, Long orgId);
		
}
