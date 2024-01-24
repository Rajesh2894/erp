package com.abm.mainet.water.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterExceptionGapDTO;

/**
 * @author Rahul.Yadav
 *
 */
public interface WaterExceptionalGapService {

    /**
     * @param waterDTO
     * @param billingFrequency
     * @param organisation
     * @param finYearId
     * @param meterType
     * @return
     */
    List<WaterExceptionGapDTO> getWaterDataForExceptionGap(TbCsmrInfoDTO waterDTO,
            long billingFrequency, String finYearId, String meterDesc);

    void saveAndUpdateExceptionalData(List<WaterExceptionGapDTO> gapDto, long orgId, Long empId, String macAddress, String reason,
            String addEditFlag);

    List<WaterExceptionGapDTO> editExceptionGapEntry(WaterExceptionGapDTO editGap, long meterType);

    Map<Long, Long> fetchForExceptionGap(List<Long> csIdn, long orgid, String status);

}
