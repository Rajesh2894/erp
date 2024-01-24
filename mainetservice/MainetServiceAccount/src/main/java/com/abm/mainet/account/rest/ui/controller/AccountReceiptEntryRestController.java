package com.abm.mainet.account.rest.ui.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

import com.abm.mainet.account.rest.dto.ReceiptResponseDto;
import com.abm.mainet.account.service.AccountBudgetProjectedRevenueEntryService;
import com.abm.mainet.account.service.AccountReceiptEntryService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.constant.MainetConstants.AccountConstants;
import com.abm.mainet.common.constant.PrefixConstants;
import com.abm.mainet.common.constant.PrefixConstants.AccountPrefix;
import com.abm.mainet.common.domain.BankMasterEntity;
import com.abm.mainet.common.domain.Organisation;
import com.abm.mainet.common.dto.TbAcVendormaster;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountReceiptDTO;
import com.abm.mainet.common.integration.acccount.dto.AccountReceiptExternalDto;
import com.abm.mainet.common.integration.acccount.dto.VendorDTO;
import com.abm.mainet.common.master.service.BankMasterService;
import com.abm.mainet.common.master.service.ICommonEncryptionAndDecryption;
import com.abm.mainet.common.master.service.TbAcVendormasterService;
import com.abm.mainet.common.service.ILocationMasService;
import com.abm.mainet.common.service.TbBankmasterService;
import com.abm.mainet.common.utility.ApplicationSession;
import com.abm.mainet.common.utility.CommonMasterUtility;
import com.abm.mainet.common.utility.LookUp;
import com.abm.mainet.common.utility.Utility;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * common REST Controller exposed for account Receipt Entry integration from other modules/department where Account module is
 * available/active
 *
 * @author Prasad.kancharla
 * @since 01-Mar-2017
 */
@ServletSecurity(httpMethodConstraints = {
        @HttpMethodConstraint(value = "POST", transportGuarantee = TransportGuarantee.CONFIDENTIAL) })
@RestController
@RequestMapping("/AccountReceiptEntry")
public class AccountReceiptEntryRestController {

    private static final Logger LOGGER = Logger.getLogger(AccountReceiptEntryRestController.class);

    @Resource
    private AccountReceiptEntryService accountReceiptEntryService;

    @Resource
    private TbAcVendormasterService tbAcVendormasterService;

    @Resource
    private TbBankmasterService banksMasterService;

    @Resource
    private AccountBudgetProjectedRevenueEntryService accountBudgetProjectedRevenueEntryService;

    @Resource
    private ILocationMasService locMasService;

    @Resource
    private BankMasterService bankMasterService;
    
    @Resource
    private ICommonEncryptionAndDecryption commonEncryptionAndDecryption;

    /**
     * consume this service by using service {@code URI=/AccountReceiptEntry/getPayeeNames }
     * 
     * @param AccountRecieptDTO : dto, which {@code dataModel} field can hold {@code accountReceiptDTO} , account related data to
     * post account effect to Account module
     * @return instance of {@code ResponseEntity} ,check status code to ensure whether posting success or failed.
     *
     */
    @RequestMapping(value = "/doGetPayeeNames", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getPayeeNames(@RequestBody final AccountReceiptDTO accountReceiptDTO) {

        LOGGER.info("Provided input for ReceiptRecordsOnSearch[" + accountReceiptDTO + "]");
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = validateOrgId(accountReceiptDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                final List<String> payeeNames = accountReceiptEntryService.getPayeeNames(accountReceiptDTO.getOrgId());
                if (payeeNames.isEmpty()) {
                    responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(session.getMessage("account.receipt.entry.rest.input"));
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.OK).body(payeeNames);
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.depositslip.response.servererror"));
            LOGGER.error("Error while Receipt Entry: " + ex.getMessage(), ex);
        }

        return responseEntity;
    }

    /**
     * consume this service by using service {@code URI=/AccountReceiptEntry/getReceiptRecordsOnSearch }
     * 
     * @param AccountRecieptDTO : dto, which {@code dataModel} field can hold {@code accountReceiptDTO} , account related data to
     * post account effect to Account module
     * @return instance of {@code ResponseEntity} ,check status code to ensure whether posting success or failed.
     *
     */
    @RequestMapping(value = "/getReceiptRecordsOnSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getReceiptRecordsOnSearch(@RequestBody final AccountReceiptDTO accountReceiptDTO) {
        final ApplicationSession session = ApplicationSession.getInstance();
        LOGGER.info("Provided input for ReceiptRecordsOnSearch[" + accountReceiptDTO + "]");
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = validateOrgId(accountReceiptDTO);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                final String validationError = accountReceiptEntryService.validateReceiptInput(
                        accountReceiptDTO.getReceiptAmount(), accountReceiptDTO.getReceiptNumber(),
                        accountReceiptDTO.getReceiptPayeeName(), accountReceiptDTO.getReceiptDate());
                if (validationError.isEmpty()) {
                    final List<AccountReceiptDTO> listOfRecieptsRestList = accountReceiptEntryService
                            .findAllReceiptRestData(accountReceiptDTO.getOrgId(), accountReceiptDTO.getReceiptAmount(),
                                    accountReceiptDTO.getReceiptNumber(), accountReceiptDTO.getReceiptPayeeName(),
                                    accountReceiptDTO.getReceiptDate());
                    if (listOfRecieptsRestList.isEmpty()) {
                        responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(session.getMessage("account.receipt.entry.rest.record"));
                    } else {
                        responseEntity = ResponseEntity.status(HttpStatus.OK).body(listOfRecieptsRestList);
                    }
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(session.getMessage("account.receipt.entry.rest.criteria"));
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.depositslip.response.servererror"));
            LOGGER.error("Error while Receipt Entry: " + ex.getMessage(), ex);
        }

        return responseEntity;
    }

    /**
     * consume this service by using service {@code URI=/AccountReceiptEntry/getReceiptDataForView }
     * 
     * @param AccountRecieptDTO : dto, which {@code dataModel} field can hold {@code accountReceiptDTO} , account related data to
     * post account effect to Account module
     * @return instance of {@code ResponseEntity} ,check status code to ensure whether posting success or failed.
     *
     */
    @RequestMapping(value = "/getReceiptDataForView", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getReceiptDataForView(@RequestBody AccountReceiptDTO accountReceiptDTO) {

        LOGGER.info("Provided input for ReceiptRecordsOnSearch[" + accountReceiptDTO + "]");
        ResponseEntity<?> responseEntity = null;
        try {
            responseEntity = validateReceiptId(accountReceiptDTO, accountReceiptDTO.getReceiptId());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                accountReceiptDTO = accountReceiptEntryService.findByRestId(accountReceiptDTO.getReceiptId(),
                        accountReceiptDTO.getOrgId());
                responseEntity = ResponseEntity.status(HttpStatus.OK).body(accountReceiptDTO);
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApplicationSession.getInstance().getMessage("account.depositslip.response.servererror"));
            LOGGER.error("Error while Receipt Entry: " + ex.getMessage(), ex);
        }

        return responseEntity;
    }

    /**
     * consume this service by using service {@code URI=/AccountReceiptEntry/getVendorList }
     * 
     * @param AccountRecieptDTO : dto, which {@code dataModel} field can hold {@code accountReceiptDTO} , account related data to
     * post account effect to Account module
     * @return instance of {@code ResponseEntity} ,check status code to ensure whether posting success or failed.
     *
     */
    @RequestMapping(value = "/getVendorList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getVendorList(@RequestBody final AccountReceiptDTO accountReceiptDTO) {
        final ApplicationSession session = ApplicationSession.getInstance();
        LOGGER.info("Provided input for ReceiptRecordsOnSearch[" + accountReceiptDTO + "]");
        ResponseEntity<?> responseEntity = null;
        try {
            final Organisation org = new Organisation();
            org.setOrgid(accountReceiptDTO.getOrgId());
            final LookUp lookUpVendorStatus = CommonMasterUtility.getLookUpFromPrefixLookUpValue(
                    AccountConstants.AC.getValue(), PrefixConstants.VSS, accountReceiptDTO.getLangId(), org);
            final Long vendorStatus = lookUpVendorStatus.getLookUpId();
            if (vendorStatus != null) {
                accountReceiptDTO.setVendorStatus(vendorStatus);
            }
            responseEntity = validateVendorStatusId(accountReceiptDTO, accountReceiptDTO.getVendorStatus());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                final List<TbAcVendormaster> list = tbAcVendormasterService
                        .getActiveVendors(accountReceiptDTO.getOrgId(), accountReceiptDTO.getVendorStatus());
                if (list.isEmpty()) {
                    responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(session.getMessage("account.receipt.entry.rest.record"));
                } else {
                    List<VendorDTO> vendorList = new ArrayList<>();
                    vendorList = tbAcVendormasterService.addVendorListData(list);
                    responseEntity = ResponseEntity.status(HttpStatus.OK).body(vendorList);
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.depositslip.response.servererror"));
            LOGGER.error("Error while Receipt Entry: " + ex.getMessage(), ex);
        }

        return responseEntity;
    }

    /**
     * consume this service by using service {@code URI=/AccountReceiptEntry/getBankList }
     * 
     * @param AccountRecieptDTO : dto, which {@code dataModel} field can hold {@code accountReceiptDTO} , account related data to
     * post account effect to Account module
     * @return instance of {@code ResponseEntity} ,check status code to ensure whether posting success or failed.
     *
     */
    @RequestMapping(value = "/getBankList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> getBankList(@RequestBody AccountReceiptDTO accountReceiptDTO) {
        final ApplicationSession session = ApplicationSession.getInstance();
        LOGGER.info("Provided input for ReceiptRecordsOnSearch[" + accountReceiptDTO + "]");
        ResponseEntity<?> responseEntity = null;
        try {
            final Long statusId = CommonMasterUtility.lookUpIdByLookUpCodeAndPrefix(
                    PrefixConstants.PaymentMode.USER_ADUSTMENT, PrefixConstants.BAS, accountReceiptDTO.getOrgId());
            if (statusId != null) {
                accountReceiptDTO.setStatusId(statusId);
            }
            responseEntity = validateStatusId(accountReceiptDTO, accountReceiptDTO.getStatusId());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                final List<Object[]> bankAccountList = banksMasterService
                        .getActiveBankAccountList(accountReceiptDTO.getOrgId(), accountReceiptDTO.getStatusId());
                if (bankAccountList.isEmpty()) {
                    responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(session.getMessage("account.receipt.entry.rest.input"));
                } else {
                    accountReceiptDTO = addBankAccountList(bankAccountList);
                    responseEntity = ResponseEntity.status(HttpStatus.OK).body(accountReceiptDTO);
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.depositslip.response.servererror"));
            LOGGER.error("Error while Receipt Entry: " + ex.getMessage(), ex);
        }

        return responseEntity;
    }

    /**
     * consume this service by using service {@code URI=/AccountReceiptEntry/getReceiptHeadList }
     * 
     * @param AccountRecieptDTO : dto, which {@code dataModel} field can hold {@code accountReceiptDTO} , account related data to
     * post account effect to Account module
     * @return instance of {@code ResponseEntity} ,check status code to ensure whether posting success or failed.
     *
     */
    @RequestMapping(value = "/getReceiptHeadList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> budgetHeadList(@RequestBody AccountReceiptDTO accountReceiptDTO) {
        final ApplicationSession session = ApplicationSession.getInstance();
        LOGGER.info("Provided input for ReceiptRecordsOnSearch[" + accountReceiptDTO + "]");
        ResponseEntity<?> responseEntity = null;
        try {
            final Long finYearId = accountReceiptEntryService.getFinanciaYearIds(new Date());
            if (finYearId != null) {
                accountReceiptDTO.setFinYearId(finYearId);
            }
            responseEntity = validateFinYearId(accountReceiptDTO, accountReceiptDTO.getFinYearId());
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                final List<Object[]> budgetHeadList = accountBudgetProjectedRevenueEntryService
                        .getBudgetCodeInRevenue(accountReceiptDTO.getFinYearId(), accountReceiptDTO.getOrgId());
                if (budgetHeadList.isEmpty()) {
                    responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(session.getMessage("account.receipt.entry.rest.input"));
                } else {
                    accountReceiptDTO = addBudgetHeadList(budgetHeadList);
                    responseEntity = ResponseEntity.status(HttpStatus.OK).body(accountReceiptDTO);
                }
            }
        } catch (final Exception ex) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(session.getMessage("account.depositslip.response.servererror"));
            LOGGER.error("Error while Receipt Entry: " + ex.getMessage(), ex);
        }

        return responseEntity;
    }

    /**
     * consume this service by using service {@code URI=/AccountVoucher/saveReceipt }
     * 
     * @param requestDTO : dto, which {@code dataModel} field can hold {@code accountReceiptDTO} , account related data to post
     * account effect to Account module
     * @return instance of {@code ResponseEntity} ,check status code to ensure whether save success or failed.
     * @throws Exception
     * @throws ParseException
     *
     */
    @RequestMapping(value = "/saveReceipt", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<?> saveReceipt(@RequestBody final AccountReceiptDTO accountReceiptDTO)
            throws ParseException, Exception {

        LOGGER.info("Provided input for ReceiptRecordsOnSearch[" + accountReceiptDTO + "]");
        final ApplicationSession session = ApplicationSession.getInstance();
        TbServiceReceiptMasEntity responseEntity = null;
        String responseValidation = accountReceiptEntryService.validateInput(accountReceiptDTO);
        if (responseValidation.isEmpty()) {
            String fieldIdExistValidation = validateFieldId(accountReceiptDTO, accountReceiptDTO.getFieldId());
            if (fieldIdExistValidation.isEmpty()) {
                responseEntity = accountReceiptEntryService.saveReceipt(accountReceiptDTO,
                        accountReceiptDTO.getFieldId());
                ResponseEntity<?> status = Optional.ofNullable(responseEntity)
                        .map(result -> new ResponseEntity<>(session.getMessage("receipt posting done for provided details"),
                                HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
                if (!status.getStatusCode().is2xxSuccessful()) {
                    throw new IllegalArgumentException("Receipt Posting failed {" + status.getBody());
                }
            }
            LOGGER.error(session.getMessage("Receipt posting failed due to:")
                    + session.getMessage("Error while Account Reciept Form:")
                    + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseValidation));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            LOGGER.error(session.getMessage("Receipt posting failed due to:")
                    + session.getMessage("Error while Account Reciept Form:")
                    + ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseValidation));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<?> validateOrgId(final AccountReceiptDTO requestDTO) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;
        if (requestDTO == null) {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(session.getMessage("accounts.receipt.accountreceiptdto"));
        } else {
            if ((requestDTO.getOrgId() == null) || (requestDTO.getOrgId() == 0l)) {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(session.getMessage("accounts.receipt.orgid"));
            }
        }
        return (responseEntity == null
                ? ResponseEntity.status(HttpStatus.OK).body(session.getMessage("accounts.receipt.entry.valid"))
                : responseEntity);
    }

    private ResponseEntity<?> validateReceiptId(final AccountReceiptDTO accountReceiptDTO, final Long receiptId) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;

        if (receiptId == null) {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(session.getMessage("accounts.receipt.entry.receiptid"));
        } else {
            if ((accountReceiptDTO.getOrgId() == null) || (accountReceiptDTO.getOrgId() == 0l)) {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(session.getMessage("accounts.receipt.orgid"));
            }
        }
        return (responseEntity == null
                ? ResponseEntity.status(HttpStatus.OK).body(session.getMessage("accounts.receipt.entry.valid"))
                : responseEntity);
    }

    private ResponseEntity<?> validateVendorStatusId(final AccountReceiptDTO accountReceiptDTO,
            final Long vendorStatus) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;

        if (vendorStatus == null) {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(session.getMessage("accounts.receipt.entry.vendorstatus"));
        } else {
            if ((accountReceiptDTO.getOrgId() == null) || (accountReceiptDTO.getOrgId() == 0l)) {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(session.getMessage("accounts.receipt.orgid"));
            }
        }
        return (responseEntity == null
                ? ResponseEntity.status(HttpStatus.OK).body(session.getMessage("accounts.receipt.entry.valid"))
                : responseEntity);
    }

    private ResponseEntity<?> validateStatusId(final AccountReceiptDTO accountReceiptDTO, final Long statusId) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;

        if (statusId == null) {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(session.getMessage("accounts.receipt.entry.statusid"));
        } else {
            if ((accountReceiptDTO.getOrgId() == null) || (accountReceiptDTO.getOrgId() == 0l)) {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(session.getMessage("accounts.receipt.orgid"));
            }
        }
        return (responseEntity == null
                ? ResponseEntity.status(HttpStatus.OK).body(session.getMessage("accounts.receipt.entry.valid"))
                : responseEntity);
    }

    private ResponseEntity<?> validateFinYearId(final AccountReceiptDTO accountReceiptDTO, final Long finYearId) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;

        if (finYearId == null) {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(session.getMessage("accounts.receipt.entry.finyearid"));
        } else {
            if ((accountReceiptDTO.getOrgId() == null) || (accountReceiptDTO.getOrgId() == 0l)) {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(session.getMessage("accounts.receipt.orgid"));
            }
        }
        return (responseEntity == null
                ? ResponseEntity.status(HttpStatus.OK).body(session.getMessage("accounts.receipt.entry.valid"))
                : responseEntity);
    }

    private String validateFieldId(final AccountReceiptDTO requestDTO, final Long fieldId) {

        StringBuilder builder = new StringBuilder();
        final ApplicationSession session = ApplicationSession.getInstance();
        // ResponseEntity<?> responseEntity = null;
        if (requestDTO == null) {
            builder.append(session.getMessage("accounts.receipt.accountreceiptdto"));
        } else {
            if ((fieldId == null) || (fieldId == 0L)) {
                builder.append(session.getMessage("accounts.receipt.entry.fieldid"));
            }
        }
        return builder.toString();
    }

    private ResponseEntity<?> validateCollectionMode(final AccountReceiptDTO requestDTO) {
        final ApplicationSession session = ApplicationSession.getInstance();
        ResponseEntity<?> responseEntity = null;
        final Organisation org = new Organisation();
        org.setOrgid(requestDTO.getOrgId());
        boolean correctModeId = false;
        final List<LookUp> paymentModeList = CommonMasterUtility.getLookUps(AccountPrefix.PAY.name(), org);
        for (final LookUp lookUp : paymentModeList) {
            if ((lookUp.getLookUpId() == requestDTO.getReceiptModeDetail().getCpdFeemode())) {
                correctModeId = true;
            }
        }
        if (!correctModeId) {
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(session.getMessage("accounts.receipt.collection.enter.mode"));
        } else {
            boolean correctModeIdCode = false;
            for (final LookUp lookUp1 : paymentModeList) {
                if ((lookUp1.getLookUpId() == requestDTO.getReceiptModeDetail().getCpdFeemode())
                        && (lookUp1.getLookUpCode().equals(requestDTO.getReceiptModeDetail().getCpdFeemodeCode()))) {
                    correctModeIdCode = true;
                }
            }
            if (!correctModeIdCode) {
                responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(session.getMessage("accounts.receipt.collection.modeid"));
            } else {
                if (!requestDTO.getReceiptModeDetail().getCpdFeemodeCode()
                        .equalsIgnoreCase(PrefixConstants.WATERMODULEPREFIX.RT)
                        && !requestDTO.getReceiptModeDetail().getCpdFeemodeCode()
                                .equalsIgnoreCase(MainetConstants.MENU.N)
                        && !requestDTO.getReceiptModeDetail().getCpdFeemodeCode()
                                .equalsIgnoreCase(PrefixConstants.TAX_TYPE.FLAT)
                        && !requestDTO.getReceiptModeDetail().getCpdFeemodeCode()
                                .equalsIgnoreCase(MainetConstants.FileStorage.DMS)
                        && !requestDTO.getReceiptModeDetail().getCpdFeemodeCode()
                                .equalsIgnoreCase(MainetConstants.MODE_CREATE)
                        && !requestDTO.getReceiptModeDetail().getCpdFeemodeCode()
                                .equalsIgnoreCase(PrefixConstants.PaymentMode.CHEQUE)
                        && !requestDTO.getReceiptModeDetail().getCpdFeemodeCode()
                                .equalsIgnoreCase(PrefixConstants.PaymentMode.PAYORDER)
                        && !requestDTO.getReceiptModeDetail().getCpdFeemodeCode()
                                .equalsIgnoreCase(PrefixConstants.PaymentMode.BANK)) {
                    responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(session.getMessage("accounts.receipt.collection.mode"));
                } else {
                    responseEntity = ResponseEntity.status(HttpStatus.OK)
                            .body(session.getMessage("accounts.receipt.entry.valid"));
                }
            }
        }
        return responseEntity;
    }

    private AccountReceiptDTO addBankAccountList(final List<Object[]> bankAccountList) {
        final AccountReceiptDTO accountReceiptDTO = new AccountReceiptDTO();
        final Map<Long, String> bankAccountMap = new LinkedHashMap<>();
        for (final Object[] bankAc : bankAccountList) {
            if ((bankAc[0] != null) && (bankAc[1] != null) && (bankAc[2] != null) && (bankAc[3] != null)) {
                bankAccountMap.put((Long) bankAc[0],
                        bankAc[3] + MainetConstants.SEPARATOR + bankAc[1] + MainetConstants.SEPARATOR + bankAc[2]);
            }
        }
        accountReceiptDTO.setBankAcList(bankAccountMap);

        final Map<Long, String> customerBankMap = new LinkedHashMap<>();
        final List<BankMasterEntity> customerBankList = bankMasterService.getBankList();
        for (final BankMasterEntity bankMasterEntity : customerBankList) {
            if ((bankMasterEntity.getBankId() != null)
                    && ((bankMasterEntity.getBank() != null) && !bankMasterEntity.getBank().isEmpty())) {
                customerBankMap.put(bankMasterEntity.getBankId(), bankMasterEntity.getBank());
            }
        }
        accountReceiptDTO.setcustomerBankMap(customerBankMap);

        return accountReceiptDTO;
    }

    private AccountReceiptDTO addBudgetHeadList(final List<Object[]> budgetHeadList) {
        final AccountReceiptDTO accountReceiptDTO = new AccountReceiptDTO();
        final Map<Long, String> headCodeMap = new LinkedHashMap<>();
        if ((budgetHeadList != null) && !budgetHeadList.isEmpty()) {
            for (final Object[] budgetArray : budgetHeadList) {
                if ((budgetArray[0] != null) && (budgetArray[1] != null)) {
                    headCodeMap.put((Long) budgetArray[0], budgetArray[1].toString());
                }
            }
        }
        accountReceiptDTO.setBudgetHeadList(headCodeMap);
        return accountReceiptDTO;
    }

	@RequestMapping(value = "/saveExternalReceipt", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
	public ResponseEntity<?> saveExternalReceipt(@RequestBody final String inputRequest, HttpServletRequest request) {

		LOGGER.info("External receipt entry started");
		List<String> validateResponse = null;
		try {
			TbServiceReceiptMasEntity responseEntity = null;
			String decryptExternalReceiptDto = commonEncryptionAndDecryption.decryptions(inputRequest);
			ObjectMapper mapper = new ObjectMapper();
			AccountReceiptExternalDto accountExternalReceiptDTO = mapper.readValue(decryptExternalReceiptDto,
					AccountReceiptExternalDto.class);
			LOGGER.info("Input Request="+mapper.writeValueAsString(accountExternalReceiptDTO));
			StringBuilder createCheckSum = new StringBuilder();
			createCheckSum.append(accountExternalReceiptDTO.getCreatedBy());
			createCheckSum.append(MainetConstants.operator.ORR);
			createCheckSum.append(accountExternalReceiptDTO.getUlbCode());
			String internalChecksum = commonEncryptionAndDecryption.commonCheckSum(createCheckSum.toString());
			LOGGER.info("external checksum="+accountExternalReceiptDTO.getCheckSum());
			LOGGER.info("internal checksum="+internalChecksum);
			if (StringUtils.equals(internalChecksum, accountExternalReceiptDTO.getCheckSum())) {
				validateResponse = accountReceiptEntryService.validateExternalSystemDTOInput(accountExternalReceiptDTO);
				LOGGER.info("After validating request");
				if (CollectionUtils.isEmpty(validateResponse)) {
					AccountReceiptDTO internalReceiptDto = accountReceiptEntryService
							.convertExternalReceiptDtoToInternalReceiptDto(accountExternalReceiptDTO);
					LOGGER.info("After converting the  EXtDto to internal DTO");
					internalReceiptDto.setLgIpMac(Utility.getClientIpAddress(request));
					responseEntity = accountReceiptEntryService.saveExtReceipt(internalReceiptDto,
							internalReceiptDto.getFieldId(),accountExternalReceiptDTO.getReceiptCategory());
					LOGGER.info("After Saving Receipt Data");
					return ResponseEntity.ok(ReceiptResponseDto.getResponse(responseEntity.getRmRcptno().toString(),internalReceiptDto.getReceiptNumber(),
							"Receipt entry done successfully", Arrays.asList(""), HttpStatus.OK));
				}else {
					return ResponseEntity.ok(ReceiptResponseDto.getResponse("","", "inputs are wrong", validateResponse,
							HttpStatus.BAD_REQUEST));
				}
			} else {
				return ResponseEntity.ok(ReceiptResponseDto.getResponse("","", "Checksum is Wrong", validateResponse,
						HttpStatus.BAD_REQUEST));
			}
		} catch (Exception e) {
			return ResponseEntity.ok(ReceiptResponseDto.getResponse("","", "Receipt Entry Failed", validateResponse,
					HttpStatus.BAD_REQUEST));
		}
		
	}
}
