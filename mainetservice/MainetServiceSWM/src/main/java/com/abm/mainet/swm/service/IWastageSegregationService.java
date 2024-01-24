/*
 * 
 */
package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.swm.dto.WastageSegregationDTO;

/**
 * The Interface WastageSegregationService.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 13-Jun-2018
 */
@WebService
public interface IWastageSegregationService {

    /**
     * Delete.
     *
     * @param tripId the trip id
     */
    void delete(Long wastageSegregationId, Long empId, String ipMacAdd);

    /**
     * Gets the by id.
     *
     * @param tripId the trip id
     * @return the by id
     */
    WastageSegregationDTO getById(Long wastageSegregationId);

    /**
     * Save.
     *
     * @param wastageSegregationDetails the wastage segregation details
     * @return the wastage segregation DTO
     */
    WastageSegregationDTO save(WastageSegregationDTO wastageSegregationDetails);

    /**
     * Update.
     *
     * @param wastageSegregationDetails the wastage segregation details
     * @return the wastage segregation DTO
     */
    WastageSegregationDTO update(WastageSegregationDTO wastageSegregationDetails);

    /**
     * Search.
     *
     * @param deId the ve id
     * @param fromDate the from date
     * @param toDate the to date
     * @param orgId the org id
     * @return the list
     */
    List<WastageSegregationDTO> search(Long deId, Date fromDate, Date toDate, Long orgId);

    /**
     * find Wastage Segregation
     * @param OrgId
     * @param deId
     * @param codWast1
     * @param codWast2
     * @param codWast3
     * @param fromDate
     * @param toDate
     * @return
     */
    WastageSegregationDTO findWastageSegregation(Long OrgId, Long deId, Long codWast1, Long codWast2, Long codWast3,
            Date fromDate, Date toDate);

    WastageSegregationDTO findSlrmWiseSegregation(Long OrgId, Long mrfId, Long monthNo);
}
