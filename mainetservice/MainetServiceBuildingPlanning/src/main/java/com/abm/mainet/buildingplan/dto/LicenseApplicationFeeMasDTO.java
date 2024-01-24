package com.abm.mainet.buildingplan.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.abm.mainet.buildingplan.domain.LicenseApplicationMaster;

public class LicenseApplicationFeeMasDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private Long licFeeid;
    private LicenseApplicationMaster tcpLicAppNo;
    private String taxCategory;
    private BigDecimal fees;
    private String calculation;
    private String feesStr;
    private String decision;
    private String resolutionComments;
    
	public Long getLicFeeid() {
		return licFeeid;
	}
	public void setLicFeeid(Long licFeeid) {
		this.licFeeid = licFeeid;
	}
	public LicenseApplicationMaster getTcpLicAppNo() {
		return tcpLicAppNo;
	}
	public void setTcpLicAppNo(LicenseApplicationMaster tcpLicAppNo) {
		this.tcpLicAppNo = tcpLicAppNo;
	}
	public String getTaxCategory() {
		return taxCategory;
	}
	public void setTaxCategory(String taxCategory) {
		this.taxCategory = taxCategory;
	}
	public BigDecimal getFees() {
		return fees;
	}
	public void setFees(BigDecimal fees) {
		this.fees = fees;
	}
	public String getCalculation() {
		return calculation;
	}
	public void setCalculation(String calculation) {
		this.calculation = calculation;
	}
	public String getFeesStr() {
		return feesStr;
	}
	public void setFeesStr(String feesStr) {
		this.feesStr = feesStr;
	}
	public String getDecision() {
		return decision;
	}
	public void setDecision(String decision) {
		this.decision = decision;
	}
	public String getResolutionComments() {
		return resolutionComments;
	}
	public void setResolutionComments(String resolutionComments) {
		this.resolutionComments = resolutionComments;
	}
	
}
