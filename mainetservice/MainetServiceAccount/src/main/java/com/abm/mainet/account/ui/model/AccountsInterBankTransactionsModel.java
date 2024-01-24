package com.abm.mainet.account.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.dto.AccountFinancialReportDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class AccountsInterBankTransactionsModel extends AbstractFormModel {
    private static final long serialVersionUID = 4985374751035052287L;
    private AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();

    public AccountFinancialReportDTO getAccountFinancialReportDTO() {
        return accountFinancialReportDTO;
    }

    public void setAccountFinancialReportDTO(AccountFinancialReportDTO accountFinancialReportDTO) {
        this.accountFinancialReportDTO = accountFinancialReportDTO;
    }

}
