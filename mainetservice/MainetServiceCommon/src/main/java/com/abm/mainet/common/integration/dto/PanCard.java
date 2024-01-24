package com.abm.mainet.common.integration.dto;


public class PanCard{
    private String txnId;
    private String format;
    private CertificateParameters certificateParameters;
    private ConsentArtifact consentArtifact;
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public CertificateParameters getCertificateParameters() {
		return certificateParameters;
	}
	public void setCertificateParameters(CertificateParameters certificateParameters) {
		this.certificateParameters = certificateParameters;
	}
	public ConsentArtifact getConsentArtifact() {
		return consentArtifact;
	}
	public void setConsentArtifact(ConsentArtifact consentArtifact) {
		this.consentArtifact = consentArtifact;
	}
    
    
}
