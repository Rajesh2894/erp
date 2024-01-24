package com.abm.mainet.account.service;

import com.abm.mainet.account.dto.AdvanceEntryDTO;

//this is created for advance ledger Report 

public interface RegisterOfAdvanceService {

    AdvanceEntryDTO findAdvanceLadgerReport(String asOnDate, Long OrgId);

}
