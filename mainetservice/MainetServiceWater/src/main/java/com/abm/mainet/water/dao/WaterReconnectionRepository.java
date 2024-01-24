package com.abm.mainet.water.dao;

import com.abm.mainet.water.domain.TbWaterReconnection;
import com.abm.mainet.water.dto.WaterReconnectionRequestDTO;
import com.abm.mainet.water.dto.WaterReconnectionResponseDTO;

/**
 * @author Arun.Chavda
 *
 */
public interface WaterReconnectionRepository {

    WaterReconnectionResponseDTO checkIsRegisteredPlumberLicNo(WaterReconnectionRequestDTO requestDTO);

    void saveWaterReconnectionDetails(TbWaterReconnection entity);

    TbWaterReconnection getReconnectionDetails(Long applicationId, Long orgId);

    void updatedWaterReconnectionDetailsByDept(TbWaterReconnection reconnection);

    void updatedBillingStatusOfCSMRInfo(Long consumerIdNo, Long orgId, String billingFlag);

    void updatedMeterStatusOfMeterMaster(Long consumerIdNo, Long orgId, String meterStatus);

    long isAlreadyAppliedForReConn(Long csIdn);
    
    Long getPlumIdByApplicationId(Long applicationId, Long orgId);

}
