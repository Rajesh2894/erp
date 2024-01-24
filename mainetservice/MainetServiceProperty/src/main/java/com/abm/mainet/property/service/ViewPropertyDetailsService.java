package com.abm.mainet.property.service;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import com.abm.mainet.cfc.challan.dto.ChallanReceiptPrintDTO;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.common.integration.dto.DocumentDetailsVO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.integration.property.dto.PropertyInputDto;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.property.dto.ProperySearchDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;
import com.abm.mainet.property.dto.ViewPropertyDetailRequestDto;

@WebService
public interface ViewPropertyDetailsService {

    List<TbBillMas> getViewData(ProperySearchDto properySearchDto);

    List<LookUp> getCollectionDetails(ProperySearchDto searchDto);

    ProvisionalAssesmentMstDto fetchPropertyByPropNo(ProperySearchDto searchDto);

    Map<Long, List<DocumentDetailsVO>> fetchApplicaionDocuments(ProperySearchDto searchDto);

    List<DocumentDetailsVO> fetchAppDocuments(ProperySearchDto searchDto);

    List<ProperySearchDto> searchPropertyDetailsByRequest(ViewPropertyDetailRequestDto viewPropDet);

    int getTotalSearchCountByRequest(ViewPropertyDetailRequestDto viewPropDet);

    ProperySearchDto getAndGenearteJasperForBill(ProperySearchDto propDto);

    ProperySearchDto getAndGenearteJasperForReceipt(ProperySearchDto propDto);

    List<ProperySearchDto> searchPropertyDetails(ProperySearchDto searchDto, PagingDTO pagingDTO, GridSearchDTO gridSearchDTO,
            Long serviceId, Long locationId);

    PropertyDetailDto getPropertyDetails(PropertyInputDto dto);

    int getTotalSearchCount(ProperySearchDto properySearchDto, PagingDTO pagingDTO, GridSearchDTO gridSearchDTO, Long serviceId);

    ProvisionalAssesmentMstDto viewAssessmentMasWithDesc(ProperySearchDto searchDto) throws Exception;

    List<ProperySearchDto> searchPropertyDetailsByApiRequest(ViewPropertyDetailRequestDto viewPropDet);

    ChallanReceiptPrintDTO getRevenueReceiptDetails(ProperySearchDto requestDto);

    boolean checkPropertyExitOrNot(ProperySearchDto dto);

    List<DocumentDetailsVO> fetchDESUploadedDocs(Long orgId, String propNo);

    CommonChallanDTO getPropDetails(CommonChallanDTO dto);

    List<ProperySearchDto> searchPropertyDetailsForAll(ProperySearchDto searchDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId, Long locationId);

    int getTotalSearchCountForAll(ProperySearchDto properySearchDto, PagingDTO pagingDTO, GridSearchDTO gridSearchDTO,
            Long serviceId);

    public CommonChallanDTO getPropDetailsByAppNo(CommonChallanDTO dto);

    PropertyDetailDto getPropertyDetailsByPropNoNFlatNo(PropertyInputDto dto);

    Boolean checkWhetherPropertyIsActive(ProvisionalAssesmentMstDto dto);

    List<ProperySearchDto> searchPropertyDetailsForMobile(ProperySearchDto searchDto);
    
    List<ChallanReceiptPrintDTO> getBillHistory(ProperySearchDto searchDto);
    
    List<ChallanReceiptPrintDTO> getPaymentHistory(ProperySearchDto searchDto);
}
