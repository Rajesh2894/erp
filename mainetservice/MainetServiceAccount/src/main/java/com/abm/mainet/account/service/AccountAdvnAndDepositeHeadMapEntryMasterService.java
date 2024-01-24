package com.abm.mainet.account.service;

import java.util.List;

import com.abm.mainet.account.dto.AccountDepositeAndAdvnHeadsMappingEntryMasterBean;
import com.abm.mainet.account.dto.DeasMasterEntryDto;

/**
 * Business Service Interface for entity TbAcFunctionMaster.
 */
public interface AccountAdvnAndDepositeHeadMapEntryMasterService {

    /**
     * @param depositeAndAdvnMapBean
     * @param det
     * @param orgId
     * @param langId
     * @param empId
     * @param mappingType
     */
    void save(AccountDepositeAndAdvnHeadsMappingEntryMasterBean depositeAndAdvnMapBean, Long orgId, int langId, Long empId,
            Long mappingType, String ipMacAddress);

    /**
     * @param orgid
     * @param mappingType
     * @param advncType
     * @param deptId
     * @return
     */
    List<DeasMasterEntryDto> searchRecordUsingRequestParam(long orgid, Long mappingType, Long advncType, Long deptId);

}
