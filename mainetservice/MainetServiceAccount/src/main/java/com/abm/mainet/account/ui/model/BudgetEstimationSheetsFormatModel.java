package com.abm.mainet.account.ui.model;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.dto.AccountFinancialReportDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class BudgetEstimationSheetsFormatModel extends AbstractFormModel {
    private static final long serialVersionUID = -7777986321640215774L;
    private Map<Long, String> listOfinalcialyear = null;
    private Map<Long, String> depList=null;
    private Map<Long, String> functionList = null;
    
    private AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();

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

	public Map<Long, String> getDepList() {
		return depList;
	}
	public void setDepList(Map<Long, String> depList) {
		this.depList = depList;
	}

	public Map<Long, String> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(Map<Long, String> functionList) {
		this.functionList = functionList;
	}
    
	
	
	
}
