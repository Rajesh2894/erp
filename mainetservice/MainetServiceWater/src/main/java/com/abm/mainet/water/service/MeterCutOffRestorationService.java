package com.abm.mainet.water.service;

import java.util.List;
import java.util.Map;

import com.abm.mainet.water.domain.TbMeterMasEntity;
import com.abm.mainet.water.domain.TbWaterCutRestoration;
import com.abm.mainet.water.dto.MeterCutOffRestorationDTO;
import com.abm.mainet.water.dto.TbMeterMas;

/**
 * @author Arun.Chavda
 *
 */
public interface MeterCutOffRestorationService {

    TbMeterMasEntity getMeterDetails(Long csId, Long orgId);

    void saveCutOffRestorationDetails(MeterCutOffRestorationDTO meterCutOffRestorationDTO, TbMeterMas meterMasDTO,
            String newMeterFlag,
            String cutOffResFlag, String meterStatus);

    List<MeterCutOffRestorationDTO> getPreviousMeterCutOffDetails(Long meterId, Long consumerId, Long orgId,
            String cutOffResFlag);

    List<MeterCutOffRestorationDTO> getNonMeterPreviousDetails(Long consumerId, Long orgId, String cutOffResFlag);

    Map<Long, TbWaterCutRestoration> getCutOffReadingDataValues(List<Long> csIdn, long orgid);

	TbWaterCutRestoration getCutOffRestore(long csIdn, long orgid);

	List<MeterCutOffRestorationDTO> getPreviousMeterCutOffDetailsOnCsIdn(Long consumerId, Long orgId,
			String cutOffResFlag);
    
}
