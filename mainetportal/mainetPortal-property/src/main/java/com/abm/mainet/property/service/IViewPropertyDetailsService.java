package com.abm.mainet.property.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.common.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.DocumentDetailsVO;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.dto.TbBillMas;
import com.abm.mainet.common.util.LookUp;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ViewPropertyDetailRequestDto;

public interface IViewPropertyDetailsService {

   // List<ProperySearchDto> searchPropertyDetails(ViewPropertyDetailRequestDto viewRequestDto);
    
    ProvisionalAssesmentMstDto fetchPropertyByPropNo(ProperySearchDto searchDto);
    
    List<TbBillMas> getViewData(ProperySearchDto properySearchDto);
    
    Map<Long, List<DocumentDetailsVO>> fetchApplicaionDocuments(ProperySearchDto searchDto);
    
    List<LookUp> getCollectionDetails(ProperySearchDto searchDto);

	//int getTotalSearchCount(ProperySearchDto searchDto, PagingDTO createPagingDTO, GridSearchDTO createGridSearchDTO);

	//int getTotalSearchCount(ViewPropertyDetailRequestDto viewRequestDto);

	List<ProperySearchDto> searchPropertyDetails(ProperySearchDto searchDto);

	int getTotalSearchCount(ProperySearchDto searchDto, PagingDTO createPagingDTO, GridSearchDTO createGridSearchDTO);

	ProperySearchDto getAndGenearteJasperForBill(ProperySearchDto propDto);

	ProperySearchDto getAndGenearteJasperForReceipt(ProperySearchDto propDto);

	ChallanReceiptPrintDTO getRevenueReceiptDetails(Long recptId, Long receiptNo, String assNo);
}
