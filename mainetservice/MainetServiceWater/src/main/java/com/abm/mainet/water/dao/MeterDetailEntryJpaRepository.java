package com.abm.mainet.water.dao;

import java.math.BigDecimal;
import java.util.List;

import com.abm.mainet.common.domain.TbWorkOrderEntity;
import com.abm.mainet.water.domain.TbKCsmrInfoMH;
import com.abm.mainet.water.domain.TbMeterMasEntity;

/**
 * Repository : TbCsmrInfo.
 */
public interface MeterDetailEntryJpaRepository {

    /**
     * @return
     */
    List<TbWorkOrderEntity> queryDataFromWorkOrderTab(BigDecimal orgId);

    /**
     * @param entity
     * @return
     */
    boolean updateFormData(TbMeterMasEntity entity);

    /**
     * @param applicationNumber
     * @param orgId
     * @param meter
     * @return
     */
    TbKCsmrInfoMH getConnectionIdFromCsmrInfoUsingAppId(Long applicationNumber, Long orgId, Long meter);

    /**
     * @param csIdn
     * @return
     */
    boolean getMeterMasEntity(Long csIdn, Long orgId);

    /**
     * 
     * @param csIdn
     * @param orgId
     * @return List<TbMeterMasEntity>
     */
	List<TbMeterMasEntity> getMeterMasEntities(Long csIdn, Long orgId);

	public boolean saveOrUpdateData(TbMeterMasEntity queryEntity);

}