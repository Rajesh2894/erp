package com.abm.mainet.swm.service;

import javax.jws.WebService;

import com.abm.mainet.swm.dto.SolidWasteConsumerMasterDTO;

/**
 * The Interface UserRegistrationService.
 * 
 * @author Lalit.Prusti
 *
 * Created Date : 19-Jun-2018
 */
@WebService
public interface ISolidWasteConsumerService {

    /**
     * Delete.
     *
     * @param registrationId the registration id
     * @return true, if successful
     */
    boolean delete(Long registrationId, Long empId, String ipMacAdd);

    /**
     * Gets the by id.
     *
     * @param registrationId the registration id
     * @return the by id
     */
    SolidWasteConsumerMasterDTO getById(Long registrationId);

    /**
     * Save.
     *
     * @param userRegistrationDetails the user registration details
     * @return the user registration DTO
     */
    SolidWasteConsumerMasterDTO save(SolidWasteConsumerMasterDTO userRegistrationDetails);

    /**
     * Update.
     *
     * @param userRegistrationDetails the user registration details
     * @return the user registration DTO
     */
    SolidWasteConsumerMasterDTO update(SolidWasteConsumerMasterDTO userRegistrationDetails);

    /**
     * Search.
     *
     * @param registrationId the registration id
     * @param custNumber the cust number
     * @param propetyNo the propety no
     * @param mobileNo the mobile no
     * @param orgId the orgn id
     * @return the user registration DTO
     */
    SolidWasteConsumerMasterDTO search(Long registrationId, String custNumber, String propetyNo, Long mobileNo, Long orgId);

    /**
     * @param userRegistrationDetails
     * @return
     */
    boolean isValidUser(SolidWasteConsumerMasterDTO userRegistrationDetails);

}
