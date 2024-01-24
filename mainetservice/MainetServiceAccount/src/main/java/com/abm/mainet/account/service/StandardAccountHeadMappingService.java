package com.abm.mainet.account.service;

import java.util.List;

import com.abm.mainet.account.dto.StandardAccountHeadDto;
import com.abm.mainet.common.domain.Organisation;

/**
 * Business Service Interface for entity TbBankmaster.
 */
public interface StandardAccountHeadMappingService {

    /**
     * Creates mapping database
     * @param entity
     * @return
     */
    StandardAccountHeadDto createMapping(StandardAccountHeadDto accountHeadDto);

    /**
     * Creates mapping database
     * @param entity
     * @return
     */
    StandardAccountHeadDto updateMapping(StandardAccountHeadDto accountHeadDto);

    /**
     * Search Account Head mapping database
     * @param AccountTypeId
     * @return
     */
    List<StandardAccountHeadDto> getAccountHead(Long accountType, Long accountSubType, Organisation organisation);

    boolean findAccountSubType(Long primaryHeadId, Long accountType, Long accountSubType, Long lookUpStatusId, Long orgId);

    StandardAccountHeadDto getDetailsUsingprimaryHeadId(Long primaryHeadId, Organisation organisation, int langId);

    boolean findStatusPaymode(Long accountType, Long payModeId, Long lookUpStatusId, Long orgId);
}
