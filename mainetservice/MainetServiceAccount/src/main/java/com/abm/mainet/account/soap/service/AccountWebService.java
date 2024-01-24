package com.abm.mainet.account.soap.service;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.dto.VoucherReversePostDTO;
import com.abm.mainet.account.service.AccountVoucherPostService;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.utility.ApplicationSession;

@WebService(targetNamespace = "http://service.soap.account.mainet.abm.com/", portName = "AccountWebServicePort", serviceName = "AccountWebServiceService")
@Component
public class AccountWebService {

    private static final Logger LOGGER = Logger.getLogger(AccountWebService.class);

    @Resource
    private AccountVoucherPostService accountVoucherPostService;

    /**
     * consume this service by using service {@code URI=/AccountVoucher/doPosting }
     * @param requestDTO : dto, which {@code dataModel} field can hold {@code VoucherPostDTO} , account related data to post
     * account effect to Account module
     * @return instance of {@code ResponseEntity} ,check status code to ensure whether posting success or failed.
     *
     */
    @WebMethod(operationName = "doVoucherPosting", action = "urn:DoVoucherPosting")
    public String doVoucherPosting(
            @RequestBody final @WebParam(name = "arg0", targetNamespace = "http://service.soap.account.mainet.abm.com/") List<VoucherPostDTO> voucherPostDTO)
            throws Exception {

        LOGGER.info("Provided input for VoucherEntryRecordsOnSearch[" + voucherPostDTO + "]");
        ApplicationSession session = ApplicationSession.getInstance();
        String responseValidation = accountVoucherPostService.validateInput(voucherPostDTO);
        AccountVoucherEntryEntity response = null;
        if (responseValidation.isEmpty()) {
            response = accountVoucherPostService.voucherPosting(voucherPostDTO);
            return Optional.ofNullable(response)
                    .map(result -> HttpStatus.OK + "," + " Account Voucher Posting done successfully")
                    .orElse("Account Voucher Posting failed");
        } else {
            LOGGER.error(session.getMessage("account.voucher.service.posting")
                    + session.getMessage("account.voucher.posting.improper.input")
                    + responseValidation);
            return HttpStatus.BAD_REQUEST + "," + " Account Voucher Posting failed : proper data is not exist";
        }
    }

    @WebMethod(operationName = "doVoucherReversePosting", action = "urn:DoVoucherReversePosting")
    public String doVoucherReversePosting(
            @RequestBody final @WebParam(name = "arg0", targetNamespace = "http://service.soap.account.mainet.abm.com/") VoucherReversePostDTO voucherPostDTO)
            throws Exception {

        LOGGER.info("Provided input for VoucherReverseEntryRecordsOnSearch[" + voucherPostDTO + "]");
        ApplicationSession session = ApplicationSession.getInstance();
        String responseValidation = accountVoucherPostService.validateReversePostInput(voucherPostDTO);
        AccountVoucherEntryEntity response = null;
        if (responseValidation.isEmpty()) {
            response = accountVoucherPostService.voucherReversePosting(voucherPostDTO);
            return Optional.ofNullable(response)
                    .map(result -> HttpStatus.OK + "," + " Account Voucher Reverse Posting done successfully")
                    .orElse("Account Voucher Reverse Posting failed");
        } else {
            LOGGER.error(session.getMessage("account.voucher.service.posting")
                    + session.getMessage("account.voucher.posting.improper.input")
                    + responseValidation);
            return HttpStatus.BAD_REQUEST + "," + " Account Voucher Reverse Posting failed : proper data is not exist";
        }
    }

}
