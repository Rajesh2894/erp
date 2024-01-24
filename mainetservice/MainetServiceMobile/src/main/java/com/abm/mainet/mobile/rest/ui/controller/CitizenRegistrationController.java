package com.abm.mainet.mobile.rest.ui.controller;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.dto.EmployeeAttendanceDTO;
import com.abm.mainet.common.dto.EmployeeDTO;
import com.abm.mainet.common.dto.OrganisationDTO;
import com.abm.mainet.common.exception.InvalidRequestException;
import com.abm.mainet.mobile.dto.LoginRequestVO;
import com.abm.mainet.mobile.dto.LoginResponseVO;
import com.abm.mainet.mobile.service.EmployeeAttendanceEntryService;
import com.abm.mainet.mobile.service.RegistrationService;
import com.abm.mainet.security.JwtUtil;

/**
 * @author umashanker.kanaujiya
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/registrationController")
public class CitizenRegistrationController {

    private static final String REQUESTDTO_CANT_NULL = "RequestDTO can not be null";
    private static final Logger LOG = Logger.getLogger(CitizenRegistrationController.class);

    @Resource
    private RegistrationService registrationService;
    @Resource
    private EmployeeAttendanceEntryService employeeAttndService;

    @RequestMapping(value = "/AuthenticationProcess", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public LoginResponseVO authenticationProcess(@RequestBody @Valid final LoginRequestVO loginRequestVO,
            final HttpServletRequest request, final HttpServletResponse response, final BindingResult bindingResult) {

        LOG.info("Start inside the authenticationProcess() in the Controller class:::");
        LOG.info("inside the authenticationProcess in controller ");
        Assert.notNull(loginRequestVO, REQUESTDTO_CANT_NULL);
        LoginResponseVO responseDTO = new LoginResponseVO();

        if (!bindingResult.hasErrors()) {
            responseDTO = registrationService.getAuthenticationProcess(loginRequestVO);
            
            setJWTTokenOnAuthenticationSuccess(response, responseDTO);

        } else {
            LOG.error("Error during binding  authenticationProcess in :" + bindingResult.getAllErrors());
            throw new InvalidRequestException("Invalid Request", bindingResult);
        }

        return responseDTO;
    }

    private void setJWTTokenOnAuthenticationSuccess(final HttpServletResponse response, LoginResponseVO responseDTO) {
	try {
	    EmployeeDTO edto = new EmployeeDTO();
	    OrganisationDTO org = new OrganisationDTO();
	    org.setOrgid(responseDTO.getOrgId());
	    edto.setOrganisation(org);
	    edto.setEmpId(responseDTO.getUserId());
	    JwtUtil jwt = new JwtUtil();
	    String jwtToken = jwt.createJWT(edto);
	    LOG.info("JWT_TOKEN{UserId-" + responseDTO.getUserId() +"]>>"+ jwtToken);
	    response.addHeader(JwtUtil.getJwtToken(), jwtToken);
	} catch (Exception e) {
	    LOG.warn("Failed To Create Token CitizenRegistrationController->setJWTTokenOnAuthenticationSuccess", e);
	}
    }
    @RequestMapping(value = "/saveEmployeeAttendance", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public EmployeeAttendanceDTO saveEmployeeAttendance(@RequestBody @Valid final EmployeeAttendanceDTO dto,
            final HttpServletRequest request, final HttpServletResponse response, final BindingResult bindingResult) {
    	employeeAttndService.saveEmployeeAttendanceData(dto);
    	return dto;
    }
    
    @RequestMapping(value = "/fetchEmployeeAttendance", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public EmployeeAttendanceDTO fetchEmployeeAttendance(@RequestBody @Valid final EmployeeAttendanceDTO dto) {
    	EmployeeAttendanceDTO dtos=employeeAttndService.fetchEmployeeAttendance(dto);
    	return dtos;
    }
}
