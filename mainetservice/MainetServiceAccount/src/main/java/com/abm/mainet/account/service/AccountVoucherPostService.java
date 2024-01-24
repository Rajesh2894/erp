package com.abm.mainet.account.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.abm.mainet.account.domain.AccountVoucherEntryEntity;
import com.abm.mainet.account.dto.AccountVoucherCommPostingMasterDto;
import com.abm.mainet.account.dto.VoucherReversePostDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostDTO;
import com.abm.mainet.common.integration.acccount.dto.VoucherPostExternalDTO;

/**
 * Common service need to be used if Accounting Module integration is required to that particular Module
 * @author Vivek.Kumar
 * @since 06 Feb 2017
 */
public interface AccountVoucherPostService {

    /**
     * being used for Accounting Voucher Posting(RV, PV, CV, JV) from other module where Account Module is available
     * @param dto : pass {@code VoucherPostDTO} by setting parameters
     * @throws NullPointerException : if Posting does not meet to a existing defined standard template for that posting parameters
     * @throws IllegalArgumentException : if mandatory parameters are not set properly
     */
    AccountVoucherEntryEntity voucherPosting(List<VoucherPostDTO> dto);

    String validateInput(List<VoucherPostDTO> dto);

    AccountVoucherCommPostingMasterDto deasEntryCommPosting(
            AccountVoucherCommPostingMasterDto accountVoucherCommPostingMasterDto);

    AccountVoucherEntryEntity voucherReversePosting(VoucherReversePostDTO dto)
            throws IllegalAccessException, InvocationTargetException;

    String contraVoucherPosting(VoucherPostDTO dto);

    String validateReversePostInput(VoucherReversePostDTO voucherPostDTO);

    List<String> checkVoucherPostValidateInput(List<VoucherPostDTO> voucherPostDTO);

    List<String> validateExternalSystemDTOInput(List<VoucherPostExternalDTO> voucherExternalPostDTO);

    List<VoucherPostDTO> convertExternalSystemDTOIntoVouPostDTO(List<VoucherPostExternalDTO> voucherExternalPostDTO,
            String clientIPAddress);
    
    //code added by rahul.chaubey #28583
  //  boolean exceptionHandling(VoucherPostDTO voucherPostDTO, StringBuilder message); 	

}
