/**
 * 
 */
package com.abm.mainet.common.integration.payment.rest.ui.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.domain.ServiceMaster;
import com.abm.mainet.common.exception.InvalidRequestException;
import com.abm.mainet.common.integration.dto.CommonAppRequestDTO;
import com.abm.mainet.common.integration.dto.PaymentDetailSearchDTO;
import com.abm.mainet.common.integration.dto.PaymentDetailsResponseDTO;
import com.abm.mainet.common.integration.dto.PaymentPostingDto;
import com.abm.mainet.common.integration.dto.ResponseMessageDto;
import com.abm.mainet.common.integration.payment.dto.PaymentRequestDTO;
import com.abm.mainet.common.integration.payment.dto.PaymentTransactionMasDTO;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;
import com.abm.mainet.common.integration.payment.service.PaymentService;
import com.abm.mainet.common.service.ServiceMasterService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.EncryptionAndDecryption;
import com.abm.mainet.common.utility.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author sarojkumar.yadav
 *
 */
@RestController
@RequestMapping("/commonPaymentController")
public class CommonPaymentController {

	private static final Logger logger = Logger.getLogger(CommonPaymentController.class);
	private static ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private PaymentService paymentService;
	@Resource
	private ServiceMasterService serviceMasterService;

	@RequestMapping(value = "/getPgList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResponseEntity<?> getPaymentGatewayList(@RequestBody @Valid final CommonAppRequestDTO requestDTO,
			final HttpServletRequest request, final HttpServletResponse response, final BindingResult bindingResult) {
		logger.info("Inter the getPaymentGatewayList  Method in commonPaymentController");
		logger.info("CommonAppRequestDTO " + requestDTO.toString());
		ResponseEntity<?> responseEntity = null;
		ServiceMaster sm = serviceMasterService.getServiceMaster(requestDTO.getServiceId(),
				requestDTO.getOrgId());
		if (!bindingResult.hasErrors()) {
			Organisation org = new Organisation();
			org.setOrgid(requestDTO.getOrgId());
			Map<Long, String> objectlist = null;
			if (Utility.isEnvPrefixAvailable(org, MainetConstants.ENV_PSCL)) {
				if (null != sm.getTbDepartment().getDpDeptcode() && !sm.getTbDepartment().getDpDeptcode().isEmpty()
						&& (MainetConstants.DEPT_SHORT_NAME.PROPERTY.equals(sm.getTbDepartment().getDpDeptcode())
								|| MainetConstants.DEPT_SHORT_NAME.WATER.equals(sm.getTbDepartment().getDpDeptcode()))) {
					objectlist = paymentService.getAllPgBankByDeptCode(requestDTO.getOrgId(),
							sm.getTbDepartment().getDpDeptcode());
				} else {
					objectlist = paymentService.getAllPgBankByDeptCode(requestDTO.getOrgId(), MainetConstants.ALL);
				}
			} else {

				objectlist = paymentService.getAllPgBank(requestDTO.getOrgId());

			}
			try {
				if (objectlist != null) {
					responseEntity = ResponseEntity.status(HttpStatus.OK).body(objectlist);
				} else {
					responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.body("Error while fetching bank list");
					logger.error("Error while fetching bank list: " + responseEntity.getBody());
				}
			} catch (Exception exp) {
				responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMessage());
				logger.error("Error while fetching bank list: " + responseEntity.getBody());
			}
		} else {
			logger.error("Error during binding  getPaymentGatewayList in :" + bindingResult.getAllErrors());
			throw new InvalidRequestException("Invalid Request", bindingResult);
		}
		return responseEntity;
	}

	@RequestMapping(value = "/savePaymentRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResponseEntity<?> savePaymentRequest(@RequestBody final PaymentRequestDTO paymentRequestDTO,
			final HttpServletRequest request, final HttpServletResponse response, final BindingResult bindingResult) {
		logger.info("Inter the savePaymentRequest  Method in commonPaymentController");
		logger.info("PaymentRequestDTO " + paymentRequestDTO.toString());
		ResponseEntity<?> responseEntity = null;
		if (!bindingResult.hasErrors()) {
			try {
				final ServiceMaster service = serviceMasterService.getServiceMaster(paymentRequestDTO.getServiceId(),
						paymentRequestDTO.getOrgId());
				if (service != null) {
					paymentRequestDTO.setUdf1(service.getSmServiceId().toString());
					paymentRequestDTO.setServiceId(service.getSmServiceId());
					paymentRequestDTO.setServiceName(service.getSmServiceName());
				}
				paymentRequestDTO.setControlUrl(request.getRequestURL().toString());
				paymentService.proceesTransactionOnApplication(request, paymentRequestDTO);
				if(paymentRequestDTO.getDueAmt() != null) {
					paymentRequestDTO.setValidateAmount(paymentRequestDTO.getDueAmt());
				} else {
					paymentRequestDTO.setValidateAmount(BigDecimal.ZERO);
				}
				responseEntity = ResponseEntity.status(HttpStatus.OK).body(paymentRequestDTO);
			} catch (Exception exp) {

				logger.error("Exeception Occur in saveOnlineTransactionMaster ", exp);
				responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMessage());
			}
		} else {
			logger.error("Error during binding in savePaymentDTORequest() of OnlinePaymentRequest in :"
					+ bindingResult.getAllErrors());
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new InvalidRequestException("Invalid Request", bindingResult));
		}
		return responseEntity;
	}

	@RequestMapping(value = "/proceesTransactionBeforePayment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResponseEntity<?> proceesTransactionBeforePayment(@RequestBody final PaymentRequestDTO paymentRequestDTO,
			final HttpServletRequest request, final HttpServletResponse response, final BindingResult bindingResult) {
		logger.info("Inter the proceesTransactionBeforePayment  Method in commonPaymentController");
		logger.info("PaymentRequestDTO " + paymentRequestDTO.toString());
		ResponseEntity<?> responseEntity = null;
		Assert.notNull(paymentRequestDTO, "Connot be null");
		if (!bindingResult.hasErrors()) {
			try {
				final String amount = EncryptionAndDecryption.encrypt(paymentRequestDTO.getDueAmt().toString());
				paymentRequestDTO.setFinalAmount(amount);
				paymentService.proceesTransactionBeforePayment(request, paymentRequestDTO);
				responseEntity = ResponseEntity.status(HttpStatus.OK).body(paymentRequestDTO);
			} catch (Exception exp) {
				logger.error("Exeception Occur in proceesTransactionBeforePayment ", exp);
				responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMessage());
			}
		} else {
			logger.error("Error during binding in proceesTransactionBeforePayment() of OnlinePaymentRequest in :"
					+ bindingResult.getAllErrors());
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new InvalidRequestException("Invalid Request", bindingResult));
		}
		return responseEntity;
	}

	@RequestMapping(value = "/cancelTransactionBeforePayment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResponseEntity<?> cancelTransactionBeforePayment(@RequestBody final PaymentRequestDTO paymentRequestDTO,
			final HttpServletRequest request, final HttpServletResponse response, final BindingResult bindingResult) {
		logger.info("Inter the cancelTransactionBeforePayment  Method in commonPaymentController");
		logger.info("PaymentRequestDTO " + paymentRequestDTO.toString());
		ResponseEntity<?> responseEntity = null;
		Assert.notNull(paymentRequestDTO, "Connot be null");
		if (!bindingResult.hasErrors()) {
			try {
				final String amount = EncryptionAndDecryption.encrypt(paymentRequestDTO.getDueAmt().toString());
				paymentRequestDTO.setFinalAmount(amount);
				paymentService.cancelTransactionBeforePayment(request, paymentRequestDTO);
				responseEntity = ResponseEntity.status(HttpStatus.OK).body(paymentRequestDTO);
			} catch (Exception exp) {
				logger.error("Exeception Occur in cancelTransactionBeforePayment ", exp);
				responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMessage());
			}
		} else {
			logger.error("Error during binding in cancelTransactionBeforePayment() of OnlinePaymentRequest in :"
					+ bindingResult.getAllErrors());
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new InvalidRequestException("Invalid Request", bindingResult));
		}
		return responseEntity;
	}

	@RequestMapping(value = "/genrateResponse/{gatewayFlag}/{sessionAmount}/{orgId}/{langId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResponseEntity<?> genrateResponse(@RequestBody final Map<String, String> responseMap,
			@PathVariable("gatewayFlag") String gatewayFlag, @PathVariable("sessionAmount") String sessionAmount,
			@PathVariable("orgId") Long orgId, @PathVariable("langId") Integer langId, final HttpServletRequest request,
			final HttpServletResponse response, final BindingResult bindingResult) {
		logger.info("Inter the genrateResponse  Method in commonPaymentController");
		logger.info("responseMap " + responseMap.toString());
		ResponseEntity<?> responseEntity = null;
		if (!bindingResult.hasErrors()) {
			try {
				Map<String, String> map = paymentService.genrateResponse(responseMap, gatewayFlag, sessionAmount, orgId,
						langId);
				responseEntity = ResponseEntity.status(HttpStatus.OK).body(map);
			} catch (Exception exp) {
				logger.error("Exeception Occur in genrateResponse ", exp);
				responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMessage());
			}
		} else {
			logger.error("Error during binding in genrateResponse() of OnlinePaymentRequest in :"
					+ bindingResult.getAllErrors());
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new InvalidRequestException("Invalid Request", bindingResult));
		}
		return responseEntity;
	}

	@RequestMapping(value = "/proceesTransactionAfterPayment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResponseEntity<?> proceesTransactionAfterPayment(@RequestBody final Map<String, String> responseMap,
			final HttpServletRequest request, final HttpServletResponse response, final BindingResult bindingResult) {
		logger.info("Inter the proceesTransactionAfterPayment  Method in commonPaymentController");
		logger.info("responseMap " + responseMap.toString());
		ResponseEntity<?> responseEntity = null;
		if (!bindingResult.hasErrors()) {
			try {
				PaymentTransactionMas transactionMas = paymentService.proceesTransactionAfterPayment(responseMap);
				responseEntity = ResponseEntity.status(HttpStatus.OK).body(transactionMas);
			} catch (Exception exp) {
				logger.error("Exeception Occur in proceesTransactionAfterPayment ", exp);
				responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exp.getMessage());
			}
		} else {
			logger.error("Error during binding in proceesTransactionAfterPayment() of OnlinePaymentRequest in :"
					+ bindingResult.getAllErrors());
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new InvalidRequestException("Invalid Request", bindingResult));
		}
		return responseEntity;
	}
	
	//Commented after discuss with Aligarh Team 
	/*@RequestMapping(value = "v1/PaymentPosting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResponseEntity<?> paymentPosting(@RequestBody Map<String, String> requestMap,HttpServletRequest request, HttpServletResponse response) {
		logger.info("Enter the paymentPosting  Method in commonPaymentController");
		Map<String, String> responseMap = new HashMap<>();
		ResponseEntity<?> responseEntity = null;
		ResponseMessageDto messageDto = new ResponseMessageDto();
		PaymentPostingDto payDto= new PaymentPostingDto();
		messageDto.setResponseCode(MainetConstants.FlagF);
		messageDto.setResponseMessage(MainetConstants.PAYMENT_STATUS.FAILURE);
		String flag = MainetConstants.BLANK;
		String decrypted = MainetConstants.BLANK;

		String encryDecryptKey=null;
			
			
			try {
				encryDecryptKey = paymentService.getEncryptDecryptKey(MainetConstants.PAYMENT_POSTING_API.PAYTM, MainetConstants.ASC);
				decrypted = EncryptionAndDecryption.decrypt(requestMap.get(MainetConstants.PAYMENT_POSTING_API.REQUEST_BODY),
						encryDecryptKey);
			} catch (Exception exp) {
				logger.error("Exeception Occur in decrypting request ", exp);

				messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_107);
				messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_107_MSG);
				messageDto.setResponseTransactionNo(-1);
				flag = MainetConstants.FlagN;
			}
			

			if(StringUtils.isNotEmpty(decrypted) && flag.equals(MainetConstants.BLANK) ) {
			try {
			    payDto = mapper.readValue(decrypted, PaymentPostingDto.class);
			} catch (Exception exp) {
				logger.error("Exeception Occur in converting request body to DTO ", exp);
				messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_101);
				messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_101_MSG);
				messageDto.setResponseTransactionNo(-1);
				flag = MainetConstants.FlagN;
			}
			 
			if(flag.equals(MainetConstants.BLANK)) {
			try {
			messageDto = paymentService.checkAllValidation(payDto,messageDto);
			if(StringUtils.isEmpty(messageDto.getErrorCode())) {
			messageDto = paymentService.paymentPostingDataUpdate(request, payDto,null);
			}else {
				logger.info("Exeception Occur in paymentPosting ");
				messageDto.setResponseCode(MainetConstants.FlagF);
				messageDto.setResponseMessage(MainetConstants.PAYMENT_STATUS.FAILURE);
				messageDto.setResponseTransactionNo(-1);
				flag = MainetConstants.FlagN;
			}
		
		} catch (Exception exp) {
			logger.error("Exeception Occur in paymentPosting ", exp);
			messageDto.setResponseCode(MainetConstants.FlagF);
			messageDto.setResponseMessage(MainetConstants.PAYMENT_STATUS.FAILURE);
			messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_108);
			messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_108_MSG);
			messageDto.setResponseTransactionNo(-1);
			flag = MainetConstants.FlagN;
		}
			}
			}
		try {
			String map = mapper.writeValueAsString(messageDto);
			logger.info("Final Response before encryption: "+map);
			
				String encrypt = EncryptionAndDecryption.encrypt(map,encryDecryptKey);
				responseMap.put(MainetConstants.PAYMENT_POSTING_API.RESPONSE_BODY, encrypt);
				if (messageDto.getResponseCode().equals(MainetConstants.FlagS)) {
					responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseMap);
				} else {
					responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
				}
			
			
		} catch (Exception exp) {
			logger.error("Exeception Occur in paymentPosting ", exp);
			messageDto.setResponseCode(MainetConstants.FlagF);
			messageDto.setResponseMessage(MainetConstants.PAYMENT_STATUS.FAILURE);
			messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_103);
			messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_103_MSG);
			messageDto.setResponseTransactionNo(-1);

		}
		return responseEntity;
	} */
	
	
	/*@RequestMapping(value = "v1/PaymentPostingStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResponseEntity<?> paymentPostingStatus(@RequestParam("requestTransactionNo") String requestTransactionNo,
			final HttpServletRequest request, final HttpServletResponse response) {
		logger.info("Enter the paymentPostingStatus  Method in commonPaymentController");
		ResponseEntity<?> responseEntity = null;
		Map<String, String> responseMap=new HashMap<>();
		try {
			responseMap=paymentService.getPaymentPostingStatus(requestTransactionNo);
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseMap);
			
		} catch (Exception exp) {
			logger.error("Exeception Occur in paymentPostingStatus ", exp);
			responseMap.put(MainetConstants.ResponseCode, MainetConstants.FlagF);
			responseMap.put(MainetConstants.ResponseMessage, MainetConstants.COMMON_STATUS.FAILURE);
			responseMap.put(MainetConstants.ResponseDiscription, MainetConstants.COMMON_STATUS.FAILURE);
			responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
		}
		return responseEntity;
	}*/
		
	@RequestMapping(value = "getStatusByReferenceNo", method = {RequestMethod.GET,RequestMethod.POST}, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	PaymentTransactionMasDTO getStatusByReferenceNo(@RequestBody PaymentTransactionMasDTO paymentDto,
			final HttpServletRequest request, final HttpServletResponse response) {
		logger.info("Enter the paymentPostingStatus  Method in commonPaymentController");
		try {
			paymentDto=paymentService.getStatusByReferenceNo(paymentDto.getReferenceId());
		} catch (Exception exp) {
			logger.error("Exception in getStatusByReferenceNo" + exp);
		}
		return paymentDto;
	}
	
	
	@RequestMapping(value = "/callStatusAPI", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseBody
	ResponseEntity<?> callStatusAPI(@RequestBody PaymentRequestDTO paymentRequestDTO, final HttpServletRequest request,
			final BindingResult bindingResult) {
		logger.info("Start callStatusAPI");
		ResponseEntity<?> responseEntity = null;
		Map<String, String> responseMap = new HashMap<>();
		try {
			responseMap = paymentService.processTransactionForCallStatusAPI(request, paymentRequestDTO);

		} catch (final Exception ex) {
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.STATUS,
					MainetConstants.PAYU_STATUS.TECH_ERROR);
			responseMap.put(MainetConstants.REQUIRED_PG_PARAM.RESPONSE_MSG,
					 ApplicationSession.getInstance().getMessage("eip.etp.msg"));
			logger.error("Exception in callStatusAPI" + ex);
		}
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseMap);
		
		return responseEntity;

	}
	
	@RequestMapping(value = "v2/fetchPaymentDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	@ResponseBody
	ResponseEntity<?> fetchPaymentDetails(@RequestBody Map<String, String> requestMap, final HttpServletRequest request,
			final HttpServletResponse response) {
		logger.info("Enter the fetchPaymentDetails  Method in commonPaymentController");
		ResponseEntity<?> responseEntity = null;
		PaymentDetailSearchDTO paySearchDeatilDto = new PaymentDetailSearchDTO();
		PaymentDetailsResponseDTO payResponseDto = new PaymentDetailsResponseDTO();
		try {
			String map = mapper.writeValueAsString(requestMap);
			paySearchDeatilDto = mapper.readValue(map, PaymentDetailSearchDTO.class);

			if ((StringUtils.isNotEmpty(paySearchDeatilDto.getApplNo())
					|| StringUtils.isNotEmpty(paySearchDeatilDto.getLegacyApplNo()))
					&& (StringUtils.isNotEmpty(paySearchDeatilDto.getOrgCode())
							&& StringUtils.isNotEmpty(paySearchDeatilDto.getDeptCode()))) {
				
				Organisation orgn = paymentService.getOgrnByOrgCode(paySearchDeatilDto.getOrgCode());
				if (orgn != null && (paySearchDeatilDto.getDeptCode().equals(MainetConstants.DEPT_SHORT_NAME.PROPERTY)
								|| paySearchDeatilDto.getDeptCode().equals(MainetConstants.DEPT_SHORT_NAME.WATER))) {
					
					responseEntity=paymentService.getDetails(paySearchDeatilDto);
				} else {
					logger.info("Invalid Organisation Code or Department Code");
					payResponseDto.setResponseCode(MainetConstants.FlagF);
					payResponseDto.setResponseMessage(MainetConstants.COMMON_STATUS.FAILURE);
					payResponseDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_109);
					payResponseDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_109_MSG);
					responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(payResponseDto);
				}

			} else {
				logger.info("Mandtory parameters are missing");
				payResponseDto.setResponseCode(MainetConstants.FlagF);
				payResponseDto.setResponseMessage(MainetConstants.COMMON_STATUS.FAILURE);
				payResponseDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_104);
				payResponseDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_104_MSG);
				responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(payResponseDto);
			}

		} catch (Exception e) {
			logger.info("Technical Error while processing request");
			payResponseDto.setResponseCode(MainetConstants.FlagF);
			payResponseDto.setResponseMessage(MainetConstants.COMMON_STATUS.FAILURE);
			payResponseDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_103);
			payResponseDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_103_MSG);
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(payResponseDto);
		}
		/*if (payResponseDto.getResponseCode().equals(MainetConstants.FlagS)) {
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(payResponseDto);
		}*/
		return responseEntity;
	}	
	
	@RequestMapping(value = "v2/PaymentPosting", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResponseEntity<?> paymentPostingNew(@RequestBody Map<String, String> requestMap,HttpServletRequest request, HttpServletResponse response) {
		logger.info("Enter the paymentPosting  Method in commonPaymentController");
		Map<String, String> responseMap = new HashMap<>();
		ResponseEntity<?> responseEntity = null;
		ResponseMessageDto messageDto = new ResponseMessageDto();
		PaymentPostingDto payDto= new PaymentPostingDto();
		messageDto.setResponseCode(MainetConstants.FlagF);
		messageDto.setResponseMessage(MainetConstants.PAYMENT_STATUS.FAILURE);
		String encryDecryptKey=null;
		String flag = MainetConstants.BLANK;
		String decrypted = MainetConstants.BLANK;
		Map<String,Object> authMap = paymentService.checkAuthorization(request);
		boolean auth = (boolean) authMap.get(MainetConstants.PAYMENT_POSTING_API.AUTH_STATUS);
		if(auth == true) {
			try {
				encryDecryptKey = paymentService.getEncryptDecryptKey((String) authMap.get(MainetConstants.PAYMENT_POSTING_API.PG_NAME), 
						(String) authMap.get(MainetConstants.PAYMENT_POSTING_API.ORG_CODE));
				decrypted = EncryptionAndDecryption.decrypt(requestMap.get(MainetConstants.PAYMENT_POSTING_API.REQUEST_BODY),
						encryDecryptKey);
			} catch (Exception exp) {
				logger.error("Exeception Occur in decrypting request ", exp);

				messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_107);
				messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_107_MSG);
				messageDto.setResponseTransactionNo(-1);
				flag = MainetConstants.FlagN;
			}
			

			if(StringUtils.isNotEmpty(decrypted) && flag.equals(MainetConstants.BLANK) ) {
			try {
			    payDto = mapper.readValue(decrypted, PaymentPostingDto.class);
			} catch (Exception exp) {
				logger.error("Exeception Occur in converting request body to DTO ", exp);
				messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_101);
				messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_101_MSG);
				messageDto.setResponseTransactionNo(-1);
				flag = MainetConstants.FlagN;
			}
			 
			if(flag.equals(MainetConstants.BLANK)) {
			try {
			messageDto = paymentService.checkValidationForPaymentPostingV2(payDto,messageDto);
			if(StringUtils.isEmpty(messageDto.getErrorCode())) {
			messageDto = paymentService.paymentPostingDataUpdate(request,payDto,
					  (String) authMap.get(MainetConstants.PAYMENT_POSTING_API.PG_NAME));
			}else {
				logger.info("Exeception Occur in paymentPosting ");
				messageDto.setResponseCode(MainetConstants.FlagF);
				messageDto.setResponseMessage(MainetConstants.PAYMENT_STATUS.FAILURE);
				messageDto.setResponseTransactionNo(-1);
				flag = MainetConstants.FlagN;
			}
		
		} catch (Exception exp) {
			logger.error("Exeception Occur in paymentPosting ", exp);
			messageDto.setResponseCode(MainetConstants.FlagF);
			messageDto.setResponseMessage(MainetConstants.PAYMENT_STATUS.FAILURE);
			messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_108);
			messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_108_MSG);
			messageDto.setResponseTransactionNo(-1);
			flag = MainetConstants.FlagN;
		}
			}
			}
		}else {
			logger.info("Unauthorized userId and password");
			messageDto.setResponseCode(MainetConstants.FlagF);
			messageDto.setResponseMessage(MainetConstants.PAYMENT_STATUS.FAILURE);
			messageDto.setErrorCode((String) authMap.get(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE));
			messageDto.setErrorDesc((String) authMap.get(MainetConstants.PAYMENT_POSTING_API.ERROR_MSG));
			messageDto.setResponseTransactionNo(-1);
			flag = MainetConstants.FlagN;
		}
		try {
			String map = mapper.writeValueAsString(messageDto);
			logger.info("Final Response before encryption: "+map);
				if(StringUtils.isNotEmpty(encryDecryptKey)) {
				String encrypt = EncryptionAndDecryption.encrypt(map,encryDecryptKey);
				responseMap.put(MainetConstants.PAYMENT_POSTING_API.RESPONSE_BODY, encrypt);
				}
				if (messageDto.getResponseCode().equals(MainetConstants.FlagS)) {
					responseEntity = ResponseEntity.status(HttpStatus.OK).body(responseMap);
				} else if(auth==false){
					String encrypt = EncryptionAndDecryption.encrypt(map,MainetConstants.PAYMENT_POSTING_API.DEFAULT_KEY);
					responseMap.put(MainetConstants.PAYMENT_POSTING_API.RESPONSE_BODY, encrypt);
					responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMap);
				}else{
					responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
				}
			
			
		} catch (Exception exp) {
			logger.error("Exeception Occur in paymentPosting ", exp);
			messageDto.setResponseCode(MainetConstants.FlagF);
			messageDto.setResponseMessage(MainetConstants.PAYMENT_STATUS.FAILURE);
			messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_103);
			messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_103_MSG);
			messageDto.setResponseTransactionNo(-1);
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);

		 }
		
		return responseEntity;
	}
	
	@RequestMapping(value = "v2/PaymentPostingStatus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	ResponseEntity<?> paymentPostingStatusV2(@RequestParam("requestTransactionNo") String requestTransactionNo,
			final HttpServletRequest request, final HttpServletResponse response) {
		logger.info("Enter the paymentPostingStatus  Method in commonPaymentController");
		ResponseEntity<?> responseEntity = null;
		Map<String, String> responseMap=new HashMap<>();
		ResponseMessageDto messageDto = new ResponseMessageDto();
		try {
			messageDto = paymentService.getPaymentPostingStatusV2(requestTransactionNo);
			
			responseEntity = ResponseEntity.status(HttpStatus.OK).body(messageDto);
			
		} catch (Exception exp) {
			logger.error("Exeception Occur in paymentPostingStatus ", exp);
			messageDto.setResponseCode(MainetConstants.FlagF);
			messageDto.setResponseMessage(MainetConstants.COMMON_STATUS.FAILURE);
			messageDto.setRequestTransactionNo(requestTransactionNo);
			messageDto.setResponseTransactionNo(-1);
			messageDto.setErrorCode(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_108);
			messageDto.setErrorDesc(MainetConstants.PAYMENT_POSTING_API.ERROR_CODE_108);
			responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseMap);
		}
		return responseEntity;
	}

	
}
