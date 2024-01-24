package com.abm.mainet.account.ui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.dto.AccountFinancialReportDTO;
import com.abm.mainet.account.dto.AccountFunctionWiseBudgetReportDto;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class SummaryOfFunctionWiseBudgetModel extends AbstractFormModel {
    private static final long serialVersionUID = 226160726258416955L;
    private Map<Long, String> listOfinalcialyear = null;
    private AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();
    private  List<AccountFunctionWiseBudgetReportDto> accountBudgetlist=new ArrayList<AccountFunctionWiseBudgetReportDto>();
    
    public Map<Long, String> getListOfinalcialyear() {
        return listOfinalcialyear;
    }

    public void setListOfinalcialyear(Map<Long, String> listOfinalcialyear) {
        this.listOfinalcialyear = listOfinalcialyear;
    }

    public AccountFinancialReportDTO getAccountFinancialReportDTO() {
        return accountFinancialReportDTO;
    }

    public void setAccountFinancialReportDTO(AccountFinancialReportDTO accountFinancialReportDTO) {
        this.accountFinancialReportDTO = accountFinancialReportDTO;
    }

	public List<AccountFunctionWiseBudgetReportDto> getAccountBudgetlist() {
		return accountBudgetlist;
	}

	public void setAccountBudgetlist(List<AccountFunctionWiseBudgetReportDto> accountBudgetlist) {
		this.accountBudgetlist = accountBudgetlist;
	}
}
