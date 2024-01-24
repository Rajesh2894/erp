package com.abm.mainet.property.service;

import java.util.Date;
import java.util.List;

import com.abm.mainet.property.dto.PropertyTransferMasterDto;

public interface PropertyTransferService {

    PropertyTransferMasterDto getPropTransferMstByAppId(Long orgId, Long applicationId);

    Long getApplicationIdByPropNo(String propNo, Long orgId);

    void saveAndUpadPropTransferMast(PropertyTransferMasterDto propTranMstDto);

    String getPropertyNoByAppId(Long orgId, Long applicationId, Long serviceId);

    List<Date> getActualTransferDateByPropNo(Long orgId, String propNo);

	List<Long> getAllApplicationIdsByPropNo(String propNo, Long orgId);

	List<Long> getAllApplicationIdsByPropNoNFlat(String propNo, String flatNo, Long orgId);
}
