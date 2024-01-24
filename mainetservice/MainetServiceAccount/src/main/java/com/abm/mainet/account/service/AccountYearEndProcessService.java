package com.abm.mainet.account.service;

import com.abm.mainet.account.dto.AccountYearEndProcessDTO;

public interface AccountYearEndProcessService {

    AccountYearEndProcessDTO processFaYearEndProcessReport(Long faYearId, Long orgId);

    AccountYearEndProcessDTO updateYearEndProcessFormData(AccountYearEndProcessDTO dto);

}
