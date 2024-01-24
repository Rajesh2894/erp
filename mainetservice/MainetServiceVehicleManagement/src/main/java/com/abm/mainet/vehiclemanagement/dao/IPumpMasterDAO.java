package com.abm.mainet.vehiclemanagement.dao;

import java.util.List;

import com.abm.mainet.vehiclemanagement.domain.FuelPumpMaster;

/**
 * The Interface PumpMasterDAO.
 *
 * @author Lalit.Prusti
 */
public interface IPumpMasterDAO {

    /**
     * Serch pump by pump type and pump name.
     *
     * @param pumpType the pump type
     * @param puId the pump id
     * @param orgId the org id
     * @return the list
     */
    List<FuelPumpMaster> searchPumpByPumpType(String status, Long pumpType, Long puId, String pumpName, Long orgId);


}
