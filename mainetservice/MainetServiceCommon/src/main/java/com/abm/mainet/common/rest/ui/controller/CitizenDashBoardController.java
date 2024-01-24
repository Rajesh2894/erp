/**
 *
 */
package com.abm.mainet.common.rest.ui.controller;

import java.util.Collections;
import java.util.List;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.cfc.loi.dto.TbLoiMas;
import com.abm.mainet.cfc.loi.service.TbLoiMasService;
import com.abm.mainet.common.dto.CitizenDashBoardReqDTO;
import com.abm.mainet.common.dto.CitizenDashBoardResDTO;
import com.abm.mainet.common.integration.payment.dto.RePaymentDTO;
import com.abm.mainet.common.service.CitizenDashBoardService;

/**
 * @author Ritesh.patil
 *
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/citizenDashboard")
public class CitizenDashBoardController {

    @Autowired
    private CitizenDashBoardService citizenDashBoardService;
    
    @Autowired
    private TbLoiMasService tbLoiMasService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CitizenDashBoardController.class);

    @RequestMapping(value = "/getDashBoardData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object getApplicationData(@RequestBody final Long[] requestParam, final HttpServletRequest request,
            final BindingResult bindingResult) {

        List<CitizenDashBoardResDTO> applicationList = Collections.emptyList();

        if ((requestParam.length != 0) && (requestParam.length != 1)) {

            try {
                CitizenDashBoardReqDTO req = new CitizenDashBoardReqDTO();
                req.setMobileNo(requestParam[0].toString());
                req.setEmpId(requestParam[0]);
                req.setOrgId(requestParam[1]);
                applicationList = citizenDashBoardService.getAllApplicationsOfCitizen(req);

            } catch (final Exception ex) {
                LOGGER.error("problem occurred while request for getting citizen app data:", ex);
            }

        } else {

            LOGGER.error("UserId is NULL ");
        }

        return applicationList;

    }

    @RequestMapping(value = "/getPayPendingDashBoardData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object getPayPendingData(@RequestBody final Object[] requestParam, final HttpServletRequest request,
            final BindingResult bindingResult) {

        List<CitizenDashBoardResDTO> applicationList = Collections.emptyList();

        if ((requestParam.length != 0) && (requestParam.length != 1)) {

            try {
                applicationList = citizenDashBoardService.getAllFaliuredOrCancelledOnlineList(requestParam[0].toString(),
                        Long.valueOf(requestParam[1].toString()));

            } catch (final Exception ex) {
                LOGGER.error("problem occurred while request for getting citizen app data:", ex);
            }

        } else {

            LOGGER.error("UserId is NULL ");
        }

        return applicationList;

    }

    @RequestMapping(value = "/getPayPendingDataByONLTransId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object getPayPendingDataByRefId(@RequestBody final Object[] requestParam, final HttpServletRequest request,
            final BindingResult bindingResult) {

        RePaymentDTO rePaymentData = null;

        if ((requestParam.length != 0)) {

            try {
                rePaymentData = citizenDashBoardService.getPayPendingDataByONLTransId(Long.valueOf(requestParam[0].toString()));

            } catch (final Exception ex) {
                LOGGER.error("problem occurred while request for getting repayment app data:", ex);
            }

        } else {

            LOGGER.error("ONLTransId is NULL ");
        }

        return rePaymentData;

    }
    

    @RequestMapping(value = "/getDashBoardDocument", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object getDashBoardDocument(@RequestBody final CitizenDashBoardResDTO requestParam, final HttpServletRequest request,
            final BindingResult bindingResult) {

        CitizenDashBoardResDTO applicationList = new CitizenDashBoardResDTO() ;

        if (requestParam != null) {

            try {
            	CitizenDashBoardResDTO req = new CitizenDashBoardResDTO();
            	req.setDocName(requestParam.getDocName());
            	req.setDocPath(requestParam.getDocPath());
                applicationList = citizenDashBoardService.getDocument(req);

            } catch (final Exception ex) {
                LOGGER.error("problem occurred while request for getting citizen app data:", ex);
            }

        } else {

            LOGGER.error("UserId is NULL ");
        }

        return applicationList;

    }
    
    //Defect #117792   
    @RequestMapping(value = "/getLoiInformation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody  
    public Object  getLoiInformation(@RequestBody final Object[] requestParam, final HttpServletRequest request,
            final BindingResult bindingResult) {
    	List<TbLoiMas> applicationList = Collections.emptyList();
    	 if (requestParam.length != 0) {   		  
             try {
                 applicationList = tbLoiMasService.getLoiInformation(Long.valueOf(Long.valueOf(requestParam[0].toString())),
                		 Long.valueOf(requestParam[1].toString()),requestParam[2].toString());

             } catch (final Exception ex) {
                 LOGGER.error("problem occurred while request for getting citizen app data:", ex);
             }

         } 
    	
    else {
        LOGGER.error("UserId is NULL ");
    }
	return applicationList;
    	
    }

}
