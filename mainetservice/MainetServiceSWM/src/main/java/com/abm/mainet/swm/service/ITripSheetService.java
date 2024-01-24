/*
 *
 */
package com.abm.mainet.swm.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.swm.dto.TripSheetDTO;

/**
 * The Interface TripSheetService.
 *
 * @author Lalit.Prusti Created Date : 04-May-2018
 */

@WebService
public interface ITripSheetService {

    /**
     * Delete tripSheet.
     *
     * @param tripId the trip id
     */
    void delete(Long tripId, Long empId, String ipMacAdd);

    /**
     * Gets the tripSheet by tripSheet id.
     *
     * @param tripId the trip id
     * @return the tripSheet by tripSheet id
     */
    TripSheetDTO getById(Long tripId);

    /**
     * Save tripSheet.
     *
     * @param tripSheetDetails the tripSheet id details
     * @return the tripSheet master DTO
     */
    TripSheetDTO save(TripSheetDTO tripSheetDetails);

    /**
     * Update tripSheet.
     *
     * @param tripSheetDetails the tripSheet id details
     * @return the tripSheet master DTO
     */
    TripSheetDTO update(TripSheetDTO tripSheetDetails);

    /**
     * Search.
     *
     * @param veId the veheicle id
     * @param fromDate the from date
     * @param toDate the to date
     * @return the list
     */
    List<TripSheetDTO> search(Long veId, Date fromDate, Date toDate, Long orgId);

    /**
     * findTripSheetReport
     * @param OrgId
     * @param veId
     * @param veRentFromdate
     * @param veRentTodate
     * @param veVetype
		@param contractNo 
     * @param vendorName 
     * @return
     */
    TripSheetDTO findTripSheetReport(Long OrgId, Long veId, Date veRentFromdate,
            Date veRentTodate, Long veVetype, String vendorName, String contractNo);

    /**
     * find Trip Sheet Report Details
     * @param OrgId
     * @param veId
     * @param veRentFromdate
     * @param veRentTodate
     * @param veVetype
     * @param contractNo 
     * @param vendorName 
     * @return
     */
    TripSheetDTO findTripSheetReportDetails(Long OrgId, Long veId, Date veRentFromdate,
            Date veRentTodate, Long veVetype, String vendorName, String contractNo);

    /**
     * @param deId
     * @param orgid
     * @param tripDate
     * @return
     */
    Long getTotalGarbageCollectInDisposalsiteByDate(Long deId, Long orgid, String tripDate);
    
    List<Object[]> getVehicleDetsOfAssociateMRFCenter(Long mrfId, Date date,Long orgId);
    
    List<Object[]> getVehicleCountandBeatCountOfAssMRFCenter(Long mrfId, Date date,Long orgId);
    
    List<Object[]> getwardCount(Long mrfId, Date date,Long orgId);
    
    List<Object[]> getMrfwiseDetails(Long mrfId, Date date,Long orgId);



}
