package com.abm.mainet.property.service;

import java.util.Map;

import com.abm.mainet.property.dto.BlockChainResponseDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentMstDto;

public interface BlockChainService {
    
    ProvisionalAssesmentMstDto getOwnerDetails(final String propNo, final String oldPropNo, final Long orgId);
    
    BlockChainResponseDto getPropertyDetails(Map<String, Object> blockChainBody);
    

}
