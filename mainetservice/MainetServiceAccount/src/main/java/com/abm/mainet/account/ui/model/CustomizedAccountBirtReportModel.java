package com.abm.mainet.account.ui.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.abm.mainet.account.dto.AccountFinancialReportDTO;
import com.abm.mainet.common.ui.model.AbstractFormModel;

@Component
@Scope("session")
public class CustomizedAccountBirtReportModel extends AbstractFormModel {
	
	private static final long serialVersionUID = 1L;
	
	private String reportType;
	
	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	private AccountFinancialReportDTO accountFinancialReportDTO = new AccountFinancialReportDTO();

	public AccountFinancialReportDTO getAccountFinancialReportDTO() {
		return accountFinancialReportDTO;
	}

	public void setAccountFinancialReportDTO(AccountFinancialReportDTO accountFinancialReportDTO) {
		this.accountFinancialReportDTO = accountFinancialReportDTO;
	}

	    

}
