package com.abm.mainet.swm.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.swm.domain.VehicleFuelling;

/**
 * The Interface VehicleFuellingDAO.
 *
 * @author Lalit.Prusti
 *
 * Created Date : 25-May-2018
 */
public interface IVehicleFuellingDAO {

    /**
     * Search vehicle by vehicle type and vehicle reg no.
     *
     * @param vehicleType the vehicle type
     * @param pumpId the pump id
     * @param fromDate the from date
     * @param toDate the to date
     * @param orgId the org id
     * @return the list
     */
    List<VehicleFuelling> searchVehicleFuelling(Long vehicleType, Long pumpId, Date fromDate, Date toDate,
            Long orgId);

    /**
     * @param pumpId pumpId the pump id
     * @param fromDate the from date
     * @param toDate the to date
     * @param orgId the org id
     * @return
     */
    List<VehicleFuelling> getVehicleFuellingByAdviceDateAndPumpId(Long pumpId, Date fromDate, Date toDate, Long orgId,
            Boolean paid);

}
