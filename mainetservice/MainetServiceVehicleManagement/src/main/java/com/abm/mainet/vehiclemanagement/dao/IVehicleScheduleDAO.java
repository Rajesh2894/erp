/*
 * 
 */
package com.abm.mainet.vehiclemanagement.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.vehiclemanagement.domain.VehicleScheduleData;

/**
 * The Interface VehicleScheduleDAO.
 *
 * @author Lalit.Prusti
 * 
 * Created Date : 06-Jun-2018
 */
public interface IVehicleScheduleDAO {

    /**
     * Search vehicle schedule by vehicle type and vehicle no.
     *
     * @param vehicleType the vehicle type
     * @param vehicleNo the vehicle no
     * @param orgId the org id
     * @return the list
     */
    List<VehicleScheduleData> searchVehicleScheduleByVehicleTypeAndVehicleNo(Long vehicleType, Long vehicleNo, Date fromDate,
            Date toDate, Long orgId);

}
