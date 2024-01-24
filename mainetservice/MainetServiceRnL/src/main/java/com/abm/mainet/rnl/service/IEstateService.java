package com.abm.mainet.rnl.service;

import java.util.List;

import com.abm.mainet.common.integration.dms.domain.AttachDocs;
import com.abm.mainet.rnl.dto.EstateMaster;
import com.abm.mainet.rnl.dto.EstateMasterGrid;

/**
 * @author ritesh.patil
 *
 */

public interface IEstateService {

    List<EstateMasterGrid> findGridRecords(Long orgId);

    List<EstateMaster> findEstateByLocId(Long orgId, Long locId, int langId);

    List<EstateMasterGrid> findGridFilterRecords(Long orgId, Long locId, Long estateId);

    String save(EstateMaster estateMaster, List<AttachDocs> attachDocs);

    boolean deleteRecord(Long id, Long empId);

    EstateMaster findById(Long esId);

    boolean saveEdit(EstateMaster estateMaster, List<AttachDocs> attachDocs, List<Long> ids);

    List<Object[]> findEstateRecordsForProperty(Long orgId);

    EstateMaster getAssetDetailsByAssetCodeAndOrgId(String assetCode, Long orgId);

    List<String> fetchAssetCodesByLookupIds(List<Long> lookupIds, Long orgId,String modeType);
    
    Boolean checkEstateRegNoExist(Long esId,String regNo,Long orgId);

	List<Object[]> fetchAssetCodesAndAssetNameByAssetClassIds(List<Long> lookupIds, Long orgId, String modeType);

	List<EstateMasterGrid> findGridRecordsByPurpose(Long orgId, Long purpose);
	
	List<EstateMasterGrid> searchRecordsByParameters(Long orgId, Long locationId, Long estateId, Long purpose,Integer type1,Integer type2, Long acqType);
}
