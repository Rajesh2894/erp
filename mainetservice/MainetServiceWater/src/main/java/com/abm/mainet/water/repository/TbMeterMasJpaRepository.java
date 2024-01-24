package com.abm.mainet.water.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.abm.mainet.water.constant.QueryConstants;
import com.abm.mainet.water.domain.TbMeterMasEntity;

/**
 * Repository : TbMeterMas.
 */
public interface TbMeterMasJpaRepository extends PagingAndSortingRepository<TbMeterMasEntity, Long> {

    /**
     * @param meterMasId
     * @param orgid
     * @return
     */
    @Query(QueryConstants.WATER_MODULE_QUERY.METER_READING.METER_READING_BY_METERID)
    TbMeterMasEntity getMeterMasterbyMeterMasId(@Param("meterMasId") Long meterMasId, @Param("orgid") long orgid);

    /**
     * @param conIds
     * @param orgid
     * @return
     */
    @Query(QueryConstants.WATER_MODULE_QUERY.METER_READING.METER_READING_BY_CONNECTIONID)
    List<TbMeterMasEntity> getMeterMasterbyMeterMasId(@Param("conIds") List<Long> conIds, @Param("orgid") long orgid);

}
