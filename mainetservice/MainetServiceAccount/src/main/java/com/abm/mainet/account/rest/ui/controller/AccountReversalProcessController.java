package com.abm.mainet.account.rest.ui.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.account.dto.VoucherReversalDTO;
import com.abm.mainet.account.rest.dto.VoucherReversalExtDTO;
import com.abm.mainet.account.service.AccountVoucherReversalService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.master.service.ICommonEncryptionAndDecryption;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 
 * @author vishwanath.s
 *
 */
@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })

@RestController
@RequestMapping("/TransactionReversal")
public class AccountReversalProcessController {

	private static final Logger LOGGER = Logger.getLogger(AccountReceiptEntryRestController.class);
	
	
	 @Resource
	 private ICommonEncryptionAndDecryption commonEncryptionAndDecryption;
	 
	 @Resource
	 private AccountVoucherReversalService accountVoucherReversalService;
	
	
	@RequestMapping(value = "/reverse", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<?> reversalTransactionProcess(@RequestBody String inputRequest,HttpServletRequest request){
		
		final ApplicationSession session = ApplicationSession.getInstance();
		ObjectMapper mapper = new ObjectMapper();
		try {
		Object decryptExternalReceiptDto = commonEncryptionAndDecryption.commonDecryption(inputRequest);
		VoucherReversalExtDTO voucherReversalExtDTO = mapper.convertValue(decryptExternalReceiptDto, VoucherReversalExtDTO.class);
		StringBuilder createCheckSum = new StringBuilder();
		createCheckSum.append(voucherReversalExtDTO.getApprovedBy());
		createCheckSum.append(MainetConstants.operator.ORR);
		createCheckSum.append(voucherReversalExtDTO.getUlbCode());
    	String internalChecksum =commonEncryptionAndDecryption.commonCheckSum(createCheckSum.toString());	
    	if(StringUtils.equals(internalChecksum, voucherReversalExtDTO.getCheckSum())) {
    		List<String> validateResponse = accountVoucherReversalService.validateExternalReversalRequest(voucherReversalExtDTO);
    		if (CollectionUtils.isEmpty(validateResponse)) {
    			 VoucherReversalDTO internalDto = accountVoucherReversalService.convertExtDtoToInternalDto(voucherReversalExtDTO);
    			 internalDto.setIpMac(Utility.getClientIpAddress(request));
    			 ResponseEntity<?> responseEntity = accountVoucherReversalService.saveReverseTransaction(internalDto);
    			 return responseEntity;
    		} else {
    			LOGGER.error(session.getMessage("Reversal process failed :")
    					+ session.getMessage("improper input parameter for reversal of transaction, these fields {")
    					+ ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validateResponse));
    			return Optional.ofNullable(null).map(result -> new ResponseEntity<>(HttpStatus.OK))
    					.orElse(new ResponseEntity<>(session.getMessage("Reversal process failed due to :" + " "+ validateResponse),
    							HttpStatus.OK));
    		}
    	}else {
    		LOGGER.error(session.getMessage("Reversal process failed :")
					+ session.getMessage("improper input parameter for reversal of transaction, these fields {")
					+ ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid checksum . Please provide proper checksum"));
			return Optional.ofNullable(null).map(result -> new ResponseEntity<>(HttpStatus.OK))
					.orElse(new ResponseEntity<>(session.getMessage("Invalid checksum . Please provide proper checksum"),
							HttpStatus.OK));
    	}		
	}
	catch(Exception ex) {
		LOGGER.error("Error while Reversal of transaction: " + ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Can not proceess the Transaction reversal Please try after some time.....");
		
	}
		
  }
}	
