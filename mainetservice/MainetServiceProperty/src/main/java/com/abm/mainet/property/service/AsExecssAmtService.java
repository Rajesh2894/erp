package com.abm.mainet.property.service;

import java.util.List;

import com.abm.mainet.property.domain.AsExcessAmtEntity;
import com.abm.mainet.property.dto.BillDisplayDto;

public interface AsExecssAmtService {

    void saveAndUpdateAsExecssAmt(AsExcessAmtEntity asExcessAmtEntity, Long orgId, Long empId);

    List<AsExcessAmtEntity> getExcessAmtEntByPropNo(String propNo, Long orgId);

    double getAdvanceAmount(String propNo, Long orgId);

    void addEntryInExcessAmt(Long orgId, String propNo, double excAmt, Long rmRcptid, Long userId);

    void inactiveAllAdvPayEnrtyByPropNo(String propNo, Long orgId);

    BillDisplayDto getBillDisplayDtoWithAdvanceAmt(String propNo, Long orgId, String flatNo);

    AsExcessAmtEntity getAdvanceEntryByRecptId(Long rmRcptid, String propNo, Long orgId);

    void inactiveAdvPayEnrtyByExcessId(Long excessId, Long orgId);

    void updateExecssAmtByAdjustedAmt(String propNo, Long orgId, double ajustedAmt, Long userId, String ipAddress, String flatNo);
    
    List<AsExcessAmtEntity> getExcessAmtEntByActivePropNo(String propNo, Long orgId);
    
    List<AsExcessAmtEntity> getExcessAmtEntByPropNoAndFlatNo(String propNo, Long orgId,String flatNo);

    double getAdvanceAmountByFlatNo(String propNo, String flatNo, Long orgId);
}
