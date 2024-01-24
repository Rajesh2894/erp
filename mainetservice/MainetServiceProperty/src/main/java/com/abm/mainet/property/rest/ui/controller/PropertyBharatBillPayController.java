package com.abm.mainet.property.rest.ui.controller;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abm.mainet.cfc.challan.service.IChallanService;
import com.abm.mainet.cfc.loi.repository.PaymentTransactionMasRepository;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.CommonChallanDTO;
import com.abm.mainet.common.dto.WardZoneBlockDTO;
import com.abm.mainet.common.integration.dto.TbBillMas;
import com.abm.mainet.common.integration.payment.entity.PaymentTransactionMas;
import com.abm.mainet.common.master.service.DepartmentService;
import com.abm.mainet.common.repository.ServiceMasterRepository;
import com.abm.mainet.common.service.IOrganisationService;
import com.abm.mainet.common.utility.ApplicationContextProvider;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.abm.mainet.property.dto.BBPSPaymentResponseDTO;
import com.abm.mainet.property.dto.BBPSRequestDTO;
import com.abm.mainet.property.dto.BBPSResponseDTO;
import com.abm.mainet.property.dto.BillPaymentDetailDto;
import com.abm.mainet.property.dto.ProvisionalAssesmentDetailDto;
import com.abm.mainet.property.repository.AssesmentMstRepository;
import com.abm.mainet.property.service.PropertyBillPaymentService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/PropertyBharatBillPay")
public class PropertyBharatBillPayController {

	private static final Logger LOGGER = Logger.getLogger(PropertyBharatBillPayController.class);

	@Autowired
	private PropertyBillPaymentService propertyBillPaymentService;

	@Autowired
	private AssesmentMstRepository assesmentMstRepository;
	
	@Autowired
    private PaymentTransactionMasRepository transactionMasRepository;
	
	@Autowired
	private ServiceMasterRepository serviceMasterRepository;
	
	@Autowired
	private IOrganisationService organisationService;

	@RequestMapping(value = "/fetchBillPayment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BBPSResponseDTO> fetchBillPayment(
			@RequestBody final BBPSRequestDTO requestDTO, final HttpServletRequest request,
			final HttpServletResponse response, final BindingResult bindingResult) {
		BBPSResponseDTO billPayResponse = new BBPSResponseDTO();
		String billingMethod = MainetConstants.BLANK;
		LOGGER.info("Begin -> " + this.getClass().getSimpleName() + " fetchBillPayment() method");
		LOGGER.info("BBPSRequestDTO " + requestDTO.toString());
		if (StringUtils.isNotBlank(requestDTO.getPropertyNumber())) {
			if (requestDTO.getPropertyNumber().length() < 16 || requestDTO.getPropertyNumber().length() > 16) {
				billPayResponse.setResponseCode(MainetConstants.BBPS_API.INVALID_PROP_CODE);
				billPayResponse.setResponseReason(MainetConstants.BBPS_API.INVALID_PROP_MSG);
				billPayResponse.setStatus(MainetConstants.BBPS_API.FAIL);
				return new ResponseEntity<BBPSResponseDTO>(billPayResponse, HttpStatus.BAD_REQUEST);
			}
			Long orgId = assesmentMstRepository.fetchOrgId(requestDTO.getPropertyNumber());
			Organisation organisation = new Organisation();
			if (orgId != null) {
				organisation = organisationService.getOrganisationById(orgId);
				billPayResponse.setOrgName(organisation.getONlsOrgname());
				billPayResponse.setOrgId(orgId);
			} else {
				billPayResponse.setResponseCode(MainetConstants.BBPS_API.INVALID_PROP_CODE);
				billPayResponse.setResponseReason(MainetConstants.BBPS_API.INVALID_PROP_MSG);
				billPayResponse.setStatus(MainetConstants.BBPS_API.FAIL);
				return new ResponseEntity<BBPSResponseDTO>(billPayResponse, HttpStatus.BAD_REQUEST);
			}
			BillPaymentDetailDto billPaymentDetailDto = propertyBillPaymentService.getBillPaymentDetail(null,
					requestDTO.getPropertyNumber(), orgId, null, null, billingMethod, requestDTO.getFlatNumber());
			if (billPaymentDetailDto != null && StringUtils.isNotBlank(billPaymentDetailDto.getErrorMsg())) {
				billPayResponse.setResponseCode(MainetConstants.BBPS_API.NOT_FOUND_CODE);
				//billPayResponse.setResponseReason(MainetConstants.BBPS_API.NOT_FOUND_MSG);
				billPayResponse.setResponseReason(billPaymentDetailDto.getErrorMsg());
				billPayResponse.setStatus(MainetConstants.BBPS_API.FAIL);
				return new ResponseEntity<BBPSResponseDTO>(billPayResponse, HttpStatus.BAD_REQUEST);
			}
			billPayResponse.setBillAmount(new BigDecimal(billPaymentDetailDto.getTotalPayableAmt()).setScale(2));
			billPayResponse.setConsumerName(billPaymentDetailDto.getOwnerFullName());
			billPayResponse.setConsumerAddress(billPaymentDetailDto.getAddress());
			if(billPaymentDetailDto.getAssmtDto() != null) {
				if(StringUtils.isNotBlank(billPaymentDetailDto.getAssmtDto().getAssNo())) {
					billPayResponse.setPropertyNumber(billPaymentDetailDto.getAssmtDto().getAssNo());
				}
			}

			if (CollectionUtils.isNotEmpty(billPaymentDetailDto.getBillMasList())) {
				List<TbBillMas> billMasList = billPaymentDetailDto.getBillMasList();
				TbBillMas billMas = billMasList.get(billMasList.size() - 1);
				if (billMas != null) {
					billPayResponse.setBillDate(Utility.dateToString(billMas.getBmBilldt()));
					billPayResponse.setBillFromDate(Utility.dateToString(billMas.getBmFromdt()));
					billPayResponse.setBillToDate(Utility.dateToString(billMas.getBmTodt()));
					billPayResponse.setBillDueDate(Utility.dateToString(billMas.getBmDuedate()));
					billPayResponse.setBillNo(Long.valueOf(billMas.getBmNo()));
				}
			}
			// billPayResponse.setPenaltyAmount(billPaymentDetailDto.getTotalPenalty());
			billPayResponse.setResponseCode(MainetConstants.BBPS_API.SUCCESS_CODE_200);
			billPayResponse.setResponseReason(MainetConstants.BBPS_API.SUCCESSFUL);
			billPayResponse.setStatus(MainetConstants.BBPS_API.SUCCESS);
			if (billPaymentDetailDto.getAssmtDto() != null && CollectionUtils
					.isNotEmpty(billPaymentDetailDto.getAssmtDto().getProvisionalAssesmentDetailDtoList())) {
				ProvisionalAssesmentDetailDto detailDto = billPaymentDetailDto.getAssmtDto()
						.getProvisionalAssesmentDetailDtoList().get(0);
				if (StringUtils.equals(MainetConstants.FlagI, billingMethod) && detailDto != null
						&& StringUtils.isNotBlank(detailDto.getOccupierName())) {
					billPayResponse.setConsumerName(detailDto.getOccupierName());
				}
			}
		} else {
			billPayResponse.setStatus(MainetConstants.BBPS_API.FAIL);
			billPayResponse.setResponseCode(MainetConstants.BBPS_API.EMPTY_PROP_CODE);
			billPayResponse.setResponseReason(MainetConstants.BBPS_API.EMPTY_PROP_MSG);
		}
		LOGGER.info("End -> " + this.getClass().getSimpleName() + " fetchBillPayment() method");
		return new ResponseEntity<BBPSResponseDTO>(billPayResponse, HttpStatus.OK);
	}

	@Transactional(rollbackFor = Exception.class)
	@RequestMapping(value = "/saveBillPayment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BBPSPaymentResponseDTO> saveBillPayment(
			@RequestBody final BBPSResponseDTO responseDTO, final HttpServletRequest request,
			final HttpServletResponse response, final BindingResult bindingResult) {
		LOGGER.info("Begin -> " + this.getClass().getSimpleName() + " saveBillPayment() method");
		BBPSPaymentResponseDTO paymentResponse = new BBPSPaymentResponseDTO();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		paymentResponse.setTimestamp(dtf.format(now));
		final CommonChallanDTO offline = new CommonChallanDTO();
		if (StringUtils.isBlank(responseDTO.getPropertyNumber())) {
			paymentResponse.setResponseCode(MainetConstants.BBPS_API.EMPTY_PROP_CODE);
			paymentResponse.setResponseReason(MainetConstants.BBPS_API.EMPTY_PROP_MSG);
			paymentResponse.setStatus(MainetConstants.BBPS_API.FAIL);
			return new ResponseEntity<BBPSPaymentResponseDTO>(paymentResponse, HttpStatus.BAD_REQUEST);
		}
		if (responseDTO.getBillAmount() == null || responseDTO.getBillAmount().doubleValue() < 10) {
			paymentResponse.setResponseCode(MainetConstants.BBPS_API.ERROR_AMOUNT_CODE);
			paymentResponse.setResponseReason(MainetConstants.BBPS_API.ERROR_AMOUNT_MSG);
			paymentResponse.setStatus(MainetConstants.BBPS_API.FAIL);
			return new ResponseEntity<BBPSPaymentResponseDTO>(paymentResponse, HttpStatus.BAD_REQUEST);
		}
		if (responseDTO.getPropertyNumber().length() < 16 || responseDTO.getPropertyNumber().length() > 16) {
			paymentResponse.setResponseCode(MainetConstants.BBPS_API.INVALID_PROP_CODE);
			paymentResponse.setResponseReason(MainetConstants.BBPS_API.INVALID_PROP_MSG);
			paymentResponse.setStatus(MainetConstants.BBPS_API.FAIL);
			return new ResponseEntity<BBPSPaymentResponseDTO>(paymentResponse, HttpStatus.BAD_REQUEST);
		}
		Long orgId = assesmentMstRepository.fetchOrgId(responseDTO.getPropertyNumber());
		Organisation organisation = new Organisation();
		if (orgId != null) {
			organisation.setOrgid(orgId);
		}
		Long deptId = ApplicationContextProvider.getApplicationContext().getBean(DepartmentService.class)
				.getDepartmentIdByDeptCode(MainetConstants.Property.PROP_DEPT_SHORT_CODE);
		Long serviceId = assesmentMstRepository.fetchServiceId(responseDTO.getPropertyNumber());
		offline.setOrgId(orgId);
		offline.setServiceId(serviceId);
		offline.setUserId(1L);
		offline.setLgIpMac(" ");
		offline.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
		offline.setAmountToPay(responseDTO.getBillAmount().toString());
		offline.setUniquePrimaryId(responseDTO.getPropertyNumber());
		offline.setPropNoConnNoEstateNoV(responseDTO.getPropertyNumber());
		offline.setDeptId(deptId);
		offline.setApplicantName(responseDTO.getConsumerName());
		offline.setApplicantAddress(responseDTO.getConsumerAddress());

		Object[] wzId = assesmentMstRepository.fetchWardZoneId(responseDTO.getPropertyNumber());
		if(wzId != null && ArrayUtils.isNotEmpty(wzId)) {
			WardZoneBlockDTO dwzDTO = new WardZoneBlockDTO();
			Object propDet = wzId[0];
			if(propDet != null) {
				Object assWard1 = Array.get(propDet, 0);
				if(assWard1 != null) {
					dwzDTO.setAreaDivision1(Long.parseLong(assWard1.toString()));					
				}
				Object assWard2 = Array.get(propDet, 1);
				if(assWard2 != null) {
					dwzDTO.setAreaDivision2(Long.parseLong(assWard2.toString()));					
				}
				offline.setDwzDTO(dwzDTO);
			}
		}
		final List<LookUp> payLookup = CommonMasterUtility.getListLookup(PrefixConstants.LookUpPrefix.PAY_AT_COUNTER,
				organisation);
		if ((payLookup != null) && !payLookup.isEmpty()) {
			for (final LookUp payPrefix : payLookup) {
				if (PrefixConstants.PaymentMode.WEB.equals(payPrefix.getLookUpCode())) {
					offline.setPayModeIn(payPrefix.getLookUpId());
				}
			}
		}

		try {
			proceesTransactionOnApplication(offline, responseDTO);
			LOGGER.info("updateDataAfterPayment()  is called ");
			ApplicationContextProvider.getApplicationContext().getBean(IChallanService.class)
					.updateDataAfterPayment(offline);
			updateTransactionOnApplication(offline, responseDTO);
			paymentResponse.setResponseCode(MainetConstants.BBPS_API.SUCCESS_CODE_200);
			paymentResponse.setResponseReason(MainetConstants.BBPS_API.SUCCESSFUL);
			paymentResponse.setStatus(MainetConstants.BBPS_API.SUCCESS);
			LOGGER.info("updateDataAfterPayment()  is completed ");
		} catch (Exception exp) {
			LOGGER.error(
					"Exception occurs while calling updateDataAfterPayment of challan Service for online Bharat Bill Payment ",
					exp);
			paymentResponse.setResponseCode(MainetConstants.BBPS_API.ERROR_CODE_500);
			paymentResponse.setResponseReason(MainetConstants.BBPS_API.ERROR_CODE_500_MSG);
			paymentResponse.setStatus(MainetConstants.BBPS_API.FAIL);
			return new ResponseEntity<BBPSPaymentResponseDTO>(paymentResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		LOGGER.info("End -> " + this.getClass().getSimpleName() + " saveBillPayment() method");
		return new ResponseEntity<BBPSPaymentResponseDTO>(paymentResponse, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/getPaymentStatus/txnReferenceId/{txnReferenceId}", method = {RequestMethod.GET, RequestMethod.POST})
	public ResponseEntity<BBPSPaymentResponseDTO> getPaymentStatus(@PathVariable("txnReferenceId") final String txnReferenceId) {
		BBPSPaymentResponseDTO billPayResponse = new BBPSPaymentResponseDTO();
		LOGGER.info("Begin -> " + this.getClass().getSimpleName() + " getPaymentStatus() method");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		billPayResponse.setTimestamp(dtf.format(now));
		if (StringUtils.isNotBlank(txnReferenceId)) {
			String status = transactionMasRepository.fetchPaymentStatus(txnReferenceId);
			if(StringUtils.isNotBlank(status)) {
				billPayResponse.setResponseCode(MainetConstants.BBPS_API.SUCCESS_CODE_200);
				billPayResponse.setResponseReason(status);
				billPayResponse.setStatus(MainetConstants.BBPS_API.SUCCESS);				
			}else {
				billPayResponse.setResponseCode(MainetConstants.BBPS_API.NOT_FOUND_CODE);
				billPayResponse.setResponseReason(MainetConstants.BBPS_API.NOT_FOUND_MSG);
				billPayResponse.setStatus(MainetConstants.BBPS_API.FAIL);
			}
		} else {
			billPayResponse.setStatus(MainetConstants.BBPS_API.FAIL);
			billPayResponse.setResponseCode(MainetConstants.BBPS_API.TX_REF_CODE);
			billPayResponse.setResponseReason(MainetConstants.BBPS_API.TX_REF_MSG);
		}
		LOGGER.info("End -> " + this.getClass().getSimpleName() + " getPaymentStatus() method");
		return new ResponseEntity<BBPSPaymentResponseDTO>(billPayResponse, HttpStatus.OK);
	}

	private void proceesTransactionOnApplication(CommonChallanDTO offline, BBPSResponseDTO responseDTO) {
        final PaymentTransactionMas paymentTransactionMas = new PaymentTransactionMas();
        paymentTransactionMas.setLmodDate(new Date());
        paymentTransactionMas.setOrgId(offline.getOrgId());
        paymentTransactionMas.setUserId(offline.getUserId());
        paymentTransactionMas.setReferenceDate(new Date());
        paymentTransactionMas.settDesc(responseDTO.getTs());//time stamp
        paymentTransactionMas.setSmServiceId(offline.getServiceId());
        paymentTransactionMas.setSendKey(MainetConstants.BankParam.KEY);
        paymentTransactionMas.setSendAmount(new BigDecimal(offline.getAmountToPay()));
        paymentTransactionMas.setSendProductinfo(serviceMasterRepository.getServiceNameByServiceId(offline.getServiceId(), offline.getOrgId()));
        paymentTransactionMas.setSendFirstname(offline.getApplicantName());
        paymentTransactionMas.setSendEmail("");
        paymentTransactionMas.setSendPhone("");
        paymentTransactionMas.setSendSurl("");
        paymentTransactionMas.setSendFurl("");
        paymentTransactionMas.setSendSalt(MainetConstants.BankParam.SALT);
        paymentTransactionMas.setSendHash(MainetConstants.BankParam.HASH);
        paymentTransactionMas.setPgType(MainetConstants.BankParam.PG);
        paymentTransactionMas.setRecvBankRefNum(responseDTO.getTxnReferenceId());        
        paymentTransactionMas.setRecvMode(offline.getPayModeIn().toString());        
        paymentTransactionMas.setReferenceId(offline.getUniquePrimaryId());
        paymentTransactionMas.setSendUrl(MainetConstants.BankParam.SURL);
        paymentTransactionMas.setRecvStatus(MainetConstants.PAYU_STATUS.PENDING);
        paymentTransactionMas.setChallanServiceType(MainetConstants.CHALLAN_RECEIPT_TYPE.REVENUE_BASED);
        paymentTransactionMas.setDocumentUploaded(MainetConstants.FlagN);
        transactionMasRepository.save(paymentTransactionMas);
    }
	
	private void updateTransactionOnApplication(CommonChallanDTO offline, BBPSResponseDTO responseDTO) {
		transactionMasRepository.updateTransactionByBankRefId(responseDTO.getTxnReferenceId(), MainetConstants.PAYU_STATUS.SUCCESS, offline.getOrgId());
		
	}

}
