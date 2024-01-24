package com.abm.mainet.materialmgmt.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.materialmgmt.dto.GoodsReceivedNotesDto;

public interface Igoodsrecievednoteservice {
	
	void saveGoodsReceivedNotes(GoodsReceivedNotesDto goodsReceivedNotesDto);

	GoodsReceivedNotesDto saveGrnInspectionData(GoodsReceivedNotesDto goodsReceivedNotesDto,
			List<Long> removeInBatchIdList, List<Long> removeSerialIdList, List<Long> removeNotInBatchList);

	List<GoodsReceivedNotesDto> searchGoodsReceivedNotes(Long storeId, Long grnid, Date fromDate, Date toDate,
			Long poid, Long orgId, String status);

	GoodsReceivedNotesDto getDataById(Long grnid);

	GoodsReceivedNotesDto getDetailsByGrnNo(Long grnid, Long orgId);

	GoodsReceivedNotesDto goodsReceivedNoteEditAndView(Long grnid, Long orgId);
	
	List<String> checkExsistingNumbers(List<String> removeItemDetIdList, Long storeId, Long orgId);

	List<Object[]> getPurchaseOrderListForGRN(Long orgId);

	GoodsReceivedNotesDto getPurchaseOrderData(GoodsReceivedNotesDto goodsReceivedNotesDto, Long orgId);

	List<Object[]> grnIdNoListByStatus(Long orgId, String status);

}
