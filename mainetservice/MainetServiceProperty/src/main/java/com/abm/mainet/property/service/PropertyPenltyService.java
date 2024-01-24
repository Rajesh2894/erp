package com.abm.mainet.property.service;

import java.util.List;

import com.abm.mainet.property.dto.PropertyPenltyDto;

public interface PropertyPenltyService {

    PropertyPenltyDto calculateExistingSurcharge(String propNo, Long finYearId, Long orgId);

    void savePropertyPenlty(String propNo, Long finYearId, Long orgId, Long userId, String ipAddress,
            double actualAmt, double pendingAmt, String parentPropNo);

    void inActiveSurchargeByPropNoAndFinYear(String activeFlag, Long finYearId, Long orgId, String propNo);
    
    void updatePropertyPenalty(PropertyPenltyDto penaltyDto, String ipAddress, Long userId);
    
    PropertyPenltyDto getLastClaculatedSurcharge(String propNo, Long orgId);
    
    List<PropertyPenltyDto> calculateExistingSurchargeByParentPropNo(String parentPropNo, Long finYearId, Long orgId);
    
    Double getPropertyPenltyByGroupPropNos(List<String> propNoList, Long finYearId, Long orgId);


}
