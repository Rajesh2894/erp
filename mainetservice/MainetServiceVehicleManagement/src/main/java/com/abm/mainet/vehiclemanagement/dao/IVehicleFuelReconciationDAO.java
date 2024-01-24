/*
 * 
 */
package com.abm.mainet.vehiclemanagement.dao;

import java.util.Date;
import java.util.List;

import com.abm.mainet.vehiclemanagement.domain.VmVehicleFuelReconciation;

/**
 * The Interface VehicleFuelReconciationDAO.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 18-Jun-2018
 */

public interface IVehicleFuelReconciationDAO {

    /**
     * Search vehicle fuel reconciation.
     *
     * @param puId the pump master id
     * @param fromDate the from date
     * @param toDate the to date
     * @param orgId the org id
     * @return the list
     */
    List<VmVehicleFuelReconciation> searchVehicleFuelReconciation(Long puId, Date fromDate, Date toDate, Long orgId);

}
