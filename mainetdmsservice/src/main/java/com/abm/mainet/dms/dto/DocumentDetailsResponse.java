package com.abm.mainet.dms.dto;

import java.util.List;

public class DocumentDetailsResponse {
	public List<DocumentDetails> getDocumentDetails() {
		return documentDetails;
	}
	public void setDocumentDetails(List<DocumentDetails> documentDetails) {
		this.documentDetails = documentDetails;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private List<DocumentDetails> documentDetails;
	private String errorMessage;
	private String status;

}
