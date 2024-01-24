package com.abm.mainet.account.rest.ui.controller;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.dto.VoucherReversePostDTO;
import com.abm.mainet.account.rest.dto.ReceiptResponseDto;
import com.abm.mainet.account.rest.dto.VoucherResponeDto;
import com.abm.mainet.account.service.AccountVoucherPostService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDetailDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostExternalDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostExternalListDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostListDTO;
import com.abm.mainet.common.master.service.ICommonEncryptionAndDecryption;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * common REST Controller exposed for account voucher posting integration from other modules/department where Account module is
 * available/active
 *
 * @author Vivek.Kumar
 * @since 08 Feb 2017
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/AccountVoucher")
public class AccountVoucherPostingRestController {

    private static final Logger LOGGER = Logger.getLogger(AccountVoucherPostingRestController.class);
    private static final String CORRECT_INPUT = "Correct Input";

    @Resource
    private AccountVoucherPostService accountVoucherPostService;

    /**
     * consume this service by using service {@code URI=/AccountVoucher/doPosting }
     * 
     * @param requestDTO : dto, which {@code dataModel} field can hold {@code VoucherPostDTO} , account related data to post
     * account effect to Account module
     * @return instance of {@code ResponseEntity} ,check status code to ensure whether posting success or failed.
     *
     */
    @RequestMapping(value = "/doPosting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> doVoucherPosting(@RequestBody final VoucherPostListDTO dto, HttpServletRequest request)  {
		
    	StringBuilder exceptionMsg = new StringBuilder();
		String expMsg=null;
        LOGGER.info("REST call to doVoucherPosting");
        final ApplicationSession session = ApplicationSession.getInstance();
        AccountVoucherEntryEntity responseEntity = null;
        try {
            List<VoucherPostDTO> voucherPostDTO = dto.getVoucherdto();
            
            //code modified by rahul.chaubey START
			List<VoucherPostDetailDTO> voucherPostDetailList = new ArrayList<>();
			
			final StringBuilder builder = new StringBuilder();
		
			if (voucherPostDTO.get(0).getVoucherDetails().size() == 1) {
				voucherPostDTO.get(0).getVoucherDetails().parallelStream().forEach(dtos -> {
					if (dtos.getVoucherAmount().compareTo(BigDecimal.ZERO) == 0 || dtos.getVoucherAmount().signum() == -1) 
					{
						builder.append("amount can not be 0 or negative so please check it again");
					}
				});

				if (!builder.toString().isEmpty()) {
					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
							.body(builder.toString());
				}
				else 
				{				}
			}
			//for more than one det list
			else 
			{
				
				voucherPostDTO.get(0).getVoucherDetails().parallelStream().forEach(dtos -> {
					dtos.getVoucherAmount();
					if (dtos.getVoucherAmount().compareTo(BigDecimal.ZERO) == 0  || dtos.getVoucherAmount().signum() == -1) 
					{}
					else
					{
						VoucherPostDetailDTO voucherPostDetailDTO = new VoucherPostDetailDTO();
						BeanUtils.copyProperties(dtos, voucherPostDetailDTO);
						
						voucherPostDetailList.add(voucherPostDetailDTO);
					}
					
				});
				voucherPostDTO.get(0).setVoucherDetails(null);
				voucherPostDTO.get(0).setVoucherDetails(voucherPostDetailList);
			
			}
            //END
            String responseValidation = accountVoucherPostService.validateInput(voucherPostDTO);
            if (responseValidation.isEmpty()) {
                responseEntity = accountVoucherPostService.voucherPosting(voucherPostDTO);
                return Optional.ofNullable(responseEntity)
                        .map(result -> new ResponseEntity<>(session.getMessage("account.voucher.rest.posting.done"),
                                HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
            } else {
                LOGGER.error(session.getMessage("account.voucher.service.posting")
                        + session.getMessage("account.voucher.posting.improper.input")
                        + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseValidation));
                return Optional.ofNullable(responseEntity).map(result -> new ResponseEntity<>(HttpStatus.OK))
                        .orElse(new ResponseEntity<>(session.getMessage("account.voucher.rest.posting.failed"),
                                HttpStatus.NOT_FOUND));
            }
        } catch (Exception ex) {
            LOGGER.error(session.getMessage("account.voucher.service.posting")
                    + session.getMessage("account.voucher.posting.improper.input")
                    + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
        }	
      }


    @RequestMapping(value = "/doReversePosting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> doVoucherReversePosting(@RequestBody final VoucherReversePostDTO voucherPostDTO)
            throws IllegalAccessException, InvocationTargetException {

        LOGGER.info("REST call to doReversePosting");
        final ApplicationSession session = ApplicationSession.getInstance();
        AccountVoucherEntryEntity responseEntity = null;
        try {
            String responseValidation = accountVoucherPostService.validateReversePostInput(voucherPostDTO);
            if (responseValidation.isEmpty()) {
                responseEntity = accountVoucherPostService.voucherReversePosting(voucherPostDTO);
                return Optional.ofNullable(responseEntity)
                        .map(result -> new ResponseEntity<>(session.getMessage("account.voucher.rest.posting.done"),
                                HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
            } else {
                LOGGER.error(session.getMessage("account.voucher.service.posting")
                        + session.getMessage("account.voucher.posting.improper.input")
                        + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseValidation));
                return Optional.ofNullable(responseEntity)
                        .map(result -> new ResponseEntity<>(session.getMessage("account.voucher.rest.posting.failed"),
                                HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
            }
        } catch (Exception ex) {
            LOGGER.error(session.getMessage("account.voucher.service.posting")
                    + session.getMessage("account.voucher.posting.improper.input")
                    + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
        }
    }

    /**
     * consume this service by using service {@code URI=/AccountVoucher/voucherPostValidateInput }
     * 
     * @param List<VoucherPostDTO> : dto, which {@code dataModel} field can hold {@code voucherPostDTO} , account related data to
     * post account effect to Account module
     * @return instance of {@code ResponseEntity} ,check status code to ensure whether posting success or failed.
     *
     */
    
    //
    @RequestMapping(value = "/voucherPostValidateInput", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> voucherPostValidateInput(@RequestBody final VoucherPostListDTO dto) {
        List<VoucherPostDTO> voucherPostDTO = dto.getVoucherdto();
        LOGGER.info("REST call to Provided input for checkingVoucherPostValidations[" + voucherPostDTO + "]");
        final ApplicationSession session = ApplicationSession.getInstance();
        AccountVoucherEntryEntity responseEntity = null;
        try {
            List<String> responseValidation = accountVoucherPostService.checkVoucherPostValidateInput(voucherPostDTO);
            if (responseValidation.size() > 0) {
                LOGGER.error(session.getMessage("account.voucher.rest.posting.validate.failed")
                        + session.getMessage("account.voucher.posting.improper.input")
                        + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseValidation));
                return Optional.ofNullable(responseEntity).map(result -> new ResponseEntity<>(HttpStatus.OK))
                        .orElse(new ResponseEntity<>(session.getMessage("account.voucher.rest.posting.validate.failed" + responseValidation),
                                HttpStatus.NOT_FOUND));
            } else {
                responseEntity = new AccountVoucherEntryEntity();
                return Optional.ofNullable(responseEntity)
                        .map(result -> new ResponseEntity<>(
                                session.getMessage("account.voucher.rest.posting.validate.done"), HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
            }
        } catch (Exception ex) {
            LOGGER.error(session.getMessage("account.voucher.rest.posting.validate.failed")
                    + session.getMessage("account.voucher.posting.improper.input")
                    + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
        }
    }

    /**
     * consume this service by using service {@code URI=/AccountVoucher/doExtSysVoucherPosting}
     * 
     * @param requestDTO : dto, which {@code dataModel} field can hold {@code VoucherPostExternalDTO} , in external system account
     * related data to post account effect to Account module
     * @return instance of {@code ResponseEntity} ,check status code to ensure whether posting success or failed.
     *
     */
   /* @RequestMapping(value = "/doExtSysVoucherPosting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> doExtSysVoucherPosting(@RequestBody final VoucherPostExternalListDTO dto,
            HttpServletRequest request) {
        LOGGER.info("REST call to doExtSysVoucherPosting");
        final ApplicationSession session = ApplicationSession.getInstance();
        AccountVoucherEntryEntity responseEntity = null;
        try {
            List<VoucherPostExternalDTO> voucherExternalPostDTO = dto.getVoucherextsysdto();
            String clientIPAddress = Utility.getClientIpAddress(request);
            List<String> responseExtSystemValidation = accountVoucherPostService
                    .validateExternalSystemDTOInput(voucherExternalPostDTO);
            if (responseExtSystemValidation.size() == 0 || responseExtSystemValidation.isEmpty()) {
                List<VoucherPostDTO> voucherPostDTO = accountVoucherPostService
                        .convertExternalSystemDTOIntoVouPostDTO(voucherExternalPostDTO, clientIPAddress);
                List<String> responseValidation = accountVoucherPostService.checkVoucherPostValidateInput(voucherPostDTO);
                if (responseValidation == null || responseValidation.isEmpty()) {
                    responseEntity = accountVoucherPostService.voucherPosting(voucherPostDTO);
                    return Optional.ofNullable(responseEntity)
                            .map(result -> new ResponseEntity<>(session.getMessage("account.voucher.rest.posting.done"),
                                    HttpStatus.OK))
                            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                } else {
                    LOGGER.error(session.getMessage("account.voucher.rest.posting.validate.failed")
                            + session.getMessage("account.voucher.posting.improper.input")
                            + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseValidation));
                    return Optional.ofNullable(responseEntity).map(result -> new ResponseEntity<>(HttpStatus.OK))
                            .orElse(new ResponseEntity<>(session.getMessage("account.voucher.rest.posting.validate.failed"),
                                    HttpStatus.NOT_FOUND));
                }
            } else {
                LOGGER.error(session.getMessage("account.voucher.service.posting")
                        + session.getMessage("account.voucher.ext.sys.posting.improper.input")
                        + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseExtSystemValidation));
                return Optional.ofNullable(responseEntity).map(result -> new ResponseEntity<>(HttpStatus.OK))
                        .orElse(new ResponseEntity<>(session.getMessage("account.voucher.rest.posting.failed"),
                                HttpStatus.NOT_FOUND));
            }
        } catch (Exception ex) {
            LOGGER.error(session.getMessage("account.voucher.service.posting")
                    + session.getMessage("account.voucher.ext.sys.posting.improper.input")
                    + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
        }
    }*/
    //above method is commented becuse it is rewrite for external service
    @RequestMapping(value = "/doExtSysVoucherPosting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.ALL_VALUE,MediaType.APPLICATION_XML_VALUE,MediaType.TEXT_PLAIN_VALUE })
    public ResponseEntity<?> doExtSysVoucherPosting(@RequestBody final String inputRequest,
            HttpServletRequest request) {
        LOGGER.info("REST call to doExtSysVoucherPosting");
        final ApplicationSession session = ApplicationSession.getInstance();
        AccountVoucherEntryEntity responseEntity = null;
        
        Object commonDecryption = ApplicationContextProvider.getApplicationContext().getBean(ICommonEncryptionAndDecryption.class).commonDecryption(inputRequest);
    	ObjectMapper mapper = new ObjectMapper();
    	VoucherPostExternalListDTO dto = mapper.convertValue(commonDecryption, VoucherPostExternalListDTO.class);
    	List<VoucherPostExternalDTO> voucherExternalPostDTO = dto.getVoucherextsysdto();
    	VoucherPostExternalDTO voucherPostExternalDTO = voucherExternalPostDTO.get(0);
    	
    	StringBuilder inputCheckSum = new StringBuilder();
    	inputCheckSum.append(voucherPostExternalDTO.getCreatedBy());
    	inputCheckSum.append(MainetConstants.operator.ORR);
    	inputCheckSum.append(voucherPostExternalDTO.getUlbCode());
    	String outputCheckSum = ApplicationContextProvider.getApplicationContext().getBean(ICommonEncryptionAndDecryption.class).commonCheckSum(inputCheckSum.toString());
    	
    	if(StringUtils.equals(dto.getCheckSum(), outputCheckSum)) {
    		 try {
    	            String clientIPAddress = Utility.getClientIpAddress(request);
    	            List<String> responseExtSystemValidation = accountVoucherPostService
    	                    .validateExternalSystemDTOInput(voucherExternalPostDTO);
    	            if (responseExtSystemValidation.size() == 0 || responseExtSystemValidation.isEmpty()) {
    	                List<VoucherPostDTO> voucherPostDTO = accountVoucherPostService
    	                        .convertExternalSystemDTOIntoVouPostDTO(voucherExternalPostDTO, clientIPAddress);
    	                List<String> responseValidation = accountVoucherPostService.checkVoucherPostValidateInput(voucherPostDTO);
    	                if (responseValidation == null || responseValidation.isEmpty()) {
    	                    responseEntity = accountVoucherPostService.voucherPosting(voucherPostDTO);
						
						  return ResponseEntity.ok(VoucherResponeDto.getResponse(responseEntity.
						  getVouNo(),voucherPostDTO.get(0).getVoucherReferenceNo(),
						  "voucher posting done for provided details"));
						/*
						 * return Optional.ofNullable(responseEntity) .map(result -> new
						 * ResponseEntity<>(session.getMessage("account.voucher.rest.posting.done"),
						 * HttpStatus.OK)) .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
						 */
    	                } else {
    	                    LOGGER.error(session.getMessage("account.voucher.rest.posting.validate.failed")
    	                            + session.getMessage("account.voucher.posting.improper.input")
    	                            + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseValidation));
    	                    return Optional.ofNullable(responseEntity).map(result -> new ResponseEntity<>(HttpStatus.OK))
    	                            .orElse(new ResponseEntity<>(session.getMessage("account.voucher.rest.posting.validate.failed"+ " "+responseValidation),
    	                                    HttpStatus.NOT_FOUND));
    	                
    	            }
    	            }else {
    	                LOGGER.error(session.getMessage("account.voucher.service.posting")
    	                        + session.getMessage("account.voucher.ext.sys.posting.improper.input")
    	                        + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseExtSystemValidation));
    	                return Optional.ofNullable(responseEntity).map(result -> new ResponseEntity<>(HttpStatus.OK))
    	                        .orElse(new ResponseEntity<>(session.getMessage("account.voucher.rest.posting.failed" +" "+responseExtSystemValidation),
    	                                HttpStatus.NOT_FOUND));
    	            }
    	        } catch (Exception ex) {
    	            LOGGER.error(session.getMessage("account.voucher.service.posting")
    	                    + session.getMessage("account.voucher.ext.sys.posting.improper.input")
    	                    + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex));
    	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
    	        }
    	}else {
    		LOGGER.error(session.getMessage("Checksum mismatch")
                    + session.getMessage("Checksum mismatch")
                    + ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("dd"));
            return Optional.ofNullable(responseEntity).map(result -> new ResponseEntity<>(HttpStatus.OK))
                    .orElse(new ResponseEntity<>(session.getMessage("Checksum mismatch"),
                            HttpStatus.UNAUTHORIZED));
    	}
    	
       
    }
    
}
