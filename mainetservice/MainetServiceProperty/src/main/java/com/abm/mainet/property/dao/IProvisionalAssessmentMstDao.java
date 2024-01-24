package com.abm.mainet.property.dao;

import java.math.BigInteger;
import java.util.List;

import com.abm.mainet.common.dto.GridSearchDTO;
import com.abm.mainet.common.dto.PagingDTO;
import com.abm.mainet.property.dto.PropertyReportRequestDto;
import com.abm.mainet.property.dto.ProperySearchDto;

public interface IProvisionalAssessmentMstDao {

    boolean CheckForAssesmentFieldForCurrYear(long orgId, String assNo, String assOldpropno, String status, Long finYearId,
            Long serviceId);

    List<Object[]> fetchDistrictTehsilVillageId(ProperySearchDto searchDto);

    List<Object[]> searchPropetyForView(ProperySearchDto searchDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId);

    int getTotalSearchCount(ProperySearchDto searchDto, PagingDTO pagingDTO, GridSearchDTO gridSearchDTO, Long serviceId);

    int validateProperty(String propNo, Long orgId, Long serviceId);

    BigInteger[] validateBill(String proertyNo, Long orgId, Long bmIdno);

    List<Object[]> searchPropetyDetailsForApi(ProperySearchDto searchDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId);

    List<String> getPropertiesToGenerateBill(PropertyReportRequestDto propertyDto, Long orgId);

    boolean CheckForAssesmentFiledInCurrentYearOrNot(long orgId, String assNo, String assOldpropno, String status,
            Long finYearId);
    int getTotalSearchCountForAll(ProperySearchDto searchDto, PagingDTO pagingDTO, GridSearchDTO gridSearchDTO,
            Long serviceId);
    
    List<Object[]> searchPropetyForViewForAll(ProperySearchDto searchDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId);

	int validatePropertyWithFlat(String propNo, String flatNo, long orgid, Long serviceId);
	
	List<Object[]> searchPropetyForViewForBillGeneration(ProperySearchDto searchDto, PagingDTO pagingDTO,
            GridSearchDTO gridSearchDTO, Long serviceId,List<String> propNos);

}
