package com.abm.mainet.bill.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.cfc.challan.domain.AdjustmentMasterEntity;
import com.abm.mainet.cfc.challan.dto.AdjustmentMasterDTO;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.CustomerInfoDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.property.dto.PropertyDetailDto;
import com.abm.mainet.common.master.dto.TbTaxMas;

public interface AdjustmentEntryService {

    List<TbBillMas> fetchModuleWiseBills(Long deptId, String ccnNoOrPropNo, Long orgId);

    List<TbTaxMas> fetchAllModulewiseTaxes(Long deptId, Organisation orgId);

    boolean saveAdjustmentEntryData(List<TbBillMas> billlist, AdjustmentMasterDTO adjustmentDto);

    List<AdjustmentMasterDTO> fetchHistory(String refNo, Long dpDeptId);

    void updateAdjustedAdjustmentData(List<AdjustmentMasterEntity> adjustmentEntity);

    Map<Long, List<AdjustmentMasterEntity>> fetchAllAdjustmentEntry(List<String> uniqueId, long orgid, Long deptId);
    
    String getPropNoByOldPropNo(final String oldPropNo, final Long orgId,Long deptId);

	Long getBillMethodIdByPropNo(String oldPropNo, Long orgId, String deptCode);

	List<String> getFlatNoIdByPropNo(String oldPropNo, Long orgId, String deptCode);

	List<TbBillMas> fetchAllBillByPropNoAndFlatNo(Long deptId, String ccnNoOrPropNo, String flatNo, Long orgId);

	public CustomerInfoDTO getCsmrInfoDetails(String ccnNoOrPropNo, Long orgId);
	
	boolean saveAdjustmentEntryDataForLastBill(List<TbBillMas> billlist, AdjustmentMasterDTO adjustmentDto);

	PropertyDetailDto getPropertyDetails(String propertyNo, Long orgId);
}
