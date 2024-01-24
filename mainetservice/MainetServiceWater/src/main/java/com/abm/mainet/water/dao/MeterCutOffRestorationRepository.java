package com.abm.mainet.water.dao;

import java.util.List;

import com.abm.mainet.water.domain.TbMeterMasEntity;
import com.abm.mainet.water.domain.TbWaterCutRestoration;
import com.abm.mainet.water.dto.MeterCutOffRestorationDTO;

/**
 * @author Arun.Chavda
 *
 */
public interface MeterCutOffRestorationRepository {

    TbMeterMasEntity getMeterDetails(Long consumerId, Long orgId);

    void saveCutOffRestorationDetails(TbWaterCutRestoration tbWaterCutRestoration);

    List<MeterCutOffRestorationDTO> getPreviousMeterCutOffDetails(Long meterId, Long consumerId, Long orgId,
            String cutOffResFlag);

    List<MeterCutOffRestorationDTO> getNonMeterPreviousDetails(Long consumerId, Long orgId, String cutOffResFlag);

    /**
     * @param csIdn
     * @param mmMtnid
     * @param orgid
     * @param flag
     * @return
     */
    Object[] getCutOffReadingAndDate(Long csIdn, Long mmMtnid, long orgid, String flag);

    /**
     * @param csIdn
     * @param orgId
     * @return
     */
    List<TbWaterCutRestoration> getCutOffReadingDataValues(List<Long> csIdn, long orgId);

	List<MeterCutOffRestorationDTO> getPreviousMeterCutOffDetailsOnCsIdn(Long consumerId, Long orgId,
			String cutOffResFlag);

}
