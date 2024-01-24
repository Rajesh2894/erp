/*
 *
 */
package com.abm.mainet.swm.service;

import java.util.Date;

import javax.jws.WebService;

import com.abm.mainet.swm.dto.AreaWiseDto;
import com.abm.mainet.swm.dto.DoorToDoorGarbageCollectionDTO;

/**
 * The Interface DoorToDoorGarbageCollectionService.
 *
 * @author Lalit.Prusti
 * 
 * Created Date : 29-June-2018
 */

@WebService
public interface IDoorToDoorGarbageCollectionService {

    /**
     * Delete collectionSheet.
     *
     * @param collectionId the collection id
     */
    void delete(Long collectionId, Long empId, String ipMacAdd);

    /**
     * Gets the collectionSheet by collectionSheet id.
     *
     * @param collectionId the collection id
     * @return the collectionSheet by collectionSheet id
     */
    DoorToDoorGarbageCollectionDTO getById(Long collectionId);

    /**
     * Save collectionSheet.
     *
     * @param collectionSheetDetails the collectionSheet id details
     * @return the collectionSheet master DTO
     */
    DoorToDoorGarbageCollectionDTO save(DoorToDoorGarbageCollectionDTO collectionSheetDetails);

    /**
     * Update collectionSheet.
     *
     * @param collectionSheetDetails the collectionSheet id details
     * @return the collectionSheet master DTO
     */
    DoorToDoorGarbageCollectionDTO update(DoorToDoorGarbageCollectionDTO collectionSheetDetails);

    /**
     * @param orgId
     * @param ptype
     * @param fromDate
     * @param toDate
     * @return
     */
    AreaWiseDto getAllAreaWiseSurveyData(long orgId, String ptype, Date fromDate, Date toDate);

}
