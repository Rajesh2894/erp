package com.abm.mainet.account.soap.service;

import java.text.ParseException;
import java.util.Optional;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.account.service.AccountReceiptEntryService;
import com.abm.mainet.common.integration.acccount.domain.TbServiceReceiptMasEntity;
import com.abm.mainet.common.integration.acccount.dto.AccountReceiptDTO;
import com.abm.mainet.common.utility.ApplicationSession;

@WebService(targetNamespace = "http://service.soap.account.mainet.abm.com/", portName = "AccountReceiptWebServicePort", serviceName = "AccountReceiptWebServiceService")
public class AccountReceiptWebService {
    private static final Logger LOGGER = Logger.getLogger(AccountReceiptWebService.class);
    @Resource
    private AccountReceiptEntryService accountReceiptEntryService;

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

    @WebMethod(operationName = "saveReceipt", action = "urn:SaveReceipt")
    public String saveReceipt(@RequestBody final @WebParam(name = "arg0") AccountReceiptDTO accountReceiptDTO)
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
                return Optional.ofNullable(responseEntity)
                        .map(result -> HttpStatus.OK + "," + " Account Receipt Posting done successfully")
                        .orElse("Account Receipt Posting failed");
            }
            LOGGER.error(session.getMessage("voucher posting failed due to:")
                    + session.getMessage("Error while Account Reciept Form:")
                    + responseValidation);
            return HttpStatus.BAD_REQUEST + "," + " Account Receipt Posting failed : filed id is not exist";
        } else {
            LOGGER.error(session.getMessage("voucher posting failed due to:")
                    + session.getMessage("Error while Account Reciept Form:")
                    + responseValidation);
            return HttpStatus.BAD_REQUEST + "," + " Account Receipt Posting failed : proper data is not exist";
        }
    }
}
