package com.abm.mainet.mobile.service;

import com.abm.mainet.common.domain.Employee;
import com.abm.mainet.integration.ws.dto.PropertyDetailDto;
import com.abm.mainet.mobile.dto.CommonAppResponseDTO;
import com.abm.mainet.mobile.dto.EmployeeOTPReqDTO;
import com.abm.mainet.mobile.dto.EmployeeRequestDTO;
import com.abm.mainet.mobile.dto.EmployeeResponseDTO;
import com.abm.mainet.mobile.dto.LoginRequestVO;
import com.abm.mainet.mobile.dto.LoginResponseVO;

/**
 * @author umashanker.kanaujiya
 *
 */
public interface RegistrationService {

    /**
     * This function are used for Citizen Registration
     * @param employeeDTO
     * @return EmployeeResponseDTO
     */
    CommonAppResponseDTO doRegistrationService(EmployeeRequestDTO employeeDTO);

    /**
     * This function are used for OTP Verification
     * @param otpReqDTO
     * @return EmployeeResponseDTO
     */
    CommonAppResponseDTO doOTPVerificationService(EmployeeOTPReqDTO otpReqDTO);

    /**
     * This function are used for OTP Verification
     * @param otpReqDTO
     * @return EmployeeResponseDTO
     */
    CommonAppResponseDTO setPasswordService(EmployeeOTPReqDTO otpReqDTO);

    /**
     * This function are used for Re Send OTP
     * @param otpReqDTO
     * @return EmployeeResponseDTO
     */
    EmployeeResponseDTO reSendOTPService(EmployeeOTPReqDTO otpReqDTO);

    /**
     * This function are used for Authentication
     * @param otpReqDTO
     * @return EmployeeResponseDTO
     */
    LoginResponseVO getAuthenticationProcess(LoginRequestVO loginRequestVO);

    EmployeeResponseDTO reSendOTPServiceForMobile(final EmployeeOTPReqDTO otpReqDTO);

    String validateInput(LoginResponseVO loginResponseVO);

    Employee saveEditedUserProfileForMobile(LoginResponseVO loginResponseVO);

    PropertyDetailDto updateOTPServiceForAssessment(String mobileNo);

    CommonAppResponseDTO doOTPVerificationForAssessment(EmployeeOTPReqDTO otpReqDTO);

    EmployeeResponseDTO sendOTPServiceForAssessment(final EmployeeOTPReqDTO otpReqDTO);

    PropertyDetailDto checkForValidOTPNo(String mobileNo);
}
