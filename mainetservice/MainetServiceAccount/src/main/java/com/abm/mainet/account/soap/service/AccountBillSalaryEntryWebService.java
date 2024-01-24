package com.abm.mainet.account.soap.service;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

import com.abm.mainet.account.rest.ui.controller.AccountVendorBillApprovalRestController;
import com.abm.mainet.account.service.AccountBillEntryService;
import com.abm.mainet.common.constant.MainetConstants;
import com.abm.mainet.common.integration.acccount.dto.VendorBillApprovalDTO;
import com.abm.mainet.common.utility.ApplicationSession;

@WebService(targetNamespace = "http://service.soap.account.mainet.abm.com/", portName = "AccountBillSalaryEntryWebServicePort", serviceName = "AccountBillSalaryEntryWebServiceService")
public class AccountBillSalaryEntryWebService {

    private static final Logger LOGGER = Logger.getLogger(AccountVendorBillApprovalRestController.class);
    @Resource
    private AccountBillEntryService billEntryService;

    @WebMethod(operationName = "saveBillApproval", action = "urn:SaveBillApproval")
    public String saveBillApproval(@RequestBody final @WebParam(name = "arg0") VendorBillApprovalDTO vendorApprovalDto) throws Exception {

        LOGGER.info("Provided input for SalaryBillRecordsOnSearch[" + vendorApprovalDto + "]");
        final ApplicationSession session = ApplicationSession.getInstance();
        VendorBillApprovalDTO approvalDto = null;
        String billSalaryValidation = billEntryService.validate(vendorApprovalDto);
        if (billSalaryValidation.isEmpty()) {
            final String validationError = billEntryService.validateInputBeforeSave(vendorApprovalDto);
            if (validationError.isEmpty()) {
                approvalDto = billEntryService.saveBillApproval(vendorApprovalDto);
                if (approvalDto.getSalaryBillExitFlag().equals(MainetConstants.Y_FLAG)) {
                    return HttpStatus.OK + "," + " Account Salary Bill Posting done successfully";
                } else {
                    return HttpStatus.BAD_REQUEST + "," + " Account Salary Bill Posting failed";
                }
            } else {
                return HttpStatus.BAD_REQUEST + "," + "Account Salary Bill Posting failed : proper data is not exist";
            }
        }
        LOGGER.error(session.getMessage("voucher posting failed due to:")
                + session.getMessage("Error while Account Reciept Form:")
                + billSalaryValidation);
        return HttpStatus.BAD_REQUEST + "," + " Account Salary Bill Posting failed : DTO data is not exist";
    }

}
