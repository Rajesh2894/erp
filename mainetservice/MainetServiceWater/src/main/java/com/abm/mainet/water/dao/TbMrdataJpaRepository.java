package com.abm.mainet.water.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.abm.mainet.water.domain.TbMrdataEntity;

/**
 * Repository : TbMrdata.
 */
public interface TbMrdataJpaRepository {

    /**
     * @param tbMrdataEntity
     */
    void saveTbMrDataEntity(TbMrdataEntity tbMrdataEntity);

    /**
     * @param mrdId
     * @param orgid
     * @return
     */
    TbMrdataEntity findOne(Long mrdId, long orgid);

    /**
     * @param csIdn
     * @param orgid
     * @return
     */
    List<TbMrdataEntity> getMrDataByCsidnAndOrgId(Long csIdn, Long orgid);

    /**
     * @param csIdn
     * @param orgid
     * @return
     */
    List<TbMrdataEntity> getMrDataByCsidnAndOrgId(List<Long> csIdn, long orgid);

    /**
     * @param connectionids
     * @param orgnisation
     */
    void updateBillGeneratedFlagInMeter(Set<Long> connectionids, long orgnisation);

    String validateBillPresentOrNot(Long mmMtnid, long orgid, Date endDate);

    List<TbMrdataEntity> meterReadingDataByCsidnAndOrgId(List<Long> csIdn, long orgid);
    
    List<TbMrdataEntity> meterReadingDataForBillGen(List<Long> csIdn, Long orgid);

}
