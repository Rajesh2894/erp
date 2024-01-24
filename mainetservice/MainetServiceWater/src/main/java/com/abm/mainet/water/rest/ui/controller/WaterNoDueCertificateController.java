package com.abm.mainet.water.rest.ui.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.FinancialYear;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.FinYearDTO;
import com.abm.mainet.common.dto.FinYearDTORespDTO;
import com.abm.mainet.common.integration.dto.RequestDTO;
import com.abm.mainet.common.integration.dto.ResponseDTO;
import com.abm.mainet.common.integration.dto.WebServiceResponseDTO;
import com.abm.mainet.common.service.IFinancialYearService;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.UtilityService;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;
import com.abm.mainet.water.dto.NoDueCerticateDTO;
import com.abm.mainet.water.dto.NoDuesCertificateReqDTO;
import com.abm.mainet.water.dto.NoDuesCertificateRespDTO;
import com.abm.mainet.water.service.WaterNoDuesCertificateService;

/**
 * @author umashanker.kanaujiya
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/waterNoDueCertificateController")
public class WaterNoDueCertificateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WaterNoDueCertificateController.class);

    @Resource
    private WaterNoDuesCertificateService certificateService;

    @Resource
    IFinancialYearService financialYearService;
    @Autowired
    private ISMSAndEmailService iSMSAndEmailService;
    @Autowired
    private IOrganisationService organisationService;
    @Value("${error.code.could_not_be_converted}")
    private String typeCastError;

    @Value("${error.cause.could_not_be_converted}")
    private String typeCastErrorCause;

    @Value("${error.code.could_not_process_request}")
    private String processRequestError;

    @Value("${error.cause.could_not_process_request}")
    private String processRequestErrorCause;

    @RequestMapping(value = "/getConnectionData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object getWaterConnData(@RequestBody final NoDuesCertificateReqDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {

        NoDuesCertificateRespDTO responseVO = new NoDuesCertificateRespDTO();
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        List<WebServiceResponseDTO> serviceResponseDTOs = null;
        if (!bindingResult.hasErrors()) {
            serviceResponseDTOs = certificateService.validation(requestDTO, MainetConstants.FORM_DATA);
            if (serviceResponseDTOs.isEmpty()) {
                try {
                    responseVO = certificateService.populateNoDuesCertificateResp(requestDTO);
                    if (responseVO != null) {
                        responseVO.setStatus(MainetConstants.WebServiceStatus.SUCCESS);
                    } else {
                        responseVO = new NoDuesCertificateRespDTO();
                        responseVO.setStatus(MainetConstants.WebServiceStatus.FAIL);
                        responseVO.setErrorMsg(ApplicationSession.getInstance().getMessage("water.invalidConnection"));
                        responseVO.setWebServiceResponseDTOs(serviceResponseDTOs);
                    }
                } catch (final Exception ex) {
                    responseVO.setStatus(MainetConstants.WebServiceStatus.FAIL);
                    responseVO.setErrorCode(processRequestError);
                    responseVO.setCause(processRequestErrorCause + ex.getMessage());

                    LOGGER.error("Error during fetching getWaterConnData", ex);
                }
            } else {
                responseVO.setStatus(MainetConstants.WebServiceStatus.FAIL);
                responseVO.setErrorCode(typeCastError);
                responseVO.setCause(typeCastErrorCause);
                responseVO.setWebServiceResponseDTOs(serviceResponseDTOs);
            }
        } else {
            responseVO.setStatus(MainetConstants.WebServiceStatus.FAIL);
            responseVO.setErrorCode(typeCastError);
            responseVO.setCause(typeCastErrorCause);
            responseVO.setWebServiceResponseDTOs(serviceResponseDTOs);
            LOGGER.error("Error during fetching getWaterConnData:" + bindingResult.getAllErrors());
        }
        return responseVO;

    }

    @RequestMapping(value = "/financialYear", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object financialYear(@RequestBody final RequestDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {

        final FinYearDTORespDTO responseVO = new FinYearDTORespDTO();
        List<FinancialYear> finList = new ArrayList<>();
        try {
            List<FinYearDTO> list = null;
            finList = financialYearService.getAllFinincialYear();
            if (finList != null) {
                list = new ArrayList<>();
                FinYearDTO dto = null;
                for (final FinancialYear fin : finList) {
                    dto = new FinYearDTO();
                    dto.setId(fin.getFaYear());
                    dto.setText(fin.getFaFromDate().toString().substring(0, 4) + "-"
                            + fin.getFaToDate().toString().substring(0, 4));
                    list.add(dto);
                }
            }

            if (finList != null) {
                responseVO.setFinList(list);
                responseVO.setStatus(MainetConstants.WebServiceStatus.SUCCESS);
            } else {
                responseVO.setStatus(MainetConstants.WebServiceStatus.FAIL);
                responseVO.setErrorCode(processRequestError);
            }
        } catch (final Exception ex) {
            responseVO.setStatus(MainetConstants.WebServiceStatus.FAIL);
            responseVO.setErrorCode(processRequestError);
            responseVO.setCause(processRequestErrorCause + ex.getMessage());

            LOGGER.error("Error during fetching getWaterConnData", ex);
        }

        return responseVO;

    }

    @RequestMapping(value = "/saveFormData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object saveFormData(@RequestBody final NoDuesCertificateReqDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {

        NoDuesCertificateRespDTO responseVO = new NoDuesCertificateRespDTO();
        Assert.notNull(requestDTO, "Request DTO Can not be null");
        List<WebServiceResponseDTO> serviceResponseDTOs = null;
        if (!bindingResult.hasErrors()) {
            serviceResponseDTOs = certificateService.validationFromData(requestDTO, MainetConstants.FORM_DATA);
            if (serviceResponseDTOs.isEmpty()) {
                try {
                    responseVO = certificateService.saveForm(requestDTO);
                    if ((responseVO != null) && (responseVO.getApplicationNo() != 0)) {
                        responseVO.setStatus(MainetConstants.WebServiceStatus.SUCCESS);
                        certificateService.initiateWorkflowForFreeService(requestDTO);
                    }
                } catch (final Exception ex) {
                    responseVO.setStatus(MainetConstants.WebServiceStatus.FAIL);
                    responseVO.setErrorCode(processRequestError);
                    responseVO.setCause(processRequestErrorCause + ex.getMessage());

                    LOGGER.error("Error during fetching getWaterConnData", ex);
                }
            } else {
                responseVO.setStatus(MainetConstants.WebServiceStatus.FAIL);
                responseVO.setErrorCode(typeCastError);
                responseVO.setCause(typeCastErrorCause);
                responseVO.setWebServiceResponseDTOs(serviceResponseDTOs);
            }
        } else {

            responseVO.setStatus(MainetConstants.WebServiceStatus.FAIL);
            responseVO.setErrorCode(typeCastError);
            responseVO.setCause(typeCastErrorCause);
            responseVO.setWebServiceResponseDTOs(serviceResponseDTOs);

            LOGGER.error("Error during fetching getWaterConnData:" + bindingResult.getAllErrors());
        }

        return responseVO;

    }

    @RequestMapping(value = "/printNoDueCertificate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object noDueCertificatePrint(@RequestBody final NoDuesCertificateReqDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {
        LOGGER.info("Start the noDueCertificatePrint()");
        NoDueCerticateDTO noDueCertificateDto = new NoDueCerticateDTO();
        try {

            noDueCertificateDto = certificateService.getNoDuesApplicationData(
                    requestDTO.getApplicationId(), requestDTO.getOrgId());

        } catch (final Exception exception) {
            LOGGER.error("Exception found in noDueCertificatePrint  method : ", exception);
        }
        // return new ModelAndView(MainetConstants.DEFAULT_EXCEPTION_FORM_VIEW);
        return noDueCertificateDto;
    }

    // Defect #126420 OTP should come to register mobile number against connection number.
    @RequestMapping(value = "/sendOTPServiceForWater", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object sendOTPServiceForWater(@RequestBody final RequestDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {
        LOGGER.info("Start the sendOTPServiceForWater()");
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            final Organisation organisation = organisationService.getOrganisationById(requestDTO.getOrgId());
            final String otp = UtilityService.generateRandomNumericCode(MainetConstants.OTP_PRASSWORD_LENGTH);
            int count = certificateService.updateOTPServiceForWater(requestDTO.getMobileNo(), requestDTO.getCcnNumber(), otp,
                    new Date());
            if (count > 0) {
                RequestDTO dto = certificateService.doOTPVerificationServiceForWater(requestDTO.getMobileNo(),
                        requestDTO.getCcnNumber());
                if (dto != null) {
                    if (dto.getEmail() != null) {
                        requestDTO.setEmail(dto.getEmail());
                    }
                    if (dto.getfName() != null) {
                        requestDTO.setfName(dto.getfName());
                    }
                }
                responseDTO.setStatus(MainetConstants.WebServiceStatus.SUCCESS);
                sendOTPEmailAndSMS(requestDTO, otp, organisation, requestDTO.getLangId().intValue());
                responseDTO.setErrorMsg("OTP send successfully");
            } else {
                responseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
                responseDTO.setErrorMsg("OTP send fail");
            }
        } catch (final Exception exception) {
            LOGGER.error("Exception found in sendOTPServiceForWater  method : ", exception);
        }
        return responseDTO;
    }

    // Defect #126420 OTP should come to register mobile number against connection number.
    @RequestMapping(value = "/doOTPVerificationServiceForWater", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public Object doOTPVerificationServiceForWater(@RequestBody final RequestDTO requestDTO,
            final HttpServletRequest request, final BindingResult bindingResult) {
        LOGGER.info("Start the doOTPVerificationServiceForWater()");
        RequestDTO dto = new RequestDTO();
        ResponseDTO responseDTO = new ResponseDTO();
        try {

            dto = certificateService.doOTPVerificationServiceForWater(requestDTO.getMobileNo(), requestDTO.getCcnNumber());
            if (dto != null) {
                Date valiDateTime = null;
                if (dto.getApplicationDate() != null) {
                    valiDateTime = dto.getApplicationDate();
                }
                final boolean isValidPeriod = UtilityService.checkOTPValidityPeriod(valiDateTime,
                        MainetConstants.OTP_VALIDITITY_IN_MINS, new Date());
                if (isValidPeriod == true) {
                    if (StringUtils.equals(dto.getOtpPass(), requestDTO.getOtpPass())) {
                        responseDTO.setStatus(MainetConstants.WebServiceStatus.SUCCESS);
                        responseDTO.setErrorMsg("OTP verification done successfully");
                    } else {
                        responseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
                        responseDTO.setErrorMsg("Wrong OTP, Please enter valid OTP");
                    }
                } else {
                    responseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
                    responseDTO.setErrorMsg("OTP has expired");
                }

            } else {
                responseDTO.setStatus(MainetConstants.WebServiceStatus.FAIL);
            }
        } catch (final Exception exception) {
            LOGGER.error("Exception found in doOTPVerificationServiceForWater  method : ", exception);
        }
        return responseDTO;
    }

    // Defect #126420 OTP should come to register mobile number against connection number.
    private void sendOTPEmailAndSMS(RequestDTO requestDTO, final String otp, Organisation organisation, final int langId) {
        final SMSAndEmailDTO dto = new SMSAndEmailDTO();
        dto.setUserId(1l);
        if (requestDTO.getEmail() != null) {
            dto.setEmail(requestDTO.getEmail());
        }
        dto.setMobnumber(requestDTO.getMobileNo());
        dto.setAppName(requestDTO.getfName());
        dto.setOneTimePassword(otp);
        if (langId == MainetConstants.ENGLISH) {
            dto.setV_muncipality_name(organisation.getONlsOrgname());
        } else {
            dto.setV_muncipality_name(organisation.getONlsOrgnameMar());
        }

        iSMSAndEmailService.sendEmailSMS(MainetConstants.WATER_DEPARTMENT_CODE, "NoDuesCertificateController.html",
                PrefixConstants.SMS_EMAIL_ALERT_TYPE.OTP_MSG, dto, organisation, langId);
    }
}
