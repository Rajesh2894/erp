
package com.abm.mainet.account.service;

import java.util.Date;

import com.abm.mainet.account.dto.AccountVoucherCommPostingMasterDto;

/**
 * @author dharmendra.chouhan
 *
 */
public interface AccountVoucherCommPostingMasterService {

    AccountVoucherCommPostingMasterDto deasEntryCommPosting(
            AccountVoucherCommPostingMasterDto accountVoucherCommPostingMasterDto);

    String findAuthVoucherNumber(String voucherTypeRvPVACvJv, Date voucherDate, Long orgId);

}
