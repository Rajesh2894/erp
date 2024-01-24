package com.abm.mainet.account.ui.model;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.dto.AccountFinancialReportDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class BudgetEstimateConsolidationFormatModel extends AbstractFormModel {
    private static final long serialVersionUID = -2768470631285917724L;
    private Map<Long, String> listOfinalcialyear = null;
    private AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();

    public AccountFinancialReportDTO getAccountFinancialReportDTO() {
        return accountFinancialReportDTO;
    }

    public void setAccountFinancialReportDTO(AccountFinancialReportDTO accountFinancialReportDTO) {
        this.accountFinancialReportDTO = accountFinancialReportDTO;
    }

    public Map<Long, String> getListOfinalcialyear() {
        return listOfinalcialyear;
    }

    public void setListOfinalcialyear(Map<Long, String> listOfinalcialyear) {
        this.listOfinalcialyear = listOfinalcialyear;
    }

}
