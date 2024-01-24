package com.abm.mainet.swm.service;

import java.util.List;

import com.abm.mainet.swm.dto.SanitationMasterDTO;

/**
 * The Interface PublicToiletMasterService.
 *
 * @author Lalit.Prusti Created Date : 14-May-2018
 */
/**
 * @author Ajay.Kumar
 *
 */
public interface IPublicToiletMasterService {

    /**
     * Search toilet by toilet type and toilet reg no.
     *
     * @param toiletType the toilet type
     * @param toiletRegNo the toilet reg no
     * @param orgId the org id
     * @return the list
     */
    List<SanitationMasterDTO> searchToiletLocation(Long number, Long type, String name, Long ward,
            Long zone, Long block, Long route, Long subRoute, Long orgId);

    /**
     * Gets the toilet by toilet id.
     *
     * @param toiletId the toilet id
     * @return the toilet by toilet id
     */
    SanitationMasterDTO getPublicToiletByPublicToiletId(Long toiletId);

    /**
     * Save toilet.
     *
     * @param toiletDetails the toilet id details
     * @return the toilet master DTO
     */
    SanitationMasterDTO savePublicToilet(SanitationMasterDTO toiletDetails);

    /**
     * Update toilet.
     *
     * @param toiletDetails the toilet id details
     * @return the toilet master DTO
     */
    SanitationMasterDTO updatePublicToilet(SanitationMasterDTO toiletDetails);

    /**
     * Delete toilet.
     *
     * @param toiletId the toilet id
     */
    void deletePublicToilet(Long toiletId, Long empId, String ipMacAdd);

    /**
     * validate Public Toilet
     * @param sanitationMasterDTO
     * @return
     */
    boolean validatePublicToilet(SanitationMasterDTO sanitationMasterDTO);

    /**
     * searchToilet
     * @param number
     * @param type
     * @param name
     * @param ward
     * @param zone
     * @param block
     * @param route
     * @param subRoute
     * @param orgId
     * @return
     */
    List<SanitationMasterDTO> searchToilet(Long number, Long type, String name, Long ward, Long zone, Long block, Long route,
            Long subRoute, Long orgId);

}
