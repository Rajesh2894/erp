package com.abm.mainet.materialmgmt.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.materialmgmt.dto.PurchaseOrderDto;

public interface IPurchaseOrderService {
	
	PurchaseOrderDto savePurchaseOrder(PurchaseOrderDto purchaseOrderDto, List<Long> removeOverheadIdList, List<Long> removeTncIdList, List<Long> removeEncIdList);

	List<PurchaseOrderDto> purchaseSearchForSummaryData(Long orgId);

	List<PurchaseOrderDto> searchPurchaseOrderData(Long storeNameId, Long vendorId, Date fromDate, Date toDate, Long orgId);

	PurchaseOrderDto purchaseOrderEditAndView(Long poId, Long orgId);

	void purchaseOrderFordelete(Long poId, Long orgId);

	PurchaseOrderDto getPurchaseRequisitionByPrID(PurchaseOrderDto purchaseOrderDto, Long orgId);

	List<Object[]> getPurcahseOrderIdAndNumbers(Long orgId);
	
}
