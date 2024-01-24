package com.abm.mainet.mobile.service;

import com.abm.mainet.mobile.dto.CommonAppResponseDTO;
import com.abm.mainet.mobile.dto.DeviceRegistrationReqDTO;

/**
 * @author umashanker.kanaujiya
 *
 */
public interface DeviceRegistration {

    /**
     * @param reqDTO
     * @return CommonAppResponseDTO
     */
    CommonAppResponseDTO doDevRegistrationService(DeviceRegistrationReqDTO reqDTO);

}
