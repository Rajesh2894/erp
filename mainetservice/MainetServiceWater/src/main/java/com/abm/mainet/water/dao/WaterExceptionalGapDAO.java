package com.abm.mainet.water.dao;

import java.util.List;

import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.WaterExceptionGapEntity;
import com.abm.mainet.water.dto.TbCsmrInfoDTO;
import com.abm.mainet.water.dto.WaterExceptionGapDTO;

/**
 * @author Rahul.Yadav
 *
 */
public interface WaterExceptionalGapDAO {

    /**
     * @param waterDTO
     * @param billingFrequency
     * @param meterType
     * @param finyearId
     * @return
     */
    List<TbKCsmrInfoMH> getWaterDataForExceptionGap(TbCsmrInfoDTO waterDTO,
            long billingFrequency, Long finyearId, String meterType);

    void saveAndUpdateExceptionalData(WaterExceptionGapEntity entity, String macAddress, Long empId);

    List<Object[]> fetchEditExceptionGapEntry(WaterExceptionGapDTO editGap, long meterType);

    List<WaterExceptionGapEntity> fetchForExceptionGap(List<Long> csIdn, long orgid, String status);

}
