package com.abm.mainet.rts.dto;

import java.util.List;

import com.abm.mainet.common.dto.DocumentDetailsVO;

public class RtsFirstAppealDTO {
	
	private List<DocumentDetailsVO> documentList;
	private List<DocumentDetailsVO> uploaddoc;
	
	private String address;
	private String pincode;
	private String reasonForAppeal;
	private String groundForAppeal;
	private String status;
	
	
	public List<DocumentDetailsVO> getDocumentList() {
		return documentList;
	}
	public void setDocumentList(List<DocumentDetailsVO> documentList) {
		this.documentList = documentList;
	}
	public List<DocumentDetailsVO> getUploaddoc() {
		return uploaddoc;
	}
	public void setUploaddoc(List<DocumentDetailsVO> uploaddoc) {
		this.uploaddoc = uploaddoc;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getReasonForAppeal() {
		return reasonForAppeal;
	}
	public void setReasonForAppeal(String reasonForAppeal) {
		this.reasonForAppeal = reasonForAppeal;
	}
	public String getGroundForAppeal() {
		return groundForAppeal;
	}
	public void setGroundForAppeal(String groundForAppeal) {
		this.groundForAppeal = groundForAppeal;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	
	
}
