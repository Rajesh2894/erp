package com.abm.mainet.workManagement.repository;

import java.util.Date;
import java.util.List;

import com.abm.mainet.workManagement.domain.ScheduleOfRateMstEntity;

/**
 * @author hiren.poriya
 * @Since 05-Dec-2017
 */
public interface ScheduleOfRateRepositoryCustom {

    /**
     * search SOR Active Records by Sor name or sor from date or sor to date with org id.
     * @param orgId
     * @param sorName
     * @param sorFromDate
     * @param sorToDate
     * @return List<ScheduleOfRateMstDto> for search criteria
     */
    List<ScheduleOfRateMstEntity> searchSorRecords(Long orgId, Long sorNameId, Date sorFromDate,
            Date sorToDate);

}
