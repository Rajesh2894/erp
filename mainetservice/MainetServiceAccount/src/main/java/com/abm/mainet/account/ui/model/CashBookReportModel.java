package com.abm.mainet.account.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.dto.AccountFinancialReportDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class CashBookReportModel extends AbstractFormModel {
    private static final long serialVersionUID = 1672249925427943495L;
    private AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();

    public AccountFinancialReportDTO getAccountFinancialReportDTO() {
        return accountFinancialReportDTO;
    }

    public void setAccountFinancialReportDTO(AccountFinancialReportDTO accountFinancialReportDTO) {
        this.accountFinancialReportDTO = accountFinancialReportDTO;
    }

}
