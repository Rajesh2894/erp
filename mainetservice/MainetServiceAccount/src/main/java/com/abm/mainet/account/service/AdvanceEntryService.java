package com.abm.mainet.account.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.abm.mainet.account.dto.AdvanceEntryDTO;
import com.abm.mainet.common.domain.Organisation;

public interface AdvanceEntryService {

    AdvanceEntryDTO saveAdvanceEntryFormData(AdvanceEntryDTO advanceEntryDTO, Organisation ordid, int langId)
            throws ParseException;

    List<AdvanceEntryDTO> findByAllGridSearchData(Long advanceNumber, Date advDate, String name, BigDecimal advAmount,
            Long advanceType, String cpdIdStatus, Long orgId,Long deptId);

    AdvanceEntryDTO getDetailsByUsingPrAdvEntryId(AdvanceEntryDTO advanceEntryDTO, Organisation ordid, int langId);

    Long getAdvAccountTypeId(Long billTypeId, Long accountHeadId, Long superOrgId, Long orgId);

    Map<Long, String> getBudgetHeadAllData(Long acountSubType, Organisation organisation, int langId);

    Long getAdvAccountTypeIdByOrgIdAndId(Long id, Long orgId);

}
