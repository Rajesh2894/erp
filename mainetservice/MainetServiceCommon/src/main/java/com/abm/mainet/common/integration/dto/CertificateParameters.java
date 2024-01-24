package com.abm.mainet.common.integration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CertificateParameters{
    private String panno;
    @JsonProperty("PANFullName") 
    private String pANFullName;
    @JsonProperty("FullName") 
    private String fullName;
    @JsonProperty("DOB") 
    private String dOB;
    @JsonProperty("GENDER") 
    private String gENDER;
	public String getPanno() {
		return panno;
	}
	public void setPanno(String panno) {
		this.panno = panno;
	}
	public String getpANFullName() {
		return pANFullName;
	}
	public void setpANFullName(String pANFullName) {
		this.pANFullName = pANFullName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getdOB() {
		return dOB;
	}
	public void setdOB(String dOB) {
		this.dOB = dOB;
	}
	public String getgENDER() {
		return gENDER;
	}
	public void setgENDER(String gENDER) {
		this.gENDER = gENDER;
	}
    
    
    
}
