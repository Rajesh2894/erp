package com.abm.mainet.mobile.service;

import com.abm.mainet.mobile.dto.LoginRequestVO;
import com.abm.mainet.mobile.dto.LoginResponseVO;

/**
 * @author umashanker.kanaujiya
 *
 */
public interface RegistrationService {

    /**
     * @param loginRequestVO
     * @return
     */
    LoginResponseVO getAuthenticationProcess(LoginRequestVO loginRequestVO);

}
