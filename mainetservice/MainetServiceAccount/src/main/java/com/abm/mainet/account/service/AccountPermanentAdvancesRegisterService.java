package com.abm.mainet.account.service;

import com.abm.mainet.account.dto.AdvanceEntryDTO;

public interface AccountPermanentAdvancesRegisterService {

    AdvanceEntryDTO findPermanentAdvanceLadger(String frmDate, Long currentOrgId);

}
