package com.abm.mainet.common.integration.dto;

import java.util.ArrayList;
import java.util.Date;

public class MCACompany {

	
	    private String cin;
	    private String companyName;
	    private String companyStatus;
	    private String email;
	    private String financialAuditStatus;
	    private Date incorporationDate;
	    private String registeredAddress;
	    private String registeredContactNo;
	    private String rocCode;
	    private ArrayList<FinancialDetail> financialDetails;
	    private boolean apiStatus;
	    private String errorMsg;
	    	    
		public String getCin() {
			return cin;
		}
		public void setCin(String cin) {
			this.cin = cin;
		}
		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public String getCompanyStatus() {
			return companyStatus;
		}
		public void setCompanyStatus(String companyStatus) {
			this.companyStatus = companyStatus;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getFinancialAuditStatus() {
			return financialAuditStatus;
		}
		public void setFinancialAuditStatus(String financialAuditStatus) {
			this.financialAuditStatus = financialAuditStatus;
		}
		
		public String getRegisteredAddress() {
			return registeredAddress;
		}
		public void setRegisteredAddress(String registeredAddress) {
			this.registeredAddress = registeredAddress;
		}
		public String getRegisteredContactNo() {
			return registeredContactNo;
		}
		public void setRegisteredContactNo(String registeredContactNo) {
			this.registeredContactNo = registeredContactNo;
		}
		public String getRocCode() {
			return rocCode;
		}
		public void setRocCode(String rocCode) {
			this.rocCode = rocCode;
		}
		public ArrayList<FinancialDetail> getFinancialDetails() {
			return financialDetails;
		}
		public void setFinancialDetails(ArrayList<FinancialDetail> financialDetails) {
			this.financialDetails = financialDetails;
		}
		public Date getIncorporationDate() {
			return incorporationDate;
		}
		public void setIncorporationDate(Date incorporationDate) {
			this.incorporationDate = incorporationDate;
		}
		public boolean isApiStatus() {
			return apiStatus;
		}
		public void setApiStatus(boolean apiStatus) {
			this.apiStatus = apiStatus;
		}
		public String getErrorMsg() {
			return errorMsg;
		}
		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
	    
	    
	    
	
}
