/*
 *
 */
package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.swm.dto.SanitationStaffTargetDTO;
import com.abm.mainet.swm.dto.SanitationStaffTargetDetDTO;

/**
 * @author Ajay.Kumar
 *
 */
@WebService
public interface ISanitationStaffTargetService {

    /**
     * delete
     * @param tripId
     * @param empId
     * @param ipMacAdd
     */
    void delete(Long tripId, Long empId, String ipMacAdd);

    /**
     * getById
     * @param tripId
     * @return
     */
    SanitationStaffTargetDTO getById(Long tripId);

    /**
     * save
     * @param sanitationStaffTargetDetails
     * @return
     */
    SanitationStaffTargetDTO save(SanitationStaffTargetDTO sanitationStaffTargetDetails);

    /**
     * update
     * @param sanitationStaffTargetDetails
     * @return
     */
    SanitationStaffTargetDTO update(SanitationStaffTargetDTO sanitationStaffTargetDetails);

    /**
     * search
     * @param sanType
     * @param fromDate
     * @param toDate
     * @param orgId
     * @return
     */
    List<SanitationStaffTargetDTO> search(Long sanType, Date fromDate, Date toDate, Long orgId);

    /**
     * @param sandId
     * @return
     */
    SanitationStaffTargetDetDTO getchildById(Long sandId);

    /**
     * find Vehicle Target Datils
     * @param OrgId
     * @param veId
     * @param fromDate
     * @param toDate
     * @return
     */
    SanitationStaffTargetDTO findVehicleTargetDatils(Long OrgId, Long veId, Date fromDate, Date toDate);

    /**
     * validate Vehicle Target
     * @param vehicleId
     * @param roId
     * @param sandId
     * @param fromDate
     * @param toDate
     * @param orgId
     * @return
     */
    boolean validateVehicleTarget(Long vehicleId, Long roId, Long sandId, Date fromDate, Date toDate, Long orgId);

}
