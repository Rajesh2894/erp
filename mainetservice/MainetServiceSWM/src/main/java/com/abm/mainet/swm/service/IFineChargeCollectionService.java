/*
 *
 */
package com.abm.mainet.swm.service;

import java.util.List;

import javax.jws.WebService;

import com.abm.mainet.swm.dto.FineChargeCollectionDTO;

/**
 * The Interface FineChargeCollectionService.
 *
 * @author Lalit.Prusti
 * 
 * Created Date : 29-June-2018
 */

@WebService
public interface IFineChargeCollectionService {

    /**
     * Delete chargeCollection.
     *
     * @param collectionId the collection id
     */
    void delete(Long chargeId, Long empId, String ipMacAdd);

    /**
     * Gets the chargeCollection by chargeCollection id.
     *
     * @param collectionId the collection id
     * @return the chargeCollection by chargeCollection id
     */
    FineChargeCollectionDTO getById(Long collectionId);

    /**
     * Save chargeCollection.
     *
     * @param chargeCollectionDetails the chargeCollection id details
     * @return the chargeCollection master DTO
     */
    FineChargeCollectionDTO save(FineChargeCollectionDTO chargeCollectionDetails);

    /**
     * Update chargeCollection.
     *
     * @param chargeCollectionDetails the chargeCollection id details
     * @return the chargeCollection master DTO
     */
    FineChargeCollectionDTO update(FineChargeCollectionDTO chargeCollectionDetails);

    /**
     * search
     * @param mobileNo
     * @param registrationId
     * @param orgId
     * @return
     */
    List<FineChargeCollectionDTO> search(String mobileNo, Long registrationId, Long orgId);

}
