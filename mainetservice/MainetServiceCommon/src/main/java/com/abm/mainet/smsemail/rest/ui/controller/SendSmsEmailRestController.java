package com.abm.mainet.smsemail.rest.ui.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.integration.dto.WSRequestDTO;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.smsemail.domain.SmsEmailTransactionDTO;
import com.abm.mainet.smsemail.dto.SMSAndEmailDTO;
import com.abm.mainet.smsemail.service.ISMSAndEmailService;

/**
 * common REST Controller exposed for sending mail/SMS
 *
 * @author Kailash.Agarwal
 * @since 06 March 2017
 */
@ServletSecurity(httpMethodConstraints = {
		@HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/SmsEmail")
public class SendSmsEmailRestController {

	private static final Logger LOGGER = Logger.getLogger(SendSmsEmailRestController.class);

	private static final String FAILED = " mail/sms failed due to ";

	@Resource
	private ISMSAndEmailService sMSAndEmailService;

	@RequestMapping(value = "/sendSMSEmail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<?> sendSMSEmail(@RequestBody final WSRequestDTO requestDTO) {

		ResponseEntity<?> responseEntity = null;
		try {
			responseEntity = sMSAndEmailService.validate(requestDTO);
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				final SMSAndEmailDTO sMSEmail = (SMSAndEmailDTO) CommonMasterUtility.castRequestToDataModel(requestDTO,
						SMSAndEmailDTO.class);
	// Added Changes As per told by Rajesh Sir For Sms and Email
				Organisation organisation = new Organisation();
				organisation.setOrgid(sMSEmail.getOrgId());

				sMSAndEmailService.sendEmailSMS(sMSEmail.getDeptShortCode(), sMSEmail.getServiceUrl(),
						sMSEmail.getTemplateType(), sMSEmail, organisation, sMSEmail.getLangId());
				// sMSAndEmailService.sendEmailSMS(sMSEmail);
				responseEntity = ResponseEntity.status(HttpStatus.OK)
						.body(ApplicationSession.getInstance().getMessage("send.rest.sms.email"));
			}
		} catch (final Exception ex) {
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
			LOGGER.error("Error while sending email/sms: " + ex.getMessage(), ex);
		}

		return responseEntity;
	}

	@RequestMapping(value = "/pendingSMSEmail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<?> getSMSEmail(@RequestBody final WSRequestDTO requestDTO) {

		ResponseEntity<?> responseEntity = null;
		try {
			responseEntity = sMSAndEmailService.validate(requestDTO);
			if (responseEntity.getStatusCode() == HttpStatus.OK) {
				final SMSAndEmailDTO sMSEmail = (SMSAndEmailDTO) CommonMasterUtility.castRequestToDataModel(requestDTO,
						SMSAndEmailDTO.class);
				List<SmsEmailTransactionDTO> list = sMSAndEmailService.getEmailSMS(sMSEmail);
				responseEntity = ResponseEntity.status(HttpStatus.OK).body(list);
			}
		} catch (final Exception ex) {
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FAILED + ex.getMessage());
			LOGGER.error("Error while get email/sms: " + ex.getMessage(), ex);
		}

		return responseEntity;
	}

}
