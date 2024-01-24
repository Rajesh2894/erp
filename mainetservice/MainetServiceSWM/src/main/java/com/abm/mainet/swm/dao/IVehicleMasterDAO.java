package com.abm.mainet.swm.dao;

import java.util.List;

import com.abm.mainet.swm.domain.VehicleMaster;

/**
 * The Interface VehicleMasterDAO.
 *
 * @author Lalit.Prusti
 */
public interface IVehicleMasterDAO {

    /**
     * Search vehicle by vehicle type and vehicle reg no.
     *
     * @param vehicleType the vehicle type
     * @param vehicleRegNo the vehicle reg no
     * @param orgId the org id
     * @return the list
     */
    List<VehicleMaster> searchVehicleByVehicleTypeAndVehicleRegNo(Long veId, Long vehicleType, String status, String vehicleRegNo,
            Long orgId);

}
